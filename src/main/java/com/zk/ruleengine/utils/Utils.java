package com.zk.ruleengine.utils;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
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
    public static Object strToDate(String dateTimeStr) {
        if (validDateFormat(dateTimeStr, TIME_PATTERN)) {
            try {
                java.util.Date date = new SimpleDateFormat("HH:mm:ss").parse(dateTimeStr);
                return new Time(date.getTime());
            } catch (ParseException e) {
                throw new IllegalArgumentException("时间参数格式不对, 可选:[HH:mm:ss, yyyy-MM-dd, yyyy-MM-dd HH:mm:ss]");
            }
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

    /**
     * 安全地将 Object 转换为 int
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot convert null to int");
        }

        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }

        if (obj instanceof String) {
            try {
                return Integer.parseInt((String) obj);
            } catch (NumberFormatException e) {
                // 尝试通过 BigDecimal 转换
                return new BigDecimal((String) obj).intValue();
            }
        }

        throw new IllegalArgumentException("Cannot convert " + obj.getClass() + " to int");
    }

    /**
     * 安全地将 Object 转换为 long
     */
    public static long toLong(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot convert null to long");
        }

        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }

        if (obj instanceof String) {
            try {
                return Long.parseLong((String) obj);
            } catch (NumberFormatException e) {
                return new BigDecimal((String) obj).longValue();
            }
        }

        throw new IllegalArgumentException("Cannot convert " + obj.getClass() + " to long");
    }

    /**
     * 安全地将 Object 转换为 double
     */
    public static double toDouble(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot convert null to double");
        }

        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }

        if (obj instanceof String) {
            return Double.parseDouble((String) obj);
        }

        throw new IllegalArgumentException("Cannot convert " + obj.getClass() + " to double");
    }

    /**
     * 安全地将 Object 转换为 BigDecimal
     */
    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot convert null to BigDecimal");
        }

        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }

        if (obj instanceof Number) {
            return new BigDecimal(obj.toString());
        }

        if (obj instanceof String) {
            return new BigDecimal((String) obj);
        }

        throw new IllegalArgumentException("Cannot convert " + obj.getClass() + " to BigDecimal");
    }

    /**
     * 检查是否为整数（没有小数部分）
     */
    public static boolean isInteger(Number number) {
        if (number instanceof Integer || number instanceof Long) {
            return true;
        }

        if (number instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) number;
            return bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
        }

        if (number instanceof Double || number instanceof Float) {
            double d = number.doubleValue();
            return d == Math.floor(d) && !Double.isInfinite(d);
        }

        return false;
    }
}
