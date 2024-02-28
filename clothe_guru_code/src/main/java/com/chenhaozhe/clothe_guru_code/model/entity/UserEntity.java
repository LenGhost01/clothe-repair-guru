package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 7706811670782559899L;
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
    private String gender;
    private String safetyQuestion;
    private Long hasMerchant;
}
