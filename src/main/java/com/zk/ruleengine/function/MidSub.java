package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 中间截取字符串
 * @author zk
 */
public class MidSub implements Function<Object, String> {

    @Override
    public String execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("MidSubFunction requires exactly two arguments.");
        }

        String inputString = (String) args.get(0);
        int length = (int) args.get(1);

        if (length < 0) {
            throw new IllegalArgumentException("Length must be non-negative.");
        }

        int totalLength = inputString.length();
        if (length > totalLength) {
            return inputString;
        }

        int startIndex = (totalLength - length) / 2;
        int endIndex = startIndex + length;
        return inputString.substring(startIndex, endIndex);
    }

    @Override
    public String name() {
        return "midSub";
    }
}