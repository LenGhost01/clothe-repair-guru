package com.chenhaozhe.clothe_guru_code.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantUpdateDTO {
    private Long merchantId;
    private String merchantName;
    private String contactMesg;
    private String email;
    private String address;
    private String registerMesg;
    private String joinTime;
    private String paymentMethod;
    private String merchantDescription;
    private List<ImageDataDTO> certification;
    private List<String> certificationText;





}
