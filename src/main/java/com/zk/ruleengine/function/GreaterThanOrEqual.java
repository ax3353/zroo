package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.util.List;

/**
 * 比较两个数值之间的大小
 * 参表为两个数值，返回布尔类型
 *
 * @author zk
 */
public class GreaterThanOrEqual extends NumberConvert implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("GreaterThanOrEqualFunction requires exactly two arguments.");
        }

        BigDecimal first = convert(evaluator, args.get(0));
        BigDecimal second = convert(evaluator, args.get(1));
        return first.compareTo(second) >= 0;
    }

    @Override
    public String name() {
        return ">=";
    }
}