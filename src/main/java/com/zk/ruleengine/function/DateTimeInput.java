package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期时间输入(eg: "2023-01-01 01:01:01")
 *
 * @author zk
 */
public class DateTimeInput implements Function<String, java.sql.Date> {

    private static final String EXPRESS = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";
    private static final Pattern PATTERN = Pattern.compile(EXPRESS);

    @Override
    @SneakyThrows
    public java.sql.Date execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("DateInput Function requires exactly one arguments.");
        }

        // 检查格式是否是yyyy-MM-dd HH:mm:ss
        String dateStr = args.get(0);
        Matcher matcher = PATTERN.matcher(dateStr);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("输入时间必须是yyyy-MM-dd HH:mm:ss格式");
        }

        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        return new java.sql.Date(date.getTime());
    }

    @Override
    public String name() {
        return "dateTimeInput";
    }
}