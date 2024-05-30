package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonToExpressionTest {
    public static void main(String[] args) {
//        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
//        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"toDate\",\"value\":{\"type\":\"@value\",\"value\":\"createTime\"}},\"right\":{\"type\":\"toDate\",\"value\":{\"type\":\"strInput\",\"value\":\"2020-09-09\"}}},\"right\":{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
//        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
//        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"+\",\"left\":{\"type\":\"numberInput\",\"value\":10},\"right\":{\"type\":\"numberInput\",\"value\":20}}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"-\",\"left\":{\"type\":\"numberInput\",\"value\":100},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
//        String ruleJsonString = "{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"right\":{\"type\":\"==\",\"left\":{\"type\":\"dateBetween\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"@value\",\"value\":\"createTime\"}},\"right\":{\"type\":\"numberInput\",\"value\":2.0}}}";

        // if-elseif-else
        String ruleJsonString = "{\"type\":\"conditional\",\"branches\":[{\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"branch\":{\"type\":\"print\",\"value\":\"执行操作A\"}},{\"condition\":{\"type\":\"==\",\"left\":{\"type\":\"dateBetween\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"@value\",\"value\":\"createTime\"}},\"right\":{\"type\":\"numberInput\",\"value\":2.0}},\"branch\":{\"type\":\"print\",\"value\":\"执行操作B\"}},{\"branch\":{\"type\":\"print\",\"value\":\"没有满足任何条件时执行操作C\"}}]}";

        // if-else
//        String ruleJsonString = "{\"type\":\"conditional\",\"branches\":[{\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"branch\":{\"type\":\"print\",\"value\":\"执行操作A\"}},{\"branch\":{\"type\":\"print\",\"value\":\"没有满足任何条件时执行操作C\"}}]}";

        JSONObject ruleJson = JSON.parseObject(ruleJsonString);
        Object ruleExpression = convertToJsonExpression(ruleJson);
        System.out.println(JSON.toJSONString(ruleExpression));
    }


    private static Object convertToJsonExpression(JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        if ("conditional".equals(type)) {
            return handleConditional(jsonObject);
        }
        return handleOtherTypes(jsonObject, type);
    }

    private static Object handleConditional(JSONObject jsonObject) {
        JSONArray branches = jsonObject.getJSONArray("branches");
        Object result = null;
        for (int i = branches.size() - 1; i >= 0; i--) {
            JSONObject branch = branches.getJSONObject(i);
            JSONObject condition = branch.getJSONObject("condition");
            if (condition != null) {
                result = new Object[]{
                        "if",
                        convertToJsonExpression(condition),
                        convertToJsonExpression(branch.getJSONObject("branch")),
                        result
                };
            } else { // else branch
                result = convertToJsonExpression(branch.getJSONObject("branch"));
            }
        }
        return result;
    }

    private static Object handleOtherTypes(JSONObject jsonObject, String type) {
        switch (type) {
            case "if":
                return new Object[]{
                        "if",
                        convertToJsonExpression(jsonObject.getJSONObject("condition")),
                        convertToJsonExpression(jsonObject.getJSONObject("trueBranch")),
                        convertToJsonExpression(jsonObject.getJSONObject("falseBranch"))
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
            case "dateBetween":
            case "date+":
            case "date-":
                return new Object[]{
                        type,
                        convertToJsonExpression(jsonObject.getJSONObject("left")),
                        convertToJsonExpression(jsonObject.getJSONObject("right"))
                };
            case "!":
                return new Object[]{
                        type,
                        convertToJsonExpression(jsonObject.getJSONObject("condition"))
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
                        convertToJsonExpression(jsonObject.getJSONObject("value"))};
            // 当前日期和时间
            case "now":
                return new Object[]{type};
            // 字符串处理函数
            case "leftSub":
            case "rightSub":
            case "midSub":
                return new Object[]{
                        type,
                        convertToJsonExpression(jsonObject.getJSONObject("source")),
                        jsonObject.getInteger("start"),
                        jsonObject.getInteger("length")
                };
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}