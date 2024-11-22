package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 为空字符串
 *
 * @author zk
 */
public class Blank implements Function<String, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Blank Function requires exactly one arguments.");
        }

        String str = args.get(0);
        return str == null || str.trim().length() == 0;
    }

    @Override
    public String name() {
        return "blank";
    }
}