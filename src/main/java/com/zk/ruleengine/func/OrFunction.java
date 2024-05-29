package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * @author zk
 */
public class OrFunction implements Function<Boolean, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Boolean> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("OrOperation requires at least one argument.");
        }

        for (Object arg : args) {
            Boolean result;
            if (arg instanceof List) {
                // 如果arg是List类型，假设它是一个需要求值的表达式
                result = (Boolean) evaluator.eval((List<Object>) arg);
            } else if (arg instanceof Boolean) {
                // 如果arg已经是Boolean类型，直接使用该值
                result = (Boolean) arg;
            } else {
                // 如果arg既不是List也不是Boolean，抛出异常
                throw new IllegalArgumentException("Argument must be a Boolean or a Boolean expression.");
            }

            if (result) {
                // 短路逻辑：一旦发现真值，立即返回 true
                return true;
            }
        }
        return false;
    }

    @Override
    public String name() {
        return "||";
    }

    @Override
    public boolean evalArgsFirst() {
        // 不预先评估所有参数
        return false;
    }
}