package com.zk.ruleengine.operator;

import com.alibaba.fastjson.JSONObject;

/**
 * 运算符
 */
public interface Operator {

    String operator();

    Object convert(JSONObject jsonObject);
}