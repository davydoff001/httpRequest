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
public class CcConfigKeyInt extends CcConfigKey{
    
    private int _value;

    public CcConfigKeyInt(String path, int value) {
        super(path);
        _value = value;
    }

    @Override
    public Integer getValue() {
        return _value;
    }

    @Override
    public CcDataType getDataType() {
        return  CcDataType.Integer;
    }

    
}
