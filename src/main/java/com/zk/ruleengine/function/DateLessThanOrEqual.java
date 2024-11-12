package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * 比较两个日期之间的大小
 * 参表为两个日期字符串，返回布尔类型
 * @author zk
 */
public class DateLessThanOrEqual implements Function<LocalDate, Boolean> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Boolean execute(Evaluator evaluator, List<LocalDate> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("DateLessThanOrEqualFunction requires exactly two arguments.");
        }

        // date1 <= date2
        return args.get(0).compareTo(args.get(1)) <= 0;
    }

    @Override
    public String name() {
        return "date<=";
    }
}