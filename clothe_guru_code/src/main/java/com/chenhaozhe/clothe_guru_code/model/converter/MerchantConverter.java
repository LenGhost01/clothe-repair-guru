package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.entity.MerchantEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchantVo;

public class MerchantConverter {
    public static MerchantVo EntityToVo(MerchantEntity entity){
        return MerchantVo.builder()
                .merchantId(entity.getMerchantId())
                .merchantName(entity.getMerchantName())
                .contactMesg(entity.getContactMesg())
                .address(entity.getAddress())
                .registerMesg(entity.getRegisterMesg())
                .merchantDescription(entity.getMerchantDescription())
                .joinTime(entity.getJoinTime())
                .paymentMethod(entity.getPaymentMethod())
                .certification(entity.getCertification())
                .build();
    }
}
