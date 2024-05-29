package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonToExpressionTest {
    public static void main(String[] args) {
//        String ruleJsonString = "{\"type\":\"if\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}},\"trueBranch\":{\"type\":\"print\",\"value\":\"正在报警\"},\"falseBranch\":{\"type\":\"if\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"<\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}}},\"trueBranch\":{\"type\":\"print\",\"value\":\"已自动恢复\"},\"falseBranch\":{\"type\":\"print\",\"value\":\"没有设备报警\"}}}";
//        String ruleJsonString1 = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
        String ruleJsonString2 = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"toDate\",\"value\":{\"type\":\"@value\",\"value\":\"createTime\"}},\"right\":{\"type\":\"toDate\",\"value\":{\"type\":\"strInput\",\"value\":\"2020-09-09\"}}},\"right\":{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
        System.out.println(ruleJsonString2);
        JSONObject ruleJson = JSON.parseObject(ruleJsonString2);
        Object ruleExpression = convertToJsonExpression(ruleJson);
        System.out.println(JSON.toJSONString(ruleExpression));
    }

    private static Object convertToJsonExpression(JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        switch (type) {
            case "if":
                return new Object[]{
                        "if",
                        convertToJsonExpression(jsonObject.getJSONObject("condition")),
                        convertToJsonExpression(jsonObject.getJSONObject("trueBranch")),
                        convertToJsonExpression(jsonObject.getJSONObject("falseBranch"))
                };
            case "&&":
            case "||":
            case "eq":
            case ">=":
            case "<=":
            case "<":
            case ">":
            case "date>=":
            case "date<=":
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
                return new Object[]{
                        type,
                        convertToJsonExpression(jsonObject.getJSONObject("value"))};
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}