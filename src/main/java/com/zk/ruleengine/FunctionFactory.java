package com.zk.ruleengine;

import com.zk.ruleengine.function.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zk
 */
public class FunctionFactory {
    private static final Map<String, Function<?, ?>> FUNCTION_MAP = new HashMap<>();

    public static void register(Function<?, ?> function) {
        FUNCTION_MAP.put(function.name(), function);
    }

    public static <T, R> Function<T, R> getFunction(String name) {
        Function<?, ?> function = FUNCTION_MAP.get(name);
        if (function == null) {
            throw new IllegalArgumentException("No function registered with name: " + name);
        }

        try {
            return (Function<T, R>) function;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Function type mismatch for: " + name, e);
        }
    }

    // 注册默认操作
    static {
        register(new Equals());
        register(new NotEquals());
        register(new Contains());
        register(new NotContains());
        register(new NumberEquals());
        register(new Add());
        register(new Subtract());
        register(new Multiply());
        register(new Divide());
        register(new Abs());
        register(new Ceil());
        register(new Floor());
        register(new Scale());
        register(new GreaterThan());
        register(new GreaterThanOrEqual());
        register(new LessThan());
        register(new LessThanOrEqual());
        register(new And());
        register(new Or());
        register(new Not());
        register(new ToStr());
        register(new LeftSub());
        register(new MidSub());
        register(new RightSub());
        register(new ToNumber());
        register(new If());
        register(new GetValue());
        register(new StrInput());
        register(new NumberInput());
        register(new DateInput());
        register(new DateTimeInput());
        register(new DateGreaterThan());
        register(new DateGreaterThanOrEqual());
        register(new DateLessThan());
        register(new DateLessThanOrEqual());
        register(new DayBetween());
        register(new HourBetween());
        register(new MinuteBetween());
        register(new SecondBetween());
        register(new ToDate());
        register(new NowDate());
        register(new NowDateTime());
        register(new Print());
    }
}