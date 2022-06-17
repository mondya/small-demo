package com.xhh.smalldemo.utils;

import com.xhh.smalldemo.pojo.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectUtil {
    public static void main(String[] args) {
//        User user = new User(12L, "hello", 25, "email", LocalDateTime.now(), LocalDateTime.now(), (byte) 1);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hello");
        map.put("email", "email");
        map.put("age", 25);
        int count = 25;
        System.out.println(count != (int) map.get("age"));
    }

    //在update时判断Object属性是否更改
    public static boolean checkChange(Object object, Map<String, Object> map) {
        Map<String, Object> checkMap = new HashMap<>();
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            //拿到所有属性名称
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            //通过属性拿到值
            for (String fieldName : fieldNames) {
                String firstStr = fieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstStr + fieldName.substring(1);
                Method method = object.getClass().getMethod(getter);
                System.out.println(method);
                Object invoke = method.invoke(object);
                checkMap.put(fieldName, invoke);
            }
            //判断是否有更改
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if (Objects.equals(entry.getValue(), checkMap.get(key))) {
//                if (!entry.getValue().equals(checkMap.get(key))){
//                if (entry.getValue() != checkMap.get(key)) {
                    return false;
                }
//                System.out.println(entry.getValue().equals(checkMap.get(key)));
            }

            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
