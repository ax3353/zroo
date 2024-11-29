package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * 右截取字符串
 * @author zk
 */
public class RightSub implements Function<Object, String> {

    @Override
    public String execute(Evaluator evaluator, List<Object> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("RightSubFunction requires exactly two arguments.");
        }

        int length;
        String inputString = (String) args.get(0);
        Object o = args.get(1);
        if (o instanceof String) {
            try {
                length = Integer.parseInt((String) o);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("[右截取操作]的截取长度参数类型不对");
            }
        } else if (o instanceof Number) {
            length = (int) o;
        } else {
            throw new IllegalArgumentException("[右截取操作]的截取长度参数类型不对");
        }

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