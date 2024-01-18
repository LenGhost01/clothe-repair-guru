package com.chenhaozhe.clothe_guru_code.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo {
    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String receiver;
    private String location;
    private String lastLogin;
    private String payMesg;
    private String avatar;
}
