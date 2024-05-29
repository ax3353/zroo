package com.zk.ruleengine;

import com.zk.ruleengine.func.*;

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
        register(new EqualsFunction());
        register(new AddFunction());
        register(new SubtractFunction());
        register(new MultiplyFunction());
        register(new DivideFunction());
        register(new GreaterThanFunction());
        register(new GreaterThanOrEqualFunction());
        register(new LessThanFunction());
        register(new LessThanOrEqualFunction());
        register(new AndFunction());
        register(new OrFunction());
        register(new NotFunction());
        register(new ToStrFunction());
        register(new ToIntFunction());
        register(new IfFunction());
        register(new GetValueFunction());
        register(new StrInputFunction());
        register(new NumberInput());
        register(new DateGreaterThanFunction());
        register(new DateGreaterThanOrEqualFunction());
        register(new DateLessThanFunction());
        register(new DateLessThanOrEqualFunction());
        register(new ToDateFunction());
        register(new PrintFunction());
    }
}