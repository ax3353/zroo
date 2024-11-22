package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import com.zk.ruleengine.utils.Utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 比较两个日期不相等
 * 参数为两个日期格式的对象，返回布尔类型
 *
 * @author zk
 */
public class DateNotEqual implements Function<Object, Boolean> {

    @Override
    public Boolean execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("DateEqual Function requires exactly two arguments.");
        }

        // 获取传入的两个日期时间
        Object time1 = args.get(0);
        Object time2 = args.get(1);

        // 字符串先转日期
        if (time1 instanceof String) {
            time1 = Utils.strToDate(String.valueOf(time1));
        }

        if (time2 instanceof String) {
            time2 = Utils.strToDate(String.valueOf(time2));
        }

        // 判断传入的日期时间类型
        if (time1 instanceof LocalDateTime && time2 instanceof LocalDateTime) {
            LocalDateTime t1 = (LocalDateTime) time1;
            LocalDateTime t2 = (LocalDateTime) time2;
            return t1.compareTo(t2) != 0;
        } else if (time1 instanceof java.sql.Date && time2 instanceof java.sql.Date) {
            java.sql.Date t1 = (java.sql.Date) time1;
            java.sql.Date t2 = (java.sql.Date) time2;
            return t1.compareTo(t2) != 0;
        } else if (time1 instanceof Time && time2 instanceof Time) {
            Time t1 = (Time) time1;
            Time t2 = (Time) time2;
            return t1.compareTo(t2) != 0;
        } else if (time1 instanceof Timestamp && time2 instanceof Timestamp) {
            Timestamp t1 = (Timestamp) time1;
            Timestamp t2 = (Timestamp) time2;
            return t1.compareTo(t2) != 0;
        } else {
            return false;
        }
    }

    @Override
    public String name() {
        return "date<>";
    }
}