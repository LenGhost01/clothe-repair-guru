package com.chenhaozhe.clothe_guru_code.model.converter;

import com.chenhaozhe.clothe_guru_code.model.entity.ViewMerchandiseEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.MerchandiseVo;

public class MerchandiseConverter {
    public static MerchandiseVo merchandiseEntityToVo(ViewMerchandiseEntity entity){
        return MerchandiseVo.builder()
                .merchandiseId(entity.getMerchandiseId())
                .merchandiseName(entity.getMerchandiseName())
                .merchandiseDescription(entity.getMerchandiseDescription())
                .lowPrice(entity.getLowPrice())
                .highPrice(entity.getHighPrice())
                .belongs(entity.getBelongs().toString())
                .rating(entity.getRating())
                .publishTime(entity.getPublishTime())
                .sales(entity.getSales())
                .mainImg(entity.getMainImg())
                .subImg(entity.getSubImg())
                .merchantName(entity.getMerchantName())
                .address(entity.getAddress())
                .paymentMethod(entity.getPaymentMethod())
                .satisfactionRate(entity.getSatisfactionRate())
                .build();
    }

}
