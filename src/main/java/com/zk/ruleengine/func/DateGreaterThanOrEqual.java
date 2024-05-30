package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 计算两个日期之间的天数差
 * 参表为两个日期字符串，返回布尔类型
 * @author zk
 */
public class DateGreaterThanOrEqual implements Function<String, Boolean> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Boolean execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("DateGreaterThanOrEqualFunction requires exactly two arguments.");
        }
        try {
            Date date1 = DATE_FORMAT.parse(args.get(0));
            Date date2 = DATE_FORMAT.parse(args.get(1));
            // date1 >= date2
            return !date1.before(date2);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd format.", e);
        }
    }

    @Override
    public String name() {
        return "date>=";
    }
}