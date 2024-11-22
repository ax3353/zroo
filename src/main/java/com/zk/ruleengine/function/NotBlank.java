package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 不为空字符串
 *
 * @author zk
 */
public class NotBlank implements Function<String, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("NotBlank Function requires exactly one arguments.");
        }

        String str = args.get(0);
        return str != null && !str.trim().isEmpty();
    }

    @Override
    public String name() {
        return "notBlank";
    }
}