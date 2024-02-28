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
public class UserFriendCorrelationViewEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 9093568979883146664L;
    private Long userId;
    private Long friendId;
    private String state;
    private String joinTime;
    private Integer id;
    private String nickname;
    private String avatar;
}
