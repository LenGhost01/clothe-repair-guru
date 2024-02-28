package com.chenhaozhe.clothe_guru_code.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -6401877235454959679L;
    private String merchantId;
    private String merchantName;
    private String contactMesg;
    private String email;
    private String address;
    private String registerMesg;
    private String joinTime;
    private String paymentMethod;
    private String merchantDescription;
    private String certification;
}
