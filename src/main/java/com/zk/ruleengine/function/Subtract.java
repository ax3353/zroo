package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 减法运算，支持多个参数连减(eg: a - b - c)
 *
 * @author zk
 */
public class Subtract extends NumberConvert implements Function<Object, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Object> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("[减法函数]至少有两个参数");
        }

        // 解析第一个参数作为初始值
        BigDecimal result = convert(evaluator, args.get(0));
        boolean isInteger = result.scale() == 0;

        // 从第二个参数开始逐个减去
        for (int i = 1; i < args.size(); i++) {
            Object arg = args.get(i);
            if (arg == null) {
                throw new IllegalArgumentException("[减法函数]不支持的参数类型: null");
            }

            BigDecimal value = convert(evaluator, arg);
            result = result.subtract(value);

            if (value.scale() > 0) {
                isInteger = false;
            }
        }

        // 设置精度
        result = result.setScale(5, RoundingMode.HALF_EVEN);

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
        return "-";
    }
}