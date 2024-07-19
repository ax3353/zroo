package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.List;

/**
 * 计算两个日期之间的分钟差
 * 参数为两个日期或日期时间字符串或LocalDateTime对象，返回两个日期之间的分钟差
 * @author zk
 */
public class MinuteBetween implements Function<Object, BigDecimal> {

    @Override
    public BigDecimal execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("DateBetweenFunction requires exactly two arguments.");
        }

        // 获取传入的两个日期时间和时间单位
        Object arg0 = args.get(0);
        Object arg1 = args.get(1);

        // 确保时间参数是 LocalDateTime 类型
        Temporal localDateTime1 = ensureLocalDateTime(arg0);
        Temporal localDateTime2 = ensureLocalDateTime(arg1);

        // 计算两个 LocalDateTime 之间的差值
        Duration duration = Duration.between(localDateTime1, localDateTime2);
        return BigDecimal.valueOf(Math.abs(duration.toMinutes()));
    }

    private Temporal ensureLocalDateTime(Object dateTime) {
        if (dateTime instanceof LocalDateTime) {
            return (LocalDateTime) dateTime;
        } else if (dateTime instanceof String) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse((String) dateTime, formatter);
        } else {
            throw new IllegalArgumentException("Unsupported date type: " + dateTime.getClass().getSimpleName());
        }
    }

    @Override
    public String name() {
        return "minuteBetween";
    }
}