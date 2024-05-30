package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 除法运算，支持多个参数连除(eg: a / b / c)
 * @author zk
 */
public class Divide implements Function<Number, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Number> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("DivideFunction requires at least one argument.");
        }

        double quotient = args.get(0).doubleValue();
        boolean allIntegers = args.get(0) instanceof Integer;

        for (int i = 1; i < args.size(); i++) {
            Number arg = args.get(i);
            if (!(arg instanceof Integer)) {
                allIntegers = false;
            }
            if (arg.doubleValue() == 0) {
                throw new ArithmeticException("Division by zero.");
            }
            quotient /= arg.doubleValue();
        }

        if (allIntegers && quotient == Math.floor(quotient)) {
            return (int) quotient;
        } else {
            return quotient;
        }
    }

    @Override
    public String name() {
        return "/";
    }

}