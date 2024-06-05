package com.zk.ruleengine;

import com.zk.ruleengine.operator.*;

import java.util.HashMap;
import java.util.Map;

public class OperatorPolicyFactory {

    private static final Map<String, OperatorPolicy> OPERATOR_MAP = new HashMap<>();

    public static void register(OperatorPolicy policy) {
        OPERATOR_MAP.put(policy.operator(), policy);
    }

    public static OperatorPolicy getOperator(String operator) {
        OperatorPolicy policy = OPERATOR_MAP.get(operator);
        if (policy == null) {
            throw new IllegalArgumentException("No function registered with name: " + operator);
        }
        return policy;
    }

    static {
        // 二元运算符批量注册
        String[] binaryOperators = {
                "&&", "||", "eq",
                ">=", "<=", "<", ">",
                "+", "-", "*", "/", "==",
                "date>=", "date<=", "date+", "date-",
                "leftSub", "rightSub", "midSub"
        };
        for (String op : binaryOperators) {
            register(new BinaryOperator(op));
        }

        // 取String值运算符批量注册
        String[] stringValueOperators = {"print", "strInput", "now", "toStr"};
        for (String op : stringValueOperators) {
            register(new StringValueOperator(op));
        }

        // 取Object值运算符批量注册
        String[] objectValueOperators = {"@value", "abs", "ceil", "floor", "scale"};
        for (String op : objectValueOperators) {
            register(new ObjectValueOperator(op));
        }

        // 取Number值运算符批量注册
        String[] numberValueOperators = {"numberInput", "toNumber"};
        for (String op : numberValueOperators) {
            register(new NumberValueOperator(op));
        }

        // 额外不通用的运算符注册
        register(new IfOperator());
        register(new BoolValueOperator("!"));
        register(new ToDateOperator("toDate"));
        register(new DateBetweenOperator("dateBetween"));
    }
}