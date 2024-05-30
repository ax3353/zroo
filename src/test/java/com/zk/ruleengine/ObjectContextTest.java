package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import com.zk.ruleengine.pojo.P;
import com.zk.ruleengine.pojo.X;

import java.util.Arrays;
import java.util.List;

public class ObjectContextTest {

    public static void main(String[] args) {
        P p = new P();
        p.setAge(20);

        X x = new X();
        x.setP(p);
        x.setEducationLevel("PhDa");
        x.setWorkExperience(3.0);
        x.setHasManagementExperience(true);

        List<Object> rules = Arrays.asList(
                "if",
                Arrays.asList("&&",
                        Arrays.asList("eq", Arrays.asList("@value", "educationLevel"), Arrays.asList("strInput", "PhD")),
                        Arrays.asList(">=", Arrays.asList("@value", "p.age"), Arrays.asList("numberInput", 30))
                ),
                Arrays.asList("-", Arrays.asList("@value", "workExperience"), Arrays.asList("numberInput", 1)),  // 如果学历=PhD或年龄>=30
                Arrays.asList("if",
                        Arrays.asList("&&",
                                Arrays.asList("eq", Arrays.asList("@value", "educationLevel"), Arrays.asList("strInput", "PhD")),
                                Arrays.asList("<", Arrays.asList("@value", "p.age"), Arrays.asList("numberInput", 30))
                        ),
                        Arrays.asList("/", Arrays.asList("@value", "workExperience"), Arrays.asList("toInt", "2")),  // 如果学历=PhDPhD且年龄<30
                        Arrays.asList("if",
                                Arrays.asList("&&",
                                        Arrays.asList("!", Arrays.asList("eq", "educationLevel", Arrays.asList("strInput", "PhD"))),
                                        Arrays.asList("eq", Arrays.asList("strInput", "hasManagementExperience"), Arrays.asList("strInput", "hasManagementExperience"))
                                ),
                                Arrays.asList("/", Arrays.asList("@value", "workExperience"), Arrays.asList("numberInput", 2)),// 如果学历=PhDPhD且有管理经验
                                Arrays.asList("print", "没有任何条件成立")  // 都不成立
                        )
                )
        );

        System.out.println(JSON.toJSONString(rules));
        // 正确的 ["if",["&&",["eq",["@value","educationLevel"],["strInput","PhD"]],[">=",["@value","p.age"],["numberInput",30]]],["-",["@value","workExperience"],["numberInput",1]],["if",["&&",["eq",["@value","educationLevel"],["strInput","PhD"]],["<",["@value","p.age"],["numberInput",30]]],["/",["@value","workExperience"],["toInt","2"]],["if",["&&",["!",["eq","educationLevel",["strInput","PhD"]]],["eq",["strInput","hasManagementExperience"],["strInput","hasManagementExperience"]]],["/",["@value","workExperience"],["numberInput",2]],["print","没有任何条件成立"]]]]
        // 得到的 ["if",["&&",["eq",["@value","educationLevel",["strInput","PhD"]]],[">=",["@value","p.age",["numberInput",30]]]],["-",["@value","workExperience",["numberInput",1]]],["if",["&&",["eq",["@value","educationLevel",["strInput","PhD"]]],["<",["@value","p.age",["numberInput",30]]]],["/",["@value","workExperience",["toInt",2]]],["if",["&&",["!",["eq","educationLevel",["strInput","PhD"]]],["eq",["strInput","hasManagementExperience","strInput","hasManagementExperience"]]],["/",["@value","workExperience",["numberInput",2]]],["print","没有任何条件成立"]]]]
        RuleEngine<X, Double> xRuleEngine = new RuleEngine<>();
        Double exec = xRuleEngine.exec(x, rules);
        System.out.println(exec);
    }
}
