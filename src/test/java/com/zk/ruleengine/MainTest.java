package com.zk.ruleengine;

import com.alibaba.fastjson.JSON;

public class MainTest {

    public static void main(String[] args) {
        MainTest testMain = new MainTest();
        testMain.test0();
        testMain.test1();
        testMain.test2();
        testMain.test3();
        testMain.test4();
        testMain.test5();
    }

    public void test0() {
        String[] tokens = {
                "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")"
        };

        Object result = RuleExpressionParser.parse(tokens);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test1() {
        String[] tokens = {
                "(", "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")", ")",
                "&&",
                "(", "@alarm_status", "==", "1", ")"
        };

        Object result = RuleExpressionParser.parse(tokens);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test2() {
        String[] tokens = {
                "(", "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")", ")",
                "&&",
                "(", "(", "leftSub", "@danger_name", "2", ")", "strEq", "alarming", ")"
        };

        Object result = RuleExpressionParser.parse(tokens);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test3() {
        String[] tokens = {
                "if", "(", "(", "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")", ")", "&&", "(", "(", "leftSub", "@danger_name", "2", ")", "strEq", "alarming", ")", ")",
                "then", "(", "(", "@id", "+", "50", ")", "-", "(", "@danger_code", "*", "2", ")", ")",
                "if", "(", "(", "(", "@id", "-", "10", ")", "<=", "(", "@danger_code", "*", "2", ")", ")", "||", "(", "@danger_status", "==", "0", ")", ")",
                "then", "(", "abs", "(", "@id", "-", "100", ")", ")",
                "else", "nowDate", "date>", "2023-01-01"
        };

        Object result = RuleExpressionParser.parse(tokens);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test4() {
        String[] tokens = {
                "if", "(", "(", "(", "@id", "+", "50", ")", ">", "(", "@danger_code", "*", "2", ")", ")", "&&", "(", "(", "leftSub", "@danger_name", "2", ")", "strEq", "alarming", ")", ")",
                "then", "(", "abs", "(", "@id", "-", "100", ")", ")",
                "else", "nowDate", "date>", "@createTime"
        };

        Object result = RuleExpressionParser.parse(tokens);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }

    private void test5() {
        String[] tokens = {
                "nowDate", "date>", "2023-01-01"
        };

        Object result = RuleExpressionParser.parse(tokens);
        String s = JSON.toJSONString(result);
        System.out.println(s);
    }
}
