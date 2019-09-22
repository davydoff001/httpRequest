/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.httprequest;

import java.util.HashMap;
import java.util.Iterator;


/**
 *
 * @author Alexandr
 */
public class CcConfigKeyMap extends HashMap<String, CcConfigKey> implements ICcConfigConsumer{
    
    @Override
    public void consumeInt(String path, int value) {
        CcConfigKeyInt ccConfigKeyInt = new CcConfigKeyInt(path,value);
        this.put(path, ccConfigKeyInt);
    }

    @Override
    public void consumeString(String path, String value) {
        CcConfigKeyString ccConfigKeyString = new CcConfigKeyString(path,value);
        this.put(path, ccConfigKeyString);
    }

    @Override
    public void consumeBool(String path, Boolean value) {
        CcConfigKeyBoolean ccConfigKeyBool = new CcConfigKeyBoolean(path,value);
        this.put(path, ccConfigKeyBool);
    }
}
