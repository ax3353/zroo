package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 比较两个数值相等
 * 参数为两个数值，返回布尔类型
 * @author zk
 */
public class NumberNotEqual implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("NumberEqualsFunction requires exactly two arguments.");
        }

        for (Object arg : args) {
            if (!(arg instanceof Number)) {
                throw new IllegalArgumentException("数字比较操作必须是数字类型");
            }
        }

        Number first = (Number) args.get(0);
        Number second = (Number) args.get(1);

        // 处理浮点数比较的精度问题
        if (first instanceof Double || first instanceof Float || second instanceof Double || second instanceof Float) {
            double diff = Math.abs(first.doubleValue() - second.doubleValue());
            double tolerance = 0.000001;
            return diff < tolerance;
        } else {
            // 对于整数，直接比较
            return first.longValue() != second.longValue();
        }
    }

    @Override
    public String name() {
        return "<>";
    }
}