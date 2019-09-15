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
public class CcConfigKeyString extends CcConfigKey{
    private String _value;

    public CcConfigKeyString(String path, String value) {
        super(path);
        _value = value;
    }

    @Override
    public Object getValue() {
        return _value;
    }

    @Override
    public CcDataType getDataType() {
        return  CcDataType.String;
    }
}
