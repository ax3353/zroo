package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.List;

/**
 * 获取当前日期
 * 接收一个时间格式的参数，返回yyyy-MM-dd格式的时间字符串
 * @author zk
 */
public class NowDate implements Function<String, Temporal> {

    @Override
    public Temporal execute(Evaluator evaluator, List<String> args) {
        return LocalDate.now();
    }

    @Override
    public String name() {
        return "nowDate";
    }
}