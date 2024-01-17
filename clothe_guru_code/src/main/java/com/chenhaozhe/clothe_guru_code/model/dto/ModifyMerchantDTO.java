package com.chenhaozhe.clothe_guru_code.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyMerchantDTO {
    private Long merchantId;
    private String contactMesg;
    private String address;
    private String paymentMethod;
    private String merchantDescription;
    private String certification;
}
