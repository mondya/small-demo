package com.xhh.smalldemocommon.utils;



import com.xhh.smalldemocommon.pojo.Student;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ObjectUtil {
    public static void main(String[] args) {
//        User user = new User(12L, "hello", 25, "email", LocalDateTime.now(), LocalDateTime.now(), (byte) 1);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hello");
        map.put("email", "email");
        map.put("age", 25);

        Student student = new Student.Builder().campusId(2L).name("helo").build();
//        int count = 25;
//        System.out.println(count != (int) map.get("age"));
        System.out.println(object2Map(student));
    }

    /**
     * 在update时判断Object属性是否更改
     * @param object
     * @param map
     * @return boolean 有更改为true,无更改为false
     */
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
                if (!Objects.equals(entry.getValue(), checkMap.get(key))) {
//                if (!entry.getValue().equals(checkMap.get(key))){
//                if (entry.getValue() != checkMap.get(key)) {
                    return true;
                }
//                System.out.println(entry.getValue().equals(checkMap.get(key)));
            }
            

        } catch (Exception e) {
            log.error("ObjectUtils checkChange method fail, message:", e);
        }
        return false;
    }

    
    public static Map<String, Object> object2Map(Object o) {
        
        Map<String, Object> map = new HashMap<>();
        
        Class<?> aClass = o.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String key = field.getName();
            
            try {
                Object value = field.get(o);

                if (value == null) {
                    value = "";
                }

                map.put(key, value);
            } catch (Exception e) {
                log.error("[cast object to map error]", e);
            }
        }
        
        return map;
    }

}
