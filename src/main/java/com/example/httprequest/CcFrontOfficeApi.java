/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.httprequest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Alexandr
 */
public class CcFrontOfficeApi {
    
    //"https://ew2yseij13.execute-api.us-west-1.amazonaws.com/dev/config"
    private String XApiKey;
    private String urlString;
    
    public void setXApiKey(String XApiKey) {
        this.XApiKey = XApiKey;
    }  

    public CcFrontOfficeApi(String gatewayRegion, String gatewayApiId, String envName) {
        this.urlString = String.format("https://%s.execute-api.%s.amazonaws.com/%s/config", gatewayApiId, gatewayRegion, envName);
    }  

    public CcFrontOfficeApi(String gatewayRegion, String gatewayApiId, String envName, String xApiKey) {
        this(gatewayRegion, gatewayApiId, envName);
        this.XApiKey = xApiKey;
    }  
    
    private URLConnection getUrlConnection(String path) throws MalformedURLException, IOException {
        URL url = new URL(urlString+path);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("x-api-key", XApiKey);
        return urlConnection;
    }
    
    public void getConfigKeysJackson(String path, ICcConfigConsumer consumer) throws MalformedURLException, IOException, ParseException, Exception{      
        URLConnection urlConnection = getUrlConnection(path);
                
        parseJson(urlConnection.getInputStream(), consumer);
    }
     
    public void parseJson(InputStream inputStream, ICcConfigConsumer consumer) throws IOException, Exception {
       
        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(inputStream);
        String path = null;
        String dataType = null; 
        Object value = null;
        
        JsonToken token = jParser.nextToken();

        if (token != JsonToken.START_ARRAY) {
            throw new Exception(String.format("Array start character not found!"));
        }

        while (true) {

            token = jParser.nextToken();
            
            if (token == JsonToken.END_ARRAY) break;

            if (token != JsonToken.START_OBJECT) {
                throw new Exception(String.format("Object start character not found!"));
            }

            while (true) {

                token = jParser.nextToken();
                if (token == JsonToken.END_OBJECT) {
                    break;
                }

                if (token != JsonToken.FIELD_NAME) {
                    throw new Exception(String.format("Field name start character not found!"));
                }

                String fieldname = jParser.getCurrentName();

                switch (fieldname) {
                    case "path":
                        token = jParser.nextToken();
                        if (token != JsonToken.VALUE_STRING) {
                            throw new Exception(String.format("Invalid path value '%s'", token));
                        }
                        path = jParser.getValueAsString();
                        break;
                    case "dataType":
                        token = jParser.nextToken();
                        if (token != JsonToken.VALUE_STRING) {
                            throw new Exception(String.format("Invalid dataType value '%s'", token));
                        }
                        dataType = jParser.getValueAsString();
                        break;
                    case "value":
                        token = jParser.nextToken();

                        switch (token) {
                            case VALUE_TRUE:
                                value = Boolean.TRUE;
                                break;
                            case VALUE_FALSE:
                                value = Boolean.FALSE;
                                break;
                            case VALUE_STRING:
                                value = jParser.getText();
                                break;
                            default:
                                throw new Exception(String.format("Invalid value '%s'", token));
                        }
                        break;
                    default:
                        throw new Exception(String.format("Invalid property field name '%s'", fieldname));
                }
            }

            if (path == null) {
                throw new Exception("The property path is not provided");
            }
            if (dataType == null) {
                throw new Exception("The property dataType is not provided");
            }
            if (value == null) {
                throw new Exception("The property value is not provided");
            }

            switch (dataType) {
                case "Integer":
                    consumer.consumeInt(path, Integer.parseInt((String) value));
                    break;
                case "Boolean":
                    consumer.consumeBool(path, (Boolean) value);
                    break;
                case "String":
                    consumer.consumeString(path, (String) value);
                    break;
                default:
                    throw new Exception(String.format("Invalide dataType '%s'", dataType));
            }

            path = null;
            dataType = null;
            value = null;
        }
    }
   
    public void getConfigKeys(String path, ICcConfigConsumer consumer) throws MalformedURLException, IOException, ParseException {

        URLConnection urlConnection = getUrlConnection(path);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        urlConnection.getInputStream()));

        JSONParser parser = new JSONParser();
        JSONArray jsonArraySettings = (JSONArray) parser.parse(in);
        
        Iterator i = jsonArraySettings.iterator();
 
        while (i.hasNext()) {
            JSONObject innerObj = (JSONObject) i.next();
            
            String odataType = (String) innerObj.get("dataType");
            String opath = (String) innerObj.get("path");
            
            if ("Integer".equals(odataType)) {              
                consumer.consumeInt(opath, Integer.parseInt((String) innerObj.get("value")));
            } else if ("Boolean".equals(odataType)) {
                consumer.consumeBool(opath, (Boolean) innerObj.get("value"));
            } else if ("String".equals(odataType)){
                consumer.consumeString(opath, (String) innerObj.get("value"));
            }
        }
        
    }
    
}
