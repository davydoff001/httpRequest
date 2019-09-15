/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.httprequest;

/**
 *
 * @author Alexandr
 */
public interface ICcConfigConsumer {
    
    void consumeInt(String path, int value);
    
    void consumeString(String path, String value);
    
    void consumeBool(String path, Boolean value);
    
}
