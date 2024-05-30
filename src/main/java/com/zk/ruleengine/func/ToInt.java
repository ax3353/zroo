package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * String转Integer
 *
 * @author zk
 */
public class ToInt implements Function<String, Integer> {

    @Override
    public Integer execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("ToIntFunction requires exactly one arguments.");
        }

        return Integer.parseInt(args.get(0));
    }

    @Override
    public String name() {
        return "toInt";
    }
}