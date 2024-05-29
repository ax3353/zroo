package com.zk.ruleengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zk
 */
public class Evaluator {

    private final Map<String, Object> context;

    public Evaluator(Map<String, Object> context) {
        this.context = context;
    }

    public Object eval(List<Object> rules) {
        String funcName = (String) rules.get(0);
        Function<Object, Object> function = FunctionFactory.getFunction(funcName);
        if (function == null) {
            throw new IllegalArgumentException("No function registered for: " + funcName);
        }

        List<Object> arguments = new ArrayList<>();
        if (function.evalArgsFirst()) {
            for (int i = 1; i < rules.size(); i++) {
                Object arg = rules.get(i);
                if (arg instanceof List) {
                    arguments.add(this.eval((List<Object>) arg));
                } else {
                    arguments.add(arg);
                }
            }
            return function.execute(this, arguments);
        } else {
            // evalArgsFirst 如果返回 false，则参数会作为原始表达式传递给函数，由函数自己决定何时评估这些参数
            return function.execute(this, rules.subList(1, rules.size()));
        }
    }

    public Map<String, Object> getContext() {
        return this.context;
    }
}