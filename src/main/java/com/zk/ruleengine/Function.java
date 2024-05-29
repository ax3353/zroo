package com.zk.ruleengine;

import java.util.List;

/**
 * 函数定义，一切规则皆为函数
 *
 * @author zk
 */
public interface Function<T, R> {

    /**
     * 函数执行的具体方法
     */
    R execute(Evaluator evaluator, List<T> args);

    /**
     * 函数指定的名字
     */
    String name();

    /**
     * 是否需要 Evaluator 预先评估所有参数
     */
    default boolean evalArgsFirst() {
        return true;
    }
}