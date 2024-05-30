package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 数值输入(eg: 123 或 123.45)
 * @author zk
 */
public class NumberInput implements Function<Number, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Number> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("IntInputFunction requires at least one argument.");
        }
        return args.get(0);
    }

    @Override
    public String name() {
        return "numberInput";
    }
}