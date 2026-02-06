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
        if (args.size() < 2) {
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
        result = result.setScale(10, RoundingMode.HALF_EVEN);

        if (isInteger) {
            // 去除尾部零，避免 .00000 的影响
            result = result.stripTrailingZeros();

            // 检查是否确实是整数（没有小数部分）
            if (result.scale() <= 0) {
                try {
                    long longValue = result.longValueExact();

                    // 判断是否在 Integer 范围内
                    if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
                        return (int) longValue;
                    } else {
                        return longValue;
                    }
                } catch (ArithmeticException e) {
                    // 如果 longValueExact 失败，返回 BigDecimal
                    return result;
                }
            }
        }

        // 返回 BigDecimal（有小数部分）
        return result.stripTrailingZeros();
    }

    @Override
    public String name() {
        return "*";
    }
}