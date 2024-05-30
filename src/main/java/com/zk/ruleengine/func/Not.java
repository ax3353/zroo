package com.zk.ruleengine.func;

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
        if (arg instanceof List) {
            Object result = evaluator.eval((List<Object>) arg);
            if (result instanceof Boolean) {
                return !(Boolean) result;
            } else {
                throw new IllegalArgumentException("Argument must evaluate to a Boolean value.");
            }
        } else if (arg != null) {
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