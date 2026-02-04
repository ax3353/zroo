package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数值输入(eg: 123 或 123.45)
 *
 * @author zk
 */
public class NumberInput implements Function<Object, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("[数值输入]操作仅支持一个参数");
        }

        Object arg = args.get(0);
        return parseNumber(arg);
    }

    /**
     * 智能解析数字，返回最合适的类型
     */
    private Number parseNumber(Object arg) {
        if (arg instanceof Number) {
            return (Number) arg;
        }

        String str = arg.toString().trim();

        // 整数处理
        if (!str.contains(".") && !str.toLowerCase().contains("e")) {
            try {
                long value = Long.parseLong(str);
                // 在 Integer 范围内
                if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
                    return (int) value;
                }
                return value;
            } catch (NumberFormatException e) {
                // 超出 Long 范围，使用 BigDecimal
                return new BigDecimal(str);
            }
        }

        // 小数处理
        try {
            BigDecimal bd = new BigDecimal(str);

            // 检查是否可以用 Double 无损表示, Double 的有效精度约 15-17 位
            if (bd.precision() <= 15) {
                double d = bd.doubleValue();
                if (!Double.isInfinite(d) && !Double.isNaN(d)) {
                    // 验证没有精度损失
                    if (BigDecimal.valueOf(d).compareTo(bd) == 0) {
                        return d;
                    }
                }
            }

            return bd;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("不支持的数字格式: " + str);
        }
    }

    @Override
    public String name() {
        return "numberInput";
    }
}