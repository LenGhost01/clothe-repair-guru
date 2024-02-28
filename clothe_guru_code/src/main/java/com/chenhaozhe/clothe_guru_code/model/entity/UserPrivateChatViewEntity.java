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
public class UserPrivateChatViewEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6284875325452639370L;
    private Long userId;
    private Integer privateChatId;
    private Long targetId;
    private String nickName;
    private String joinTime;
    private String avatar;
}
