package com.zk.ruleengine;

import java.util.HashMap;
import java.util.Map;

public class ExpressionTest {

    public static void main(String[] args) {
        Map<String, Object> context = new HashMap<>();
        context.put("createTime", "2024-06-02");
        context.put("deviceName", "河庄门店");
        context.put("pressure", 30.00);
        context.put("alarmTime", "2024-06-02");

//        String a = "[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"||\",[\"&&\",[\">=\",[\"@value\",\"pressure\"],[\"numberInput\",60]],[\"<=\",[\"@value\",\"pressure\"],[\"numberInput\",100]]],[\"!\",[\"&&\",[\"date>=\",[\"@value\",\"alarmTime\"],[\"strInput\",\"2024-05-01\"]],[\"date<=\",[\"@value\",\"alarmTime\"],[\"strInput\",\"2024-05-20\"]]]]]],[\"print\",\"正在报警\"],[\"if\",[\"&&\",[\"eq\",[\"@value\",\"deviceName\"],[\"strInput\",\"河庄门店\"]],[\"<\",[\"@value\",\"pressure\"],[\"numberInput\",60]]],[\"print\",\"已自动恢复\"],[\"print\",\"没有设备报警\"]]]";
        String b = "[\"&&\",[\"eq\",[\"toDate\",[\"@value\",\"createTime\"]],[\"toDate\",[\"strInput\",\"2024-06-02\"]]],[\"||\",[\"&&\",[\">=\",[\"@value\",\"pressure\"],[\"numberInput\",60]],[\"<=\",[\"@value\",\"pressure\"],[\"numberInput\",100]]],[\"!\",[\"&&\",[\"date>=\",[\"@value\",\"alarmTime\"],[\"strInput\",\"2024-05-01\"]],[\"date<=\",[\"@value\",\"alarmTime\"],[\"strInput\",\"2024-05-20\"]]]]]]";
        RuleParser<Object> parser1 = new RuleParser<>(b, context);
        Object eval = parser1.eval();
        System.out.println(eval);
    }
}