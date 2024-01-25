package com.chenhaozhe.clothe_guru_code.model.vo;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterVo {
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,16}$",message = "用户名不符合要求，必须使用大小写的英文字符或_-字符组成的4-16位的用户名。")
    private String username;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$",message = "密码强度不符合要求，要求最少6位，必须包含大小写字母和数字的组合。")
    private String password;
    @Pattern(regexp="^[\\u4E00-\\u9FA5A-Za-z0-9]{2,20}$",message = "昵称输入非法，存在非法字符或长度超出范围。")
    private String nickname;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+",message = "邮箱格式不符合要求，请检查。")
    private String email;
    private String captcha;
}
