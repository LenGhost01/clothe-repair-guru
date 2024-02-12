package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.dto.MerchantUpdateDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.MerchantEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchantVo;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;

public class MerchantConverter {
    public static MerchantVo EntityToVo(MerchantEntity entity){
        return MerchantVo.builder()
                .merchantId(entity.getMerchantId().toString())
                .merchantName(entity.getMerchantName())
                .contactMesg(entity.getContactMesg())
                .address(entity.getAddress())
                .registerMesg(entity.getRegisterMesg())
                .merchantDescription(entity.getMerchantDescription())
                .joinTime(entity.getJoinTime())
                .paymentMethod(entity.getPaymentMethod())
                .certification(entity.getCertification())
                .email(entity.getEmail())
                .build();
    }

    public static MerchantEntity merchantDTOTOEntity(MerchantUpdateDTO dto){
        return MerchantEntity.builder()
                .merchantId(dto.getMerchantId())
                .merchantName(dto.getMerchantName())
                .merchantDescription(dto.getMerchantDescription())
                .email(dto.getEmail())
                .paymentMethod(dto.getPaymentMethod())
                .registerMesg(dto.getRegisterMesg())
                .joinTime(dto.getJoinTime())
                .contactMesg(dto.getContactMesg())
                .certification(JackonUtil.ListToJson(dto.getCertification()))
                .build();
    }
}
