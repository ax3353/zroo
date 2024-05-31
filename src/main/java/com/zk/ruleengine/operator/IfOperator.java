package com.zk.ruleengine.operator;

import com.alibaba.fastjson.JSONObject;
import com.zk.ruleengine.utils.ExpressionGenerator;

public class IfOperator implements Operator {

    private static final String operator = "if";

    @Override
    public String operator() {
        return operator;
    }

    @Override
    public Object convert(JSONObject jsonObject) {
        return new Object[]{
                operator,
                ExpressionGenerator.toRuleExpression(jsonObject.getJSONObject("condition")),
                ExpressionGenerator.toRuleExpression(jsonObject.getJSONObject("trueBranch")),
                ExpressionGenerator.toRuleExpression(jsonObject.getJSONObject("falseBranch"))
        };
    }
}