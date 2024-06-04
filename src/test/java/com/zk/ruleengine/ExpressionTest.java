package com.zk.ruleengine;

import java.util.HashMap;
import java.util.Map;

public class ExpressionTest {

    public static void main(String[] args) {
        Map<String, Object> context = new HashMap<>();
        context.put("deviceName", "河庄门店1");
        context.put("pressure", 6);
        context.put("alarmTime", "2024-07-02");
        context.put("createTime", "2024-06-05 16:10:10");

//        String exp = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"print\",\"执行操作A\"],[\"if\",[\"==\",[\"dateBetween\",[\"@value\",\"alarmTime\"],[\"@value\",\"createTime\"]],[\"numberInput\",2.0]],[\"print\",\"执行操作B\"],[\"print\",\"没有满足任何条件时执行操作C\"]]]";
//        String exp = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"print\",\"执行操作A\"],[\"if\",[\"==\",[\"dateBetween\",[\"now\",\"date\"],[\"@value\",\"createTime\"]],[\"numberInput\",2.0]],[\"print\",\"执行操作B\"],[\"print\",\"没有满足任何条件时执行操作C\"]]]";
        String exp = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"toStr\",\"执行操作A\"],[\"if\",[\"==\",[\"dateBetween\",[\"now\",\"datetime\"],[\"@value\",\"createTime\"],[\"strInput\",\"hour\"]],[\"numberInput\",48]],[\"toStr\",\"执行操作B\"],[\"toStr\",\"没有满足任何条件时执行操作C\"]]]";
        System.out.println(exp);

        RuleExecutor<Object> parser = new RuleExecutor<>(exp, context);
        Object eval = parser.execute();
        System.out.println(eval);

        System.out.println("------------------------------");

        Map<String, Object> context1 = new HashMap<>();
        context1.put("phone", "18576481122");
        context1.put("sex", "男");

        String exp1 = "[\"if\",[\"eq\",[\"@value\",\"sex\"],[\"strInput\",\"男\"]],[\"numberInput\",1],[\"if\",[\"eq\",[\"@value\",\"sex\"],[\"strInput\",\"女\"]],[\"numberInput\",2],[\"numberInput\",0]]]";
        System.out.println(exp1);

        RuleExecutor<Object> parser1 = new RuleExecutor<>(exp1, context1);
        Object eval1 = parser1.execute();
        System.out.println(eval1);

        String exp2 = "[\"rightSub\", [\"@value\", \"phone\"], 4]";
        System.out.println(exp2);

        RuleExecutor<Object> parser2 = new RuleExecutor<>(exp2, context1);
        Object eval2 = parser2.execute();
        System.out.println(eval2);
    }
}