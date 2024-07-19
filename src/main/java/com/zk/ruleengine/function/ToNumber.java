package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.util.List;

/**
 * String转Number
 * @author zk
 */
public class ToNumber implements Function<String, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("ToNumberFunction requires exactly one arguments.");
        }

        return new BigDecimal(args.get(0));
    }

    @Override
    public String name() {
        return "toNumber";
    }
}