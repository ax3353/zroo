package com.zk.ruleengine.operator;

import com.alibaba.fastjson.JSONObject;
import com.zk.ruleengine.OperatorPolicy;

public class ObjectValueOperator implements OperatorPolicy {

    private final String operator;

    public ObjectValueOperator(String operator) {
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
                jsonObject.get("value")
        };
    }
}