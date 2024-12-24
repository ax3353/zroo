package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;
import com.zk.ruleengine.utils.RuleExpressionParser;

import java.util.Arrays;
import java.util.List;

public class ExpressionParserTest {

    public static void main(String[] args) {
        ExpressionParserTest testMain = new ExpressionParserTest();
//        testMain.test0();
//        testMain.test1();
//        testMain.test2();
//        testMain.test3();
//        testMain.test4();
//        testMain.test5();
//        testMain.test6();
        testMain.test7();
    }

    public void test0() {
        String[] tokens = {
                "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")"
        };

        List<String> strings = Arrays.asList(tokens);
        Object result = RuleExpressionParser.parse(strings);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test1() {
        String[] tokens = {
                "(", "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")", ")",
                "&&",
                "(", "@alarm_status", "==", "1", ")"
        };

        List<String> strings = Arrays.asList(tokens);
        Object result = RuleExpressionParser.parse(strings);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test2() {
        String[] tokens = {
                "(", "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")", ")",
                "&&",
                "(", "(", "leftSub", "@danger_name", "2", ")", "strEq", "alarming", ")"
        };

        List<String> strings = Arrays.asList(tokens);
        Object result = RuleExpressionParser.parse(strings);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test3() {
        String[] tokens = {
                "if", "(", "(", "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")", ")", "&&", "(", "(", "leftSub", "@danger_name", "2", ")", "strEq", "alarming", ")", ")",
                "then", "(", "(", "@id", "+", "50", ")", "-", "(", "@danger_code", "*", "2", ")", ")",
                "if", "(", "(", "(", "@id", "-", "10", ")", "<=", "(", "@danger_code", "*", "2", ")", ")", "||", "(", "@danger_status", "==", "0", ")", ")",
                "then", "(", "abs", "(", "@id", "-", "100", ")", ")",
                "else", "&nowDate", "date>", "2023-01-01"
        };

        List<String> strings = Arrays.asList(tokens);
        Object result = RuleExpressionParser.parse(strings);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test4() {
        String[] tokens = {
                "if", "(", "(", "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")", ")", "&&", "(", "(", "leftSub", "@danger_name", "2", ")", "strEq", "alarming", ")", ")",
                "then", "(", "abs", "(", "@id", "-", "100", ")", ")",
                "else", "&nowDate", "date>", "@createTime"
        };

        List<String> strings = Arrays.asList(tokens);
        Object result = RuleExpressionParser.parse(strings);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test5() {
        String[] tokens = {
                "&nowDate", "date>", "2023-01-01"
        };

        List<String> strings = Arrays.asList(tokens);
        Object result = RuleExpressionParser.parse(strings);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test6() {
        String[] tokens = {
                "if", "(", "(", "@id", "==", "(&number)12", ")", "&&", "(", "(", "@ecode", "contains", "(&string)13", ")", "||", "(", "@station_code", "notContains", "(&string)14", ")", ")", ")",
                "then", "(", "@hidden_danger_code", "strEq", "(&string)13", ")",
                "else", "(", "@station_name", "notContains", "(&string)123", ")"
        };

        List<String> strings = Arrays.asList(tokens);
        Object result = RuleExpressionParser.parse(strings);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test7() {
        String[] tokens = {
                "if", "(", "(", "@gid", ">", "(&string)12", ")", "&&", "(", "(", "@ecode", "notContains", "(&string)13", ")", "||", "(", "@station_code", "contains", "(&string)14", ")", ")", ")",
                "then", "(", "(", "leftSub", "@station_name", "(&number)3", ")", "strEq", "(&string)abc", ")",
                "if", "(", "&nowDateTime", "date<", "(&datetime)2024-12-24 00:00:00", ")",
                "then", "(", "(&string)aaaa", ")",
                "else", "(", "(&string)bbbb", ")"
        };

        List<String> strings = Arrays.asList(tokens);
        Object result = RuleExpressionParser.parse(strings);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }
}
