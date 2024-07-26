package com.zk.ruleengine;

import java.util.HashMap;
import java.util.Map;

public class ExpressionTest {

    public static void main(String[] args) {
        RuleEngine engine = RuleEngine.getInstance();

        Map<String, Object> context = new HashMap<>();
        context.put("deviceName", "河庄门店1");
        context.put("alarmTime", "2024-06-04 16:10:10");
        context.put("pressure", 7);
        context.put("createTime", "2024-07-16 13:10:10");
        context.put("phone", "18576481122");
        context.put("sex", "男");

//        String exp = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"print\",\"执行操作A\"],[\"if\",[\"==\",[\"dayBetween\",[\"@value\",\"alarmTime\"],[\"@value\",\"createTime\"]]],[\"print\",\"执行操作B\"],[\"print\",\"没有满足任何条件时执行操作C\"]]]";
//        String exp = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"print\",\"执行操作A\"],[\"if\",[\"==\",[\"dayBetween\",[\"now\",\"date\"],[\"@value\",\"createTime\"]]],[\"print\",\"执行操作B\"],[\"print\",\"没有满足任何条件时执行操作C\"]]]";
//        String exp = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"toStr\",\"执行操作A\"],[\"if\",[\"==\",[\"dayBetween\",[\"now\",\"datetime\"],[\"@value\",\"createTime\"]],[\"numberInput\",48]],[\"toStr\",\"执行操作B\"],[\"toStr\",\"没有满足任何条件时执行操作C\"]]]";
        String exp = "[\"if\",[\"&&\",[\"!\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"strInput\",\"执行操作A\"],[\"if\",[\"==\",[\"dayBetween\",[\"@value\",\"alarmTime\"],[\"@value\",\"createTime\"]],[\"numberInput\",2.0]],[\"strInput\",\"执行操作B\"],[\"strInput\",\"没有满足任何条件时执行操作C\"]]]";
        System.out.println(exp);
        Object eval = engine.execute(context, exp);
        System.out.println(eval);

        System.out.println("------------------------------");

        String exp1 = "[\"if\",[\"eq\",[\"@value\",\"sex\"],[\"strInput\",\"男\"]],[\"numberInput\",1],[\"if\",[\"eq\",[\"@value\",\"sex\"],[\"strInput\",\"女\"]],[\"numberInput\",2],[\"numberInput\",0]]]";
        System.out.println(exp1);
        Object eval1 = engine.execute(context, exp1);
        System.out.println(eval1);

        System.out.println("------------------------------");

        String exp2 = "[\"midSub\",[\"strInput\",\"abcdef\"],[\"numberInput\",3]]";
        System.out.println(exp2);
        Object eval2 = engine.execute(context, exp2);
        System.out.println(eval2);

        System.out.println("------------------------------");

        String exp3 = "[\"||\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\">=\",[\"dayBetween\",[\"now\",\"datetime\"],[\"@value\",\"createTime\"]],[\"numberInput\",2]]]";
        System.out.println(exp3);
        Boolean eval3 = engine.execute(context, exp3);
        System.out.println(eval3);

        System.out.println("------------------------------");

        String exp4 = "[\"||\",[\"||\",[\"eq\",[\"strInput\",\"abc\"],[\"strInput\",\"abcd\"]],[\">=\",[\"numberInput\",5],[\"numberInput\",30]]],[\"==\",[\"dayBetween\",[\"@value\",\"alarmTime\"],[\"@value\",\"createTime\"]],[\"numberInput\",1]]]";
        System.out.println(exp4);
        Object eval4 = engine.execute(context, exp4);
        System.out.println(eval4);
    }
}