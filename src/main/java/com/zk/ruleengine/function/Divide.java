package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 除法运算，支持多个参数连除(eg: a / b / c)
 *
 * @author zk
 */
public class Divide implements Function<Object, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Object> args) {
        if (args.size() < 2) {
            throw new IllegalArgumentException("[除法函数]至少有两个参数");
        }

        BigDecimal result = parseToBigDecimal(args.get(0));
        boolean isInteger = true;

        for (int i = 1; i < args.size(); i++) {
            Object arg = args.get(i);
            if (arg == null) {
                throw new IllegalArgumentException("[除法函数]不支持的参数类型: null");
            }

            BigDecimal divisor = parseToBigDecimal(arg);
            if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException("[除法函数]除数不能为0");
            }

            // 设置精度
            result = result.divide(divisor, 5, RoundingMode.HALF_EVEN);
            if (result.stripTrailingZeros().scale() > 0) {
                isInteger = false;
            }
        }

        // 根据是否为整数返回合适的类型
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
        return "/";
    }

    /**
     * 将参数解析为 BigDecimal
     */
    private BigDecimal parseToBigDecimal(Object arg) {
        if (arg instanceof Integer || arg instanceof Long) {
            return BigDecimal.valueOf(((Number) arg).longValue());
        } else if (arg instanceof Double || arg instanceof Float) {
            return BigDecimal.valueOf(((Number) arg).doubleValue());
        } else if (arg instanceof BigDecimal) {
            return (BigDecimal) arg;
        } else if (arg instanceof String) {
            try {
                return new BigDecimal((String) arg);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("[除法函数]无效的数值: " + arg);
            }
        } else {
            throw new IllegalArgumentException("[除法函数]不支持的参数类型: " + arg.getClass().getName());
        }
    }
}