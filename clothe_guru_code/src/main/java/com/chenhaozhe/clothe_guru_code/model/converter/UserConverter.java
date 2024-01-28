package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.dto.UserDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.UserEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.UserRecordEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.UserRecordVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UserVo;

import java.util.Objects;

public class UserConverter {
    public static UserVo convertToVO(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserVo vo = UserVo.builder()
                .userId(entity.getUserId().toString())
                .nickname(entity.getNickname())
                .phone(entity.getPhone())
                .payMesg(entity.getPayMesg())
                .lastLogin(entity.getLastLogin())
                .receiver(entity.getReceiver())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .passwordExists(!Objects.equals(entity.getPassword(),null)) //只需返回密码存不存在
                .avatar(entity.getAvatar())
                .gender(entity.getGender())
                .safetyQuestion(entity.getSafetyQuestion())
                .merchantId(entity.getHasMerchant()==null?null:entity.getHasMerchant().toString())
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
                .avatar(dto.getAvatar())
                .gender(dto.getGender())
                .safetyQuestion(dto.getSafetyQuestion())
                .build();
        return entity;
    }

    public static UserRecordVo convertUserRecordEntityToVo(UserRecordEntity entity){
        if(entity == null){
            return null;
        }
        return UserRecordVo.builder()
                .recordId(entity.getRecordId())
                .loginIp(entity.getLoginIp())
                .loginLocation(entity.getLoginLocation())
                .loginTime(entity.getLoginTime())
                .build();
    }
}
