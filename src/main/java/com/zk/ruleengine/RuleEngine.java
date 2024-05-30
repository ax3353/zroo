package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import com.zk.ruleengine.utils.ObjectFlattener;

import java.util.List;
import java.util.Map;

/**
 * @author zk
 */
public final class RuleEngine<T, R> {

    public R exec(T t, String ruleExpression) {
        Map<String, Object> context = ObjectFlattener.flatMap(t);
        RuleParser<Number> parser = new RuleParser<>(ruleExpression, context);
        return (R) parser.eval();
    }

    public R exec(T t, List<Object> rules) {
        Map<String, Object> context = ObjectFlattener.flatMap(t);
        String ruleExpression = JSON.toJSONString(rules);
        RuleParser<Number> parser = new RuleParser<>(ruleExpression, context);
        return (R) parser.eval();
    }
}
