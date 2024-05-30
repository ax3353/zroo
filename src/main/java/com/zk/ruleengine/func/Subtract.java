package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 减法运算，支持多个参数连减(eg: a - b - c)
 * @author zk
 */
public class Subtract implements Function<Number, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Number> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("SubtractFunction requires at least one argument.");
        }

        double result = args.get(0).doubleValue();
        boolean allIntegers = args.get(0) instanceof Integer;

        for (int i = 1; i < args.size(); i++) {
            Number arg = args.get(i);
            if (!(arg instanceof Integer)) {
                allIntegers = false;
            }
            result -= arg.doubleValue();
        }

        if (allIntegers) {
            return (int) result;
        } else {
            return result;
        }
    }

    @Override
    public String name() {
        return "-";
    }
}