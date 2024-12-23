package com.zk.ruleengine.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zk.ruleengine.OperatorPolicy;
import com.zk.ruleengine.OperatorPolicyFactory;

/**
 * 规则表达式生成器
 * 将json参数转规则表达式
 *
 * @author zk
 */
public class ExpressionGenerator {

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
            if (condition != null) { // if-elseif branch
                result = new Object[]{
                        "if",
                        toRuleExpression(condition),
                        toRuleExpression(branch.getJSONObject("then")),
                        result
                };
            } else { // else branch
                JSONObject then = branch.getJSONObject("then");
                if (then != null && then.size() > 0) {
                    result = toRuleExpression(then);
                }
            }
        }
        return result;
    }

    private static Object handleOtherOperators(JSONObject jsonObject, String operator) {
        OperatorPolicy policy = OperatorPolicyFactory.getOperator(operator);
        return policy.convert(jsonObject);
    }
}