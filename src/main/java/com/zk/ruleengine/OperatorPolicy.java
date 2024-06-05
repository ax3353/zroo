package com.zk.ruleengine;

import com.alibaba.fastjson.JSONObject;

/**
 * 运算符策略接口, 其实现类为每一个单独的运符算的处理策略
 */
public interface OperatorPolicy {

    /**
     * 运算符
     */
    String operator();

    /**
     * json参数向表达式的转换
     * <p>
     * Examples:
     * <blockquote><pre>
     * json参数: {"type":"leftSub","left":{"type":"strInput","value":"1234567890"},"right":{"type":"numberInput","value":2}}
     * 转换后的规则表达式: ["leftSub",["strInput","1234567890"],["numberInput",2]]
     * </pre></blockquote>
     */
    Object convert(JSONObject jsonObject);
}