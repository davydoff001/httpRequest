/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.httprequest;

import java.util.ArrayList;
import javax.swing.UIManager;

/**
 *
 * @author Alexandr
 */
public class CcConfigKeyList extends ArrayList<CcConfigKey> implements ICcConfigConsumer{

    
    @Override
    public void consumeInt(String path, int value) {
        this.add(new CcConfigKeyInt(path,value));
    }

    @Override
    public void consumeString(String path, String value) {
        this.add(new CcConfigKeyString(path,value));
    }

    @Override
    public void consumeBool(String path, Boolean value) {
        this.add(new CcConfigKeyBoolean(path,value));
    }
    
}
