package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zk
 */
public class GreaterThanFunction implements Function<Number, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Number> args) {
        if (args.size() != 2 || args.get(0) == null || args.get(1) == null) {
            throw new IllegalArgumentException("GreaterThanFunction requires exactly two arguments.");
        }

        BigDecimal first = toComparable(evaluator, args.get(0));
        BigDecimal second = toComparable(evaluator, args.get(1));
        return first.compareTo(second) > 0;
    }

    @Override
    public String name() {
        return ">";
    }

    private BigDecimal toComparable(Evaluator evaluator, Object arg) {
        if (arg instanceof List) {
            return new BigDecimal((evaluator.eval((List<Object>) arg).toString()));
        } else {
            return new BigDecimal(arg.toString());
        }
    }
}