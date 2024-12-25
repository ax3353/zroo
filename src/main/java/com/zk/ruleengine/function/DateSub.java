package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import com.zk.ruleengine.utils.Utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 日期时间减法运算
 * <p>
 * 传入3个参数，分别是被减小的日期时间、减小的值、时间单位（天时分秒）
 *
 * @author zk
 */
public class DateSub implements Function<Object, Object> {

    @Override
    public Object execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException("DateSub Function requires exactly three arguments.");
        }

        // 获取日期时间、减小的值、时间单位
        Object time = args.get(0);
        Object add = args.get(1);
        Object unit = args.get(2);

        if (!(add instanceof Integer)) {
            throw new IllegalArgumentException("[日期时间减法运算]操作的减小值必须是整数");
        }

        if (!(unit instanceof String)) {
            throw new IllegalArgumentException("[日期时间减法运算]操作的单位可选[天,时,分,秒]");
        }

        // 字符串先转日期
        if (time instanceof String) {
            time = Utils.strToDate(String.valueOf(time));
        }

        long toAdd = -(int) add;
        String timeUnit = (String) unit;

        // 判断传入的日期时间类型
        if (time instanceof LocalDate) {
            LocalDate t1 = (LocalDate) time;
            if (!"dayUnit".equals(timeUnit)) {
                throw new IllegalArgumentException("[日期时间减法运算]LocalDate类型未知的时间单位");
            }
            return t1.plusDays(toAdd);
        } else if (time instanceof LocalDateTime) {
            LocalDateTime t1 = (LocalDateTime) time;
            switch (timeUnit) {
                case "dayUnit":
                    return t1.plusDays(toAdd);
                case "hourUnit":
                    return t1.plusHours(toAdd);
                case "minuteUnit":
                    return t1.plusMinutes(toAdd);
                case "secondUnit":
                    return t1.plusSeconds(toAdd);
                default:
                    throw new IllegalArgumentException("[日期时间减法运算]LocalDateTime类型未知的时间单位");
            }
        } else if (time instanceof java.sql.Date) {
            java.sql.Date t1 = (java.sql.Date) time;
            if (!"dayUnit".equals(timeUnit)) {
                throw new IllegalArgumentException("[日期时间减法运算]LocalDate类型未知的时间单位");
            }
            return new java.sql.Date(t1.getTime() + toAdd * 24 * 60 * 60 * 1000);
        } else if (time instanceof Time) {
            Time t1 = (Time) time;
            long lTime = t1.getTime();
            switch (timeUnit) {
                case "hourUnit":
                    return new Time(lTime + toAdd * 60 * 60 * 1000);
                case "minuteUnit":
                    return new Time(lTime + toAdd * 60 * 1000);
                case "secondUnit":
                    return new Time(lTime + toAdd * 1000L);
                default:
                    throw new IllegalArgumentException("[日期时间减法运算]Time类型未知的时间单位");
            }
        } else if (time instanceof Timestamp) {
            Timestamp t1 = (Timestamp) time;
            long lTime = t1.getTime();
            switch (timeUnit) {
                case "dayUnit":
                    return new Timestamp(lTime + toAdd * 24 * 60 * 60 * 1000);
                case "hourUnit":
                    return new Timestamp(lTime + toAdd * 60 * 60 * 1000);
                case "minuteUnit":
                    return new Timestamp(lTime + toAdd * 60 * 1000);
                case "secondUnit":
                    return new Timestamp(lTime + toAdd * 1000);
                default:
                    throw new IllegalArgumentException("[日期时间减法运算]Timestamp类型未知的时间单位");
            }
        } else {
            throw new IllegalArgumentException("[日期时间减法运算]不支持的时间类型");
        }
    }

    @Override
    public String name() {
        return "date-";
    }
}