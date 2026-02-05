package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import com.zk.ruleengine.utils.Utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 日期时间减法运算
 * <p>
 * 传入3个参数，分别是被减少的日期时间、减少的值、时间单位
 * 支持的时间单位：年(yearUnit)、月(monthUnit)、天(dayUnit)、时(hourUnit)、分(minuteUnit)、秒(secondUnit)
 *
 * @author zk
 */
public class DateSub implements Function<Object, Object> {

    @Override
    public Object execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException("DateSub Function requires exactly three arguments.");
        }

        // 获取日期时间、减少的值、时间单位
        Object time = args.get(0);
        Object sub = args.get(1);
        Object unit = args.get(2);

        if (!(sub instanceof Number)) {
            throw new IllegalArgumentException("[日期时间减法运算]操作的减少值必须是数字");
        }

        if (!Utils.isInteger((Number) sub)) {
            throw new IllegalArgumentException("[日期时间减法运算]操作的减少值必须是整数");
        }

        if (!(unit instanceof String)) {
            throw new IllegalArgumentException("[日期时间减法运算]操作的单位可选[年(yearUnit),月(monthUnit),天(dayUnit),时(hourUnit),分(minuteUnit),秒(secondUnit)]");
        }

        // 字符串先转日期
        if (time instanceof String) {
            time = Utils.strToDate(String.valueOf(time));
        }

        long toSub = ((Number) sub).longValue();
        String timeUnit = (String) unit;

        // 判断传入的日期时间类型
        if (time instanceof LocalDate) {
            return handleLocalDate((LocalDate) time, toSub, timeUnit);
        } else if (time instanceof LocalDateTime) {
            return handleLocalDateTime((LocalDateTime) time, toSub, timeUnit);
        } else if (time instanceof java.sql.Date) {
            return handleSqlDate((java.sql.Date) time, toSub, timeUnit);
        } else if (time instanceof Time) {
            return handleTime((Time) time, toSub, timeUnit);
        } else if (time instanceof Timestamp) {
            return handleTimestamp((Timestamp) time, toSub, timeUnit);
        } else if (time instanceof java.util.Date) {
            return handleUtilDate((java.util.Date) time, toSub, timeUnit);
        } else {
            throw new IllegalArgumentException("[日期时间减法运算]不支持的时间类型: " + time.getClass().getName());
        }
    }

    /**
     * 处理 LocalDate 类型
     * 支持: 年、月、天
     */
    private LocalDate handleLocalDate(LocalDate date, long toSub, String timeUnit) {
        switch (timeUnit) {
            case "yearUnit":
                return date.minusYears(toSub);
            case "monthUnit":
                return date.minusMonths(toSub);
            case "dayUnit":
                return date.minusDays(toSub);
            default:
                throw new IllegalArgumentException(
                        "[日期时间减法运算]LocalDate类型仅支持yearUnit(年)、monthUnit(月)、dayUnit(天)，不支持: " + timeUnit
                );
        }
    }

    /**
     * 处理 LocalDateTime 类型
     * 支持: 年、月、天、时、分、秒
     */
    private LocalDateTime handleLocalDateTime(LocalDateTime dateTime, long toSub, String timeUnit) {
        switch (timeUnit) {
            case "yearUnit":
                return dateTime.minusYears(toSub);
            case "monthUnit":
                return dateTime.minusMonths(toSub);
            case "dayUnit":
                return dateTime.minusDays(toSub);
            case "hourUnit":
                return dateTime.minusHours(toSub);
            case "minuteUnit":
                return dateTime.minusMinutes(toSub);
            case "secondUnit":
                return dateTime.minusSeconds(toSub);
            default:
                throw new IllegalArgumentException(
                        "[日期时间减法运算]LocalDateTime类型不支持的时间单位: " + timeUnit
                );
        }
    }

    /**
     * 处理 java.sql.Date 类型
     * 支持: 年、月、天
     */
    private java.sql.Date handleSqlDate(java.sql.Date date, long toSub, String timeUnit) {
        // 转换为 LocalDate 进行计算
        LocalDate localDate = date.toLocalDate();
        LocalDate result;

        switch (timeUnit) {
            case "yearUnit":
                result = localDate.minusYears(toSub);
                break;
            case "monthUnit":
                result = localDate.minusMonths(toSub);
                break;
            case "dayUnit":
                result = localDate.minusDays(toSub);
                break;
            default:
                throw new IllegalArgumentException(
                        "[日期时间减法运算]java.sql.Date类型仅支持yearUnit(年)、monthUnit(月)、dayUnit(天)，不支持: " + timeUnit
                );
        }

        return java.sql.Date.valueOf(result);
    }

    /**
     * 处理 Time 类型
     * 仅支持: 时、分、秒（不支持年、月、天）
     */
    private Time handleTime(Time time, long toSub, String timeUnit) {
        long lTime = time.getTime();

        switch (timeUnit) {
            case "hourUnit":
                return new Time(lTime - toSub * 60 * 60 * 1000);
            case "minuteUnit":
                return new Time(lTime - toSub * 60 * 1000);
            case "secondUnit":
                return new Time(lTime - toSub * 1000L);
            default:
                throw new IllegalArgumentException(
                        "[日期时间减法运算]Time类型仅支持hourUnit(时)、minuteUnit(分)、secondUnit(秒)，不支持: " + timeUnit
                );
        }
    }

    /**
     * 处理 Timestamp 类型
     * 支持: 年、月、天、时、分、秒
     */
    private Timestamp handleTimestamp(Timestamp timestamp, long toSub, String timeUnit) {
        // 对于年和月，转换为 LocalDateTime 计算更准确
        if ("yearUnit".equals(timeUnit) || "monthUnit".equals(timeUnit)) {
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            LocalDateTime result;

            if ("yearUnit".equals(timeUnit)) {
                result = localDateTime.minusYears(toSub);
            } else {
                result = localDateTime.minusMonths(toSub);
            }

            return Timestamp.valueOf(result);
        }

        // 对于天、时、分、秒，直接使用毫秒计算
        long lTime = timestamp.getTime();

        switch (timeUnit) {
            case "dayUnit":
                return new Timestamp(lTime - toSub * 24 * 60 * 60 * 1000);
            case "hourUnit":
                return new Timestamp(lTime - toSub * 60 * 60 * 1000);
            case "minuteUnit":
                return new Timestamp(lTime - toSub * 60 * 1000);
            case "secondUnit":
                return new Timestamp(lTime - toSub * 1000);
            default:
                throw new IllegalArgumentException(
                        "[日期时间减法运算]Timestamp类型不支持的时间单位: " + timeUnit
                );
        }
    }

    private java.util.Date handleUtilDate(java.util.Date time, long toSub, String timeUnit) {
        // 转换为 ZonedDateTime 进行计算
        ZonedDateTime zonedDateTime = time.toInstant().atZone(ZoneId.systemDefault());
        ZonedDateTime result;

        switch (timeUnit) {
            case "yearUnit":
                result = zonedDateTime.minusYears(toSub);
                break;
            case "monthUnit":
                result = zonedDateTime.minusMonths(toSub);
                break;
            case "dayUnit":
                result = zonedDateTime.minusDays(toSub);
                break;
            case "hourUnit":
                result = zonedDateTime.minusHours(toSub);
                break;
            case "minuteUnit":
                result = zonedDateTime.minusMinutes(toSub);
                break;
            case "secondUnit":
                result = zonedDateTime.minusSeconds(toSub);
                break;
            default:
                throw new IllegalArgumentException(
                        "[日期时间减法运算]java.util.Date类型支持的时间单位有: " +
                                "yearUnit(年)、monthUnit(月)、dayUnit(天)、" +
                                "hourUnit(小时)、minuteUnit(分钟)、secondUnit(秒)，" +
                                "不支持: " + timeUnit
                );
        }

        // 转换回 java.util.Date
        return java.util.Date.from(result.toInstant());
    }

    @Override
    public String name() {
        return "date-";
    }
}