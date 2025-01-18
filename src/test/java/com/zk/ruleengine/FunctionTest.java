package com.zk.ruleengine;

import java.math.BigDecimal;
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
        Function<String, BigDecimal> operation1 = FunctionFactory.getFunction("toNumber");
        BigDecimal result1 = operation1.execute(evaluator, rule1);
        System.out.println("int Operation Result: " + result1);

        List<Object> multiplyParam = Arrays.asList("3", 1, new BigDecimal("6"));
        Function<Object, Number> multiply = FunctionFactory.getFunction("*");
        Number multiplyResult = multiply.execute(evaluator, multiplyParam);
        System.out.println("multiply Operation Result: " + multiplyResult);

        List<Object> divideParam = Arrays.asList("18", 2, new BigDecimal("6.000"));
        Function<Object, Number> operation2 = FunctionFactory.getFunction("/");
        Number result2 = operation2.execute(evaluator, divideParam);
        System.out.println("divide Operation Result: " + result2);

        List<Object> addParam = Arrays.asList("3", 1, new BigDecimal(6));
        Function<Object, Number> add = FunctionFactory.getFunction("+");
        Number addResult = add.execute(evaluator, addParam);
        System.out.println("add Operation Result: " + addResult);

        List<Object> minusParam = Arrays.asList("9", 1, new BigDecimal(6));
        Function<Object, Number> minus = FunctionFactory.getFunction("-");
        Number minusResult = minus.execute(evaluator, minusParam);
        System.out.println("minus Operation Result: " + minusResult);

        Function<String, Object> nowFunction = FunctionFactory.getFunction("nowDate");
        Object execute5 = nowFunction.execute(evaluator, null);
        System.out.println(execute5);

        List<Object> numNotEqParam = Arrays.asList("1.00", 1.000000001);
        Function<Object, Boolean> numNotEq = FunctionFactory.getFunction("<>");
        boolean numNotEqRt = numNotEq.execute(evaluator, numNotEqParam);
        System.out.println("numNotEq Operation Result: " + numNotEqRt);

        List<Object> gtOrEqParam = Arrays.asList("1.0000000010", 1.000000001);
        Function<Object, Boolean> gtOrEq = FunctionFactory.getFunction(">=");
        boolean gtOrEqParamRt = gtOrEq.execute(evaluator, gtOrEqParam);
        System.out.println("gtOrEq Operation Result: " + gtOrEqParamRt);
    }
}
