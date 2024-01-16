package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.dto.UserDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.UserEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.UserVo;

public class UserConverter {
    public static UserVo convertToVO(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserVo vo = UserVo.builder()
                .userId(entity.getUserId())
                .nickname(entity.getNickname())
                .phone(entity.getPhone())
                .payMesg(entity.getPayMesg())
                .lastLogin(entity.getLastLogin())
                .receiver(entity.getReceiver())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .password(null) //对密码擦除
                .build();
        return vo;
    }

    public static UserEntity convertToEntity(UserDTO dto){
        if (dto == null){
            return null;
        }
        UserEntity entity = UserEntity.builder()
                .userId(dto.getUserId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .receiver(dto.getReceiver())
                .location(dto.getLocation())
                .lastLogin(dto.getLastLogin())
                .payMesg(dto.getPayMesg())
                .build();
        return entity;
    }
}
