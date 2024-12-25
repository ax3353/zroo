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
        context.put("idx", 12);
        context.put("hidden_danger_code", "13");
        context.put("danger_code", 0);
        context.put("danger_status", 100);
        context.put("danger_name", "is_alarming");
        context.put("description", "14");
        context.put("event_status", "41");
        context.put("rectification_before_image", "40");
        context.put("rectification_after_image", "40");
        context.put("created_time", LocalDate.now());
        context.put("flow_meter_code", "123");
        context.put("report_time", "2024-11-20");
        context.put("nowDate", "2024-11-20");
        context.put("ecode", "130");
        context.put("name", "12");

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

        System.out.println("------------------------------5");

        String exp5 = "[\"&&\",[\">\",[\"+\",[\"@value\",\"id\"],50],[\"*\",[\"@value\",\"danger_code\"],20]],[\"strEq\",[\"rightSub\",[\"@value\",\"danger_name\"],8],\"alarming\"]]";
        System.out.println(exp5);
        Object eval5 = engine.execute(context, exp5);
        System.out.println(eval5);

        System.out.println("------------------------------6");

        String exp6 = "[\"if\",[\"&&\",[\">\",[\"+\",[\"@value\",\"id\"],[\"numberInput\",50]],[\"*\",[\"@value\",\"danger_code\"],[\"numberInput\",2]]],[\"strEq\",[\"leftSub\",[\"@value\",\"danger_name\"],[\"numberInput\",2]],[\"strInput\",\"alarming\"]]],[\"strInput\",\"设备正在报警中\"],[\"if\",[\"||\",[\"<=\",[\"-\",[\"@value\",\"id\"],[\"numberInput\",10]],[\"*\",[\"@value\",\"danger_code\"],[\"numberInput\",2]]],[\"==\",[\"@value\",\"danger_status\"],[\"numberInput\",0]]],[\"strInput\",\"设备已停止报警\"],[\"strInput\",\"设备状态未知\"]]]";
        System.out.println(exp6);
        Object eval6 = engine.execute(context, exp6);
        System.out.println(eval6);

        System.out.println("------------------------------7");

        String exp7 = "[\"if\",[\"&&\",[\">\",[\"+\",[\"@value\",\"id\"],[\"numberInput\",50]],[\"*\",[\"@value\",\"danger_code\"],[\"numberInput\",2]]],[\"strEq\",[\"leftSub\",[\"@value\",\"danger_name\"],[\"numberInput\",2]],[\"strInput\",\"alarming\"]]],[\">\",[\"+\",[\"@value\",\"id\"],[\"numberInput\",50]],[\"*\",[\"@value\",\"danger_code\"],[\"numberInput\",2]]],[\"if\",[\"||\",[\"<=\",[\"-\",[\"@value\",\"id\"],[\"numberInput\",10]],[\"*\",[\"@value\",\"danger_code\"],[\"numberInput\",2]]],[\"==\",[\"@value\",\"danger_status\"],[\"numberInput\",0]]],[\"strInput\",\"aaaaa\"],[\"strInput\",\"bbbbbbbbbbb\"]]]";
        System.out.println(exp7);
        Object eval7 = engine.execute(context, exp7);
        System.out.println(eval7);

        System.out.println("------------------------------8");

        String exp8 = "[\"date>\",[\"nowDate\"],[\"dateInput\",\"2023-01-01\"]]";
        System.out.println(exp8);
        Object eval8 = engine.execute(context, exp8);
        System.out.println(eval8);
        System.out.println("------------------------------9");

        String exp9 = "[\"if\",[\">\",[\"numberInput\",1],[\"numberInput\",0]],[\"if\",[\"<\",[\"numberInput\",3],[\"numberInput\",4]],[\"numberInput\",10],[\"numberInput\",20]],[\"numberInput\",30]]";
        System.out.println(exp9);
        Object eval9 = engine.execute(context, exp9);
        System.out.println(eval9);
        System.out.println("------------------------------10");

        String exp10 = "[\"if\",[\"&&\",[\"==\",[\"@value\",\"idx\"],[\"numberInput\",12]],[\"||\",[\"contains\",[\"@value\",\"ecode\"],[\"strInput\",\"13\"]],[\"notContains\",[\"@value\",\"station_code\"],[\"strInput\",\"14\"]]]],[\"strEq\",[\"@value\",\"hidden_danger_code\"],[\"strInput\",\"13\"]],[\"notContains\",[\"@value\",\"station_name\"],[\"strInput\",\"123\"]]]";
        System.out.println(exp10);
        Object eval10 = engine.execute(context, exp10);
        System.out.println(eval10);
        System.out.println("------------------------------11");

        String exp11 = "[\"&&\",[\"strEq\",[\"@value\",\"name\"],[\"strInput\",\"12\"]],[\"||\",[\"strNeq\",[\"@value\",\"id\"],[\"strInput\",\"13\"]],[\"&&\",[\"contains\",[\"@value\",\"gid\"],[\"strInput\",\"14\"]],[\"||\",[\"notContains\",[\"@value\",\"ecode\"],[\"strInput\",\"15\"]],[\"contains\",[\"@value\",\"station_code\"],[\"strInput\",\"16\"]]]]]]";
        System.out.println(exp11);
        Object eval11 = engine.execute(context, exp11);
        System.out.println(eval11);

        String exp12 = "[\"date-\",[\"nowDateTime\"],[\"numberInput\",1],[\"strInput\",\"时 \"]]";
        System.out.println(exp12);
        Object eval12 = engine.execute(context, exp12);
        System.out.println(eval12);
    }
}