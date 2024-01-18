package com.chenhaozhe.clothe_guru_code.util;

public class GenerateFormattedImagePath {
    public static String generateNewPath(String fileName){

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return "IMG_"+System.currentTimeMillis()+"img_" + System.currentTimeMillis() + suffix;

    }
}
