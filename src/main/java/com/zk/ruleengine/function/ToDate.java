package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import com.zk.ruleengine.utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

/**
 * 转yyyy-MM-dd或yyyy-MM-dd HH:mm:ss格式的日期
 *
 * @author zk
 */
public class ToDate implements Function<Object, Temporal> {

    @Override
    public Temporal execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("ToDate Function requires exactly one argument.");
        }

        Object time1 = args.get(0);
        if (time1 instanceof String) {
            time1 = Utils.strToDate(String.valueOf(time1));
        }

        if (time1 instanceof LocalDate) {
            return (LocalDate) time1;
        }

        if (time1 instanceof LocalDateTime) {
            return (LocalDateTime) time1;
        }
        throw new IllegalArgumentException("无法转换成日期: " + time1);
    }

    @Override
    public String name() {
        return "toDate";
    }
}