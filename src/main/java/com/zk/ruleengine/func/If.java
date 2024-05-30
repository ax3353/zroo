package com.zk.ruleengine.func;

import com.zk.ruleengine.Evaluator;
import com.zk.ruleengine.Function;

import java.util.List;

/**
 * if-elseif-else
 * @author zk
 */
public class If implements Function<Object, Object> {

    @Override
    public Object execute(Evaluator evaluator, List<Object> args) {
        if (args.size() < 2) {
            throw new IllegalArgumentException("IfFunction requires at least two arguments: condition and trueBranch.");
        }

        for (int i = 0; i < args.size() - 1; i += 2) {
            Object condition = args.get(i);
            Object conditionResult;
            if (condition instanceof List) {
                conditionResult = evaluator.eval((List<Object>) condition);
            } else {
                conditionResult = condition;
            }

            if (!(conditionResult instanceof Boolean)) {
                throw new IllegalArgumentException("Condition expression must evaluate to a Boolean.");
            }

            if ((Boolean) conditionResult) {
                Object trueBranch = args.get(i + 1);
                if (trueBranch instanceof List) {
                    return evaluator.eval((List<Object>) trueBranch);
                } else {
                    return trueBranch;
                }
            }
        }

        // 处理最后一个else
        if (args.size() % 2 != 0) {
            Object elseBranch = args.get(args.size() - 1);
            if (elseBranch instanceof List) {
                return evaluator.eval((List<Object>) elseBranch);
            } else {
                return elseBranch;
            }
        }

        // 所有条件都为假，没有else分支
        return null;
    }

    @Override
    public String name() {
        return "if";
    }

    @Override
    public boolean evalArgsFirst() {
        return false;
    }
}