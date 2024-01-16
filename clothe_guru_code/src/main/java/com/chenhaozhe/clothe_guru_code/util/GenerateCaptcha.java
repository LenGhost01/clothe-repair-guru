package com.chenhaozhe.clothe_guru_code.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenerateCaptcha {
    private static final String CaptchaSet = "0123456789abcdefghijklmnopqrstvuwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generate(){
        String captcha = "";
        for (int i =0;i<4;i++){
            captcha += CaptchaSet.charAt((int)(Math.random() * 62));
        }
        return captcha;
    }
}
