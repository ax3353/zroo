package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * @author zk
 */
public class Equals implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("EqualsOperation requires exactly 2 arguments.");
        }
        return args.get(0).equals(args.get(1));
    }

    @Override
    public String name() {
        return "eq";
    }
}