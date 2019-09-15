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
public abstract class CcConfigKey {
    
    public final String path;

    public CcConfigKey(String path) {
        this.path = path;
    }

    public abstract CcDataType getDataType();
          
    public abstract Object getValue();
      
}
