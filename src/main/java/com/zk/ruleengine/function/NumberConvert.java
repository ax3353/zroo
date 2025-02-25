package com.zk.ruleengine.function;

import com.zk.ruleengine.Evaluator;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数值转换
 *
 * @author zk
 */
public abstract class NumberConvert {

    /**
     * 转换
     */
    protected BigDecimal convert(Evaluator evaluator, Object arg) {
        if (arg instanceof List) {
            Object evaluated = evaluator.eval((List<Object>) arg);
            return new BigDecimal(evaluated.toString());
        } else if (arg instanceof Number) {
            return new BigDecimal(arg.toString());
        } else if (arg instanceof String) {
            try {
                return new BigDecimal((String) arg);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("不支持的数字格式: " + arg);
            }
        } else {
            throw new IllegalArgumentException("不支持的数字格式: " + arg.getClass().getName());
        }
    }
}
