package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * @author zk
 */
public class StrInputFunction implements Function<String, String> {

    @Override
    public String execute(Evaluator evaluator, List<String> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("StrInputFunction requires at least one argument.");
        }
        return args.get(0);
    }

    @Override
    public String name() {
        return "strInput";
    }
}