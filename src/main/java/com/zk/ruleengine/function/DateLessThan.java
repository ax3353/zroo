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
 * 比较两个日期之间的大小
 * 参表为两个日期字符串，返回布尔类型
 *
 * @author zk
 */
public class DateLessThan implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("DateLessThan Function requires exactly two arguments.");
        }

        // 获取传入的两个日期时间
        Object time1 = args.get(0);
        Object time2 = args.get(1);

        if (!time1.getClass().equals(time2.getClass())) {
            throw new IllegalArgumentException("[比较日期大小]操作参数类型不一致");
        }

        // 字符串先转日期
        if (time1 instanceof String) {
            time1 = Utils.strToDate(String.valueOf(time1));
        }

        if (time2 instanceof String) {
            time2 = Utils.strToDate(String.valueOf(time2));
        }

        // 判断传入的日期时间类型
        if (time1 instanceof LocalDate && time2 instanceof LocalDate) {
            LocalDate t1 = (LocalDate) time1;
            LocalDate t2 = (LocalDate) time2;
            return t1.isBefore(t2);
        } else if (time1 instanceof LocalDateTime && time2 instanceof LocalDateTime) {
            LocalDateTime t1 = (LocalDateTime) time1;
            LocalDateTime t2 = (LocalDateTime) time2;
            return t1.isBefore(t2);
        } else if (time1 instanceof java.sql.Date && time2 instanceof java.sql.Date) {
            java.sql.Date t1 = (java.sql.Date) time1;
            java.sql.Date t2 = (java.sql.Date) time2;
            return t1.before(t2);
        } else if (time1 instanceof Time && time2 instanceof Time) {
            Time t1 = (Time) time1;
            Time t2 = (Time) time2;
            return t1.before(t2);
        } else if (time1 instanceof Timestamp && time2 instanceof Timestamp) {
            Timestamp t1 = (Timestamp) time1;
            Timestamp t2 = (Timestamp) time2;
            return t1.before(t2);
        } else {
            return false;
        }
    }

    @Override
    public String name() {
        return "date<";
    }
}