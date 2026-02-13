package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 规则DSL完整流程测试
 * <p>
 * 测试流程: DSL字符串 -> Tokenizer -> Token列表 -> Parser -> 表达式 -> RuleEngine -> 结果
 * <p>
 * 这是最符合实际使用场景的测试！
 *
 * @author Test
 */
public class RuleDSLIntegrationTest {

    private RuleEngine ruleEngine;

    @Before
    public void setUp() {
        ruleEngine = RuleEngine.getInstance();
        System.out.println("========================================");
    }

    // ==================== DSL语法测试 ====================

    @Test
    public void testSimpleArithmeticDSL() {
        System.out.println("测试: 简单算术DSL - 10 + 20");

        String dsl = "10 + 20";

        // Tokenize
        List<String> tokens = RuleTokenizer.tokenize(dsl);
        System.out.println("Tokens: " + tokens);

        // Parse
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        // Execute
        Number result = ruleEngine.execute(new HashMap<>(), ruleExpression);

        assertEquals("10 + 20 = 30", new BigDecimal("30"), new BigDecimal(result.toString()));
        System.out.println("✓ 结果: " + result);
    }

    @Test
    public void testComplexArithmeticDSL() {
        System.out.println("测试: 复杂算术DSL");

        String dsl = "((10 + 20) * 3) - (5 / 2)";

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Number result = ruleEngine.execute(new HashMap<>(), ruleExpression);

        // (10 + 20) * 3 - 5 / 2 = 30 * 3 - 2.5 = 87.5
        assertEquals(new BigDecimal("87.5"), new BigDecimal(result.toString()));
        System.out.println("✓ DSL: " + dsl);
        System.out.println("✓ 结果: " + result);
    }

    @Test
    public void testVariableArithmeticDSL() {
        System.out.println("测试: 变量算术DSL");

        String dsl = "(@price * @quantity) + @tax";

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("price", 100.5);
        context.put("quantity", 3);
        context.put("tax", 15.5);

        Number result = ruleEngine.execute(context, ruleExpression);

        assertEquals(new BigDecimal("317"), new BigDecimal(result.toString()));
        System.out.println("✓ DSL: " + dsl);
        System.out.println("✓ 结果: (100.5 * 3) + 15.5 = " + result);
    }

    // ==================== 字符串DSL测试 ====================

    @Test
    public void testStringLiteralsDSL() {
        System.out.println("测试: 字符串字面量DSL");

        // 双引号字符串
        String dsl1 = "strEq(@name, \"VIP_USER\")";
        List<String> tokens1 = RuleTokenizer.tokenize(dsl1);
        Object expression1 = RuleExpressionParser.parse(tokens1);
        String ruleExpression1 = JSON.toJSONString(expression1);
        System.out.println("Expression: " + ruleExpression1);

        Map<String, Object> context1 = new HashMap<>();
        context1.put("name", "VIP_USER");
        Boolean result1 = ruleEngine.execute(context1, ruleExpression1);
        assertTrue("双引号字符串应该匹配", result1);
        System.out.println("✓ 双引号: " + dsl1 + " = " + result1);

        // 单引号字符串
        String dsl2 = "strEq(@name, 'VIP_USER')";
        List<String> tokens2 = RuleTokenizer.tokenize(dsl2);
        Object expression2 = RuleExpressionParser.parse(tokens2);
        String ruleExpression2 = JSON.toJSONString(expression2);
        System.out.println("Expression: " + ruleExpression2);

        Map<String, Object> context2 = new HashMap<>();
        context2.put("name", "VIP_USER");
        Boolean result2 = ruleEngine.execute(context2, ruleExpression2);
        assertTrue("单引号字符串应该匹配", result2);
        System.out.println("✓ 单引号: " + dsl2 + " = " + result2);
    }

    @Test
    public void testStringContainsDSL() {
        System.out.println("测试: 字符串包含DSL");

        String dsl = "contains(@userName, 'VIP')";

        Object tokens = RuleTokenizer.compile(dsl);
        String ruleExpression = JSON.toJSONString(tokens);
        System.out.println("Expression: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("userName", "VIP_MEMBER_001");
        Boolean result = ruleEngine.execute(context, ruleExpression);

        assertTrue("应该包含VIP", result);
        System.out.println("✓ DSL: " + dsl);
        System.out.println("✓ 结果: " + result);
    }

    // ==================== 条件表达式DSL测试 ====================

    @Test
    public void testSimpleIfDSL() {
        System.out.println("测试: 简单条件DSL");

        String dsl = "if (@score >= 60) then 'Pass' else 'Fail'";

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        System.out.println("Tokens: " + tokens);

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        // 测试及格
        Map<String, Object> context1 = new HashMap<>();
        context1.put("score", 75);
        String result1 = ruleEngine.execute(context1, ruleExpression);
        assertEquals("Pass", result1);
        System.out.println("✓ score=75: " + result1);

        // 测试不及格
        Map<String, Object> context2 = new HashMap<>();
        context2.put("score", 45);
        String result2 = ruleEngine.execute(context2, ruleExpression);
        assertEquals("Fail", result2);
        System.out.println("✓ score=45: " + result2);
    }

    @Test
    public void testNestedIfDSL() {
        System.out.println("测试: 嵌套条件DSL - 成绩等级");

        String dsl =
                "if (@score >= 90) then 'A' " +
                        "else if (@score >= 80) then 'B' " +
                        "else if (@score >= 70) then 'C' " +
                        "else if (@score >= 60) then 'D' " +
                        "else 'F'";
        System.out.println("DSL: " + dsl);

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        // 测试各个等级
        int[] scores = {95, 85, 75, 65, 55};
        String[] expectedGrades = {"A", "B", "C", "D", "F"};

        for (int i = 0; i < scores.length; i++) {
            Map<String, Object> context = new HashMap<>();
            context.put("score", scores[i]);
            String result = ruleEngine.execute(context, ruleExpression);
            assertEquals(expectedGrades[i], result);
            System.out.println("✓ score=" + scores[i] + ": " + result);
        }
    }

    // ==================== 真实业务场景DSL ====================

    @Test
    public void testEcommercePriceDSL() {
        System.out.println("测试: 电商价格计算DSL");

        String dsl =
                "if (strEq(@memberLevel, 'gold')) then (@price * 0.8) " +
                        "else if (strEq(@memberLevel, 'silver')) then (@price * 0.9) " +
                        "else @price";

        System.out.println("DSL: " + dsl);

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        // 测试金卡
        Map<String, Object> goldContext = new HashMap<>();
        goldContext.put("memberLevel", "gold");
        goldContext.put("price", 100);
        Number goldResult = ruleEngine.execute(goldContext, ruleExpression);
        assertEquals(new BigDecimal("80"), new BigDecimal(goldResult.toString()));
        System.out.println("✓ 金卡: 100 * 0.8 = " + goldResult);

        // 测试银卡
        Map<String, Object> silverContext = new HashMap<>();
        silverContext.put("memberLevel", "silver");
        silverContext.put("price", 100);
        Number silverResult = ruleEngine.execute(silverContext, ruleExpression);
        assertEquals(new BigDecimal("90"), new BigDecimal(silverResult.toString()));
        System.out.println("✓ 银卡: 100 * 0.9 = " + silverResult);

        // 测试普通
        Map<String, Object> normalContext = new HashMap<>();
        normalContext.put("memberLevel", "normal");
        normalContext.put("price", 100);
        Number normalResult = ruleEngine.execute(normalContext, ruleExpression);
        assertEquals(new BigDecimal("100"), new BigDecimal(normalResult.toString()));
        System.out.println("✓ 普通: 原价 = " + normalResult);
    }

    @Test
    public void testComplexDiscountDSL() {
        System.out.println("测试: 复杂折扣规则DSL");

        String dsl =
                "if ((@amount > 1000) && strEq(@memberLevel, 'vip')) then (@amount * 0.7) " +
                        "else if (@amount > 500) then (@amount * 0.85) " +
                        "else if (@amount > 100) then (@amount * 0.95) " +
                        "else @amount";

        System.out.println("DSL: " + dsl);

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        // VIP大额
        Map<String, Object> vipContext = new HashMap<>();
        vipContext.put("amount", 1500);
        vipContext.put("memberLevel", "vip");
        Number vipResult = ruleEngine.execute(vipContext, ruleExpression);
        assertEquals(new BigDecimal("1050"), new BigDecimal(vipResult.toString()));
        System.out.println("✓ VIP 1500: 1500 * 0.7 = " + vipResult);

        // 中等金额
        Map<String, Object> mediumContext = new HashMap<>();
        mediumContext.put("amount", 600);
        mediumContext.put("memberLevel", "normal");
        Number mediumResult = ruleEngine.execute(mediumContext, ruleExpression);
        assertEquals(new BigDecimal("510"), new BigDecimal(mediumResult.toString()));
        System.out.println("✓ 普通 600: 600 * 0.85 = " + mediumResult);
    }

    @Test
    public void testLoanApprovalDSL() {
        System.out.println("测试: 贷款审批DSL");

        String dsl =
                "(@creditScore >= 700) && " +
                        "(@income > (@debt * 3)) && " +
                        "((@age >= 22) && (@age <= 60))";

        System.out.println("DSL: " + dsl);

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        // 合格申请
        Map<String, Object> qualifiedContext = new HashMap<>();
        qualifiedContext.put("creditScore", 750);
        qualifiedContext.put("income", 15000);
        qualifiedContext.put("debt", 3000);
        qualifiedContext.put("age", 35);
        Boolean qualifiedResult = ruleEngine.execute(qualifiedContext, ruleExpression);
        assertTrue("合格申请应该通过", qualifiedResult);
        System.out.println("✓ 合格申请: " + qualifiedResult);

        // 不合格申请
        Map<String, Object> unqualifiedContext = new HashMap<>();
        unqualifiedContext.put("creditScore", 750);
        unqualifiedContext.put("income", 15000);
        unqualifiedContext.put("debt", 3000);
        unqualifiedContext.put("age", 65);
        Boolean unqualifiedResult = ruleEngine.execute(unqualifiedContext, ruleExpression);
        assertFalse("年龄超限应该拒绝", unqualifiedResult);
        System.out.println("✓ 不合格申请: " + unqualifiedResult);
    }

    @Test
    public void testRiskControlDSL() {
        System.out.println("测试: 风控规则DSL");

        String dsl = "(@amount > (@avgAmount * 10)) || " +
                        "(@transCount24h > 50) || " +
                "((strNeq(@city, @lastCity)) && (@historyDays < 30))";

        System.out.println("DSL: " + dsl);

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        System.out.println("tokens: " + tokens);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        // 高风险: 异地登录 + 新用户
        Map<String, Object> highRiskContext = new HashMap<>();
        highRiskContext.put("amount", 500);
        highRiskContext.put("avgAmount", 200);
        highRiskContext.put("transCount24h", 30);
        highRiskContext.put("city", "Beijing");
        highRiskContext.put("lastCity", "Shanghai");
        highRiskContext.put("historyDays", 15);
        Boolean highRiskResult = ruleEngine.execute(highRiskContext, ruleExpression);
        assertTrue("异地+新用户应为高风险", highRiskResult);
        System.out.println("✓ 高风险场景: " + highRiskResult);

        // 低风险
        Map<String, Object> lowRiskContext = new HashMap<>();
        lowRiskContext.put("amount", 500);
        lowRiskContext.put("avgAmount", 200);
        lowRiskContext.put("transCount24h", 30);
        lowRiskContext.put("city", "Beijing");
        lowRiskContext.put("lastCity", "Beijing");
        lowRiskContext.put("historyDays", 365);
        Boolean lowRiskResult = ruleEngine.execute(lowRiskContext, ruleExpression);
        assertFalse("同城+老用户应为低风险", lowRiskResult);
        System.out.println("✓ 低风险场景: " + lowRiskResult);
    }

    // ==================== 数学函数DSL ====================

    @Test
    public void testMathFunctionsDSL() {
        System.out.println("测试: 数学函数DSL");

        String dsl = "abs(ceil(-3.2))";

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Number result = ruleEngine.execute(new HashMap<>(), ruleExpression);

        // ceil(-3.2) = -3, abs(-3) = 3
        assertEquals(new BigDecimal("3"), new BigDecimal(result.toString()));
        System.out.println("✓ DSL: " + dsl);
        System.out.println("✓ 结果: " + result);
    }

    @Test
    public void testPointsCalculationDSL() {
        System.out.println("测试: 积分计算DSL");

        String dsl = "floor(@amount * @multiplier)";

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("amount", 158.8);
        context.put("multiplier", 1.5);

        Number result = ruleEngine.execute(context, ruleExpression);

        // floor(158.8 * 1.5) = floor(238.2) = 238
        assertEquals(new BigDecimal("238"), new BigDecimal(result.toString()));
        System.out.println("✓ DSL: " + dsl);
        System.out.println("✓ floor(158.8 * 1.5) = " + result);
    }

    // ==================== 复杂组合DSL ====================

    @Test
    public void testCompleteOrderCalculationDSL() {
        System.out.println("测试: 完整订单计算DSL");

        String dsl =
                "((@price * " +
                        "  if (strEq(@memberLevel, 'vip')) then 0.8 else 1.0) * " +
                        "  if (@quantity >= 10) then 0.95 else 1.0) - " +
                        "@coupon";

        System.out.println("DSL: " + dsl);

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("price", 100);
        context.put("memberLevel", "vip");
        context.put("quantity", 12);
        context.put("coupon", 20);

        Number result = ruleEngine.execute(context, ruleExpression);

        // 100 * 0.8 * 0.95 - 20 = 76 - 20 = 56
        assertEquals(new BigDecimal("56"), new BigDecimal(result.toString()));
        System.out.println("✓ VIP购买12件: 100*0.8*0.95-20 = " + result);
    }

    // ==================== 便捷方法测试 ====================

    @Test
    public void testCompileMethod() {
        System.out.println("测试: 便捷编译方法");

        String dsl = "if (@age >= 18) then 'Adult' else 'Minor'";

        // 使用便捷方法直接编译
        Object expression = RuleTokenizer.compile(dsl);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("age", 25);
        String result = ruleEngine.execute(context, ruleExpression);

        assertEquals("Adult", result);
        System.out.println("✓ DSL: " + dsl);
        System.out.println("✓ age=25: " + result);
    }

    @Test
    public void testDSLWithComments() {
        System.out.println("测试: 多行DSL（无注释支持）");

        // 虽然Java字符串可以多行，但tokenizer会忽略空白
        String dsl =
                "if (@score >= 90) then 'A' " +
                        "else if (@score >= 80) then 'B' " +
                        "else 'C'";

        Object expression = RuleTokenizer.compile(dsl);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("score", 85);
        String result = ruleEngine.execute(context, ruleExpression);

        assertEquals("B", result);
        System.out.println("✓ 多行DSL正常工作");
        System.out.println("✓ score=85: " + result);
    }

    // ==================== 错误处理测试 ====================

    @Test(expected = IllegalArgumentException.class)
    public void testUnclosedString() {
        System.out.println("测试: 未闭合的字符串");

        String dsl = "strEq(@name, 'VIP)";  // 缺少闭合引号

        RuleTokenizer.tokenize(dsl);
        fail("应该抛出异常");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCharacter() {
        System.out.println("测试: 无效字符");

        String dsl = "@amount $ 100";  // $ 是无效字符

        RuleTokenizer.tokenize(dsl);
        fail("应该抛出异常");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyDSL() {
        System.out.println("测试: 空DSL");

        RuleTokenizer.tokenize("");
        fail("应该抛出异常");
    }

    // ==================== 边界测试 ====================

    @Test
    public void testNegativeNumbers() {
        System.out.println("测试: 负数DSL");

        String dsl = "-10 + 20";

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Number result = ruleEngine.execute(new HashMap<>(), ruleExpression);

        assertEquals(new BigDecimal("10"), new BigDecimal(result.toString()));
        System.out.println("✓ DSL: " + dsl);
        System.out.println("✓ 结果: " + result);
    }

    @Test
    public void testDecimalNumbers() {
        System.out.println("测试: 小数DSL");

        String dsl = "3.14 * 2";

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Number result = ruleEngine.execute(new HashMap<>(), ruleExpression);

        assertEquals(new BigDecimal("6.28"), new BigDecimal(result.toString()));
        System.out.println("✓ DSL: " + dsl);
        System.out.println("✓ 结果: " + result);
    }

    @Test
    public void testEscapedStrings() {
        System.out.println("测试: 转义字符串DSL");

        String dsl = "strEq(@text, \"Hello\\nWorld\")";

        List<String> tokens = RuleTokenizer.tokenize(dsl);
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("Expression: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("text", "Hello\nWorld");
        Boolean result = ruleEngine.execute(context, ruleExpression);

        assertTrue("转义字符应该正确处理", result);
        System.out.println("✓ 转义字符处理正确");
    }
}