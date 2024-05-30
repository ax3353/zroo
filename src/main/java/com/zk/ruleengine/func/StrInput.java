package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 字符输入(eg: "hello world")
 * @author zk
 */
public class StrInput implements Function<String, String> {

    @Override
    public String execute(Evaluator evaluator, List<String> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("StrInputFunction requires exactly one arguments.");
        }
        return args.get(0);
    }

    @Override
    public String name() {
        return "strInput";
    }
}