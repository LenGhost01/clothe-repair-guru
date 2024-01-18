package com.chenhaozhe.clothe_guru_code.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchandiseInsertDTO{
    private Integer merchandiseId;
    private String merchandiseName;
    private String merchandiseDescription;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private String[] category;
    private String[] material;
    private BigDecimal rating;
    private Long merchantId;

}
