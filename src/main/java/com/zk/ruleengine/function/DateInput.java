package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 日期输入(eg: "2023-01-01")
 * @author zk
 */
public class DateInput implements Function<String, LocalDate> {

    @Override
    public LocalDate execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("DateGreaterThanFunction requires exactly one arguments.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(args.get(0), formatter);
    }

    @Override
    public String name() {
        return "dateInput";
    }
}