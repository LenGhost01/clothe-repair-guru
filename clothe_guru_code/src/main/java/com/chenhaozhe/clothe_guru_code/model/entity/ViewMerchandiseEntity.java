package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewMerchandiseEntity {
    private Integer merchandiseId;
    private String merchandiseName;
    private String merchandiseDescription;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private String category;
    private String material;
    private Long belongs;
    private BigDecimal rating;
    private String publishTime;
    private Integer sales;
    private BigDecimal satisfactionRate;
    private String mainImg;
    private String subImg;
    private String merchantName;
    private String address;
    private String paymentMethod;
}
