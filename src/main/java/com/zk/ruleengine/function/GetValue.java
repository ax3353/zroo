package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;
import java.util.Map;

/**
 * 从上下文中取值，支持类似el的表达式(eg: person.age)
 * @author zk
 */
public class GetValue implements Function<String, Object> {

    @Override
    public Object execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("GetValueFunction requires exactly one string argument.");
        }

        String key = args.get(0);
        Map<String, Object> context = evaluator.getContext();
        if (!context.containsKey(key)) {
            throw new IllegalArgumentException("Key '" + key + "' not found in context.");
        }
        return context.get(key);
    }

    @Override
    public String name() {
        return "@value";
    }

}