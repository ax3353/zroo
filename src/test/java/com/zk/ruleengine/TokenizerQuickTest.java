package com.zk.ruleengine;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tokenizer快速验证测试
 */
public class TokenizerQuickTest {

    @Test
    public void testBasicTokenization() {
        System.out.println("=== 测试基本分词 ===");

        String dsl = "10 + 20";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens: " + tokens);

        assertEquals(3, tokens.size());
        assertEquals("(&number)10", tokens.get(0));
        assertEquals("+", tokens.get(1));
        assertEquals("(&number)20", tokens.get(2));
    }

    @Test
    public void testFunctionWithComma() {
        System.out.println("=== 测试函数调用(带逗号) ===");

        String dsl = "strEq(@name, 'John')";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens: " + tokens);

        // 期望: strEq ( @name , (&string)John )
        assertEquals("strEq", tokens.get(0));
        assertEquals("(", tokens.get(1));
        assertEquals("@name", tokens.get(2));
        assertEquals(",", tokens.get(3));
        assertEquals("(&string)John", tokens.get(4));
        assertEquals(")", tokens.get(5));

        System.out.println("✓ 逗号正确识别");
    }

    @Test
    public void testMultipleParameters() {
        System.out.println("=== 测试多参数函数 ===");

        String dsl = "midSub(@text, 5, 10)";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens: " + tokens);

        // 期望: midSub ( @text , 5 , 10 )
        assertEquals("midSub", tokens.get(0));
        assertEquals("(", tokens.get(1));
        assertEquals("@text", tokens.get(2));
        assertEquals(",", tokens.get(3));
        assertEquals("(&number)5", tokens.get(4));
        assertEquals(",", tokens.get(5));
        assertEquals("(&number)10", tokens.get(6));
        assertEquals(")", tokens.get(7));

        System.out.println("✓ 多参数正确识别");
    }

    @Test
    public void testStringWithComma() {
        System.out.println("=== 测试字符串内的逗号 ===");

        String dsl = "strEq(@text, \"Hello, World\")";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens: " + tokens);

        // 期望: strEq ( @text , (&string)Hello, World )
        assertEquals("strEq", tokens.get(0));
        assertEquals("(", tokens.get(1));
        assertEquals("@text", tokens.get(2));
        assertEquals(",", tokens.get(3));
        assertEquals("(&string)Hello, World", tokens.get(4));  // 字符串内的逗号不分割
        assertEquals(")", tokens.get(5));

        System.out.println("✓ 字符串内的逗号保留正确");
    }

    @Test
    public void testEscapedString() {
        System.out.println("=== 测试转义字符 ===");

        String dsl = "strEq(@text, \"Hello\\nWorld\")";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens: " + tokens);

        // 期望: strEq ( @text , (&string)Hello\nWorld )
        assertEquals("strEq", tokens.get(0));
        assertEquals("(", tokens.get(1));
        assertEquals("@text", tokens.get(2));
        assertEquals(",", tokens.get(3));

        String stringToken = tokens.get(4);
        assertTrue("应该包含换行符", stringToken.contains("\n"));
        assertEquals("(&string)Hello\nWorld", stringToken);

        System.out.println("✓ 转义字符正确处理");
    }

    @Test
    public void testComplexExpression() {
        System.out.println("=== 测试复杂表达式 ===");

        String dsl = "if (strEq(@level, 'VIP')) then (@price * 0.8) else @price";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens count: " + tokens.size());
        System.out.println("Tokens: " + tokens);

        assertTrue("应该有多个token", tokens.size() > 10);
        assertTrue("应该包含if", tokens.contains("if"));
        assertTrue("应该包含then", tokens.contains("then"));
        assertTrue("应该包含else", tokens.contains("else"));
        assertTrue("应该包含strEq", tokens.contains("strEq"));

        System.out.println("✓ 复杂表达式正确分词");
    }

    @Test
    public void testNestedFunctions() {
        System.out.println("=== 测试嵌套函数 ===");

        String dsl = "abs(ceil(-3.2))";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens: " + tokens);

        // 期望: abs ( ceil ( -3.2 ) )
        assertEquals("abs", tokens.get(0));
        assertEquals("(", tokens.get(1));
        assertEquals("ceil", tokens.get(2));
        assertEquals("(", tokens.get(3));
        assertEquals("(&number)-3.2", tokens.get(4));
        assertEquals(")", tokens.get(5));
        assertEquals(")", tokens.get(6));

        System.out.println("✓ 嵌套函数正确识别");
    }

    @Test
    public void testVariableWithDot() {
        System.out.println("=== 测试带点的变量 ===");

        String dsl = "@user.name";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens: " + tokens);

        assertEquals(1, tokens.size());
        assertEquals("@user.name", tokens.get(0));

        System.out.println("✓ 变量中的点保留正确");
    }

    @Test
    public void testAllOperators() {
        System.out.println("=== 测试所有操作符 ===");

        String[] operators = {
                "+", "-", "*", "/", "%",
                ">", "<", ">=", "<=", "==", "<>",
                "&&", "||"
        };

        for (String op : operators) {
            String dsl = "@a " + op + " @b";
            List<String> tokens = RuleTokenizer.tokenize(dsl);

            assertEquals("操作符: " + op, 3, tokens.size());
            assertEquals("@a", tokens.get(0));
            assertEquals(op, tokens.get(1));
            assertEquals("@b", tokens.get(2));
        }

        System.out.println("✓ 所有操作符识别正确");
    }

    @Test
    public void testDateFunctions() {
        System.out.println("=== 测试日期函数 ===");

        String dsl = "@createDate > nowDate";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        System.out.println("DSL: " + dsl);
        System.out.println("Tokens: " + tokens);

        assertEquals("@createDate", tokens.get(0));
        assertEquals(">", tokens.get(1));
        assertEquals("nowDate", tokens.get(2));

        System.out.println("✓ nowDate识别为关键字");
    }

    @Test
    public void testFormatTokens() {
        System.out.println("=== 测试Token格式化 ===");

        String dsl = "if (@age >= 18) then 'Adult' else 'Minor'";
        List<String> tokens = RuleTokenizer.tokenize(dsl);

        String formatted = RuleTokenizer.formatTokens(tokens);

        System.out.println("DSL: " + dsl);
        System.out.println("Formatted Tokens:");
        System.out.println(formatted);

        assertTrue("格式化输出应该包含索引", formatted.contains("0:"));
        assertTrue("格式化输出应该包含if", formatted.contains("if"));

        System.out.println("✓ Token格式化正确");
    }

    public static void main(String[] args) {
        TokenizerQuickTest test = new TokenizerQuickTest();

        try {
            test.testBasicTokenization();
            test.testFunctionWithComma();
            test.testMultipleParameters();
            test.testStringWithComma();
            test.testEscapedString();
            test.testComplexExpression();
            test.testNestedFunctions();
            test.testVariableWithDot();
            test.testAllOperators();
            test.testDateFunctions();
            test.testFormatTokens();

            System.out.println("\n========================================");
            System.out.println("✓✓✓ 所有测试通过！✓✓✓");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("\n========================================");
            System.err.println("✗✗✗ 测试失败！✗✗✗");
            System.err.println("========================================");
            e.printStackTrace();
        }
    }
}