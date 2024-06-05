package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 取有效位数
 * @author zk
 */
public class Scale implements Function<Number, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Number> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("DecimalPlaces function requires exactly two arguments.");
        }

        BigDecimal number = new BigDecimal(args.get(0).toString());
        int scale = (int) args.get(1);

        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be non-negative.");
        }

        return number.setScale(scale, RoundingMode.HALF_UP);
    }

    @Override
    public String name() {
        return "scale";
    }
}