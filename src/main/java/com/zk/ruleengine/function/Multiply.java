package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 乘法运算，支持多个参数连乘(eg: a * b * c)
 *
 * @author zk
 */
public class Multiply extends NumberConvert implements Function<Object, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Object> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("[乘法函数]至少有两个参数");
        }

        BigDecimal result = BigDecimal.ONE;
        boolean isInteger = true;

        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("[乘法函数]不支持的参数类型: null");
            }

            BigDecimal value = convert(evaluator, arg);
            result = result.multiply(value);

            // 如果有小数部分
            if (value.scale() > 0) {
                isInteger = false;
            }
        }

        // 设置精度
        result = result.setScale(5, RoundingMode.HALF_EVEN);

        // 根据结果类型返回适当的Number类型
        if (isInteger) {
            return (result.longValueExact() <= Integer.MAX_VALUE
                    && result.longValueExact() >= Integer.MIN_VALUE)
                    ? result.intValue()
                    : result.longValue();
        } else {
            return result.stripTrailingZeros();
        }
    }

    @Override
    public String name() {
        return "*";
    }
}