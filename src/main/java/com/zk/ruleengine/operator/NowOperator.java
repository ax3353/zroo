package com.zk.ruleengine.operator;

import com.alibaba.fastjson.JSONObject;
import com.zk.ruleengine.utils.ExpressionGenerator;

public class NowOperator implements Operator {

    private final String operator;

    public NowOperator(String operator) {
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
                jsonObject.getString("value")
        };
    }
}