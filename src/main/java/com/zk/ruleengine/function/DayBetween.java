package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * 计算两个日期之间的天数差。
 * 参数为两个日期或日期时间字符串或日期对象，返回两个日期之间的天数差。
 * <p>
 * 支持参数类型：
 * - java.time.LocalDateTime
 * - java.time.LocalDate
 * - java.util.Date
 * - java.sql.Date
 * - String（yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss）
 *
 * @author zk
 */
public class DayBetween implements Function<Object, Long> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Long execute(Evaluator evaluator, List<Object> args) {
        if (args == null || args.size() != 2) {
            throw new IllegalArgumentException("dayBetween requires exactly two arguments.");
        }

        Object arg0 = args.get(0);
        Object arg1 = args.get(1);

        LocalDate d1 = ensureLocalDate(arg0);
        LocalDate d2 = ensureLocalDate(arg1);

        return ChronoUnit.DAYS.between(d1, d2);
    }

    /**
     * 将入参统一转换为 LocalDate（仅保留日期部分）
     */
    private LocalDate ensureLocalDate(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("dayBetween argument cannot be null.");
        }

        if (value instanceof LocalDate) {
            return (LocalDate) value;
        }

        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).toLocalDate();
        }

        // java.sql.Date 是 java.util.Date 的子类，但它本质是“日期”语义；优先按日期解析
        if (value instanceof java.sql.Date) {
            return ((java.sql.Date) value).toLocalDate();
        }

        if (value instanceof Date) {
            Date date = (Date) value;
            Instant instant = Instant.ofEpochMilli(date.getTime());
            return instant.atZone(ZoneId.systemDefault()).toLocalDate();
        }

        if (value instanceof String) {
            String s = ((String) value).trim();
            if (s.isEmpty()) {
                throw new IllegalArgumentException("dayBetween argument string cannot be blank.");
            }

            // 先尝试 yyyy-MM-dd HH:mm:ss，再尝试 yyyy-MM-dd
            try {
                return LocalDateTime.parse(s, DATE_TIME_FORMATTER).toLocalDate();
            } catch (DateTimeParseException ignore) {
                try {
                    return LocalDate.parse(s, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Unsupported date string format: " + s
                            + ". Expected 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm:ss'.", e);
                }
            }
        }

        throw new IllegalArgumentException("Unsupported date type: " + value.getClass().getName());
    }

    @Override
    public String name() {
        return "dayBetween";
    }
}