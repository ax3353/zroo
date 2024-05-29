package com.zk.ruleengine.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UnaryExpression extends Expression {

    private String operator;
    private Expression operand;

    public UnaryExpression(String operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }
}
