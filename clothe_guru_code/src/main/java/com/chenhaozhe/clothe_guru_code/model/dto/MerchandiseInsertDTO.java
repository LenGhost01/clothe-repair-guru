package com.chenhaozhe.clothe_guru_code.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchandiseInsertDTO{
    private Long merchantId;
    private String merchandiseName;
    private String merchandiseDescription;
    private String lowPrice;
    private String highPrice;
    private String[] category;
    private String[] material;
}
