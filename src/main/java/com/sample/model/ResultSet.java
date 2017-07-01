package com.sample.model;

import java.util.List;

/**
 * Created by changer on 2017/2/10.
 * email: dingjc@mchz.com.cn
 */
public class ResultSet {
    private String error;
    private List<String> meta;
    private List<List<String>> fields;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<List<String>> getFields() {
        return fields;
    }

    public void setFields(List<List<String>> fields) {
        this.fields = fields;
    }

    public List<String> getMeta() {
        return meta;
    }

    public void setMeta(List<String> meta) {
        this.meta = meta;
    }
}
