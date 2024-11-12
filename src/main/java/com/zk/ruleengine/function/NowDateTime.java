package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

/**
 * 获取当前日期时间
 * 接收一个时间格式的参数，返回yyyy-MM-dd HH:mm:ss格式的时间字符串
 * @author zk
 */
public class NowDateTime implements Function<String, Temporal> {

    @Override
    public Temporal execute(Evaluator evaluator, List<String> args) {
        return LocalDateTime.now();
    }

    @Override
    public String name() {
        return "nowDateTime";
    }
}