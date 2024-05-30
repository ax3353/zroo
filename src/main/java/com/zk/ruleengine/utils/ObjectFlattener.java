package com.zk.ruleengine.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象扁平化成map
 * @author zk
 */
public class ObjectFlattener {

    private static final String SEPARATOR = ".";

    /**
     * 嵌套对象转大map(扁平化)
     *
     * @param object 源对象
     * @return map
     */
    public static Map<String, Object> flatMap(Object object) {
        Map<String, Object> maps = JSON.parseObject(JSON.toJSONString(object), Map.class);
        Map<String, Object> result = new HashMap<>();
        maps.forEach((key, value) -> common(maps, result, key, value, key));
        return result;
    }

    /**
     * List嵌套对象转大list map(扁平化)
     *
     * @param objects 源List对象
     * @return map
     */
    public static <T> List<Map<String, Object>> listFlatmap(List<T> objects) {
        ArrayList<Map<String, Object>> resultList = new ArrayList<>();
        for (T t : objects) {
            resultList.add(flatMap(t));
        }
        return resultList;
    }

    private static Map<String, Object> flatMap(Map<String, Object> maps, String prefix) {
        Map<String, Object> result = new HashMap<>();
        String keyPrefix = prefix + SEPARATOR;
        maps.forEach((key, value) -> {
            String newKey = keyPrefix + key;
            common(maps, result, key, value, newKey);
        });
        return result;
    }

    private static void common(Map<String, Object> maps, Map<String, Object> result, String key,
                               Object value, String newKey) {
        if (maps.get(key) != null && value instanceof JSONObject) {
            Map<String, Object> subMaps = (Map) maps.get(key);
            Map<String, Object> map = flatMap(subMaps, newKey);
            if (!map.isEmpty()) {
                result.putAll(map);
            }
        } else {
            result.put(newKey, maps.get(key));
        }
    }
}