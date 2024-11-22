package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import com.zk.ruleengine.utils.ObjectFlattener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则引擎入口
 *
 * @author zk
 */
public final class RuleEngine {

    private static final RuleEngine INSTANCE = new RuleEngine();

    private RuleEngine() {
    }

    public static RuleEngine getInstance() {
        return INSTANCE;
    }

    /**
     * 注册自定义的Function
     */
    public void registerFunction(Function<?, ?> function) {
        FunctionFactory.register(function);
    }

    /**
     * 注册自定义的OperatorPolicy
     */
    public void registerOperatorPolicy(OperatorPolicy policy) {
        OperatorPolicyFactory.register(policy);
    }

    /**
     * 执行规则
     *
     * @param context        执行所需要的参数上下文
     * @param ruleExpression 规则表达式
     * @return R             执行返回结果
     */
    public <R> R execute(Object context, String ruleExpression) {
        Map<String, Object> contextMap;
        if (context == null) {
            contextMap = new HashMap<>();
        } else if (context instanceof Map) {
            contextMap = (Map<String, Object>) context;
        } else {
            contextMap = ObjectFlattener.flatMap(context);
        }

        List<Object> rules = JSON.parseObject(ruleExpression, List.class);
        Evaluator evaluator = new Evaluator(contextMap);
        return (R) evaluator.eval(rules);
    }
}
