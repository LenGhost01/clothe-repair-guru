package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.entity.UserPrivateChatViewEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.UserPrivateChatViewVo;

public class UserPrivateChatViewConverter {
    public static UserPrivateChatViewVo userPrivateChatViewEntityToVo(UserPrivateChatViewEntity entity){
        return UserPrivateChatViewVo.builder()
                .joinTime(entity.getJoinTime())
                .privateChatId(entity.getPrivateChatId().toString())
                .nickName(entity.getNickName())
                .targetId(entity.getTargetId().toString())
                .userId(entity.getUserId().toString())
                .avatar(entity.getAvatar())
                .build();
    }
}
