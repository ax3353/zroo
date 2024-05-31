package com.zk.ruleengine;

import com.alibaba.fastjson2.JSON;

import java.util.List;
import java.util.Map;

/**
 * 规则执行器，负责加载，解析，执行规则
 * @author zk
 */
public class RuleExecutor<R> {
    private final List<Object> rules;
    private final Map<String, Object> context;

    public RuleExecutor(List<Object> rules, Map<String, Object> context) {
        this.rules = rules;
        this.validate(this.rules);
        this.context = context;
    }

    public RuleExecutor(String jsonRule, Map<String, Object> context) {
        this.rules = JSON.parseObject(jsonRule, List.class);
        this.validate(this.rules);
        this.context = context;
    }

    public R execute() {
        Evaluator evaluator = new Evaluator(context);
        return (R) evaluator.eval(this.rules);
    }

    private void validate(List<?> rules) {
        if (rules == null || rules.size() < 2) {
            throw new RuntimeException("Must have at least one argument.");
        }
    }
}