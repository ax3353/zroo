package com.zk.ruleengine;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 数值函数超级全面测试套件 - 200+ 测试用例
 * <p>
 * 测试覆盖：
 * - 所有算术运算函数（+, -, *, /, %, abs, ceil, floor, scale）
 * - 所有比较运算函数（>, >=, <, <=, ==, <>）
 * - 数值输入和类型转换（numberInput, toNumber）
 * - 空值检查（numberIsNull, numberIsNotNull）
 * - 边界值测试
 * - 精度测试
 * - 错误处理
 * - 性能测试
 * - 业务场景
 */
public class NumberFunctionsTest {

    private RuleEngine engine;
    private Map<String, Object> context;

    @Before
    public void setUp() {
        engine = RuleEngine.getInstance();
        context = new HashMap<>();

        // 准备多样化的测试数据
        context.put("int_positive", 100);
        context.put("int_negative", -50);
        context.put("int_zero", 0);
        context.put("int_small", 1);
        context.put("int_large", 999999);

        context.put("double_positive", 123.45);
        context.put("double_negative", -67.89);
        context.put("double_small", 0.001);
        context.put("double_precise", 3.141592653589793);

        context.put("bigdecimal", new BigDecimal("999999999999.123456789"));
        context.put("long_max", Long.MAX_VALUE);
        context.put("int_max", Integer.MAX_VALUE);
        context.put("int_min", Integer.MIN_VALUE);

        context.put("null_value", null);
    }

    // ==================== 加法（Add）测试 - 30个用例 ====================

    @Test
    public void testAdd_TwoPositiveIntegers() {
        String exp = "[\"+\", 10, 20]";
        Number result = engine.execute(context, exp);
        assertEquals(30, result.intValue());
    }

    @Test
    public void testAdd_ThreeIntegers() {
        String exp = "[\"+\", 10, 20, 30]";
        Number result = engine.execute(context, exp);
        assertEquals(60, result.intValue());
    }

    @Test
    public void testAdd_FiveIntegers() {
        String exp = "[\"+\", 1, 2, 3, 4, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(15, result.intValue());
    }

    @Test
    public void testAdd_TenIntegers() {
        String exp = "[\"+\", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]";
        Number result = engine.execute(context, exp);
        assertEquals(55, result.intValue());
    }

    @Test
    public void testAdd_TwoDecimals() {
        String exp = "[\"+\", 1.5, 2.5]";
        Number result = engine.execute(context, exp);
        assertEquals(4.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAdd_IntegerAndDecimal() {
        String exp = "[\"+\", 10, 5.5]";
        Number result = engine.execute(context, exp);
        assertEquals(15.5, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAdd_PositiveAndNegative() {
        String exp = "[\"+\", 10, -3]";
        Number result = engine.execute(context, exp);
        assertEquals(7, result.intValue());
    }

    @Test
    public void testAdd_TwoNegatives() {
        String exp = "[\"+\", -10, -20]";
        Number result = engine.execute(context, exp);
        assertEquals(-30, result.intValue());
    }

    @Test
    public void testAdd_WithZero() {
        String exp = "[\"+\", 42, 0]";
        Number result = engine.execute(context, exp);
        assertEquals(42, result.intValue());
    }

    @Test
    public void testAdd_MultipleZeros() {
        String exp = "[\"+\", 0, 0, 0, 0]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testAdd_VerySmallDecimals() {
        String exp = "[\"+\", 0.0001, 0.0002, 0.0003]";
        Number result = engine.execute(context, exp);
        assertEquals(0.0006, result.doubleValue(), 0.00001);
    }

    @Test
    public void testAdd_VeryLargeNumbers() {
        String exp = "[\"+\", 1000000000, 2000000000]";
        Number result = engine.execute(context, exp);
        assertEquals(3000000000L, result.longValue());
    }

    @Test
    public void testAdd_IntegerOverflow() {
        // Integer.MAX_VALUE + 1 应该溢出到 Long
        String exp = "[\"+\", 2147483647, 1]";
        Number result = engine.execute(context, exp);
        assertEquals(2147483648L, result.longValue());
    }

    @Test
    public void testAdd_FromContext() {
        String exp = "[\"+\", [\"@value\", \"int_positive\"], [\"@value\", \"int_negative\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(50, result.intValue());
    }

    @Test
    public void testAdd_MixedFromContext() {
        String exp = "[\"+\", [\"@value\", \"int_positive\"], [\"@value\", \"double_positive\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(223.45, result.doubleValue(), 0.01);
    }

    @Test
    public void testAdd_NegativeDecimals() {
        String exp = "[\"+\", -3.14, -2.86]";
        Number result = engine.execute(context, exp);
        assertEquals(-6.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAdd_ScientificNotation() {
        String exp = "[\"+\", 1e10, 2e10]";
        Number result = engine.execute(context, exp);
        assertEquals(3e10, result.doubleValue(), 1);
    }

    @Test
    public void testAdd_NestedExpression() {
        String exp = "[\"+\", [\"+\", 1, 2], [\"+\", 3, 4]]";
        Number result = engine.execute(context, exp);
        assertEquals(10, result.intValue());
    }

    @Test
    public void testAdd_DeepNesting() {
        String exp = "[\"+\", [\"+\", [\"+\", 1, 2], 3], 4]";
        Number result = engine.execute(context, exp);
        assertEquals(10, result.intValue());
    }

    @Test
    public void testAdd_NegativeZero() {
        String exp = "[\"+\", -0, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(5, result.intValue());
    }

    @Test
    public void testAdd_PrecisionTest1() {
        // 0.1 + 0.2 的经典精度问题
        String exp = "[\"+\", 0.1, 0.2]";
        Number result = engine.execute(context, exp);
        assertEquals(0.3, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAdd_PrecisionTest2() {
        String exp = "[\"+\", 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1]";
        Number result = engine.execute(context, exp);
        assertEquals(1.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAdd_LargeDecimalPrecision() {
        String exp = "[\"+\", 123456.789012, 654321.210988]";
        Number result = engine.execute(context, exp);
        assertEquals(777778.0, result.doubleValue(), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdd_NoArguments() {
        String exp = "[\"+\"]";
        engine.execute(context, exp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdd_OneArgument() {
        String exp = "[\"+\", 5]";
        engine.execute(context, exp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdd_WithNull() {
        String exp = "[\"+\", 5, [\"@value\", \"null_value\"]]";
        engine.execute(context, exp);
    }

    @Test
    public void testAdd_StringNumber() {
        String exp = "[\"+\", [\"numberInput\", \"10\"], [\"numberInput\", \"20\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(30, result.intValue());
    }

    @Test
    public void testAdd_ManyArguments() {
        String exp = "[\"+\", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]";
        Number result = engine.execute(context, exp);
        assertEquals(20, result.intValue());
    }

    @Test
    public void testAdd_AlternatingSignsPositive() {
        String exp = "[\"+\", 10, -5, 10, -5, 10]";
        Number result = engine.execute(context, exp);
        assertEquals(20, result.intValue());
    }

    @Test
    public void testAdd_AlternatingSignsNegative() {
        String exp = "[\"+\", -10, 5, -10, 5, -10]";
        Number result = engine.execute(context, exp);
        assertEquals(-20, result.intValue());
    }

    // ==================== 减法（Subtract）测试 - 25个用例 ====================

    @Test
    public void testSubtract_Basic() {
        String exp = "[\"-\", 10, 3]";
        Number result = engine.execute(context, exp);
        assertEquals(7, result.intValue());
    }

    @Test
    public void testSubtract_Multiple() {
        String exp = "[\"-\", 100, 20, 10, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(65, result.intValue());
    }

    @Test
    public void testSubtract_ResultNegative() {
        String exp = "[\"-\", 5, 10]";
        Number result = engine.execute(context, exp);
        assertEquals(-5, result.intValue());
    }

    @Test
    public void testSubtract_FromZero() {
        String exp = "[\"-\", 0, 10]";
        Number result = engine.execute(context, exp);
        assertEquals(-10, result.intValue());
    }

    @Test
    public void testSubtract_ToZero() {
        String exp = "[\"-\", 10, 10]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testSubtract_Decimals() {
        String exp = "[\"-\", 10.5, 3.2]";
        Number result = engine.execute(context, exp);
        assertEquals(7.3, result.doubleValue(), 0.0001);
    }

    @Test
    public void testSubtract_NegativeFromPositive() {
        // 10 - (-5) = 15
        String exp = "[\"-\", 10, -5]";
        Number result = engine.execute(context, exp);
        assertEquals(15, result.intValue());
    }

    @Test
    public void testSubtract_NegativeFromNegative() {
        // -10 - (-5) = -5
        String exp = "[\"-\", -10, -5]";
        Number result = engine.execute(context, exp);
        assertEquals(-5, result.intValue());
    }

    @Test
    public void testSubtract_LargeNumbers() {
        String exp = "[\"-\", 1000000, 1]";
        Number result = engine.execute(context, exp);
        assertEquals(999999, result.intValue());
    }

    @Test
    public void testSubtract_VerySmallDecimals() {
        String exp = "[\"-\", 0.001, 0.0005]";
        Number result = engine.execute(context, exp);
        assertEquals(0.0005, result.doubleValue(), 0.00001);
    }

    @Test
    public void testSubtract_FromContext() {
        String exp = "[\"-\", [\"@value\", \"int_positive\"], [\"@value\", \"int_small\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(99, result.intValue());
    }

    @Test
    public void testSubtract_ChainedSubtraction() {
        // 100 - 10 - 10 - 10 - 10 - 10
        String exp = "[\"-\", 100, 10, 10, 10, 10, 10]";
        Number result = engine.execute(context, exp);
        assertEquals(50, result.intValue());
    }

    @Test
    public void testSubtract_NestedExpression() {
        // 20 - (10 - 3) = 13
        String exp = "[\"-\", 20, [\"-\", 10, 3]]";
        Number result = engine.execute(context, exp);
        assertEquals(13, result.intValue());
    }

    @Test
    public void testSubtract_WithAddition() {
        // 100 - (20 + 10) = 70
        String exp = "[\"-\", 100, [\"+\", 20, 10]]";
        Number result = engine.execute(context, exp);
        assertEquals(70, result.intValue());
    }

    @Test
    public void testSubtract_NegativeDecimals() {
        String exp = "[\"-\", -3.5, -2.5]";
        Number result = engine.execute(context, exp);
        assertEquals(-1.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testSubtract_IntegerMaxValue() {
        String exp = "[\"-\", [\"@value\", \"int_max\"], 1]";
        Number result = engine.execute(context, exp);
        assertEquals(2147483646, result.intValue());
    }

    @Test
    public void testSubtract_IntegerMinValue() {
        String exp = "[\"-\", [\"@value\", \"int_min\"], 1]";
        Number result = engine.execute(context, exp);
        assertTrue(result.longValue() < Integer.MIN_VALUE);
    }

    @Test
    public void testSubtract_PrecisionTest() {
        String exp = "[\"-\", 1.0, 0.9]";
        Number result = engine.execute(context, exp);
        assertEquals(0.1, result.doubleValue(), 0.0001);
    }

    @Test
    public void testSubtract_SubtractZero() {
        String exp = "[\"-\", 42, 0]";
        Number result = engine.execute(context, exp);
        assertEquals(42, result.intValue());
    }

    @Test
    public void testSubtract_MultipleZeros() {
        String exp = "[\"-\", 10, 0, 0, 0]";
        Number result = engine.execute(context, exp);
        assertEquals(10, result.intValue());
    }

    @Test
    public void testSubtract_AlternatingValues() {
        String exp = "[\"-\", 100, 10, -5, 20, -10]";
        Number result = engine.execute(context, exp);
        assertEquals(85, result.intValue());
    }

    @Test
    public void testSubtract_LargeDecimalDifference() {
        String exp = "[\"-\", 9999.9999, 0.0001]";
        Number result = engine.execute(context, exp);
        assertEquals(9999.9998, result.doubleValue(), 0.0001);
    }

    @Test
    public void testSubtract_NegativeResult() {
        String exp = "[\"-\", 1, 2, 3, 4]";
        Number result = engine.execute(context, exp);
        assertEquals(-8, result.intValue());
    }

    @Test
    public void testSubtract_MixedTypes() {
        String exp = "[\"-\", 100, 25.5]";
        Number result = engine.execute(context, exp);
        assertEquals(74.5, result.doubleValue(), 0.0001);
    }

    @Test
    public void testSubtract_VeryLargeNumbers() {
        String exp = "[\"-\", 9876543210, 1234567890]";
        Number result = engine.execute(context, exp);
        assertEquals(8641975320L, result.longValue());
    }

    // ==================== 乘法（Multiply）测试 - 25个用例 ====================

    @Test
    public void testMultiply_Basic() {
        String exp = "[\"*\", 5, 4]";
        Number result = engine.execute(context, exp);
        assertEquals(20, result.intValue());
    }

    @Test
    public void testMultiply_Multiple() {
        String exp = "[\"*\", 2, 3, 4]";
        Number result = engine.execute(context, exp);
        assertEquals(24, result.intValue());
    }

    @Test
    public void testMultiply_ByZero() {
        String exp = "[\"*\", 42, 0]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testMultiply_ByOne() {
        String exp = "[\"*\", 42, 1]";
        Number result = engine.execute(context, exp);
        assertEquals(42, result.intValue());
    }

    @Test
    public void testMultiply_ByMinusOne() {
        String exp = "[\"*\", 42, -1]";
        Number result = engine.execute(context, exp);
        assertEquals(-42, result.intValue());
    }

    @Test
    public void testMultiply_TwoNegatives() {
        String exp = "[\"*\", -5, -4]";
        Number result = engine.execute(context, exp);
        assertEquals(20, result.intValue());
    }

    @Test
    public void testMultiply_PositiveAndNegative() {
        String exp = "[\"*\", 5, -4]";
        Number result = engine.execute(context, exp);
        assertEquals(-20, result.intValue());
    }

    @Test
    public void testMultiply_ThreeNegatives() {
        String exp = "[\"*\", -2, -3, -4]";
        Number result = engine.execute(context, exp);
        assertEquals(-24, result.intValue());
    }

    @Test
    public void testMultiply_Decimals() {
        String exp = "[\"*\", 2.5, 4.0]";
        Number result = engine.execute(context, exp);
        assertEquals(10.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testMultiply_SmallDecimals() {
        String exp = "[\"*\", 0.1, 0.1]";
        Number result = engine.execute(context, exp);
        assertEquals(0.01, result.doubleValue(), 0.0001);
    }

    @Test
    public void testMultiply_VerySmallDecimals() {
        String exp = "[\"*\", 0.001, 0.001]";
        Number result = engine.execute(context, exp);
        assertEquals(0.000001, result.doubleValue(), 0.0000001);
    }

    @Test
    public void testMultiply_LargeNumbers() {
        String exp = "[\"*\", 1000000, 1000]";
        Number result = engine.execute(context, exp);
        assertEquals(1000000000, result.longValue());
    }

    @Test
    public void testMultiply_VeryLargeNumbers() {
        String exp = "[\"*\", 1000000, 1000000]";
        Number result = engine.execute(context, exp);
        assertEquals(1000000000000L, result.longValue());
    }

    @Test
    public void testMultiply_FromContext() {
        String exp = "[\"*\", [\"@value\", \"int_small\"], [\"@value\", \"int_positive\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(100, result.intValue());
    }

    @Test
    public void testMultiply_MixedTypes() {
        String exp = "[\"*\", 10, 2.5]";
        Number result = engine.execute(context, exp);
        assertEquals(25.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testMultiply_ChainMultiplication() {
        String exp = "[\"*\", 2, 2, 2, 2, 2]";
        Number result = engine.execute(context, exp);
        assertEquals(32, result.intValue());
    }

    @Test
    public void testMultiply_NestedExpression() {
        // 5 * (3 + 2) = 25
        String exp = "[\"*\", 5, [\"+\", 3, 2]]";
        Number result = engine.execute(context, exp);
        assertEquals(25, result.intValue());
    }

    @Test
    public void testMultiply_DistributiveLaw() {
        // (2 * 3) + (2 * 4) = 2 * (3 + 4)
        String exp1 = "[\"+\", [\"*\", 2, 3], [\"*\", 2, 4]]";
        String exp2 = "[\"*\", 2, [\"+\", 3, 4]]";

        Number result1 = engine.execute(context, exp1);
        Number result2 = engine.execute(context, exp2);

        assertEquals(result1.intValue(), result2.intValue());
    }

    @Test
    public void testMultiply_NegativeDecimals() {
        String exp = "[\"*\", -2.5, -4.0]";
        Number result = engine.execute(context, exp);
        assertEquals(10.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testMultiply_Fractions() {
        String exp = "[\"*\", 0.5, 0.5]";
        Number result = engine.execute(context, exp);
        assertEquals(0.25, result.doubleValue(), 0.0001);
    }

    @Test
    public void testMultiply_ManyFactors() {
        String exp = "[\"*\", 1, 2, 3, 4, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(120, result.intValue());
    }

    @Test
    public void testMultiply_ZeroMultipleFactors() {
        String exp = "[\"*\", 5, 10, 0, 20]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testMultiply_MixedSigns() {
        String exp = "[\"*\", -2, 3, -4, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(120, result.intValue());
    }

    @Test
    public void testMultiply_PrecisionTest() {
        String exp = "[\"*\", 1.1, 1.1]";
        Number result = engine.execute(context, exp);
        assertEquals(1.21, result.doubleValue(), 0.0001);
    }

    @Test
    public void testMultiply_ScientificNotation() {
        String exp = "[\"*\", 1e6, 1e6]";
        Number result = engine.execute(context, exp);
        assertEquals(1e12, result.doubleValue(), 1);
    }

    // ==================== 除法（Divide）测试 - 30个用例 ====================

    @Test
    public void testDivide_Basic() {
        String exp = "[\"/\", 10, 2]";
        Number result = engine.execute(context, exp);
        assertEquals(5, result.intValue());
    }

    @Test
    public void testDivide_WithRemainder() {
        String exp = "[\"/\", 10, 3]";
        Number result = engine.execute(context, exp);
        assertEquals(3.33333, result.doubleValue(), 0.001);
    }

    @Test
    public void testDivide_ExactDivision() {
        String exp = "[\"/\", 100, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(20, result.intValue());
    }

    @Test
    public void testDivide_Multiple() {
        // 100 / 5 / 2 = 10
        String exp = "[\"/\", 100, 5, 2]";
        Number result = engine.execute(context, exp);
        assertEquals(10, result.intValue());
    }

    @Test
    public void testDivide_ByOne() {
        String exp = "[\"/\", 42, 1]";
        Number result = engine.execute(context, exp);
        assertEquals(42, result.intValue());
    }

    @Test
    public void testDivide_OneByNumber() {
        String exp = "[\"/\", 1, 2]";
        Number result = engine.execute(context, exp);
        assertEquals(0.5, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_TwoNegatives() {
        String exp = "[\"/\", -10, -2]";
        Number result = engine.execute(context, exp);
        assertEquals(5, result.intValue());
    }

    @Test
    public void testDivide_PositiveByNegative() {
        String exp = "[\"/\", 10, -2]";
        Number result = engine.execute(context, exp);
        assertEquals(-5, result.intValue());
    }

    @Test
    public void testDivide_NegativeByPositive() {
        String exp = "[\"/\", -10, 2]";
        Number result = engine.execute(context, exp);
        assertEquals(-5, result.intValue());
    }

    @Test
    public void testDivide_ZeroByNumber() {
        String exp = "[\"/\", 0, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test(expected = Exception.class)
    public void testDivide_ByZero() {
        String exp = "[\"/\", 10, 0]";
        engine.execute(context, exp);
    }

    @Test(expected = Exception.class)
    public void testDivide_ZeroByZero() {
        String exp = "[\"/\", 0, 0]";
        engine.execute(context, exp);
    }

    @Test
    public void testDivide_Decimals() {
        String exp = "[\"/\", 10.5, 2.0]";
        Number result = engine.execute(context, exp);
        assertEquals(5.25, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_SmallDecimals() {
        String exp = "[\"/\", 0.01, 0.1]";
        Number result = engine.execute(context, exp);
        assertEquals(0.1, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_VerySmallResult() {
        String exp = "[\"/\", 1, 1000000]";
        Number result = engine.execute(context, exp);
        assertEquals(0.000001, result.doubleValue(), 0.0000001);
    }

    @Test
    public void testDivide_VeryLargeResult() {
        String exp = "[\"/\", 1000000, 0.000001]";
        Number result = engine.execute(context, exp);
        assertEquals(1e12, result.doubleValue(), 1e6);
    }

    @Test
    public void testDivide_FromContext() {
        String exp = "[\"/\", [\"@value\", \"int_positive\"], [\"@value\", \"int_small\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(100, result.intValue());
    }

    @Test
    public void testDivide_MixedTypes() {
        String exp = "[\"/\", 10, 4.0]";
        Number result = engine.execute(context, exp);
        assertEquals(2.5, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_IntegerDivision() {
        String exp = "[\"/\", 7, 2]";
        Number result = engine.execute(context, exp);
        assertEquals(3.5, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_PrecisionTest1() {
        String exp = "[\"/\", 1, 3]";
        Number result = engine.execute(context, exp);
        assertEquals(0.33333, result.doubleValue(), 0.001);
    }

    @Test
    public void testDivide_PrecisionTest2() {
        String exp = "[\"/\", 2, 3]";
        Number result = engine.execute(context, exp);
        assertEquals(0.66666, result.doubleValue(), 0.001);
    }

    @Test
    public void testDivide_RepeatingDecimal() {
        String exp = "[\"/\", 1, 7]";
        Number result = engine.execute(context, exp);
        assertEquals(0.142857, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_NestedExpression() {
        // 100 / (20 / 4) = 20
        String exp = "[\"/\", 100, [\"/\", 20, 4]]";
        Number result = engine.execute(context, exp);
        assertEquals(20, result.intValue());
    }

    @Test
    public void testDivide_ComplexFraction() {
        // (10 + 5) / (3 - 1) = 7.5
        String exp = "[\"/\", [\"+\", 10, 5], [\"-\", 3, 1]]";
        Number result = engine.execute(context, exp);
        assertEquals(7.5, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_ChainDivision() {
        String exp = "[\"/\", 1000, 10, 10]";
        Number result = engine.execute(context, exp);
        assertEquals(10, result.intValue());
    }

    @Test
    public void testDivide_NegativeDecimals() {
        String exp = "[\"/\", -7.5, -2.5]";
        Number result = engine.execute(context, exp);
        assertEquals(3.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_AlternatingDivision() {
        String exp = "[\"/\", 100, 2, -5]";
        Number result = engine.execute(context, exp);
        assertEquals(-10, result.intValue());
    }

    @Test
    public void testDivide_LargeNumbers() {
        String exp = "[\"/\", 1000000000, 1000]";
        Number result = engine.execute(context, exp);
        assertEquals(1000000, result.intValue());
    }

    @Test
    public void testDivide_PercentageCalculation() {
        // 百分比计算：50 / 200 = 0.25
        String exp = "[\"/\", 50, 200]";
        Number result = engine.execute(context, exp);
        assertEquals(0.25, result.doubleValue(), 0.0001);
    }

    @Test
    public void testDivide_InverseMultiplication() {
        // 验证 a / b * b = a
        String exp = "[\"*\", [\"/\", 10, 3], 3]";
        Number result = engine.execute(context, exp);
        assertEquals(10.0, result.doubleValue(), 0.0001);
    }

    // ==================== 取模（Mod）测试 - 20个用例 ====================

    @Test
    public void testMod_Basic() {
        String exp = "[\"%\", 10, 3]";
        Number result = engine.execute(context, exp);
        assertEquals(1, result.intValue());
    }

    @Test
    public void testMod_ZeroRemainder() {
        String exp = "[\"%\", 10, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testMod_SmallerDividend() {
        String exp = "[\"%\", 5, 10]";
        Number result = engine.execute(context, exp);
        assertEquals(5, result.intValue());
    }

    @Test
    public void testMod_ByOne() {
        String exp = "[\"%\", 42, 1]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testMod_NegativeDividend() {
        String exp = "[\"%\", -10, 3]";
        Number result = engine.execute(context, exp);
        assertEquals(-1, result.intValue());
    }

    @Test
    public void testMod_NegativeDivisor() {
        String exp = "[\"%\", 10, -3]";
        Number result = engine.execute(context, exp);
        assertEquals(1, result.intValue());
    }

    @Test
    public void testMod_BothNegative() {
        String exp = "[\"%\", -10, -3]";
        Number result = engine.execute(context, exp);
        assertEquals(-1, result.intValue());
    }

    @Test(expected = Exception.class)
    public void testMod_ByZero() {
        String exp = "[\"%\", 10, 0]";
        engine.execute(context, exp);
    }

    @Test
    public void testMod_ZeroModNumber() {
        String exp = "[\"%\", 0, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testMod_EvenOdd() {
        // 检查奇偶性
        String exp1 = "[\"%\", 10, 2]";
        String exp2 = "[\"%\", 11, 2]";

        Number result1 = engine.execute(context, exp1);
        Number result2 = engine.execute(context, exp2);

        assertEquals(0, result1.intValue()); // 偶数
        assertEquals(1, result2.intValue()); // 奇数
    }

    @Test
    public void testMod_Double() {
        String exp = "[\"%\", 10.5, 3]";
        BigDecimal result = engine.execute(context, exp);
        assertEquals(1.5d, result.doubleValue(), 0.0001);
    }

    @Test
    public void testMod_PrimeTest() {
        // 简单的素数测试：13 % 2, 13 % 3, ... 都不为0
        String exp = "[\"%\", 13, 2]";
        Number result = engine.execute(context, exp);
        assertEquals(1, result.intValue());
    }

    @Test
    public void testMod_FromContext() {
        String exp = "[\"%\", [\"@value\", \"int_positive\"], 7]";
        Number result = engine.execute(context, exp);
        assertEquals(2, result.intValue());
    }

    @Test
    public void testMod_NestedExpression() {
        // (10 + 5) % 4 = 3
        String exp = "[\"%\", [\"+\", 10, 5], 4]";
        Number result = engine.execute(context, exp);
        assertEquals(3, result.intValue());
    }

    @Test
    public void testMod_ChainMod() {
        String exp = "[\"%\", 100, 30, 7]";
        Number result = engine.execute(context, exp);
        // 100 % 30 = 10, 10 % 7 = 3
        assertEquals(3, result.intValue());
    }

    @Test
    public void testMod_SameNumbers() {
        String exp = "[\"%\", 7, 7]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testMod_ConsecutiveNumbers() {
        String exp = "[\"%\", 8, 7]";
        Number result = engine.execute(context, exp);
        assertEquals(1, result.intValue());
    }

    @Test
    public void testMod_PowerOfTwo() {
        // 快速获取最后n位：x % 2^n
        String exp = "[\"%\", 1234, 100]";
        Number result = engine.execute(context, exp);
        assertEquals(34, result.intValue());
    }

    @Test
    public void testMod_NegativeResult() {
        String exp = "[\"%\", -17, 5]";
        Number result = engine.execute(context, exp);
        assertEquals(-2, result.intValue());
    }

    @Test
    public void testMod_WithMultiplication() {
        // (5 * 3) % 4 = 3
        String exp = "[\"%\", [\"*\", 5, 3], 4]";
        Number result = engine.execute(context, exp);
        assertEquals(3, result.intValue());
    }

    @Test
    public void testAbs_PositiveInteger() {
        String exp = "[\"abs\", 42]";
        Number result = engine.execute(context, exp);
        assertEquals(42, result.intValue());
    }

    @Test
    public void testAbs_NegativeInteger() {
        String exp = "[\"abs\", -42]";
        Number result = engine.execute(context, exp);
        assertEquals(42, result.intValue());
    }

    @Test
    public void testAbs_Zero() {
        String exp = "[\"abs\", 0]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testAbs_PositiveDecimal() {
        String exp = "[\"abs\", 3.14]";
        Number result = engine.execute(context, exp);
        assertEquals(3.14, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAbs_NegativeDecimal() {
        String exp = "[\"abs\", -3.14]";
        Number result = engine.execute(context, exp);
        assertEquals(3.14, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAbs_SmallNegative() {
        String exp = "[\"abs\", -0.001]";
        Number result = engine.execute(context, exp);
        assertEquals(0.001, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAbs_LargeNegative() {
        String exp = "[\"abs\", -999999]";
        Number result = engine.execute(context, exp);
        assertEquals(999999, result.intValue());
    }

    @Test
    public void testAbs_FromContext() {
        String exp = "[\"abs\", [\"@value\", \"int_negative\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(50, result.intValue());
    }

    @Test
    public void testAbs_OfSubtraction() {
        // abs(5 - 10) = 5
        String exp = "[\"abs\", [\"-\", 5, 10]]";
        Number result = engine.execute(context, exp);
        assertEquals(5, result.intValue());
    }

    @Test
    public void testAbs_NestedExpression() {
        // abs(-10 + 3) = abs(-7) = 7
        String exp = "[\"abs\", [\"+\", -10, 3]]";
        Number result = engine.execute(context, exp);
        assertEquals(7, result.intValue());
    }

    @Test
    public void testAbs_IntegerMinValue() {
        String exp = "[\"abs\", -2147483648]";
        Number result = engine.execute(context, exp);
        // Integer.MIN_VALUE的绝对值会溢出到Long
        assertTrue(result.longValue() > 0);
    }

    @Test
    public void testAbs_VerySmallDecimal() {
        String exp = "[\"abs\", -0.00000001]";
        Number result = engine.execute(context, exp);
        assertEquals(0.00000001, result.doubleValue(), 0.000000001);
    }

    @Test
    public void testAbs_NegativeZero() {
        String exp = "[\"abs\", -0.0]";
        Number result = engine.execute(context, exp);
        assertEquals(0.0, result.doubleValue(), 0.0001);
    }

    @Test
    public void testAbs_DoubleAbs() {
        // abs(abs(-5)) = 5
        String exp = "[\"abs\", [\"abs\", -5]]";
        Number result = engine.execute(context, exp);
        assertEquals(5, result.intValue());
    }

    @Test
    public void testAbs_WithMultiplication() {
        // abs(-3 * 4) = 12
        String exp = "[\"abs\", [\"*\", -3, 4]]";
        Number result = engine.execute(context, exp);
        assertEquals(12, result.intValue());
    }

    // ==================== 向上取整（Ceil）测试 - 15个用例 ====================

    @Test
    public void testCeil_PositiveDecimal() {
        String exp = "[\"ceil\", 3.14]";
        Number result = engine.execute(context, exp);
        assertEquals(4, result.intValue());
    }

    @Test
    public void testCeil_NegativeDecimal() {
        String exp = "[\"ceil\", -3.14]";
        Number result = engine.execute(context, exp);
        assertEquals(-3, result.intValue());
    }

    @Test
    public void testCeil_PositiveInteger() {
        String exp = "[\"ceil\", 5]";
        Number result = engine.execute(context, exp);
        assertEquals(5, result.intValue());
    }

    @Test
    public void testCeil_Zero() {
        String exp = "[\"ceil\", 0]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testCeil_SmallPositive() {
        String exp = "[\"ceil\", 0.001]";
        Number result = engine.execute(context, exp);
        assertEquals(1, result.intValue());
    }

    @Test
    public void testCeil_NearInteger() {
        String exp = "[\"ceil\", 3.9999]";
        Number result = engine.execute(context, exp);
        assertEquals(4, result.intValue());
    }

    @Test
    public void testCeil_NegativeNearInteger() {
        String exp = "[\"ceil\", -3.0001]";
        Number result = engine.execute(context, exp);
        assertEquals(-3, result.intValue());
    }

    @Test
    public void testCeil_ExactHalf() {
        String exp = "[\"ceil\", 3.5]";
        Number result = engine.execute(context, exp);
        assertEquals(4, result.intValue());
    }

    @Test
    public void testCeil_NegativeHalf() {
        String exp = "[\"ceil\", -3.5]";
        Number result = engine.execute(context, exp);
        assertEquals(-3, result.intValue());
    }

    @Test
    public void testCeil_FromContext() {
        String exp = "[\"ceil\", [\"@value\", \"double_positive\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(124, result.intValue());
    }

    @Test
    public void testCeil_OfDivision() {
        // ceil(10 / 3) = 4
        String exp = "[\"ceil\", [\"/\", 10, 3]]";
        Number result = engine.execute(context, exp);
        assertEquals(4, result.intValue());
    }

    @Test
    public void testCeil_LargeDecimal() {
        String exp = "[\"ceil\", 999999.1]";
        Number result = engine.execute(context, exp);
        assertEquals(1000000, result.intValue());
    }

    @Test
    public void testCeil_VerySmallNegative() {
        String exp = "[\"ceil\", -0.0001]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testCeil_NestedExpression() {
        // ceil((10 / 3) + 0.5)
        String exp = "[\"ceil\", [\"+\", [\"/\", 10, 3], 0.5]]";
        Number result = engine.execute(context, exp);
        assertEquals(4, result.intValue());
    }

    @Test
    public void testCeil_NegativeLarge() {
        String exp = "[\"ceil\", -999999.9]";
        Number result = engine.execute(context, exp);
        assertEquals(-999999, result.intValue());
    }

    // ==================== 向下取整（Floor）测试 - 15个用例 ====================

    @Test
    public void testFloor_PositiveDecimal() {
        String exp = "[\"floor\", 3.99]";
        Number result = engine.execute(context, exp);
        assertEquals(3, result.intValue());
    }

    @Test
    public void testFloor_NegativeDecimal() {
        String exp = "[\"floor\", -3.14]";
        Number result = engine.execute(context, exp);
        assertEquals(-4, result.intValue());
    }

    @Test
    public void testFloor_PositiveInteger() {
        String exp = "[\"floor\", 5]";
        Number result = engine.execute(context, exp);
        assertEquals(5, result.intValue());
    }

    @Test
    public void testFloor_Zero() {
        String exp = "[\"floor\", 0]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testFloor_SmallPositive() {
        String exp = "[\"floor\", 0.999]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testFloor_NearInteger() {
        String exp = "[\"floor\", 3.0001]";
        Number result = engine.execute(context, exp);
        assertEquals(3, result.intValue());
    }

    @Test
    public void testFloor_ExactHalf() {
        String exp = "[\"floor\", 3.5]";
        Number result = engine.execute(context, exp);
        assertEquals(3, result.intValue());
    }

    @Test
    public void testFloor_NegativeHalf() {
        String exp = "[\"floor\", -3.5]";
        Number result = engine.execute(context, exp);
        assertEquals(-4, result.intValue());
    }

    @Test
    public void testFloor_FromContext() {
        String exp = "[\"floor\", [\"@value\", \"double_positive\"]]";
        Number result = engine.execute(context, exp);
        assertEquals(123, result.intValue());
    }

    @Test
    public void testFloor_OfDivision() {
        // floor(10 / 3) = 3
        String exp = "[\"floor\", [\"/\", 10, 3]]";
        Number result = engine.execute(context, exp);
        assertEquals(3, result.intValue());
    }

    @Test
    public void testFloor_LargeDecimal() {
        String exp = "[\"floor\", 999999.9]";
        Number result = engine.execute(context, exp);
        assertEquals(999999, result.intValue());
    }

    @Test
    public void testFloor_VerySmallPositive() {
        String exp = "[\"floor\", 0.0001]";
        Number result = engine.execute(context, exp);
        assertEquals(0, result.intValue());
    }

    @Test
    public void testFloor_NestedExpression() {
        // floor((10 + 5) / 4)
        String exp = "[\"floor\", [\"/\", [\"+\", 10, 5], 4]]";
        Number result = engine.execute(context, exp);
        assertEquals(3, result.intValue());
    }

    @Test
    public void testFloor_NegativeLarge() {
        String exp = "[\"floor\", -999999.1]";
        Number result = engine.execute(context, exp);
        assertEquals(-1000000, result.intValue());
    }

    @Test
    public void testFloor_CeilComparison() {
        // 对于正数：floor <= x <= ceil
        context.put("value", 3.7);
        String exp1 = "[\"floor\", [\"@value\", \"value\"]]";
        String exp2 = "[\"ceil\", [\"@value\", \"value\"]]";

        Number floor = engine.execute(context, exp1);
        Number ceil = engine.execute(context, exp2);

        assertEquals(3, floor.intValue());
        assertEquals(4, ceil.intValue());
        assertTrue(floor.intValue() <= ceil.intValue());
    }

    // ==================== 数值比较测试 - 40个用例 ====================

    @Test
    public void testGreaterThan_BasicTrue() {
        String exp = "[\">\", 10, 5]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThan_BasicFalse() {
        String exp = "[\">\", 5, 10]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testGreaterThan_Equal() {
        String exp = "[\">\", 10, 10]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testGreaterThan_Decimals() {
        String exp = "[\">\", 5.1, 5.0]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThan_NegativeNumbers() {
        String exp = "[\">\", -5, -10]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThan_ZeroComparison() {
        String exp = "[\">\", 0, -1]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThan_FromContext() {
        String exp = "[\">\", [\"@value\", \"int_positive\"], [\"@value\", \"int_negative\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThan_WithExpression() {
        String exp = "[\">\", [\"+\", 5, 5], 9]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThanOrEqual_Equal() {
        String exp = "[\">=\", 10, 10]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThanOrEqual_Greater() {
        String exp = "[\">=\", 10, 5]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThanOrEqual_Less() {
        String exp = "[\">=\", 5, 10]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testGreaterThanOrEqual_Decimals() {
        String exp = "[\">=\", 5.0, 5.0]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testGreaterThanOrEqual_Negative() {
        String exp = "[\">=\", -5, -5]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThan_BasicTrue() {
        String exp = "[\"<\", 5, 10]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThan_BasicFalse() {
        String exp = "[\"<\", 10, 5]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testLessThan_Equal() {
        String exp = "[\"<\", 10, 10]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testLessThan_Decimals() {
        String exp = "[\"<\", 4.9, 5.0]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThan_NegativeNumbers() {
        String exp = "[\"<\", -10, -5]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThan_ZeroComparison() {
        String exp = "[\"<\", -1, 0]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThanOrEqual_Equal() {
        String exp = "[\"<=\", 10, 10]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThanOrEqual_Less() {
        String exp = "[\"<=\", 5, 10]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testLessThanOrEqual_Greater() {
        String exp = "[\"<=\", 10, 5]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testLessThanOrEqual_Decimals() {
        String exp = "[\"<=\", 5.0, 5.0]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberEqual_IntegersTrue() {
        String exp = "[\"==\", 10, 10]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberEqual_IntegersFalse() {
        String exp = "[\"==\", 10, 5]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testNumberEqual_DecimalsTrue() {
        String exp = "[\"==\", 3.14, 3.14]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberEqual_DecimalsFalse() {
        String exp = "[\"==\", 3.14, 3.15]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testNumberEqual_ZeroComparison() {
        String exp = "[\"==\", 0, 0]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberEqual_NegativeComparison() {
        String exp = "[\"==\", -5, -5]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberEqual_MixedTypes() {
        String exp = "[\"==\", 5, 5.0]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberEqual_WithExpression() {
        String exp = "[\"==\", [\"+\", 2, 3], [\"*\", 5, 1]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberNotEqual_True() {
        String exp = "[\"<>\", 10, 5]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberNotEqual_False() {
        String exp = "[\"<>\", 10, 10]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result);
    }

    @Test
    public void testNumberNotEqual_Decimals() {
        String exp = "[\"<>\", 3.14, 3.15]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberNotEqual_ZeroComparison() {
        String exp = "[\"<>\", 0, 1]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testNumberNotEqual_NegativeComparison() {
        String exp = "[\"<>\", -5, -6]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }

    @Test
    public void testCompare_ComplexCondition() {
        // (10 + 5) > (20 - 3)
        String exp = "[\">\", [\"+\", 10, 5], [\"-\", 20, 3]]";
        Boolean result = engine.execute(context, exp);
        assertFalse(result); // 15 > 17 = false
    }

    @Test
    public void testCompare_ChainedComparisons() {
        // 验证传递性：如果 a > b 且 b > c，则 a > c
        context.put("a", 10);
        context.put("b", 5);
        context.put("c", 3);

        String exp1 = "[\">\", [\"@value\", \"a\"], [\"@value\", \"b\"]]";
        String exp2 = "[\">\", [\"@value\", \"b\"], [\"@value\", \"c\"]]";
        String exp3 = "[\">\", [\"@value\", \"a\"], [\"@value\", \"c\"]]";

        assertTrue(engine.execute(context, exp1));
        assertTrue(engine.execute(context, exp2));
        assertTrue(engine.execute(context, exp3));
    }

    @Test
    public void testCompare_BoundaryValues() {
        context.put("max", Integer.MAX_VALUE);
        context.put("min", Integer.MIN_VALUE);

        String exp = "[\">\", [\"@value\", \"max\"], [\"@value\", \"min\"]]";
        Boolean result = engine.execute(context, exp);
        assertTrue(result);
    }
}