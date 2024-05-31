package com.zk.ruleengine;

import com.alibaba.fastjson.JSONObject;
import com.zk.ruleengine.operator.*;

import java.util.HashMap;
import java.util.Map;

public class OperatorConverter {

    private static final Map<String, Operator> strategies = new HashMap<>();

    public static void register(Operator expressionStrategy) {
        strategies.put(expressionStrategy.operator(), expressionStrategy);
    }

    static {
        // 二元运算符批量注册
        String[] operators = {
                "&&", "||", "eq",
                ">=", "<=", "<", ">",
                "+", "-", "*", "/", "==",
                "date>=", "date<=", "dateBetween", "date+", "date-"
        };
        for (String op : operators) {
            register(new BinaryOperator(op));
        }

        register(new IfOperator());
        register(new StringValueOperator("print"));
        register(new ObjectValueOperator("@value"));
        register(new StringValueOperator("strInput"));
        register(new NumberInputOperator("numberInput"));
        register(new DateOperator("toDate"));
        register(new NowOperator("now"));
    }

    public static Object convert(String operator, JSONObject jsonObject) {
        Operator strategy = strategies.get(operator);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown operator: " + operator);
        }
        return strategy.convert(jsonObject);
    }
}