package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.List;

/**
 * 计算两个日期之间的天数差。
 * 参数为两个日期或日期时间字符串或LocalDateTime对象，返回两个日期之间的天数差
 * @author zk
 */
public class DateBetween implements Function<Object, BigDecimal> {

    @Override
    public BigDecimal execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException("DateBetweenFunction requires exactly three arguments.");
        }

        // 获取传入的两个日期时间和时间单位
        Object arg0 = args.get(0);
        Object arg1 = args.get(1);
        String timeUnit = args.get(2).toString();

        // 确保时间参数是 LocalDateTime 类型
        Temporal localDateTime1 = ensureLocalDateTime(arg0);
        Temporal localDateTime2 = ensureLocalDateTime(arg1);

        // 计算两个 LocalDateTime 之间的差值
        Duration duration = Duration.between(localDateTime1, localDateTime2);

        // 根据时间单位进行转换
        switch (timeUnit.toLowerCase()) {
            case "day":
                return BigDecimal.valueOf(Math.abs(duration.toDays()));
            case "hour":
                return BigDecimal.valueOf(Math.abs(duration.toHours()));
            case "minute":
                return BigDecimal.valueOf(Math.abs(duration.toMinutes()));
            case "second":
                return BigDecimal.valueOf(Math.abs(duration.getSeconds()));
            default:
                throw new IllegalArgumentException("Unknown time unit");
        }
    }

    private Temporal ensureLocalDateTime(Object dateTime) {
        if (dateTime instanceof LocalDateTime) {
            return (LocalDateTime) dateTime;
        } else if (dateTime instanceof String) {
            return stringToLocalDateTime((String) dateTime);
        } else {
            throw new IllegalArgumentException("Unsupported date type: " + dateTime.getClass().getSimpleName());
        }
    }

    @SneakyThrows
    private LocalDateTime stringToLocalDateTime(String dateTimeStr) {
        // 尝试解析日期时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    @Override
    public String name() {
        return "dateBetween";
    }
}