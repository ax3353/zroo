package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * @author zk
 */
public class ToStrFunction implements Function<Object, String> {

    @Override
    public String execute(Evaluator evaluator, List<Object> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("StringOperation requires at least one argument.");
        }
        
        return String.valueOf(args.get(0));
    }

    @Override
    public String name() {
        return "toStr";
    }
}