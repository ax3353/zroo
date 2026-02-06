package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 取模运算，支持多个参数连除(eg: a % b % c)
 *
 * @author zk
 */
public class Mod extends NumberConvert implements Function<Object, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Object> args) {
        if (args.size() < 2) {
            throw new IllegalArgumentException("[取模函数]至少有两个参数");
        }

        boolean isInteger = true;
        BigDecimal result = convert(evaluator, args.get(0));

        // 检查第一个参数是否为整数
        if (result.scale() > 0 && result.stripTrailingZeros().scale() > 0) {
            isInteger = false;
        }

        for (int i = 1; i < args.size(); i++) {
            Object arg = args.get(i);
            if (arg == null) {
                throw new IllegalArgumentException("[取模函数]不支持的参数类型: null");
            }

            BigDecimal divisor = convert(evaluator, arg);

            // 检查除数是否为整数
            if (divisor.scale() > 0 && divisor.stripTrailingZeros().scale() > 0) {
                isInteger = false;
            }

            if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException("[取模函数]除数不能为0");
            }

            result = result.remainder(divisor);
        }

        // 设置精度（与其他算术函数保持一致）
        result = result.setScale(10, RoundingMode.HALF_EVEN);

        // 根据类型返回合适的数值
        if (isInteger) {
            // 去除尾部的零
            result = result.stripTrailingZeros();

            // 如果结果确实是整数
            if (result.scale() <= 0) {
                try {
                    long longValue = result.longValueExact();

                    // 判断是否在 Integer 范围内
                    if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
                        return result.intValue();
                    } else {
                        return longValue;
                    }
                } catch (ArithmeticException e) {
                    // 如果有小数部分，返回 BigDecimal
                    return result;
                }
            }
        }

        // 返回 BigDecimal（有小数部分或非整数）
        return result.stripTrailingZeros();
    }

    @Override
    public String name() {
        return "%";
    }
}