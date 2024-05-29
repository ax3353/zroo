package com.zk.ruleengine;

import com.alibaba.fastjson2.JSON;

import java.util.List;
import java.util.Map;

/**
 * @author zk
 */
public class RuleParser<R> {
    private final List<Object> rules;
    private final Map<String, Object> context;

    public RuleParser(List<Object> rules, Map<String, Object> context) {
        this.rules = rules;
        this.context = context;
    }

    public RuleParser(String jsonRule, Map<String, Object> context) {
        this.rules = JSON.parseObject(jsonRule, List.class);
        this.validate(this.rules);
        this.context = context;
    }

    public R eval() {
        Evaluator evaluator = new Evaluator(context);
        return (R) evaluator.eval(this.rules);
    }

    private void validate(List<?> rules) {
        if (rules == null || rules.size() < 2) {
            throw new RuntimeException("Must have at least one argument.");
        }
    }
}