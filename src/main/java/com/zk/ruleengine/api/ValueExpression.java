package com.zk.ruleengine.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ValueExpression extends Expression {

    private String type;
    private String value;

    public ValueExpression(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
