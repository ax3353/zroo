package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 数值输入(eg: 123 或 123.45)
 *
 * @author zk
 */
public class NumberInput extends NumberConvert implements Function<Object, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("[数值输入]操作仅支持一个参数");
        }

        return this.convert(evaluator, args.get(0));
    }

    @Override
    public String name() {
        return "numberInput";
    }
}