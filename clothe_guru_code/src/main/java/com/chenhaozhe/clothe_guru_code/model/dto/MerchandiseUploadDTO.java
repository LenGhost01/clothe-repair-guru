package com.chenhaozhe.clothe_guru_code.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchandiseUploadDTO {
    private Integer merchandiseId;
    private String merchandiseName;
    private String merchandiseDescription;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private String[] category;
    private String[] material;
    private BigDecimal rating;
}
