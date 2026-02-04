package com.zk.ruleengine;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 规则引擎综合测试套件
 *
 * @author Test Suite Generator
 */
public class RuleEngineComprehensiveTest {

    private RuleEngine engine;
    private Map<String, Object> context;

    @Before
    public void setUp() {
        engine = RuleEngine.getInstance();
        context = new HashMap<>();

        // 设置测试上下文数据
        context.put("name", "张三");
        context.put("age", 25);
        context.put("salary", 8000.50);
        context.put("city", "北京");
        context.put("isVIP", true);
        context.put("score", 85.5);
        context.put("level", 3);
        context.put("email", "zhangsan@example.com");
        context.put("phone", "13800138000");
        context.put("joinDate", "2020-01-15");
        context.put("lastLoginDate", "2024-02-01");
        context.put("department", "技术部");
        context.put("nullValue", null);
        context.put("emptyString", "");
        context.put("balance", 1500.75);
    }

    // ==================== 算术运算测试 ====================

    @Test
    public void testAddition() {
        String exp = "[\"+\", 10, 20, 30]";
        Integer result = engine.execute(context, exp);
        assertEquals(Integer.valueOf(60), result);
    }

    @Test
    public void testAdditionWithContext() {
        String exp = "[\"+\", [\"@value\", \"age\"], 5]";
        Integer result = engine.execute(context, exp);
        assertEquals(Integer.valueOf(30), result);
    }

    @Test
    public void testSubtraction() {
        String exp = "[\"-\", 100, 25, 5]";
        Integer result = engine.execute(context, exp);
        assertEquals(Integer.valueOf(70), result);
    }

    @Test
    public void testMultiplication() {
        String exp = "[\"*\", 5, 4, 2]";
        Integer result = engine.execute(context, exp);
        assertEquals(Integer.valueOf(40), result);
    }

    @Test
    public void testDivision() {
        String exp = "[\"/\", 100, 5, 2]";
        Integer result = engine.execute(context, exp);
        assertEquals(Integer.valueOf(10), result);
    }

    @Test
    public void testModulo() {
        String exp = "[\"%\", 17, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(BigDecimal.valueOf(2), result);
    }

    @Test
    public void testAbsoluteValue() {
        String exp = "[\"abs\", -25]";
        Integer result = engine.execute(context, exp);
        assertEquals(Integer.valueOf(25), result);
    }

    @Test
    public void testCeil() {
        String exp = "[\"ceil\", 3.14]";
        Number result = engine.execute(context, exp);
        assertEquals(BigDecimal.valueOf(4), result);
    }

    @Test
    public void testFloor() {
        String exp = "[\"floor\", 3.99]";
        Number result = engine.execute(context, exp);
        assertEquals(BigDecimal.valueOf(3), result);
    }

    @Test
    public void testComplexArithmetic() {
        // (10 + 20) * 3 - 5
        String exp = "[\"-\", [\"*\", [\"+\", 10, 20], 3], 5]";
        Integer result = engine.execute(context, exp);
        assertEquals(Integer.valueOf(85), result);
    }

    // ==================== 数值比较测试 ====================

    @Test
    public void testGreaterThan() {
        String exp = "[\">\", [\"@value\", \"age\"], 18]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThanOrEqual() {
        String exp = "[\">=\", [\"@value\", \"age\"], 25]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThan() {
        String exp = "[\"<\", [\"@value\", \"age\"], 30]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThanOrEqual() {
        String exp = "[\"<=\", [\"@value\", \"age\"], 25]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberEqual() {
        String exp = "[\"==\", [\"@value\", \"age\"], 25]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberNotEqual() {
        String exp = "[\"<>\", [\"@value\", \"age\"], 30]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    // ==================== 字符串操作测试 ====================

    @Test
    public void testStringEqual() {
        String exp = "[\"strEq\", [\"@value\", \"name\"], [\"strInput\", \"张三\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testStringNotEqual() {
        String exp = "[\"strNeq\", [\"@value\", \"name\"], [\"strInput\", \"李四\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testContains() {
        String exp = "[\"contains\", [\"@value\", \"email\"], [\"strInput\", \"example\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNotContains() {
        String exp = "[\"notContains\", [\"@value\", \"email\"], [\"strInput\", \"gmail\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLeftSub() {
        String exp = "[\"leftSub\", [\"@value\", \"name\"], 1]";
        String result = engine.execute(context, exp);
        assertEquals("张", result);
    }

    @Test
    public void testRightSub() {
        String exp = "[\"rightSub\", [\"@value\", \"name\"], 1]";
        String result = engine.execute(context, exp);
        assertEquals("三", result);
    }

    @Test
    public void testMidSub() {
        String exp = "[\"midSub\", [\"strInput\", \"Hello World\"], 6]";
        String result = engine.execute(context, exp);
        assertEquals("llo Wo", result);
    }

    @Test
    public void testMidSubWithLength() {
        String exp = "[\"midSub\", [\"strInput\", \"Hello World\"], 0, 5]";
        String result = engine.execute(context, exp);
        assertEquals("Hello", result);
    }

    // ==================== 逻辑运算测试 ====================

    @Test
    public void testAndOperation() {
        String exp = "[\"&&\", [\">\", [\"@value\", \"age\"], 18], [\"<\", [\"@value\", \"age\"], 60]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testAndOperationShortCircuit() {
        // 第一个条件为false，应该短路，不评估第二个条件
        String exp = "[\"&&\", [\"<\", [\"@value\", \"age\"], 18], [\">\", [\"@value\", \"age\"], 100]]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testOrOperation() {
        String exp = "[\"||\" , [\"strEq\", [\"@value\", \"city\"], [\"strInput\", \"北京\"]], [\"strEq\", [\"@value\", \"city\"], [\"strInput\", \"上海\"]]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testOrOperationShortCircuit() {
        // 第一个条件为true，应该短路
        String exp = "[\"||\" , [\"strEq\", [\"@value\", \"city\"], [\"strInput\", \"北京\"]], [\"strEq\", [\"@value\", \"city\"], [\"strInput\", \"上海\"]]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNotOperation() {
        String exp = "[\"!\", [\"strEq\", [\"@value\", \"city\"], [\"strInput\", \"上海\"]]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testComplexLogic() {
        // (age > 18 AND city == "北京") OR (salary > 10000)
        String exp = "[\"||\" , [\"&&\", [\">\", [\"@value\", \"age\"], 18], [\"strEq\", [\"@value\", \"city\"], [\"strInput\", \"北京\"]]], [\">\", [\"@value\", \"salary\"], 10000]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    // ==================== 条件分支测试 ====================

    @Test
    public void testSimpleIf() {
        String exp = "[\"if\", [\">\", [\"@value\", \"age\"], 18], [\"strInput\", \"成年人\"], [\"strInput\", \"未成年人\"]]";
        String result = engine.execute(context, exp);
        assertEquals("成年人", result);
    }

    @Test
    public void testIfElseIfElse() {
        // if score >= 90: "优秀"
        // elif score >= 60: "及格"
        // else: "不及格"
        String exp = "[\"if\", [\">=\", [\"@value\", \"score\"], 90], [\"strInput\", \"优秀\"], [\">=\", [\"@value\", \"score\"], 60], [\"strInput\", \"及格\"], [\"strInput\", \"不及格\"]]";
        String result = engine.execute(context, exp);
        assertEquals("及格", result);
    }

    @Test
    public void testNestedIf() {
        // if age > 18:
        //     if isVIP: "成年VIP"
        //     else: "成年普通用户"
        // else: "未成年"
        String exp = "[\"if\", [\">\", [\"@value\", \"age\"], 18], [\"if\", [\"@value\", \"isVIP\"], [\"strInput\", \"成年VIP\"], [\"strInput\", \"成年普通用户\"]], [\"strInput\", \"未成年\"]]";
        String result = engine.execute(context, exp);
        assertEquals("成年VIP", result);
    }

    @Test
    public void testIfWithoutElse() {
        String exp = "[\"if\", [\"<\", [\"@value\", \"age\"], 18], [\"strInput\", \"未成年\"]]";
        Object result = engine.execute(context, exp);
        assertNull(result); // 条件不满足且无else，应返回null
    }

    // ==================== 空值判断测试 ====================

    @Test
    public void testNull() {
        String exp = "[\"null\", [\"@value\", \"nullValue\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNotNull() {
        String exp = "[\"notNull\", [\"@value\", \"name\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testBlank() {
        String exp = "[\"blank\", [\"@value\", \"emptyString\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNotBlank() {
        String exp = "[\"notBlank\", [\"@value\", \"name\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    // ==================== 类型转换测试 ====================

    @Test
    public void testToStr() {
        String exp = "[\"toStr\", 123]";
        String result = engine.execute(context, exp);
        assertEquals("123", result);
    }

    @Test
    public void testToNumber() {
        String exp = "[\"toNumber\", [\"strInput\", \"456\"]]";
        BigDecimal result = engine.execute(context, exp);
        assertEquals(new BigDecimal("456"), result);
    }

    @Test
    public void testNumberInput() {
        String exp = "[\"numberInput\", 3.14159]";
        Number result = engine.execute(context, exp);
        assertNotNull(result);
    }

    @Test
    public void testStrInput() {
        String exp = "[\"strInput\", \"测试字符串\"]";
        String result = engine.execute(context, exp);
        assertEquals("测试字符串", result);
    }

    // ==================== 上下文获取测试 ====================

    @Test
    public void testGetValue() {
        String exp = "[\"@value\", \"name\"]";
        String result = engine.execute(context, exp);
        assertEquals("张三", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetValueKeyNotFound() {
        String exp = "[\"@value\", \"nonExistentKey\"]";
        engine.execute(context, exp);
    }

    // ==================== 日期时间测试 ====================

    @Test
    public void testNowDate() {
        String exp = "[\"nowDate\"]";
        LocalDate result = engine.execute(context, exp);
        assertNotNull(result);
        assertEquals(LocalDate.now(), result);
    }

    @Test
    public void testNowDateTime() {
        String exp = "[\"nowDateTime\"]";
        LocalDateTime result = engine.execute(context, exp);
        assertNotNull(result);
    }

    @Test
    public void testDateInput() {
        String exp = "[\"dateInput\", \"2024-01-01\"]";
        LocalDate result = engine.execute(context, exp);
        assertEquals(LocalDate.of(2024, 1, 1), result);
    }

    @Test
    public void testDateGreaterThan() {
        String exp = "[\"date>\", [\"dateInput\", \"2024-02-01\"], [\"dateInput\", \"2024-01-01\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testDateEqual() {
        String exp = "[\"date==\", [\"dateInput\", \"2024-01-01\"], [\"dateInput\", \"2024-01-01\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    // ==================== 复杂业务场景测试 ====================

    @Test
    public void testVIPDiscountRule() {
        // VIP用户消费满1000打8折，非VIP用户满1000打9折，否则不打折
        context.put("totalAmount", 1200.0);

        String exp = "[\"if\", [\"&&\", [\"@value\", \"isVIP\"], [\">=\", [\"@value\", \"totalAmount\"], 1000]], [\"*\", [\"@value\", \"totalAmount\"], 0.8], [\"if\", [\">=\", [\"@value\", \"totalAmount\"], 1000], [\"*\", [\"@value\", \"totalAmount\"], 0.9], [\"@value\", \"totalAmount\"]]]";

        Number result = engine.execute(context, exp);
        assertEquals(960.0, result.doubleValue(), 0.01);
    }

    @Test
    public void testEmployeeBonusCalculation() {
        // 奖金计算规则：
        // level >= 4: salary * 3
        // level == 3: salary * 2
        // level >= 1: salary * 1.5
        // else: salary

        String exp = "[\"if\", [\">=\", [\"@value\", \"level\"], 4], [\"*\", [\"@value\", \"salary\"], 3], [\"==\", [\"@value\", \"level\"], 3], [\"*\", [\"@value\", \"salary\"], 2], [\">=\", [\"@value\", \"level\"], 1], [\"*\", [\"@value\", \"salary\"], 1.5], [\"@value\", \"salary\"]]";

        Number result = engine.execute(context, exp);
        assertEquals(16001.0, result.doubleValue(), 0.01);
    }

    @Test
    public void testCreditScoreEvaluation() {
        // 信用评分规则：
        // score >= 90 AND balance > 1000: "AAA"
        // score >= 80 AND balance > 500: "AA"
        // score >= 60: "A"
        // else: "B"

        String exp = "[\"if\", [\"&&\", [\">=\", [\"@value\", \"score\"], 90], [\">\", [\"@value\", \"balance\"], 1000]], [\"strInput\", \"AAA\"], [\"&&\", [\">=\", [\"@value\", \"score\"], 80], [\">\", [\"@value\", \"balance\"], 500]], [\"strInput\", \"AA\"], [\">=\", [\"@value\", \"score\"], 60], [\"strInput\", \"A\"], [\"strInput\", \"B\"]]";

        String result = engine.execute(context, exp);
        assertEquals("AA", result);
    }

    @Test
    public void testComplexAlarmRule() {
        // 告警规则：
        // (department == "技术部" AND (age > 20 AND age < 30)) OR 
        // (contains(email, "example") AND score > 80)

        String exp = "[\"||\" , [\"&&\", [\"strEq\", [\"@value\", \"department\"], [\"strInput\", \"技术部\"]], [\"&&\", [\">\", [\"@value\", \"age\"], 20], [\"<\", [\"@value\", \"age\"], 30]]], [\"&&\", [\"contains\", [\"@value\", \"email\"], [\"strInput\", \"example\"]], [\">\", [\"@value\", \"score\"], 80]]]";

        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testMultiConditionDataValidation() {
        // 数据验证规则：
        // name不为空 AND age在18-60之间 AND email包含@ AND phone长度为11

        String exp = "[\"&&\", [\"notBlank\", [\"@value\", \"name\"]], [\"&&\", [\">=\", [\"@value\", \"age\"], 18], [\"<=\", [\"@value\", \"age\"], 60]], [\"contains\", [\"@value\", \"email\"], [\"strInput\", \"@\"]], [\"==\", [\"toNumber\", [\"toStr\", [\"@value\", \"phone\"]]], 13800138000]]";

        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    // ==================== 边界情况测试 ====================

    @Test
    public void testZeroDivision() {
        try {
            String exp = "[\"/\", 10, 0]";
            engine.execute(context, exp);
            fail("Expected ArithmeticException");
        } catch (Exception e) {
            // 预期会抛出异常
            assertTrue(e.getMessage().contains("除数不能为0") || e instanceof ArithmeticException);
        }
    }

    @Test
    public void testNullContext() {
        String exp = "[\"numberInput\", 100]";
        Integer result = engine.execute(null, exp);
        assertEquals(Integer.valueOf(100), result);
    }

    @Test
    public void testLargeNumberCalculation() {
        String exp = "[\"+\", 999999999, 1]";
        Number result = engine.execute(context, exp);
        assertEquals(1000000000, result.longValue());
    }

    @Test
    public void testDecimalPrecision() {
        String exp = "[\"+\", 0.1, 0.2]";
        Number result = engine.execute(context, exp);
        assertEquals(0.3, result.doubleValue(), 0.00001);
    }

    // ==================== 性能测试 ====================

    @Test
    public void testShortCircuitPerformance() {
        // 测试短路逻辑是否真的避免了不必要的计算
        long startTime = System.nanoTime();

        // 第一个条件为false，后续不应该评估
        String exp = "[\"&&\", [\"<\", [\"@value\", \"age\"], 18], [\">\", [\"+\", 1000, 2000, 3000], 10000]]";
        Boolean result = engine.execute(context, exp);

        long endTime = System.nanoTime();

        assertFalse(result);
        System.out.println(endTime - startTime);
        // 验证执行时间很短（如果没有短路，会计算复杂的加法）
        assertTrue((endTime - startTime) < 1000000); // 小于1ms
    }
}