package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 计算两个日期之间的天数差。
 * 参数为两个日期字符串，返回两个日期之间的天数差
 * @author zk
 */
public class DateBetweenFunction implements Function<String, Long> {

    @Override
    public Long execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("DateBetweenFunction requires exactly two arguments.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate startDate, endDate;
        try {
            startDate = LocalDate.parse(args.get(0), formatter);
            endDate = LocalDate.parse(args.get(1), formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use ISO_LOCAL_DATE format (yyyy-MM-dd).", e);
        }

        return Math.abs(ChronoUnit.DAYS.between(startDate, endDate));
    }

    @Override
    public String name() {
        return "dateBetween";
    }

    @Override
    public boolean evalArgsFirst() {
        return true;
    }
}