package com.zk.ruleengine;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FunctionTest {

    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator(new HashMap<>());

        List<Double> rule = Collections.singletonList(2.0);
        Function<Double, String> operation = FunctionFactory.getFunction("toStr");
        String result = operation.execute(evaluator, rule);
        System.out.println("str Operation Result: " + result);

        List<String> rule1 = Collections.singletonList("2");
        Function<String, Integer> operation1 = FunctionFactory.getFunction("toInt");
        Integer result1 = operation1.execute(evaluator, rule1);
        System.out.println("int Operation Result: " + result1);

        List<Integer> rule2 = Arrays.asList(1, 2, 3);
        Function<Integer, Double> operation2 = FunctionFactory.getFunction("/");
        Double result2 = operation2.execute(evaluator, rule2);
        System.out.println("add Operation Result: " + result2);

        List<Boolean> rule3 = Arrays.asList(false, true);
        Function<Boolean, Boolean> operation3 = FunctionFactory.getFunction("||");
        Boolean execute = operation3.execute(evaluator, rule3);
        System.out.println(execute);

        List<Boolean> rule4 = Arrays.asList(true, false);
        Function<Boolean, Boolean> operation4 = FunctionFactory.getFunction("if");
        Boolean execute4 = operation4.execute(evaluator, rule4);
        System.out.println(execute4);
    }
}
