package com.zk.ruleengine;

import com.zk.ruleengine.operator.BinaryOperator;
import com.zk.ruleengine.operator.NumberValueOperator;
import com.zk.ruleengine.operator.ObjectValueOperator;
import com.zk.ruleengine.operator.StringValueOperator;

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
                "&&", "||", "eq", "neq", "contains", "notContains", "==", "<>",
                ">=", "<=", "<", ">",
                "+", "-", "*", "/",
                "date>", "date>=", "date<", "date<=", "date==", "date<>",
                "dayBetween", "hourBetween", "minuteBetween", "secondBetween",
                "leftSub", "rightSub", "midSub"
        };
        for (String op : binaryOperators) {
            register(new BinaryOperator(op));
        }

        // 取String值运算符批量注册
        String[] stringValueOperators = {"strInput", "toStr", "blank", "notBlank",
                "nowDate", "nowDateTime", "toDate", "timeInput", "dateInput", "dateTimeInput"};
        for (String op : stringValueOperators) {
            register(new StringValueOperator(op));
        }

        // 取Object值运算符批量注册
        String[] objectValueOperators = {"@value", "null", "notNull"};
        for (String op : objectValueOperators) {
            register(new ObjectValueOperator(op));
        }

        // 取Number值运算符批量注册
        String[] numberValueOperators = {"numberInput", "toNumber", "abs", "ceil", "floor", "scale"};
        for (String op : numberValueOperators) {
            register(new NumberValueOperator(op));
        }
    }
}