package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * RuleExpressionParser 集成测试
 * 测试流程: Token列表 -> Parser解析 -> 生成表达式 -> RuleEngine执行 -> Assert验证结果
 *
 * @author zk
 */
public class RuleExpressionParserIntegrationTest {

    private RuleEngine ruleEngine;

    @Before
    public void setUp() {
        ruleEngine = RuleEngine.getInstance();
        System.out.println("========================================");
    }

    // ==================== 算术运算测试 ====================

    @Test
    public void testSimpleAddition() {
        System.out.println("测试: 简单加法 10 + 20 = 30");

        // 构建表达式: 10 + 20
        List<String> tokens = Arrays.asList(
                "(", "+", "(&number)10", "(&number)20", ")"
        );

        // 解析生成表达式
        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 执行规则
        Map<String, Object> context = new HashMap<>();
        Number result = ruleEngine.execute(context, ruleExpression);

        // 验证结果
        assertEquals("10 + 20 应该等于 30", new BigDecimal("30"), new BigDecimal(result.toString()));
        System.out.println("✓ 测试通过: 结果 = " + result);
    }

    @Test
    public void testComplexArithmetic() {
        System.out.println("测试: 复杂算术 (10 + 20) * 3 - 5 / 2");

        // 构建表达式: ((10 + 20) * 3) - (5 / 2)
        List<String> tokens = Arrays.asList(
                "(", "-",
                "(", "*",
                "(", "+", "(&number)10", "(&number)20", ")",
                "(&number)3",
                ")",
                "(", "/", "(&number)5", "(&number)2", ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        Number result = ruleEngine.execute(context, ruleExpression);

        // (10 + 20) * 3 - 5 / 2 = 30 * 3 - 2.5 = 90 - 2.5 = 87.5
        assertEquals("计算结果应该正确", new BigDecimal("87.5"), new BigDecimal(result.toString()));
        System.out.println("✓ 测试通过: 结果 = " + result);
    }

    @Test
    public void testArithmeticWithVariables() {
        System.out.println("测试: 变量算术 (price * quantity) + tax");

        // 构建表达式: (price * quantity) + tax
        List<String> tokens = Arrays.asList(
                "(", "+",
                "(", "*", "@price", "@quantity", ")",
                "@tax",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 准备上下文数据
        Map<String, Object> context = new HashMap<>();
        context.put("price", 100.5);
        context.put("quantity", 3);
        context.put("tax", 15.5);

        Number result = ruleEngine.execute(context, ruleExpression);

        // 100.5 * 3 + 15.5 = 301.5 + 15.5 = 317.0
        assertEquals("计算结果应该正确", new BigDecimal("317"), new BigDecimal(result.toString()));
        System.out.println("✓ 测试通过: 结果 = " + result);
    }

    // ==================== 比较运算测试 ====================

    @Test
    public void testGreaterThan() {
        System.out.println("测试: 大于比较 age > 18");

        List<String> tokens = Arrays.asList(
                "(", ">", "@age", "(&number)18", ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);

        // 测试 age = 25 > 18
        Map<String, Object> context1 = new HashMap<>();
        context1.put("age", 25);
        Boolean result1 = ruleEngine.execute(context1, ruleExpression);
        assertTrue("25 > 18 应该为 true", result1);
        System.out.println("✓ 25 > 18 = " + result1);

        // 测试 age = 15 > 18
        Map<String, Object> context2 = new HashMap<>();
        context2.put("age", 15);
        Boolean result2 = ruleEngine.execute(context2, ruleExpression);
        assertFalse("15 > 18 应该为 false", result2);
        System.out.println("✓ 15 > 18 = " + result2);
    }

    @Test
    public void testComplexComparison() {
        System.out.println("测试: 复杂比较 (age >= 18) && (age <= 65)");

        // 构建表达式: (age >= 18) && (age <= 65)
        List<String> tokens = Arrays.asList(
                "(", "&&",
                "(", ">=", "@age", "(&number)18", ")",
                "(", "<=", "@age", "(&number)65", ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 测试 age = 30，应该在范围内
        Map<String, Object> context1 = new HashMap<>();
        context1.put("age", 30);
        Boolean result1 = ruleEngine.execute(context1, ruleExpression);
        assertTrue("30应该在18-65范围内", result1);
        System.out.println("✓ age=30: " + result1);

        // 测试 age = 70，应该不在范围内
        Map<String, Object> context2 = new HashMap<>();
        context2.put("age", 70);
        Boolean result2 = ruleEngine.execute(context2, ruleExpression);
        assertFalse("70应该不在18-65范围内", result2);
        System.out.println("✓ age=70: " + result2);
    }

    // ==================== 字符串操作测试 ====================

    @Test
    public void testStringContains() {
        System.out.println("测试: 字符串包含 contains(name, 'VIP')");

        List<String> tokens = Arrays.asList(
                "(", "contains", "@name", "(&string)VIP", ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);

        Map<String, Object> context1 = new HashMap<>();
        context1.put("name", "VIP_USER_001");
        Boolean result1 = ruleEngine.execute(context1, ruleExpression);
        assertTrue("VIP_USER_001 应该包含 VIP", result1);
        System.out.println("✓ 'VIP_USER_001' contains 'VIP' = " + result1);

        Map<String, Object> context2 = new HashMap<>();
        context2.put("name", "NORMAL_USER");
        Boolean result2 = ruleEngine.execute(context2, ruleExpression);
        assertFalse("NORMAL_USER 不应该包含 VIP", result2);
        System.out.println("✓ 'NORMAL_USER' contains 'VIP' = " + result2);
    }

    @Test
    public void testStringSubstring() {
        System.out.println("测试: 字符串截取 leftSub(code, 3)");

        // 截取左边3个字符
        List<String> tokens = Arrays.asList(
                "(", "leftSub", "@code", "(&number)3", ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);

        Map<String, Object> context = new HashMap<>();
        context.put("code", "CN-12345");
        String result = ruleEngine.execute(context, ruleExpression);

        assertEquals("应该截取前3个字符", "CN-", result);
        System.out.println("✓ leftSub('CN-12345', 3) = '" + result + "'");
    }

    @Test
    public void testStringChain() {
        System.out.println("测试: 字符串操作链 contains(leftSub(code, 2), 'CN')");

        // 先截取左边2个字符，再判断是否包含CN
        List<String> tokens = Arrays.asList(
                "(", "strEq",
                "(", "leftSub", "@code", "(&number)2", ")",
                "(&string)CN",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("code", "CN12345");
        Boolean result = ruleEngine.execute(context, ruleExpression);

        assertTrue("CN12345的前2个字符应该等于CN", result);
        System.out.println("✓ 测试通过: " + result);
    }

    // ==================== 条件表达式测试 ====================

    @Test
    public void testSimpleIfThenElse() {
        System.out.println("测试: 简单条件 if (score >= 60) then 'Pass' else 'Fail'");

        List<String> tokens = Arrays.asList(
                "(", "if",
                "(", ">=", "@score", "(&number)60", ")",
                "then", "(&string)Pass",
                "else", "(&string)Fail",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 测试及格分数
        Map<String, Object> context1 = new HashMap<>();
        context1.put("score", 75);
        String result1 = ruleEngine.execute(context1, ruleExpression);
        assertEquals("75分应该及格", "Pass", result1);
        System.out.println("✓ score=75: " + result1);

        // 测试不及格分数
        Map<String, Object> context2 = new HashMap<>();
        context2.put("score", 45);
        String result2 = ruleEngine.execute(context2, ruleExpression);
        assertEquals("45分应该不及格", "Fail", result2);
        System.out.println("✓ score=45: " + result2);
    }

    @Test
    public void testNestedIfExpression() {
        System.out.println("测试: 嵌套if - 成绩等级评定");

        // if (score >= 90) then 'A'
        // else if (score >= 80) then 'B'
        // else if (score >= 70) then 'C'
        // else if (score >= 60) then 'D'
        // else 'F'
        List<String> tokens = Arrays.asList(
                "(", "if",
                "(", ">=", "@score", "(&number)90", ")",
                "then", "(&string)A",
                "else",
                "(", "if",
                "(", ">=", "@score", "(&number)80", ")",
                "then", "(&string)B",
                "else",
                "(", "if",
                "(", ">=", "@score", "(&number)70", ")",
                "then", "(&string)C",
                "else",
                "(", "if",
                "(", ">=", "@score", "(&number)60", ")",
                "then", "(&string)D",
                "else", "(&string)F",
                ")",
                ")",
                ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 测试各个等级
        Map<String, Object>[] contexts = new Map[]{
                createContext("score", 95),
                createContext("score", 85),
                createContext("score", 75),
                createContext("score", 65),
                createContext("score", 55)
        };
        String[] expectedGrades = {"A", "B", "C", "D", "F"};

        for (int i = 0; i < contexts.length; i++) {
            String result = ruleEngine.execute(contexts[i], ruleExpression);
            assertEquals("分数应该对应正确的等级", expectedGrades[i], result);
            System.out.println("✓ score=" + contexts[i].get("score") + ": " + result);
        }
    }

    // ==================== 真实业务场景测试 ====================

    @Test
    public void testEcommercePriceCalculation() {
        System.out.println("测试: 电商价格计算 - 会员折扣");

        // if (memberLevel == 'gold') then price * 0.8
        // else if (memberLevel == 'silver') then price * 0.9
        // else price
        List<String> tokens = Arrays.asList(
                "(", "if",
                "(", "strEq", "@memberLevel", "(&string)gold", ")",
                "then", "(", "*", "@price", "(&number)0.8", ")",
                "else",
                "(", "if",
                "(", "strEq", "@memberLevel", "(&string)silver", ")",
                "then", "(", "*", "@price", "(&number)0.9", ")",
                "else", "@price",
                ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 测试金卡会员
        Map<String, Object> goldContext = new HashMap<>();
        goldContext.put("memberLevel", "gold");
        goldContext.put("price", 100);
        Number goldResult = ruleEngine.execute(goldContext, ruleExpression);
        assertEquals("金卡会员应享8折", new BigDecimal("80"), new BigDecimal(goldResult.toString()));
        System.out.println("✓ 金卡会员: 100 * 0.8 = " + goldResult);

        // 测试银卡会员
        Map<String, Object> silverContext = new HashMap<>();
        silverContext.put("memberLevel", "silver");
        silverContext.put("price", 100);
        Number silverResult = ruleEngine.execute(silverContext, ruleExpression);
        assertEquals("银卡会员应享9折", new BigDecimal("90"), new BigDecimal(silverResult.toString()));
        System.out.println("✓ 银卡会员: 100 * 0.9 = " + silverResult);

        // 测试普通会员
        Map<String, Object> normalContext = new HashMap<>();
        normalContext.put("memberLevel", "normal");
        normalContext.put("price", 100);
        Number normalResult = ruleEngine.execute(normalContext, ruleExpression);
        assertEquals("普通会员应无折扣", new BigDecimal("100"), new BigDecimal(normalResult.toString()));
        System.out.println("✓ 普通会员: 原价 = " + normalResult);
    }

    @Test
    public void testComplexDiscountCalculation() {
        System.out.println("测试: 复杂折扣计算 - 基于金额和会员等级");

        // if (amount > 1000 && memberLevel == 'vip') then amount * 0.7
        // else if (amount > 500) then amount * 0.85
        // else if (amount > 100) then amount * 0.95
        // else amount
        List<String> tokens = Arrays.asList(
                "(", "if",
                "(", "&&",
                "(", ">", "@amount", "(&number)1000", ")",
                "(", "strEq", "@memberLevel", "(&string)vip", ")",
                ")",
                "then", "(", "*", "@amount", "(&number)0.7", ")",
                "else",
                "(", "if",
                "(", ">", "@amount", "(&number)500", ")",
                "then", "(", "*", "@amount", "(&number)0.85", ")",
                "else",
                "(", "if",
                "(", ">", "@amount", "(&number)100", ")",
                "then", "(", "*", "@amount", "(&number)0.95", ")",
                "else", "@amount",
                ")",
                ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 测试VIP大额订单
        Map<String, Object> vipContext = new HashMap<>();
        vipContext.put("amount", 1500);
        vipContext.put("memberLevel", "vip");
        Number vipResult = ruleEngine.execute(vipContext, ruleExpression);
        assertEquals("VIP大额应享7折", new BigDecimal("1050"), new BigDecimal(vipResult.toString()));
        System.out.println("✓ VIP 1500元: 1500 * 0.7 = " + vipResult);

        // 测试中等金额
        Map<String, Object> mediumContext = new HashMap<>();
        mediumContext.put("amount", 600);
        mediumContext.put("memberLevel", "normal");
        Number mediumResult = ruleEngine.execute(mediumContext, ruleExpression);
        assertEquals("中等金额应享85折", new BigDecimal("510"), new BigDecimal(mediumResult.toString()));
        System.out.println("✓ 普通 600元: 600 * 0.85 = " + mediumResult);

        // 测试小额订单
        Map<String, Object> smallContext = new HashMap<>();
        smallContext.put("amount", 50);
        smallContext.put("memberLevel", "normal");
        Number smallResult = ruleEngine.execute(smallContext, ruleExpression);
        assertEquals("小额订单无折扣", new BigDecimal("50"), new BigDecimal(smallResult.toString()));
        System.out.println("✓ 普通 50元: 原价 = " + smallResult);
    }

    @Test
    public void testPointsCalculation() {
        System.out.println("测试: 积分计算 - 消费金额 * 会员倍数");

        // floor(amount * memberMultiplier)
        List<String> tokens = Arrays.asList(
                "(", "floor",
                "(", "*", "@amount", "@memberMultiplier", ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        context.put("amount", 158.8);
        context.put("memberMultiplier", 1.5);
        Number result = ruleEngine.execute(context, ruleExpression);

        // floor(158.8 * 1.5) = floor(238.2) = 238
        assertEquals("积分应该向下取整", new BigDecimal("238"), new BigDecimal(result.toString()));
        System.out.println("✓ floor(158.8 * 1.5) = " + result);
    }

    @Test
    public void testLoanApprovalSimple() {
        System.out.println("测试: 贷款审批 - 简化版");

        // (creditScore >= 700) && (income > debt * 3) && (age >= 22 && age <= 60)
        List<String> tokens = Arrays.asList(
                "(", "&&",
                "(", "&&",
                "(", ">=", "@creditScore", "(&number)700", ")",
                "(", ">", "@income", "(", "*", "@debt", "(&number)3", ")", ")",
                ")",
                "(", "&&",
                "(", ">=", "@age", "(&number)22", ")",
                "(", "<=", "@age", "(&number)60", ")",
                ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 测试合格申请人
        Map<String, Object> qualifiedContext = new HashMap<>();
        qualifiedContext.put("creditScore", 750);
        qualifiedContext.put("income", 15000);
        qualifiedContext.put("debt", 3000);  // income > debt * 3 => 15000 > 9000
        qualifiedContext.put("age", 35);
        Boolean qualifiedResult = ruleEngine.execute(qualifiedContext, ruleExpression);
        assertTrue("符合所有条件应该批准", qualifiedResult);
        System.out.println("✓ 合格申请人: " + qualifiedResult);

        // 测试不合格申请人（年龄过大）
        Map<String, Object> unqualifiedContext = new HashMap<>();
        unqualifiedContext.put("creditScore", 750);
        unqualifiedContext.put("income", 15000);
        unqualifiedContext.put("debt", 3000);
        unqualifiedContext.put("age", 65);
        Boolean unqualifiedResult = ruleEngine.execute(unqualifiedContext, ruleExpression);
        assertFalse("年龄超限应该拒绝", unqualifiedResult);
        System.out.println("✓ 不合格申请人(年龄65): " + unqualifiedResult);
    }

    @Test
    public void testRiskControl() {
        System.out.println("测试: 风控规则 - 高风险交易检测");

        // (amount > avgAmount * 10) || (transCount24h > 50)
        List<String> tokens = Arrays.asList(
                "(", "||",
                "(", ">", "@amount", "(", "*", "@avgAmount", "(&number)10", ")", ")",
                "(", ">", "@transCount24h", "(&number)50", ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 测试异常大额交易
        Map<String, Object> largeAmountContext = new HashMap<>();
        largeAmountContext.put("amount", 5000);
        largeAmountContext.put("avgAmount", 200);  // 5000 > 200 * 10
        largeAmountContext.put("transCount24h", 10);
        Boolean largeAmountResult = ruleEngine.execute(largeAmountContext, ruleExpression);
        assertTrue("大额异常应该触发风控", largeAmountResult);
        System.out.println("✓ 大额异常(5000 > 200*10): " + largeAmountResult);

        // 测试高频交易
        Map<String, Object> highFreqContext = new HashMap<>();
        highFreqContext.put("amount", 500);
        highFreqContext.put("avgAmount", 200);
        highFreqContext.put("transCount24h", 60);  // 60 > 50
        Boolean highFreqResult = ruleEngine.execute(highFreqContext, ruleExpression);
        assertTrue("高频交易应该触发风控", highFreqResult);
        System.out.println("✓ 高频异常(60次 > 50): " + highFreqResult);

        // 测试正常交易
        Map<String, Object> normalContext = new HashMap<>();
        normalContext.put("amount", 500);
        normalContext.put("avgAmount", 200);
        normalContext.put("transCount24h", 20);
        Boolean normalResult = ruleEngine.execute(normalContext, ruleExpression);
        assertFalse("正常交易不应触发风控", normalResult);
        System.out.println("✓ 正常交易: " + normalResult);
    }

    // ==================== 类型转换测试 ====================

    @Test
    public void testTypeConversion() {
        System.out.println("测试: 类型转换 toNumber(toStr(123))");

        // 先转字符串再转回数字
        List<String> tokens = Arrays.asList(
                "(", "toNumber",
                "(", "toStr", "(&number)123", ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        Number result = ruleEngine.execute(context, ruleExpression);

        assertEquals("转换后应该是123", new BigDecimal("123"), new BigDecimal(result.toString()));
        System.out.println("✓ toNumber(toStr(123)) = " + result);
    }

    @Test
    public void testMathFunctions() {
        System.out.println("测试: 数学函数 abs(ceil(-3.2))");

        // abs(ceil(-3.2)) = abs(-3) = 3
        List<String> tokens = Arrays.asList(
                "(", "abs",
                "(", "ceil", "(&number)-3.2", ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        Map<String, Object> context = new HashMap<>();
        Number result = ruleEngine.execute(context, ruleExpression);

        assertEquals("结果应该是3", new BigDecimal("3"), new BigDecimal(result.toString()));
        System.out.println("✓ abs(ceil(-3.2)) = " + result);
    }

    // ==================== 边界和特殊值测试 ====================

    @Test
    public void testZeroValues() {
        System.out.println("测试: 零值处理 0 * 100 = 0");

        List<String> tokens = Arrays.asList(
                "(", "*", "(&number)0", "(&number)100", ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);

        Map<String, Object> context = new HashMap<>();
        Number result = ruleEngine.execute(context, ruleExpression);

        assertEquals("0乘任何数都是0", new BigDecimal("0"), new BigDecimal(result.toString()));
        System.out.println("✓ 0 * 100 = " + result);
    }

    @Test
    public void testNegativeNumbers() {
        System.out.println("测试: 负数运算 -10 + 20 = 10");

        List<String> tokens = Arrays.asList(
                "(", "+", "(&number)-10", "(&number)20", ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);

        Map<String, Object> context = new HashMap<>();
        Number result = ruleEngine.execute(context, ruleExpression);

        assertEquals("负数加法应该正确", new BigDecimal("10"), new BigDecimal(result.toString()));
        System.out.println("✓ -10 + 20 = " + result);
    }

    @Test
    public void testDecimalPrecision() {
        System.out.println("测试: 小数精度 0.1 + 0.2");

        List<String> tokens = Arrays.asList(
                "(", "+", "(&number)0.1", "(&number)0.2", ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);

        Map<String, Object> context = new HashMap<>();
        Number result = ruleEngine.execute(context, ruleExpression);

        // 使用BigDecimal应该精确计算
        assertEquals("小数加法应该精确", new BigDecimal("0.3"), new BigDecimal(result.toString()));
        System.out.println("✓ 0.1 + 0.2 = " + result);
    }

    @Test
    public void testEmptyStringCheck() {
        System.out.println("测试: 空字符串检查 blank(str)");

        List<String> tokens = Arrays.asList(
                "(", "blank", "@str", ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);

        // 测试空字符串
        Map<String, Object> context1 = new HashMap<>();
        context1.put("str", "");
        Boolean result1 = ruleEngine.execute(context1, ruleExpression);
        assertTrue("空字符串应该返回true", result1);
        System.out.println("✓ blank('') = " + result1);

        // 测试非空字符串
        Map<String, Object> context2 = new HashMap<>();
        context2.put("str", "test");
        Boolean result2 = ruleEngine.execute(context2, ruleExpression);
        assertFalse("非空字符串应该返回false", result2);
        System.out.println("✓ blank('test') = " + result2);
    }

    // ==================== 复杂真实场景测试 ====================

    @Test
    public void testCompleteOrderPriceCalculation() {
        System.out.println("测试: 完整订单价格计算");
        System.out.println("规则: 原价 * 会员折扣 * 数量折扣 - 优惠券");

        // ((price * memberDiscount * quantityDiscount) - coupon)
        // memberDiscount = memberLevel == 'vip' ? 0.8 : 1.0
        // quantityDiscount = quantity >= 10 ? 0.95 : 1.0
        List<String> tokens = Arrays.asList(
                "(", "-",
                "(", "*",
                "(", "*",
                "@price",
                "(", "if",
                "(", "strEq", "@memberLevel", "(&string)vip", ")",
                "then", "(&number)0.8",
                "else", "(&number)1.0",
                ")",
                ")",
                "(", "if",
                "(", ">=", "@quantity", "(&number)10", ")",
                "then", "(&number)0.95",
                "else", "(&number)1.0",
                ")",
                ")",
                "@coupon",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // VIP会员购买12件商品，有优惠券
        Map<String, Object> context = new HashMap<>();
        context.put("price", 100);
        context.put("memberLevel", "vip");
        context.put("quantity", 12);
        context.put("coupon", 20);

        Number result = ruleEngine.execute(context, ruleExpression);

        // 计算: 100 * 0.8 * 0.95 - 20 = 76 - 20 = 56
        assertEquals("完整订单价格计算", new BigDecimal("56"), new BigDecimal(result.toString()));
        System.out.println("✓ VIP购买12件: 100*0.8*0.95-20 = " + result);
    }

    @Test
    public void testMultiConditionRiskAssessment() {
        System.out.println("测试: 多条件风险评估");
        System.out.println("规则: 高风险 = (大额异常 || 高频交易 || 异地登录) && 无历史记录");

        // ((amount > avgAmount * 10) || (transCount > 50) || (city != lastCity)) && (historyDays < 30)
        List<String> tokens = Arrays.asList(
                "(", "&&",
                "(", "||",
                "(", "||",
                "(", ">", "@amount", "(", "*", "@avgAmount", "(&number)10", ")", ")",
                "(", ">", "@transCount", "(&number)50", ")",
                ")",
                "(", "strNeq", "@city", "@lastCity", ")",
                ")",
                "(", "<", "@historyDays", "(&number)30", ")",
                ")"
        );

        Object expression = RuleExpressionParser.parse(tokens);
        String ruleExpression = JSON.toJSONString(expression);
        System.out.println("生成的表达式: " + ruleExpression);

        // 测试高风险场景：异地登录 + 新用户
        Map<String, Object> highRiskContext = new HashMap<>();
        highRiskContext.put("amount", 500);
        highRiskContext.put("avgAmount", 200);
        highRiskContext.put("transCount", 30);
        highRiskContext.put("city", "Beijing");
        highRiskContext.put("lastCity", "Shanghai");  // 异地
        highRiskContext.put("historyDays", 15);  // 新用户

        Boolean highRiskResult = ruleEngine.execute(highRiskContext, ruleExpression);
        assertTrue("异地登录且新用户应判定为高风险", highRiskResult);
        System.out.println("✓ 高风险场景: " + highRiskResult);

        // 测试低风险场景：正常城市 + 老用户
        Map<String, Object> lowRiskContext = new HashMap<>();
        lowRiskContext.put("amount", 500);
        lowRiskContext.put("avgAmount", 200);
        lowRiskContext.put("transCount", 30);
        lowRiskContext.put("city", "Beijing");
        lowRiskContext.put("lastCity", "Beijing");  // 同城
        lowRiskContext.put("historyDays", 365);  // 老用户

        Boolean lowRiskResult = ruleEngine.execute(lowRiskContext, ruleExpression);
        assertFalse("同城且老用户应判定为低风险", lowRiskResult);
        System.out.println("✓ 低风险场景: " + lowRiskResult);
    }

    // ==================== 辅助方法 ====================

    private Map<String, Object> createContext(String key, Object value) {
        Map<String, Object> context = new HashMap<>();
        context.put(key, value);
        return context;
    }

    private void printSeparator() {
        System.out.println("----------------------------------------");
    }
}