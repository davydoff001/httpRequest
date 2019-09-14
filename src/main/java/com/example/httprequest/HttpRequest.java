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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Alexandr
 */
public class HttpRequest {
    static JSONArray httpPostRequest(String urlString) throws MalformedURLException, IOException, ParseException{
        
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("x-api-key",
                "E3TH93DxgW.zl6uYQ~L~xCkhA3MKs25R");
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                urlConnection.getInputStream()));
        
        JSONParser parser = new JSONParser();
        JSONArray jsonArraySettings = (JSONArray) parser.parse(in);
        
        return jsonArraySettings;
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, MalformedURLException, ParseException {
        JSONArray response = httpPostRequest("https://ew2yseij13.execute-api.us-west-1.amazonaws.com/dev/config");
        Iterator i = response.iterator();
 
        while (i.hasNext()) {
            JSONObject innerObj = (JSONObject) i.next();
            System.out.println("path "+ innerObj.get("path") + 
                    " dataType " + innerObj.get("dataType") +
                        " value " + innerObj.get("value"));
        }
    }
}
