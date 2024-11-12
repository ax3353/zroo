package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 数值对象是否为空
 * @author zk
 */
public class NumberIsNull implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("NumberIsNull requires exactly one arguments.");
        }

        return args.get(0) == null;
    }

    @Override
    public String name() {
        return "numberIsNull";
    }
}