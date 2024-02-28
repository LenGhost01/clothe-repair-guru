package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.entity.UserFriendCorrelationViewEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.UserFriendCorrelationViewVo;
import lombok.Builder;

public class UserFriendCorrelationViewConverter {
    public static UserFriendCorrelationViewVo UserFriendCorrelationEntityToVo(UserFriendCorrelationViewEntity entity){
        return UserFriendCorrelationViewVo.builder()
                .userId(entity.getUserId().toString())
                .friendId(entity.getFriendId().toString())
                .id(entity.getId().toString())
                .state(entity.getState().toString())
                .nickname(entity.getNickname())
                .joinTime(entity.getJoinTime())
                .avatar(entity.getAvatar())
                .build();
    }

}
