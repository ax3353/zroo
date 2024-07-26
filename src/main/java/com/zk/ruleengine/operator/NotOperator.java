package com.zk.ruleengine.operator;

import com.alibaba.fastjson.JSONObject;
import com.zk.ruleengine.OperatorPolicy;
import com.zk.ruleengine.OperatorPolicyFactory;

import java.util.ArrayList;
import java.util.List;

public class NotOperator implements OperatorPolicy {

    @Override
    public String operator() {
        return "!";
    }

    @Override
    public Object convert(JSONObject jsonObject) {
        List<Object> expression = new ArrayList<>();
        expression.add("!");

        // 获取并转换右操作数（逻辑非是一元操作符，只需要右操作数）
        Object right = jsonObject.get("right");
        if (right instanceof JSONObject) {
            // 如果右操作数是一个嵌套的表达式，递归转换
            OperatorPolicy nestedPolicy = OperatorPolicyFactory.getOperator(((JSONObject) right).getString("type"));
            expression.add(nestedPolicy.convert((JSONObject) right));
        } else {
            // 如果右操作数是一个简单值，直接添加
            expression.add(right);
        }
        return expression;
    }
}