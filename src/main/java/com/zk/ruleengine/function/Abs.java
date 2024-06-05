package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 取绝对值
 * @author zk
 */
public class Abs implements Function<Number, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Number> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("AbsFunction requires exactly one argument.");
        }

        Number number1 = args.get(0);
        if (number1 instanceof Integer) {
            return Math.abs((Integer) number1);
        }
        if (number1 instanceof Long) {
            return Math.abs((Long) number1);
        }
        if (number1 instanceof Double) {
            return Math.abs((Double) number1);
        }
        throw new IllegalArgumentException("AbsFunction parameter must be of type Number.");
    }

    @Override
    public String name() {
        return "abs";
    }
}