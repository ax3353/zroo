package com.zk.ruleengine.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 将用户配置的规则脚本解析成规则表达式
 *
 * @author zk
 */
public class RuleExpressionParser {

    private static final Map<String, Integer> OPERATOR_ARITY = new HashMap<>();

    static {
        // 一元操作符
        OPERATOR_ARITY.put("toStr", 1);
        OPERATOR_ARITY.put("abs", 1);
        OPERATOR_ARITY.put("ceil", 1);
        OPERATOR_ARITY.put("floor", 1);
        OPERATOR_ARITY.put("scale", 1);
        OPERATOR_ARITY.put("toDate", 1);
        OPERATOR_ARITY.put("toNumber", 1);
        OPERATOR_ARITY.put("blank", 1);
        OPERATOR_ARITY.put("notBlank", 1);
        OPERATOR_ARITY.put("null", 1);
        OPERATOR_ARITY.put("notNull", 1);
        OPERATOR_ARITY.put("timeInput", 1);
        OPERATOR_ARITY.put("dateInput", 1);
        OPERATOR_ARITY.put("dateTimeInput", 1);
        OPERATOR_ARITY.put("strInput", 1);
        OPERATOR_ARITY.put("numberInput", 1);
        OPERATOR_ARITY.put("@value", 1);

        // 二元操作符
        OPERATOR_ARITY.put("+", 2);
        OPERATOR_ARITY.put("-", 2);
        OPERATOR_ARITY.put("*", 2);
        OPERATOR_ARITY.put("/", 2);
        OPERATOR_ARITY.put(">", 2);
        OPERATOR_ARITY.put("<", 2);
        OPERATOR_ARITY.put("==", 2);
        OPERATOR_ARITY.put(">=", 2);
        OPERATOR_ARITY.put("<=", 2);
        OPERATOR_ARITY.put("<>", 2);
        OPERATOR_ARITY.put("strEq", 2);
        OPERATOR_ARITY.put("strNeq", 2);
        OPERATOR_ARITY.put("contains", 2);
        OPERATOR_ARITY.put("notContains", 2);
        OPERATOR_ARITY.put("leftSub", 2);
        OPERATOR_ARITY.put("rightSub", 2);
        OPERATOR_ARITY.put("midSub", 2);
        OPERATOR_ARITY.put("date+", 2);
        OPERATOR_ARITY.put("date-", 2);
        OPERATOR_ARITY.put("date<", 2);
        OPERATOR_ARITY.put("date>", 2);
        OPERATOR_ARITY.put("date>=", 2);
        OPERATOR_ARITY.put("date<=", 2);
        OPERATOR_ARITY.put("date==", 2);
        OPERATOR_ARITY.put("date<>", 2);
        OPERATOR_ARITY.put("&&", 2);
        OPERATOR_ARITY.put("||", 2);
        OPERATOR_ARITY.put("dayBetween", 2);
        OPERATOR_ARITY.put("hourBetween", 2);
        OPERATOR_ARITY.put("minuteBetween", 2);
        OPERATOR_ARITY.put("secondBetween", 2);

        // 三元操作符
        OPERATOR_ARITY.put("if", 3);
    }

    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("-?\\d+\\.\\d+");
    private static final Pattern TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");

    public static Object parse(String[] tokens) {
        Deque<Object> operandStack = new ArrayDeque<>();
        Deque<String> operatorStack = new ArrayDeque<>();
        int openParenthesesCount = 0;

        for (String token : tokens) {
            if (token.equals("(")) {
                operatorStack.push("(");
                openParenthesesCount++;
            } else if (token.equals(")")) {
                handleCloseParenthesis(operandStack, operatorStack, openParenthesesCount);
                openParenthesesCount--;
            } else if (token.equals("if") || token.equals("then") || token.equals("else")) {
                operatorStack.push(token);
            } else if (OPERATOR_ARITY.containsKey(token)) {
                operatorStack.push(token);
            } else {
                operandStack.push(parseValue(token));
            }
        }

        if (openParenthesesCount != 0) {
            throw new IllegalArgumentException("括号不匹配：缺少右括号，未闭合的左括号数量：" + openParenthesesCount);
        }

        while (!operatorStack.isEmpty()) {
            processOperator(operandStack, operatorStack.pop());
        }

        return operandStack.pop();
    }

    private static void handleCloseParenthesis(Deque<Object> operandStack, Deque<String> operatorStack, int openParenthesesCount) {
        if (openParenthesesCount == 0) {
            throw new IllegalArgumentException("括号不匹配：多余的右括号");
        }
        while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
            processOperator(operandStack, operatorStack.pop());
        }
        operatorStack.pop(); // 弹出左括号
    }

    private static void processOperator(Deque<Object> operandStack, String operator) {
        if (operator.equals("then") || operator.equals("else")) {
            return;
        }

        if (operator.equals("if")) {
            handleIfThenElse(operandStack);
            return;
        }

        int arity = OPERATOR_ARITY.get(operator);
        Object[] operands = new Object[arity];

        for (int i = arity - 1; i >= 0; i--) {
            if (!operandStack.isEmpty()) {
                operands[i] = operandStack.pop();
            } else {
                throw new IllegalStateException("操作符[" + operator + "]缺少参数");
            }
        }

        Object[] expression = new Object[arity + 1];
        expression[0] = operator;
        System.arraycopy(operands, 0, expression, 1, arity);

        operandStack.push(expression);
    }

    private static void handleIfThenElse(Deque<Object> operandStack) {
        Object elseExpr = operandStack.pop();
        Object thenExpr = operandStack.pop();
        Object conditionExpr = operandStack.pop();
        operandStack.push(new Object[]{"if", conditionExpr, thenExpr, elseExpr});
    }

    private static Object parseValue(String token) {
        if (token.startsWith("@")) {
            return new Object[]{"@value", token.substring(1)};
        } else if (INTEGER_PATTERN.matcher(token).matches()) {
            return new Object[]{"numberInput", Integer.parseInt(token)};
        } else if (DOUBLE_PATTERN.matcher(token).matches()) {
            return new Object[]{"numberInput", Double.parseDouble(token)};
        } else if (TIME_PATTERN.matcher(token).matches()) {
            return new Object[]{"timeInput", token};
        } else if (DATE_PATTERN.matcher(token).matches()) {
            return new Object[]{"dateInput", token};
        } else if (DATE_TIME_PATTERN.matcher(token).matches()) {
            return new Object[]{"dateTimeInput", token};
        } else if ("&nowDate".equals(token)) {
            return new Object[]{"nowDate"};
        } else if ("&nowDateTime".equals(token)) {
            return new Object[]{"nowDateTime"};
        } else {
            return new Object[]{"strInput", token};
        }
    }
}