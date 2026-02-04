package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;
import com.zk.ruleengine.utils.Utils;

import java.util.List;

/**
 * 中间截取字符串 - 支持两种模式
 * <p>
 * 模式1 (2个参数): ["midSub", "字符串", 长度]
 * 从字符串中间位置截取指定长度
 * 示例: ["midSub", "abcdef", 3] → "bcd" (从中间截取3个字符)
 * <p>
 * 模式2 (3个参数): ["midSub", "字符串", 起始位置, 长度]
 * 从指定位置开始截取指定长度
 * 示例: ["midSub", "Hello World", 0, 5] → "Hello"
 * 示例: ["midSub", "Hello World", 6, 5] → "World"
 *
 * @author zk
 */
public class MidSub implements Function<Object, String> {

    @Override
    public String execute(Evaluator evaluator, List<Object> args) {
        // 参数数量校验
        if (args.size() != 2 && args.size() != 3) {
            throw new IllegalArgumentException(
                    "[中间截取操作]需要2个或3个参数。" +
                            "用法1: [\"midSub\", 字符串, 长度] - 从中间截取; " +
                            "用法2: [\"midSub\", 字符串, 起始位置, 长度] - 从指定位置截取"
            );
        }

        String inputString = (String) args.get(0);

        if (args.size() == 2) {
            // 模式1: 从中间截取指定长度
            return executeMiddleMode(inputString, args.get(1));
        } else {
            // 模式2: 从指定位置截取指定长度
            return executePositionMode(inputString, args.get(1), args.get(2));
        }
    }

    /**
     * 模式1: 从字符串中间位置截取指定长度
     *
     * @param inputString 输入字符串
     * @param lengthArg   要截取的长度
     * @return 截取后的字符串
     */
    private String executeMiddleMode(String inputString, Object lengthArg) {
        int length = Utils.toInt(lengthArg);

        if (length < 0) {
            throw new IllegalArgumentException("[中间截取操作]截取长度不能为负数: " + length);
        }

        int totalLength = inputString.length();

        // 如果要截取的长度大于等于字符串长度，直接返回整个字符串
        if (length >= totalLength) {
            return inputString;
        }

        // 计算中间位置
        int startIndex = (totalLength - length) / 2;
        int endIndex = startIndex + length;

        return inputString.substring(startIndex, endIndex);
    }

    /**
     * 模式2: 从指定位置开始截取指定长度
     *
     * @param inputString 输入字符串
     * @param startArg    起始位置（从0开始）
     * @param lengthArg   要截取的长度
     * @return 截取后的字符串
     */
    private String executePositionMode(String inputString, Object startArg, Object lengthArg) {
        int start = Utils.toInt(startArg);
        int length = Utils.toInt(lengthArg);

        // 参数校验
        if (start < 0) {
            throw new IllegalArgumentException("[中间截取操作]起始位置不能为负数: " + start);
        }

        if (length < 0) {
            throw new IllegalArgumentException("[中间截取操作]截取长度不能为负数: " + length);
        }

        int totalLength = inputString.length();

        // 如果起始位置超出字符串长度，返回空字符串
        if (start >= totalLength) {
            return "";
        }

        // 计算结束位置（不超过字符串长度）
        int endIndex = Math.min(start + length, totalLength);

        return inputString.substring(start, endIndex);
    }

    @Override
    public String name() {
        return "midSub";
    }
}