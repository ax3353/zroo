package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 加法运算，支持多个参数连除(eg: a + b + c)
 * @author zk
 */
public class Add implements Function<Number, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Number> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("AddFunction requires at least one argument.");
        }

        double sum = 0;
        boolean allIntegers = true;

        for (Number arg : args) {
            if (!(arg instanceof Integer)) {
                allIntegers = false;
            }
            sum += arg.doubleValue();
        }

        if (allIntegers) {
            return (int) sum;
        } else {
            return sum;
        }
    }

    @Override
    public String name() {
        return "+";
    }
}