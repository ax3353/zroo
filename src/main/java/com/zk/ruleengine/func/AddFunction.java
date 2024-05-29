package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 *
 * @author zk
 */
public class AddFunction implements Function<Number, Number> {

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