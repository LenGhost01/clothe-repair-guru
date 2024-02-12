package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.ApplicationEntityVo;

public class ApplicationConverter {
    public static ApplicationEntityVo ApplicationEntityToVo(ApplicationsEntity entity){
        return ApplicationEntityVo.builder()
                .applicationId(entity.getApplicationId())
                .userId(entity.getUserId().toString())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .merchantName(entity.getMerchantName())
                .applyTime(entity.getApplyTime())
                .auditState(entity.getAuditState())
                .auditFeedback(entity.getAuditFeedback())
                .certification(entity.getCertification())
                .introduce(entity.getIntroduce())
                .build();
    }
}
