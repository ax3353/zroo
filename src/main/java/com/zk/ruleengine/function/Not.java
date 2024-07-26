package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 逻辑非(!)运算
 * @author zk
 */
public class Not implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1 || args.get(0) == null) {
            throw new IllegalArgumentException("NotFunction requires exactly one argument.");
        }

        Object arg = args.get(0);

        // 如果参数是一个列表（表达式），需要先评估
        if (arg instanceof List) {
            arg = evaluator.eval((List<Object>) arg);
        }

        // 处理评估后的结果
        if (arg instanceof Boolean) {
            return !(Boolean) arg;
        } else {
            throw new IllegalArgumentException("Argument must be a Boolean value or an expression list.");
        }
    }

    @Override
    public String name() {
        return "!";
    }
}