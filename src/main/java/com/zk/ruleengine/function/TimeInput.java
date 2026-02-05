package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间输入(eg: "09:01:01")
 *
 * @author zk
 */
public class TimeInput implements Function<String, LocalTime> {

    private static final String EXPRESS = "^\\d{2}:\\d{2}:\\d{2}$";
    private static final Pattern PATTERN = Pattern.compile(EXPRESS);
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public LocalTime execute(Evaluator evaluator, List<String> args) {
        if (args == null || args.size() != 1) {
            throw new IllegalArgumentException("TimeInput Function requires exactly one argument.");
        }

        String timeStr = args.get(0);
        if (timeStr == null || timeStr.trim().isEmpty()) {
            throw new IllegalArgumentException("时间字符串不能为空");
        }

        timeStr = timeStr.trim();

        // 检查格式是否是HH:mm:ss
        Matcher matcher = PATTERN.matcher(timeStr);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("输入时间必须是HH:mm:ss格式，例如：14:30:45");
        }

        // 解析时间
        try {
            return LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            // 验证时间各部分是否合法
            String[] parts = timeStr.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            int second = Integer.parseInt(parts[2]);

            if (hour < 0 || hour > 23) {
                throw new IllegalArgumentException("小时必须在0-23之间");
            }
            if (minute < 0 || minute > 59) {
                throw new IllegalArgumentException("分钟必须在0-59之间");
            }
            if (second < 0 || second > 59) {
                throw new IllegalArgumentException("秒必须在0-59之间");
            }

            // 如果解析失败但数值有效，直接创建
            return LocalTime.of(hour, minute, second);
        }
    }

    @Override
    public String name() {
        return "timeInput";
    }
}