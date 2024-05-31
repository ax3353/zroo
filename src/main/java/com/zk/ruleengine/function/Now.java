package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

/**
 * 获取当当时间
 * 接收一个时间格式的参数，返回yyyy-MM-dd HH:mm:ss格式的时间字符串
 * @author zk
 */
public class Now implements Function<String, Temporal> {

    @Override
    public Temporal execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("NowFunction requires exactly one arguments.");
        }

        String format = args.get(0);
        if (format.equals("date")) {
            return LocalDate.now();
        }

        if (format.equals("datetime")) {
            return LocalDateTime.now();
        }
        throw new IllegalArgumentException("The format only supports 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm:ss'.");
    }

    @Override
    public String name() {
        return "now";
    }
}