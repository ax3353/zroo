package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import com.zk.ruleengine.utils.ExpressionGenerator;

public class JsonToExpressionTest {
    public static void main(String[] args) {
//        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
//        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"toDate\",\"value\":{\"type\":\"@value\",\"value\":\"alarmTime\"}},\"right\":{\"type\":\"toDate\",\"value\":{\"type\":\"strInput\",\"value\":\"2020-09-09\"}}},\"right\":{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
//        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":60}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":100}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
//        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"+\",\"left\":{\"type\":\"numberInput\",\"value\":10},\"right\":{\"type\":\"numberInput\",\"value\":20}}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"-\",\"left\":{\"type\":\"numberInput\",\"value\":100},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"right\":{\"type\":\"!\",\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"date>=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-01\"}},\"right\":{\"type\":\"date<=\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"strInput\",\"value\":\"2024-05-20\"}}}}}}";
//        String ruleJsonString = "{\"type\":\"||\",\"left\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"right\":{\"type\":\"==\",\"left\":{\"type\":\"dayBetween\",\"left\":{\"type\":\"nowDateTime\",\"value\":\"datetime\"},\"right\":{\"type\":\"@value\",\"value\":\"createTime\"}},\"right\":{\"type\":\"numberInput\",\"value\":2.0}}}";

        // if-elseif-else
//        String ruleJsonString = "{\"type\":\"conditional\",\"branches\":[{\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"then\":{\"type\":\"print\",\"value\":\"执行操作A\"}},{\"condition\":{\"type\":\"==\",\"left\":{\"type\":\"dayBetween\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"@value\",\"value\":\"createTime\"}},\"right\":{\"type\":\"numberInput\",\"value\":2.0}},\"then\":{\"type\":\"print\",\"value\":\"执行操作B\"}},{\"then\":{\"type\":\"print\",\"value\":\"没有满足任何条件时执行操作C\"}}]}";
//        String ruleJsonString = "{\"type\":\"conditional\",\"branches\":[{\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"then\":{\"type\":\"print\",\"value\":\"执行操作A\"}},{\"condition\":{\"type\":\"==\",\"left\":{\"type\":\"dayBetween\",\"left\":{\"type\":\"nowDateTime\",\"value\":\"date\"},\"right\":{\"type\":\"@value\",\"value\":\"createTime\"}},\"right\":{\"type\":\"numberInput\",\"value\":2.0}},\"then\":{\"type\":\"print\",\"value\":\"执行操作B\"}},{\"then\":{\"type\":\"print\",\"value\":\"没有满足任何条件时执行操作C\"}}]}";
//        String ruleJsonString = "{\"type\":\"conditional\",\"branches\":[{\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"then\":{\"type\":\"print\",\"value\":\"执行操作A\"}},{\"condition\":{\"type\":\"==\",\"left\":{\"type\":\"dayBetween\",\"left\":{\"type\":\"nowDateTime\",\"value\":\"date\"},\"mid\":{\"type\":\"@value\",\"value\":\"createTime\"}\"right\":{\"type\":\"strInput\",\"value\":\"timeUint\"}},\"right\":{\"type\":\"numberInput\",\"value\":2}},\"then\":{\"type\":\"print\",\"value\":\"执行操作B\"}},{\"then\":{\"type\":\"print\",\"value\":\"没有满足任何条件时执行操作C\"}}]}";
//        String ruleJsonString = "{\"type\":\"conditional\",\"branches\":[{\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"!\",\"right\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"then\":{\"type\":\"strInput\",\"value\":\"执行操作A\"}},{\"condition\":{\"type\":\"==\",\"left\":{\"type\":\"dayBetween\",\"left\":{\"type\":\"@value\",\"value\":\"alarmTime\"},\"right\":{\"type\":\"@value\",\"value\":\"createTime\"}},\"right\":{\"type\":\"numberInput\",\"value\":2.0}},\"then\":{\"type\":\"strInput\",\"value\":\"执行操作B\"}},{\"then\":{\"type\":\"strInput\",\"value\":\"没有满足任何条件时执行操作C\"}}]}";

        // if-else
//        String ruleJsonString = "{\"type\":\"conditional\",\"branches\":[{\"condition\":{\"type\":\"&&\",\"left\":{\"type\":\"eq\",\"left\":{\"type\":\"@value\",\"value\":\"deviceName\"},\"right\":{\"type\":\"strInput\",\"value\":\"河庄门店\"}},\"right\":{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":30}},\"right\":{\"type\":\"<=\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"pressure\"},\"right\":{\"type\":\"numberInput\",\"value\":5}},\"right\":{\"type\":\"numberInput\",\"value\":50}}}},\"then\":{\"type\":\"print\",\"value\":\"执行操作A\"}},{\"then\":{\"type\":\"print\",\"value\":\"没有满足任何条件时执行操作C\"}}]}";

//        String ruleJsonString = "{\"type\":\"midSub\",\"left\":{\"type\":\"strInput\",\"value\":\"abcdef\"},\"right\":{\"type\":\"numberInput\",\"value\":2}}";

//        String ruleJsonString = "{\"type\":\"floor\",\"value\":123.22}";
        String ruleJsonString = "{\"type\":\"&&\",\"left\":{\"type\":\">=\",\"left\":{\"type\":\"-\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"*\",\"left\":{\"type\":\"@value\",\"value\":\"hidden_danger_code\"},\"right\":{\"type\":\"numberInput\",\"value\":\"5\"}},\"right\":{\"type\":\"numberInput\",\"value\":\"6\"}},\"right\":{\"type\":\"numberInput\",\"value\":\"7\"}},\"right\":{\"type\":\"numberInput\",\"value\":\"8\"}},\"right\":{\"type\":\"numberInput\",\"value\":\"3\"}},\"right\":{\"type\":\"numberInput\",\"value\":\"300\"}},\"right\":{\"type\":\"date>\",\"left\":{\"type\":\"@value\",\"value\":\"report_time\"},\"right\":{\"type\":\"dateInput\",\"value\":\"2024-11-12\"}}}";

        System.out.println(ruleJsonString + "\n");
        Object ruleExpression = ExpressionGenerator.toRuleExpression(ruleJsonString);
        System.out.println(JSON.toJSONString(ruleExpression));
    }
}