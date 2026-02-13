package com.zk.ruleengine;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则DSL词法分析器（Tokenizer）
 * <p>
 * 将规则DSL字符串解析为Token列表，供RuleExpressionParser使用
 * <p>
 * 支持的DSL语法：
 * 1. 括号: ( )
 * 2. 操作符: +, -, *, /, %, >, <, ==, >=, <=, <>, &&, ||, if, then, else等
 * 3. 变量: @variableName
 * 4. 数字: 123, -456, 3.14, -2.5
 * 5. 字符串: "hello", 'world'
 * 6. 日期: date(2024-01-01), datetime(2024-01-01 12:30:45), time(12:30:45)
 * 7. 特殊值: nowDate, nowDateTime
 * <p>
 * 使用示例:
 * String dsl = "if (@age > 18) then 'Adult' else 'Minor'";
 * List<String> tokens = RuleTokenizer.tokenize(dsl);
 *
 * @author zk
 */
public class RuleTokenizer {

    // 操作符集合
    private static final String[] OPERATORS = {
            // 三字符操作符
            "date+", "date-",
            // 双字符操作符
            "==", ">=", "<=", "<>", "&&", "||",
            "date<", "date>", "date==", "date>=", "date<=", "date<>",
            // 单字符操作符
            "+", "-", "*", "/", "%", ">", "<",
            // 括号和分隔符
            "(", ")", ","
    };

    // 关键字
    private static final String[] KEYWORDS = {
            "if", "then", "else",
            // 一元操作符
            "abs", "ceil", "floor", "toStr", "toNumber", "toDate",
            "blank", "notBlank", "null", "notNull", "scale",
            // 二元操作符
            "strEq", "strNeq", "contains", "notContains",
            "leftSub", "rightSub", "midSub",
            "dayBetween", "hourBetween", "minuteBetween", "secondBetween",
            // 特殊值
            "nowDate", "nowDateTime"
    };

    /**
     * 将DSL字符串解析为Token列表
     *
     * @param dsl 规则DSL字符串
     * @return Token列表
     */
    public static List<String> tokenize(String dsl) {
        if (dsl == null || dsl.trim().isEmpty()) {
            throw new IllegalArgumentException("DSL字符串不能为空");
        }

        List<String> tokens = new ArrayList<>();
        int pos = 0;
        int length = dsl.length();

        while (pos < length) {
            char ch = dsl.charAt(pos);

            // 跳过空白字符
            if (Character.isWhitespace(ch)) {
                pos++;
                continue;
            }

            // 1. 尝试匹配括号和逗号
            if (ch == '(' || ch == ')' || ch == ',') {
                tokens.add(String.valueOf(ch));
                pos++;
                continue;
            }

            // 2. 尝试匹配字符串字面量（双引号或单引号）
            if (ch == '"' || ch == '\'') {
                StringBuilder str = new StringBuilder();
                char quote = ch;
                pos++; // 跳过开始引号

                while (pos < length && dsl.charAt(pos) != quote) {
                    if (dsl.charAt(pos) == '\\' && pos + 1 < length) {
                        // 处理转义字符
                        pos++;
                        char escaped = dsl.charAt(pos);
                        switch (escaped) {
                            case 'n':
                                str.append('\n');
                                break;
                            case 't':
                                str.append('\t');
                                break;
                            case 'r':
                                str.append('\r');
                                break;
                            case '\\':
                                str.append('\\');
                                break;
                            case '"':
                                str.append('"');
                                break;
                            case '\'':
                                str.append('\'');
                                break;
                            default:
                                str.append(escaped);
                        }
                    } else {
                        str.append(dsl.charAt(pos));
                    }
                    pos++;
                }

                if (pos >= length) {
                    throw new IllegalArgumentException("未闭合的字符串字面量，位置: " + (pos - str.length()));
                }

                pos++; // 跳过结束引号
                tokens.add("(&string)" + str.toString());
                continue;
            }

            // 3. 尝试匹配变量（@开头）
            if (ch == '@') {
                StringBuilder var = new StringBuilder("@");
                pos++;

                while (pos < length) {
                    char c = dsl.charAt(pos);
                    if (Character.isLetterOrDigit(c) || c == '_' || c == '.') {
                        var.append(c);
                        pos++;
                    } else {
                        break;
                    }
                }

                tokens.add(var.toString());
                continue;
            }

            // 4. 尝试匹配数字（包括负数和小数）
            if (Character.isDigit(ch) || (ch == '-' && pos + 1 < length && Character.isDigit(dsl.charAt(pos + 1)))) {
                StringBuilder num = new StringBuilder();

                if (ch == '-') {
                    num.append(ch);
                    pos++;
                }

                while (pos < length) {
                    char c = dsl.charAt(pos);
                    if (Character.isDigit(c) || c == '.') {
                        num.append(c);
                        pos++;
                    } else {
                        break;
                    }
                }

                tokens.add("(&number)" + num.toString());
                continue;
            }

            // 5. 尝试匹配函数调用形式的日期/时间
            // date(2024-01-01), datetime(2024-01-01 12:30:45), time(12:30:45)
            if (pos + 4 < length && dsl.substring(pos, pos + 4).equals("date")) {
                if (pos + 5 < length && dsl.charAt(pos + 4) == '(') {
                    // 查找匹配的右括号
                    int endPos = dsl.indexOf(')', pos + 5);
                    if (endPos == -1) {
                        throw new IllegalArgumentException("未闭合的日期函数，位置: " + pos);
                    }
                    String dateValue = dsl.substring(pos + 5, endPos);
                    tokens.add("(&date)" + dateValue.trim());
                    pos = endPos + 1;
                    continue;
                }
            }

            if (pos + 8 < length && dsl.substring(pos, pos + 8).equals("datetime")) {
                if (pos + 9 < length && dsl.charAt(pos + 8) == '(') {
                    int endPos = dsl.indexOf(')', pos + 9);
                    if (endPos == -1) {
                        throw new IllegalArgumentException("未闭合的日期时间函数，位置: " + pos);
                    }
                    String datetimeValue = dsl.substring(pos + 9, endPos);
                    tokens.add("(&datetime)" + datetimeValue.trim());
                    pos = endPos + 1;
                    continue;
                }
            }

            if (pos + 4 < length && dsl.substring(pos, pos + 4).equals("time")) {
                if (pos + 5 < length && dsl.charAt(pos + 4) == '(') {
                    int endPos = dsl.indexOf(')', pos + 5);
                    if (endPos == -1) {
                        throw new IllegalArgumentException("未闭合的时间函数，位置: " + pos);
                    }
                    String timeValue = dsl.substring(pos + 5, endPos);
                    tokens.add("(&time)" + timeValue.trim());
                    pos = endPos + 1;
                    continue;
                }
            }

            // 6. 尝试匹配操作符（按长度降序匹配，避免误匹配）
            boolean operatorMatched = false;
            for (String op : OPERATORS) {
                if (pos + op.length() <= length && dsl.substring(pos, pos + op.length()).equals(op)) {
                    // 对于字母操作符，需要确保后面不是字母或数字（避免误匹配）
                    if (op.matches("[a-zA-Z]+")) {
                        if (pos + op.length() < length) {
                            char nextChar = dsl.charAt(pos + op.length());
                            if (Character.isLetterOrDigit(nextChar)) {
                                continue; // 不是完整的操作符，继续尝试
                            }
                        }
                    }

                    tokens.add(op);
                    pos += op.length();
                    operatorMatched = true;
                    break;
                }
            }

            if (operatorMatched) {
                continue;
            }

            // 7. 尝试匹配关键字或标识符
            if (Character.isLetter(ch)) {
                StringBuilder identifier = new StringBuilder();

                while (pos < length) {
                    char c = dsl.charAt(pos);
                    if (Character.isLetterOrDigit(c) || c == '_') {
                        identifier.append(c);
                        pos++;
                    } else {
                        break;
                    }
                }

                String id = identifier.toString();

                // 检查是否是关键字
                boolean isKeyword = false;
                for (String keyword : KEYWORDS) {
                    if (keyword.equals(id)) {
                        tokens.add(id);
                        isKeyword = true;
                        break;
                    }
                }

                // 如果不是关键字，当作字符串字面量处理
                if (!isKeyword) {
                    tokens.add("(&string)" + id);
                }

                continue;
            }

            // 8. 无法识别的字符
            throw new IllegalArgumentException("无法识别的字符: '" + ch + "' at position " + pos +
                    "\n上下文: " + getContext(dsl, pos));
        }

        return tokens;
    }

    /**
     * 获取错误位置的上下文信息
     */
    private static String getContext(String dsl, int pos) {
        int start = Math.max(0, pos - 20);
        int end = Math.min(dsl.length(), pos + 20);
        String context = dsl.substring(start, end);
        int caretPos = pos - start;
        StringBuilder sb = new StringBuilder();
        sb.append(context).append("\n");
        for (int i = 0; i < caretPos; i++) {
            sb.append(" ");
        }
        sb.append("^");
        return sb.toString();
    }

    /**
     * 格式化Token列表为易读的字符串
     */
    public static String formatTokens(List<String> tokens) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tokens.size(); i++) {
            sb.append("  ").append(i).append(": \"").append(tokens.get(i)).append("\"");
            if (i < tokens.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 完整的DSL到表达式的转换
     * 这是最终用户应该使用的便捷方法
     */
    public static Object compile(String dsl) {
        List<String> tokens = tokenize(dsl);
        return RuleExpressionParser.parse(tokens);
    }
}