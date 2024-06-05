package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * Object.toString，复杂对象需要实现toString方法
 * @author zk
 */
public class ToStr implements Function<Object, String> {

    @Override
    public String execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("ToStrFunction takes only one argument.");
        }

        return args.get(0).toString();
    }

    @Override
    public String name() {
        return "toStr";
    }
}