package com.chenhaozhe.clothe_guru_code.model.vo;

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
public class UserVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 8657155271696131170L;
    private String userId;
    private String username;
    private Boolean passwordExists;
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
    private String merchantId;
}
