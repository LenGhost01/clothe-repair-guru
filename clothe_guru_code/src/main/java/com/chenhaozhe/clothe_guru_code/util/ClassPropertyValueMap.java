package com.chenhaozhe.clothe_guru_code.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPropertyValueMap {
    public static Map<String,String> getPropertyValueMap(Object targetObject) throws IllegalAccessException {
        Class<?> targetClass = targetObject.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        Map<String,String> propertyValueMap = new HashMap();
        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有字段
            Object value = field.get(targetObject);
            if(value!=null){
                propertyValueMap.put(field.getName(),value.toString());
            }

        }
        return propertyValueMap;
    }

    public static Map<String,String> getPropertyValueMapListSnackCase(Object targetObject) throws IllegalAccessException {
        Class<?> targetClass = targetObject.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        Map<String,String> propertyValueMap = new HashMap();
        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有字段
            String property = CamelSnakeTranslate.convert(field.getName());
            Object value = field.get(targetObject);
            if(value!=null){
                propertyValueMap.put(property,value.toString());
            }
        }
        return propertyValueMap;
    }

}
