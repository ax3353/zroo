package com.zk.ruleengine.operator;

import com.alibaba.fastjson.JSONObject;

public class NumberInputOperator implements Operator {

    private final String operator;

    public NumberInputOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String operator() {
        return this.operator;
    }

    @Override
    public Object convert(JSONObject jsonObject) {
        return new Object[]{
                operator,
                jsonObject.getBigDecimal("value")
        };
    }
}