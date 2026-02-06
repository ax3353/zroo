package com.zk.ruleengine;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 日期时间功能专项测试
 *
 * @author Test Suite Generator
 */
public class DateTimeFunctionTest {

    private RuleEngine engine;
    private Map<String, Object> context;

    @Before
    public void setUp() {
        engine = RuleEngine.getInstance();
        context = new HashMap<>();

        context.put("startDate", "2024-01-01");
        context.put("endDate", "2024-12-31");
        context.put("birthDate", "1995-06-15");
        context.put("eventDateTime", "2024-02-04 10:30:00");
        context.put("meetingTime", "14:30:00");
    }

    // ==================== 日期输入与获取 ====================

    @Test
    public void testDateInput() {
        String exp = "[\"dateInput\", \"2024-02-04\"]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2024, 2, 4), result);
    }

    @Test
    public void testDateTimeInput() {
        String exp = "[\"dateTimeInput\", \"2024-02-04 15:30:45\"]";
        LocalDateTime result = engine.execute(context, exp);
        assertEquals(LocalDateTime.of(2024, 2, 4, 15, 30, 45), result);
    }

    @Test
    public void testTimeInput() {
        String exp = "[\"timeInput\", \"09:15:30\"]";
        LocalTime result = engine.execute(context, exp);
        assertEquals(LocalTime.of(9, 15, 30), result);
    }

    @Test
    public void testNowDate() {
        String exp = "[\"nowDate\"]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.now(), result);
    }

    @Test
    public void testNowDateTime() {
        String exp = "[\"nowDateTime\"]";
        LocalDateTime result = engine.execute(context, exp);
        assertNotNull(result);
        // 验证日期是今天
        assertEquals(LocalDate.now(), result.toLocalDate());
    }

    // ==================== 日期比较 ====================

    @Test
    public void testDateGreaterThan() {
        String exp = "[\"date>\", [\"dateInput\", \"2024-12-31\"], [\"dateInput\", \"2024-01-01\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testDateGreaterThanOrEqual() {
        String exp = "[\"date>=\", [\"dateInput\", \"2024-06-15\"], [\"dateInput\", \"2024-06-15\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testDateLessThan() {
        String exp = "[\"date<\", [\"dateInput\", \"2024-01-01\"], [\"dateInput\", \"2024-12-31\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testDateLessThanOrEqual() {
        String exp = "[\"date<=\", [\"dateInput\", \"2024-03-15\"], [\"dateInput\", \"2024-03-15\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testDateEqual() {
        String exp = "[\"date==\", [\"toDate\", [\"@value\", \"startDate\"]], [\"dateInput\", \"2024-01-01\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    // ==================== 日期加减 ====================

    @Test
    public void testDateAddDays() {
        // 2024-01-01 + 10天 = 2024-01-11
        String exp = "[\"date+\", [\"dateInput\", \"2024-01-01\"], 10, [\"strInput\", \"dayUnit\"]]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2024, 1, 11), result);
    }

    @Test
    public void testDateAddMonths() {
        // 2024-01-01 + 2个月 = 2024-03-01
        String exp = "[\"date+\", [\"dateInput\", \"2024-01-01\"], 2, [\"strInput\", \"monthUnit\"]]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2024, 3, 1), result);
    }

    @Test
    public void testDateAddYears() {
        // 2024-01-01 + 1年 = 2025-01-01
        String exp = "[\"date+\", [\"dateInput\", \"2024-01-01\"], 1, [\"strInput\", \"yearUnit\"]]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2025, 1, 1), result);
    }

    @Test
    public void testDateSubtractDays() {
        // 2024-01-11 - 10天 = 2024-01-01
        String exp = "[\"date-\", [\"dateInput\", \"2024-01-11\"], 10, [\"strInput\", \"dayUnit\"]]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2024, 1, 1), result);
    }

    @Test
    public void testDateSubtractMonths() {
        // 2024-03-01 - 2个月 = 2024-01-01
        String exp = "[\"date-\", [\"dateInput\", \"2024-03-01\"], 2, [\"strInput\", \"monthUnit\"]]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2024, 1, 1), result);
    }

    @Test
    public void testDateSubtractYears() {
        // 2025-01-01 - 1年 = 2024-01-01
        String exp = "[\"date-\", [\"dateInput\", \"2025-01-01\"], 1, [\"strInput\", \"yearUnit\"]]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2024, 1, 1), result);
    }

    @Test
    public void testUtilDateSubtractYears() {
        context.put("currentDate", new Date(1770343200000L));

        // 2026-02-06 10:00:00 - 1年 = 2025-02-06 10:00:00
        String exp = "[\"date-\", [\"@value\", \"currentDate\"], 1, [\"strInput\", \"yearUnit\"]]";
        Date result = engine.execute(context, exp);
        assertEquals(new Date(1738807200000L), result);
    }

    @Test
    public void testSqlDateSubtractYears() {
        context.put("currentDate", new java.sql.Date(1770307200000L));

        // 2026-02-06 - 1年 = 2025-02-06
        String exp = "[\"date-\", [\"@value\", \"currentDate\"], 1, [\"strInput\", \"yearUnit\"]]";
        java.sql.Date result = engine.execute(context, exp);
        assertEquals(new java.sql.Date(1738771200000L), result);
    }

    // ==================== 日期间隔计算 ====================

    @Test
    public void testDayBetween() {
        // 2024-01-01 到 2024-01-11 = 10天
        String exp = "[\"dayBetween\", [\"dateInput\", \"2024-01-01\"], [\"dateInput\", \"2024-01-11\"]]";
        Long result = engine.execute(context, exp);
        assertEquals(Long.valueOf(10), result);
    }

    @Test
    public void testDayBetweenNegative() {
        // 2024-01-11 到 2024-01-01 = -10天
        String exp = "[\"dayBetween\", [\"dateInput\", \"2024-01-11\"], [\"dateInput\", \"2024-01-01\"]]";
        Long result = engine.execute(context, exp);
        assertEquals(Long.valueOf(-10), result);
    }

    @Test
    public void testHourBetween() {
        // 测试小时差
        String exp = "[\"hourBetween\", [\"dateTimeInput\", \"2024-01-01 10:00:00\"], [\"dateTimeInput\", \"2024-01-01 15:00:00\"]]";
        BigDecimal result = engine.execute(context, exp);
        assertEquals(BigDecimal.valueOf(5), result);
    }

    @Test
    public void testMinuteBetween() {
        // 测试分钟差
        String exp = "[\"minuteBetween\", [\"dateTimeInput\", \"2024-01-01 10:00:00\"], [\"dateTimeInput\", \"2024-01-01 10:30:00\"]]";
        BigDecimal result = engine.execute(context, exp);
        assertEquals(BigDecimal.valueOf(30), result);
    }

    @Test
    public void testSecondBetween() {
        // 测试秒差
        String exp = "[\"secondBetween\", [\"dateTimeInput\", \"2024-01-01 10:00:00\"], [\"dateTimeInput\", \"2024-01-01 10:01:00\"]]";
        BigDecimal result = engine.execute(context, exp);
        assertEquals(BigDecimal.valueOf(60), result);
    }

    // ==================== 日期类型转换 ====================

    @Test
    public void testToDate() {
        String exp = "[\"toDate\", [\"strInput\", \"2024-06-15\"]]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2024, 6, 15), result);
    }

    // ==================== 业务场景测试 ====================

    @Test
    public void testAgeCalculation() {
        // 计算年龄：当前日期 - 出生日期
        String exp = "[\"dayBetween\", [\"toDate\", [\"@value\", \"birthDate\"]], [\"nowDate\"]]";
        Long days = engine.execute(context, exp);

        // 年龄应该大于等于28岁（1995年到2024年）
        assertTrue(days > 28 * 365);
    }

    @Test
    public void testContractExpiration() {
        // 合同是否在30天内到期
        context.put("contractEndDate", LocalDate.of(2024, 3, 5));

        String exp = "[\"<=\", [\"dayBetween\", [\"nowDate\"], [\"@value\", \"contractEndDate\"]], 30]";
        Boolean result = engine.execute(context, exp);
        assertNotNull(result);
    }

    @Test
    public void testWorkingDaysCalculation() {
        // 计算2024年1月1日到1月31日的天数
        String exp = "[\"dayBetween\", [\"dateInput\", \"2024-01-01\"], [\"dateInput\", \"2024-01-31\"]]";
        Long days = engine.execute(context, exp);
        assertEquals(Long.valueOf(30), days);
    }

    @Test
    public void testEventWithinRange() {
        // 检查事件是否在指定日期范围内
        context.put("eventDate", LocalDate.of(2024, 6, 15));

        String exp = "[\"&&\", [\"date>=\", [\"@value\", \"eventDate\"], [\"dateInput\", \"2024-01-01\"]], [\"date<=\", [\"@value\", \"eventDate\"], [\"dateInput\", \"2024-12-31\"]]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testOverdueCheck() {
        // 检查是否逾期
        context.put("dueDate", LocalDate.of(2024, 1, 15));

        String exp = "[\"date>\", [\"nowDate\"], [\"@value\", \"dueDate\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result); // 当前日期（2024-02-04）已过期
    }

    @Test
    public void testTrialPeriodCheck() {
        // 检查试用期是否已结束（试用期30天）
        context.put("registerDate", LocalDate.of(2024, 1, 1));

        String exp = "[\">\", [\"dayBetween\", [\"@value\", \"registerDate\"], [\"nowDate\"]], 30]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }
}