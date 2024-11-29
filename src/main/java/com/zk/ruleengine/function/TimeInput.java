package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import lombok.SneakyThrows;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间输入(eg: "09:01:01")
 * @author zk
 */
public class TimeInput implements Function<String, Time> {

    private static final String EXPRESS = "^\\d{2}:\\d{2}:\\d{2}$";
    private static final Pattern PATTERN = Pattern.compile(EXPRESS);

    @Override
    @SneakyThrows
    public Time execute(Evaluator evaluator, List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("DateInput Function requires exactly one arguments.");
        }

        // 检查格式是否是HH:mm:ss
        String timeStr = args.get(0);
        Matcher matcher = PATTERN.matcher(timeStr);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("输入时间必须是HH:mm:ss格式");
        }

        java.util.Date utilDate = new SimpleDateFormat("HH:mm:ss").parse(timeStr);
        return new Time(utilDate.getTime());
    }

    @Override
    public String name() {
        return "timeInput";
    }
}