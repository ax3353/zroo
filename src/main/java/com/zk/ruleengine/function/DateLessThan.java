package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 比较两个日期之间的大小
 * 参表为两个日期字符串，返回布尔类型
 * @author zk
 */
public class DateLessThan implements Function<String, Boolean> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Boolean execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("DateLessThanFunction requires exactly two arguments.");
        }
        try {
            Date date1 = DATE_FORMAT.parse(args.get(0));
            Date date2 = DATE_FORMAT.parse(args.get(1));
            // date1 < date2
            return date1.before(date2);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd format.", e);
        }
    }

    @Override
    public String name() {
        return "date<";
    }
}