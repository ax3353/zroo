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
                        Arrays.asList("strEq", Arrays.asList("@value", "educationLevel"), Arrays.asList("strInput", "PhD")),
                        Arrays.asList(">=", Arrays.asList("@value", "p.age"), Arrays.asList("numberInput", 30))
                ),
                Arrays.asList("-", Arrays.asList("@value", "workExperience"), Arrays.asList("numberInput", 1)),  // 如果学历=PhD或年龄>=30
                Arrays.asList("if",
                        Arrays.asList("&&",
                                Arrays.asList("strEq", Arrays.asList("@value", "educationLevel"), Arrays.asList("strInput", "PhD")),
                                Arrays.asList("<", Arrays.asList("@value", "p.age"), Arrays.asList("numberInput", 30))
                        ),
                        Arrays.asList("/", Arrays.asList("@value", "workExperience"), Arrays.asList("toInt", "2")),  // 如果学历=PhDPhD且年龄<30
                        Arrays.asList("if",
                                Arrays.asList("&&",
                                        Arrays.asList("!", Arrays.asList("strEq", "educationLevel", Arrays.asList("strInput", "PhD"))),
                                        Arrays.asList("strEq", Arrays.asList("strInput", "hasManagementExperience"), Arrays.asList("strInput", "hasManagementExperience"))
                                ),
                                Arrays.asList("/", Arrays.asList("@value", "workExperience"), Arrays.asList("numberInput", 2)),// 如果学历=PhDPhD且有管理经验
                                Arrays.asList("print", "没有任何条件成立")  // 都不成立
                        )
                )
        );

        RuleEngine engine = RuleEngine.getInstance();
        Object exec = engine.execute(x, JSON.toJSONString(rules));
        System.out.println(exec);
    }
}
