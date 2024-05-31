package com.zk.ruleengine;

import java.util.HashMap;
import java.util.Map;

public class ExpressionTest {

    public static void main(String[] args) {
        Map<String, Object> context = new HashMap<>();
        context.put("deviceName", "河庄门店1");
        context.put("pressure", 6);
        context.put("alarmTime", "2024-07-02");
        context.put("createTime", "2024-07-04");

//        String exp = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"print\",\"执行操作A\"],[\"if\",[\"==\",[\"dateBetween\",[\"@value\",\"alarmTime\"],[\"@value\",\"createTime\"]],[\"numberInput\",2.0]],[\"print\",\"执行操作B\"],[\"print\",\"没有满足任何条件时执行操作C\"]]]";
        String exp = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"print\",\"执行操作A\"],[\"if\",[\"==\",[\"dateBetween\",[\"now\",\"date\"],[\"@value\",\"createTime\"]],[\"numberInput\",2.0]],[\"print\",\"执行操作B\"],[\"print\",\"没有满足任何条件时执行操作C\"]]]";

        System.out.println(exp);
        RuleExecutor<Object> parser = new RuleExecutor<>(exp, context);
        Object eval = parser.execute();
        System.out.println(eval);
    }
}