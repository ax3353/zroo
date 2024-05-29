package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * @author zk
 */
public class ToIntFunction implements Function<String, Integer> {

    @Override
    public Integer execute(Evaluator evaluator, List<String> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("IntegerOperation requires at least one argument.");
        }

        return Integer.parseInt(String.valueOf(args.get(0)));
    }

    @Override
    public String name() {
        return "toInt";
    }
}