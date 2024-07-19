package com.zk.ruleengine.operator;

import com.alibaba.fastjson.JSONObject;
import com.zk.ruleengine.OperatorPolicy;
import com.zk.ruleengine.utils.ExpressionGenerator;

public class BoolValueOperator implements OperatorPolicy {

    private final String operator;

    public BoolValueOperator(String operator) {
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
                ExpressionGenerator.toRuleExpression(jsonObject.getJSONObject("condition"))
        };
    }
}