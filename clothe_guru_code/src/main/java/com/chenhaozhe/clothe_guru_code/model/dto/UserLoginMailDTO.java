package com.chenhaozhe.clothe_guru_code.model.dto;

import lombok.Data;

@Data
public class UserLoginMailDTO {
    private String email;
    private String captcha;
}
