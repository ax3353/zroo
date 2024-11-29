package com.zk.ruleengine;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class RuleExpressionParserOk {

    private static final Map<String, Integer> OPERATOR_ARITY = new HashMap<>();

    static {
        // 一元操作符
        OPERATOR_ARITY.put("toStr", 1);
        OPERATOR_ARITY.put("abs", 1);
        OPERATOR_ARITY.put("ceil", 1);
        OPERATOR_ARITY.put("floor", 1);
        OPERATOR_ARITY.put("scale", 1);
//        OPERATOR_ARITY.put("nowDateTime", 0); // 无参数
//        OPERATOR_ARITY.put("nowDate", 0); // 无参数
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

    public static Object parse(String[] tokens) {
        Stack<Object> stack = new Stack<>();
        Stack<String> operators = new Stack<>();
        int openParenthesesCount = 0;

        for (String token : tokens) {
            if (token.equals("(")) {
                operators.push("(");
                openParenthesesCount++;
            } else if (token.equals(")")) {
                handleCloseParenthesis(stack, operators, openParenthesesCount);
                openParenthesesCount--;
            } else if (token.equals("if") || token.equals("then") || token.equals("else")) {
                operators.push(token);
            } else if (OPERATOR_ARITY.containsKey(token)) {
                operators.push(token);
            } else {
                stack.push(parseValue(token));
            }
        }

        if (openParenthesesCount != 0) {
            throw new IllegalArgumentException("括号不匹配：缺少右括号");
        }

        while (!operators.isEmpty()) {
            processOperator(stack, operators.pop());
        }

        return stack.pop();
    }

    private static void handleCloseParenthesis(Stack<Object> stack, Stack<String> operators, int openParenthesesCount) {
        if (openParenthesesCount == 0) {
            throw new IllegalArgumentException("括号不匹配：多余的右括号");
        }
        while (!operators.isEmpty() && !operators.peek().equals("(")) {
            processOperator(stack, operators.pop());
        }
        operators.pop(); // 弹出左括号
    }

    private static void processOperator(Stack<Object> stack, String operator) {
        if (operator.equals("then") || operator.equals("else")) {
            return;
        }

        if (operator.equals("if")) {
            handleIfThenElse(stack);
            return;
        }

        int arity = OPERATOR_ARITY.get(operator);
        Object[] operands = new Object[arity];

        // 处理其他操作符
        for (int i = arity - 1; i >= 0; i--) {
            if (!stack.isEmpty()) {
                operands[i] = stack.pop();
            } else {
                throw new IllegalStateException("缺少该操作符的参数: " + operator);
            }
        }

        Object[] expression = new Object[arity + 1];
        expression[0] = operator;
        System.arraycopy(operands, 0, expression, 1, arity);

        stack.push(expression);
    }

    private static void handleIfThenElse(Stack<Object> stack) {
        Object elseExpr = stack.pop();
        Object thenExpr = stack.pop();
        Object conditionExpr = stack.pop();
        stack.push(new Object[]{"if", conditionExpr, thenExpr, elseExpr});
    }

    private static Object parseValue(String token) {
        if (token.startsWith("@")) {
            return new Object[]{"@value", token.substring(1)};
        } else if (token.matches("-?\\d+")) {
            return new Object[]{"numberInput", Integer.parseInt(token)};
        } else if (token.matches("-?\\d+\\.\\d+")) {
            return new Object[]{"numberInput", Double.parseDouble(token)};
        } else if (token.matches("\\d{2}:\\d{2}:\\d{2}")) {
            return new Object[]{"timeInput", token};
        } else if (token.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return new Object[]{"dateInput", token};
        } else if (token.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            return new Object[]{"dateTimeInput", token};
        } else if (token.equals("nowDate") || token.equals("nowDateTime")) {
            return new Object[]{token};
        } else {
            return new Object[]{"strInput", token};
        }
    }
}