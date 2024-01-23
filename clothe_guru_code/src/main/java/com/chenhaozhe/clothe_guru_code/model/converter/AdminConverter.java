package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.entity.AdminEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.AdminVo;

public class AdminConverter {
    public static AdminVo EntityToVo(AdminEntity entity){
        return AdminVo.builder()
                .adminId(entity.getAdminId())
                .adminName(entity.getAdminName())
                .adminIdentity(entity.getAdminIdentity())
                .password(null)
                .build();
    }
}
