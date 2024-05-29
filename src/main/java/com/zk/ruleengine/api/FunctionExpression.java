package com.zk.ruleengine.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FunctionExpression extends Expression {

    private String functionName;
    private Expression argument;

    public FunctionExpression(String functionName, Expression argument) {
        this.functionName = functionName;
        this.argument = argument;
    }
}
