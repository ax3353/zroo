package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 右截取字符串
 * 
 * @author zk
 */
public class RightSub implements Function<Object, String> {

    @Override
    public String execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("RightSubFunction requires exactly two arguments.");
        }

        String inputString = args.get(0).toString();
        int length = (int) args.get(1);

        if (length < 0) {
            throw new IllegalArgumentException("Length must be non-negative.");
        }

        if (inputString.length() <= length) {
            return inputString;
        }

        return inputString.substring(inputString.length() - length);
    }

    @Override
    public String name() {
        return "rightSub";
    }
}