package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.util.List;

/**
 * 取绝对值
 * @author zk
 */
public class Abs implements Function<Number, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Number> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Abs Function requires exactly one argument.");
        }

        Number number = args.get(0);
        if (number instanceof Integer) {
            return Math.abs((Integer) number);
        }
        if (number instanceof Long) {
            return Math.abs((Long) number);
        }
        if (number instanceof Float) {
            return Math.abs((Float) number);
        }
        if (number instanceof Double) {
            return Math.abs((Double) number);
        }
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).abs();
        }
        throw new IllegalArgumentException("Abs Function parameter must be of type Number.");
    }

    @Override
    public String name() {
        return "abs";
    }
}