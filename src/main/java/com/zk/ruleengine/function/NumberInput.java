package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 数值输入(eg: 123 或 123.45)
 * @author zk
 */
public class NumberInput implements Function<Object, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Object> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("IntInputFunction requires at least one argument.");
        }

        Number number;
        Object o = args.get(0);
        if (o instanceof String) {
            try {
                number = Integer.parseInt((String) o);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("[数值输入操作]的参数类型不对");
            }
        } else if (o instanceof Number) {
            number = (Number) o;
        } else {
            throw new IllegalArgumentException("[数值输入操作]的参数类型不对");
        }
        return number;
    }

    @Override
    public String name() {
        return "numberInput";
    }
}