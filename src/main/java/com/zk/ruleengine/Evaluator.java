package com.zk.ruleengine;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表达式评估器
 * 规则表达式会被其评估，通过一个递归由内向外评估
 * @author zk
 */
@Getter
public class Evaluator {

    private final Map<String, Object> context;

    public Evaluator(Map<String, Object> context) {
        this.context = context;
    }

    public Object eval(List<Object> rules) {
        // 规则表达的第一个元素必须是函数名
        String functionName = (String) rules.get(0);
        Function<Object, Object> function = FunctionFactory.getFunction(functionName);

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

}