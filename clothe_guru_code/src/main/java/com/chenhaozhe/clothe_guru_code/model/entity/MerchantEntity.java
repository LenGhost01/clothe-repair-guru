package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantEntity {
    private Long merchantId;
    private String merchantName;
    private String contactMesg;
    private String address;
    private String registerMesg;
    private String joinTime;
    private String paymentMethod;
    private String merchantDescription;
    private String certification;
}
