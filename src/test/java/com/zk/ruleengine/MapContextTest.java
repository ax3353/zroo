package com.zk.ruleengine;

import com.alibaba.fastjson2.JSON;

import java.util.*;

public class MapContextTest {

    public static void main(String[] args) {
        Map<String, Object> context = new HashMap<>();
        context.put("education_level", "PhDa");
        context.put("age", 35);
        context.put("work_experience", 5);
        context.put("has_management_experience", "false");

//        java代码规则描述：
//        if (context.get("education_level").equals("PhD") && (Integer) context.get("age") >= 30) {
//            System.out.println("-----------------1");
//        } else if (context.get("education_level").equals("PhD") && (Integer) context.get("age") < 30) {
//            System.out.println("-----------------2");
//        } else if (!context.get("education_level").equals("PhD") && context.get("has_management_experience").equals("true")) {
//            System.out.println("-----------------3");
//        } else {
//            System.out.println("-----------------4");
//        }

//        lisp风格的写法：
//        ["if",["||",["eq","education_level","PhD"],[">=","age",30]],["-","work_experience",1],["if",["&&",["eq","education_level","PhD"],["<","age",30]],["/","work_experience",["toInt","2"]],["if",["&&",["!",["eq","education_level","PhD"]],["eq","has_management_experience","true"]],["/","work_experience",10,["+",11,10]],["print","没有任何条件成立"]]]]
        List<Object> rules = Arrays.asList(
                "if",
                Arrays.asList("&&",
                        Arrays.asList("eq", Arrays.asList("@value", "education_level"), "PhD"),
                        Arrays.asList(">=", Arrays.asList("@value", "age"), 30)
                ),
                Arrays.asList("-", Arrays.asList("@value", "work_experience"), 1),  // 如果学历=PhD或年龄>=30
                Arrays.asList("if",
                        Arrays.asList("||",
                                Arrays.asList("eq", Arrays.asList("@value", "education_level"), "PhD"),
                                Arrays.asList("<", Arrays.asList("@value", "age"), 30)
                        ),
                        Arrays.asList("/", Arrays.asList("@value", "work_experience"), Arrays.asList("toInt", "2")),  // 如果学历=PhDPhD且年龄<30
                        Arrays.asList("if",
                                Arrays.asList("&&",
                                        Arrays.asList("!", Arrays.asList("eq", Arrays.asList("@value", "education_level"), "PhD")),
                                        Arrays.asList("eq", "has_management_experience", "has_management_experience")
                                ),
                                Arrays.asList("/", Arrays.asList("@value", "work_experience"), 2, Arrays.asList("+", 0.5, Arrays.asList("toInt", "2"))),// 如果学历=PhDPhD且有管理经验
                                Arrays.asList("print", "没有任何条件成立")  // 都不成立
                        )
                )
        );

        String ruleJsonStr = JSON.toJSONString(rules);
        System.out.println(ruleJsonStr);

        RuleParser<Number> parser = new RuleParser<>(rules, context);
        Number eval1 = parser.eval();
        System.out.println("Arrays Evaluation Result: " + eval1);

        RuleParser<Number> parser1 = new RuleParser<>(ruleJsonStr, context);
        Number eval2 = parser1.eval();
        System.out.println("RuleJsonStr Evaluation Result: " + eval2);
    }
}