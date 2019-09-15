/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.httprequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
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
    
    public void getConfigKeys(String path, ICcConfigConsumer consumer) throws MalformedURLException, IOException, ParseException {

        URL url = new URL(urlString+path);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("x-api-key", XApiKey);
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
            } else {
                consumer.consumeString(opath, (String) innerObj.get("value"));
            }
        }
        
    }
    
}
