package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import com.zk.ruleengine.utils.Utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 转yyyy-MM-dd格式的日期
 *
 * @author zk
 */
public class ToDate implements Function<Object, java.sql.Date> {

    @Override
    public java.sql.Date execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("ToDate Function requires exactly one arguments.");
        }

        Object time1 = args.get(0);
        if (time1 instanceof String) {
            time1 = Utils.strToDate(String.valueOf(time1));
        }

        if (time1 instanceof java.sql.Date) {
            return (java.sql.Date) time1;
        } else if (time1 instanceof java.sql.Timestamp) {
            return new java.sql.Date(((java.sql.Timestamp) time1).getTime());
        } else if (time1 instanceof LocalDateTime) {
            LocalDateTime t1 = (LocalDateTime) time1;
            Instant instant = t1.atZone(java.time.ZoneId.systemDefault()).toInstant();
            return new java.sql.Date(instant.toEpochMilli());
        } else {
            throw new IllegalArgumentException("时间参数格式不对, 可选:[yyyy-MM-dd, yyyy-MM-dd HH:mm:ss]");
        }
    }

    @Override
    public String name() {
        return "toDate";
    }
}
