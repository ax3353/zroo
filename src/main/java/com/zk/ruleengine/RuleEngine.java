package com.zk.ruleengine;

import com.zk.ruleengine.utils.ObjectFlattener;

import java.util.List;
import java.util.Map;

/**
 * 规则引擎入口
 * @author zk
 */
public final class RuleEngine<T, R> {

    /**
     * 执行规则
     * @param t 上下文对象
     * @param ruleExpression 规则表达式
     * @return R
     */
    public R execute(T t, String ruleExpression) {
        Map<String, Object> context = ObjectFlattener.flatMap(t);
        RuleExecutor<Number> parser = new RuleExecutor<>(ruleExpression, context);
        return (R) parser.execute();
    }

    /**
     * 执行规则
     * @param t 上下文对象
     * @param rules 规则表达式
     * @return R
     */
    public R execute(T t, List<Object> rules) {
        Map<String, Object> context = ObjectFlattener.flatMap(t);
        RuleExecutor<Number> parser = new RuleExecutor<>(rules, context);
        return (R) parser.execute();
    }
}
