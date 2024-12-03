package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期时间输入(eg: "2023-01-01 01:01:01")
 * @author zk
 */
public class DateTimeInput implements Function<String, LocalDateTime> {

    private static final String EXPRESS = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";
    private static final Pattern PATTERN = Pattern.compile(EXPRESS);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @SneakyThrows
    public LocalDateTime execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("DateTimeInput Function requires exactly one arguments.");
        }

        // 检查格式是否是yyyy-MM-dd HH:mm:ss
        String dateStr = args.get(0);
        Matcher matcher = PATTERN.matcher(dateStr);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("[日期时间输入]参数格式必须是yyyy-MM-dd HH:mm:ss");
        }
        return LocalDateTime.parse(dateStr, FORMATTER);
    }

    @Override
    public String name() {
        return "dateTimeInput";
    }
}