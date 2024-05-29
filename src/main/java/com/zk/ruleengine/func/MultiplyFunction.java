package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * @author zk
 */
public class MultiplyFunction implements Function<Number, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Number> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("MultiplyFunction requires at least one argument.");
        }

        double product = 1.0;
        boolean allIntegers = true;

        for (Number arg : args) {
            if (!(arg instanceof Integer)) {
                allIntegers = false;
            }
            product *= arg.doubleValue();
        }

        if (allIntegers) {
            return (int) product;
        } else {
            return product;
        }
    }

    @Override
    public String name() {
        return "*";
    }
}