package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 一个字符串包含另一个字符串
 * @author zk
 */
public class Contains implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Contains Function requires exactly 2 arguments.");
        }
        return args.get(0).toString().contains(args.get(1).toString());
    }

    @Override
    public String name() {
        return "contains";
    }
}