package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
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

        BigDecimal result = convert(evaluator, args.get(0));
        for (int i = 1; i < args.size(); i++) {
            Object arg = args.get(i);
            if (arg == null) {
                throw new IllegalArgumentException("[取模函数]不支持的参数类型: null");
            }
            BigDecimal divisor = convert(evaluator, arg);
            if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException("[取模函数]除数不能为0");
            }
            result = result.remainder(divisor);
        }
        return result;
    }

    @Override
    public String name() {
        return "%";
    }
}