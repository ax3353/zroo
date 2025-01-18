package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 比较两个数值相等
 * 参数为两个数值，返回布尔类型
 *
 * @author zk
 */
public class NumberNotEqual extends NumberConvert implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("NumberEqualsFunction requires exactly two arguments.");
        }

        // 确定两个 BigDecimal 值的最大精度
        BigDecimal first = convert(evaluator, args.get(0));
        BigDecimal second = convert(evaluator, args.get(1));

        // 确定两个 BigDecimal 值的最大精度
        int scale = Math.max(first.scale(), second.scale());
        BigDecimal firstScaled = first.setScale(scale, RoundingMode.HALF_UP);
        BigDecimal secondScaled = second.setScale(scale, RoundingMode.HALF_UP);
        return firstScaled.compareTo(secondScaled) != 0;
    }

    @Override
    public String name() {
        return "<>";
    }
}