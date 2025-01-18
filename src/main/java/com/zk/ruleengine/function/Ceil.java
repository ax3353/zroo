package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 向上取整
 *
 * @author zk
 */
public class Ceil extends NumberConvert implements Function<Object, Number> {

    @Override
    public Number execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("CeilFunction requires exactly one argument.");
        }

        BigDecimal number = this.convert(evaluator, args.get(0));
        return number.setScale(0, RoundingMode.UP);
    }

    @Override
    public String name() {
        return "ceil";
    }
}