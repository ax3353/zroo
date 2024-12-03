package com.zk.ruleengine.utils;

import lombok.SneakyThrows;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 规则引擎的一些辅助工具方法
 *
 * @author zk
 */
public class Utils {

    private static final String TIME_EXP = "^\\d{2}:\\d{2}:\\d{2}$";
    private static final String DATE_EXP = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String DATE_TIME_EXP = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";

    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_EXP);
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE_EXP);
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile(DATE_TIME_EXP);
    private static final DateTimeFormatter FORMATTER0 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATTER1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间转日期
     */
    @SneakyThrows
    public static Object strToDate(String dateTimeStr) {
        if (validDateFormat(dateTimeStr, TIME_PATTERN)) {
            java.util.Date date = new SimpleDateFormat("HH:mm:ss").parse(dateTimeStr);
            return new Time(date.getTime());
        } else if (validDateFormat(dateTimeStr, DATE_PATTERN)) {
            return LocalDate.parse(dateTimeStr, FORMATTER0);
        } else if (validDateFormat(dateTimeStr, DATE_TIME_PATTERN)) {
            return LocalDateTime.parse(dateTimeStr, FORMATTER1);
        } else {
            throw new IllegalArgumentException("时间参数格式不对, 可选:[HH:mm:ss, yyyy-MM-dd, yyyy-MM-dd HH:mm:ss]");
        }
    }

    /**
     * 验证时间日期格式
     */
    public static boolean validDateFormat(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
