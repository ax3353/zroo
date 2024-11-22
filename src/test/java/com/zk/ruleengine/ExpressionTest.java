package com.zk.ruleengine;

import java.time.LocalDate;
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
        context.put("id", "12");
        context.put("hidden_danger_code", "13");
        context.put("description", "14");
        context.put("event_status", "41");
        context.put("rectification_before_image", "40");
        context.put("rectification_after_image", "40");
        context.put("created_time", LocalDate.now());
        context.put("flow_meter_code", "123");
        context.put("report_time", "2024-11-20");

        System.out.println("------------------------------0");
//        String exp = "[\"if\",[\"&&\",[\"strEq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"print\",\"执行操作A\"],[\"if\",[\"==\",[\"dayBetween\",[\"@value\",\"alarmTime\"],[\"@value\",\"createTime\"]]],[\"print\",\"执行操作B\"],[\"print\",\"没有满足任何条件时执行操作C\"]]]";
//        String exp = "[\"if\",[\"&&\",[\"strEq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"print\",\"执行操作A\"],[\"if\",[\"==\",[\"dayBetween\",[\"nowDateTime\",\"date\"],[\"@value\",\"createTime\"]]],[\"print\",\"执行操作B\"],[\"print\",\"没有满足任何条件时执行操作C\"]]]";
//        String exp = "[\"if\",[\"&&\",[\"strEq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"toStr\",\"执行操作A\"],[\"if\",[\"==\",[\"dayBetween\",[\"nowDateTime\",\"datetime\"],[\"@value\",\"createTime\"]],[\"numberInput\",48]],[\"toStr\",\"执行操作B\"],[\"toStr\",\"没有满足任何条件时执行操作C\"]]]";
        String exp = "[\"if\",[\"&&\",[\"!\",[\"strEq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\"strInput\",\"执行操作A\"],[\"if\",[\"==\",[\"dayBetween\",[\"@value\",\"alarmTime\"],[\"@value\",\"createTime\"]],[\"numberInput\",2.0]],[\"strInput\",\"执行操作B\"],[\"strInput\",\"没有满足任何条件时执行操作C\"]]]";
        System.out.println(exp);
        Object eval = engine.execute(context, exp);
        System.out.println(eval);

        System.out.println("------------------------------1");

        String exp1 = "[\"if\",[\"strEq\",[\"@value\",\"sex\"],[\"strInput\",\"男\"]],[\"numberInput\",1],[\"if\",[\"strEq\",[\"@value\",\"sex\"],[\"strInput\",\"女\"]],[\"numberInput\",2],[\"numberInput\",0]]]";
        System.out.println(exp1);
        Object eval1 = engine.execute(context, exp1);
        System.out.println(eval1);

        System.out.println("------------------------------2");

        String exp2 = "[\"midSub\",[\"strInput\",\"abcdef\"],[\"numberInput\",3]]";
        System.out.println(exp2);
        Object eval2 = engine.execute(context, exp2);
        System.out.println(eval2);

        System.out.println("------------------------------3");

        String exp3 = "[\"||\",[\"&&\",[\"strEq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"&&\",[\">=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",30]],[\"<=\",[\"*\",[\"@value\",\"pressure\"],[\"numberInput\",5]],[\"numberInput\",50]]]],[\">=\",[\"dayBetween\",[\"nowDateTime\",\"datetime\"],[\"@value\",\"createTime\"]],[\"numberInput\",2]]]";
        System.out.println(exp3);
        Boolean eval3 = engine.execute(context, exp3);
        System.out.println(eval3);

        System.out.println("------------------------------4");

        String exp4 = "[\"if\",[\"&&\",[\"||\",[\"strEq\",[\"@value\",\"id\"],[\"strInput\",\"12\"]],[\"strEq\",[\"@value\",\"description\"],[\"strInput\",\"14\"]]],[\"strNeq\",[\"@value\",\"hidden_danger_code\"],[\"strInput\",\"13\"]]],[\"toStr\",\"条件一成立\"],[\"if\",[\"&&\",[\"strNeq\",[\"@value\",\"event_status\"],[\"strInput\",\"14\"]],[\"strEq\",[\"@value\",\"rectification_before_image\"],[\"strInput\",\"41\"]]],[\"toStr\",\"条件二成立\"],[\"if\",[\"||\",[\"strNeq\",[\"@value\",\"flow_meter_code\"],[\"strInput\",\"123\"]],[\"strEq\",[\"@value\",\"rectification_after_image\"],[\"strInput\",\"421\"]]],[\"date<\",[\"@value\",\"report_time\"],[\"dateInput\",\"2024-11-15\"]],[\"strInput\",\"条件三成立\"],]]]]";
        System.out.println(exp4);
        Object eval4 = engine.execute(context, exp4);
        System.out.println(eval4);
    }
}