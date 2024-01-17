package com.chenhaozhe.clothe_guru_code.util;

public class CamelSnakeTranslate {
    public static String convert(String camelCaseStr) {
        if (camelCaseStr == null) {
            return null;
        }

        // 正则表达式: 在大写字母前添加下划线并转为小写
        String snakeCaseStr = camelCaseStr
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();

        return snakeCaseStr;
    }
}
