package com.zk.ruleengine.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 规则表达式生成器
 * 将json参数转规则表达式
 * @author zk
 */
public class ExpressionGenerator_old {

    public static Object toRuleExpression(String ruleJsonString) {
        JSONObject jsonObject = JSON.parseObject(ruleJsonString);
        return toRuleExpression(jsonObject);
    }

    public static Object toRuleExpression(JSONObject jsonObject) {
        String operator = jsonObject.getString("type");
        if ("conditional".equals(operator)) {
            return handleConditional(jsonObject);
        }
        return handleOtherOperators(jsonObject, operator);
    }

    private static Object handleConditional(JSONObject jsonObject) {
        JSONArray branches = jsonObject.getJSONArray("branches");
        Object result = null;
        // 逆序遍历
        for (int i = branches.size() - 1; i >= 0; i--) {
            JSONObject branch = branches.getJSONObject(i);
            JSONObject condition = branch.getJSONObject("condition");
            if (condition != null) {
                result = new Object[]{
                        "if",
                        toRuleExpression(condition),
                        toRuleExpression(branch.getJSONObject("then")),
                        result
                };
            } else { // else branch
                result = toRuleExpression(branch.getJSONObject("then"));
            }
        }
        return result;
    }

    private static Object handleOtherOperators(JSONObject jsonObject, String type) {
        switch (type) {
            case "if":
                return new Object[]{
                        "if",
                        toRuleExpression(jsonObject.getJSONObject("condition")),
                        toRuleExpression(jsonObject.getJSONObject("trueBranch")),
                        toRuleExpression(jsonObject.getJSONObject("falseBranch"))
                };
            // 逻辑运算
            case "&&":
            case "||":
            case "eq":
                // 数值运算
            case ">=":
            case "<=":
            case "<":
            case ">":
            case "*":
            case "+":
            case "-":
            case "/":
            case "==":
                // 日期运算
            case "date>=":
            case "date<=":
            case "dayBetween":
            case "date+":
            case "date-":
                return new Object[]{
                        type,
                        toRuleExpression(jsonObject.getJSONObject("left")),
                        toRuleExpression(jsonObject.getJSONObject("right"))
                };
            case "!":
                return new Object[]{
                        type,
                        toRuleExpression(jsonObject.getJSONObject("condition"))
                };
            case "@value":
            case "strInput":
            case "print":
                return new Object[]{
                        type,
                        jsonObject.getString("value")
                };
            case "numberInput":
                return new Object[]{
                        type,
                        jsonObject.getBigDecimal("value")
                };
            case "toDate":
            case "numberIsNull":
            case "numberIsNotNull":
            case "contains":
            case "notContains":
            case "isBlank":
            case "notBlank":
            case "abs":
            case "ceil":
            case "floor":
            case "scale":
                return new Object[]{
                        type,
                        toRuleExpression(jsonObject.getJSONObject("value"))
                };
            // 当前日期和时间
            case "now":
                return new Object[]{type};
            // 字符串处理函数
            case "leftSub":
            case "rightSub":
            case "midSub":
                return new Object[]{
                        type,
                        toRuleExpression(jsonObject.getJSONObject("source")),
                        jsonObject.getInteger("start"),
                        jsonObject.getInteger("length")
                };
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}