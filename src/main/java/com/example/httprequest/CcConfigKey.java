/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.httprequest;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.path);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;       
        
        final CcConfigKey other = (CcConfigKey) obj;
       
        return this.path.equals(other.path) && this.getValue().equals(other.getValue());
    }
         
}
