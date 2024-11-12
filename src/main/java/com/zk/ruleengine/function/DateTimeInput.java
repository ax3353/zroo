package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 日期时间输入(eg: "2023-01-01 01:01:01")
 * @author zk
 */
public class DateTimeInput implements Function<String, LocalDateTime> {

    @Override
    public LocalDateTime execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("DateGreaterThanFunction requires exactly one arguments.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(args.get(0), formatter);
    }

    @Override
    public String name() {
        return "dateTimeInput";
    }
}