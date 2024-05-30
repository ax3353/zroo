package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 比较两个数字是否相等。
 * 参数列表为两个数字，返回两个数字是否相等的布尔值
 * @author zk
 */
public class NumberEquals implements Function<Number, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Number> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("EqualsFunction requires exactly two arguments.");
        }

        Number first = args.get(0);
        Number second = args.get(1);

        // 处理浮点数比较的精度问题
        if (first instanceof Double || first instanceof Float || second instanceof Double || second instanceof Float) {
            double diff = Math.abs(first.doubleValue() - second.doubleValue());
            double tolerance = 0.000001;
            return diff < tolerance;
        } else {
            // 对于整数，直接比较
            return first.longValue() == second.longValue();
        }
    }

    @Override
    public String name() {
        return "==";
    }
}