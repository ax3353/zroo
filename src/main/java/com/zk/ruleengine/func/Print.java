package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * @author zk
 */
public class Print implements Function<Object, Void> {

    @Override
    public Void execute(Evaluator evaluator, List<Object> args) {
        for (Object arg : args) {
            if (arg instanceof List) {
                System.out.print(evaluator.eval((List<Object>) arg) + " ");
            } else {
                System.out.print(arg + " ");
            }
        }
        System.out.println();
        return null;
    }

    @Override
    public String name() {
        return "print";
    }
}