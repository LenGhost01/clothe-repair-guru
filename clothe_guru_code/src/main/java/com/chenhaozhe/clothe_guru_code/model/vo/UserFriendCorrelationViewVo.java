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
public class UserFriendCorrelationViewVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -7065084437701403446L;
    private String userId;
    private String friendId;
    private String state;
    private String joinTime;
    private String id;
    private String nickname;
    private String avatar;
}
