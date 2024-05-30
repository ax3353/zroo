package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 字符串转日期
 * @author zk
 */
public class ToDate implements Function<String, Date> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("DateGreaterThanFunction requires exactly one arguments.");
        }

        String dateString = args.get(0);
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.", e);
        }
    }

    @Override
    public String name() {
        return "toDate";
    }
}
