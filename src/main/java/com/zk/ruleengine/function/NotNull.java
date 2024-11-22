package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 不为空对象
 *
 * @author zk
 */
public class NotNull implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("NotNull Function requires exactly one arguments.");
        }
        return args.get(0) != null;
    }

    @Override
    public String name() {
        return "notNull";
    }
}