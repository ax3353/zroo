package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 两个字符串相同
 *
 * @author zk
 */
public class StrEqual implements Function<String, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Equals Function requires exactly 2 arguments.");
        }
        return args.get(0).equals(args.get(1));
    }

    @Override
    public String name() {
        return "strEq";
    }
}