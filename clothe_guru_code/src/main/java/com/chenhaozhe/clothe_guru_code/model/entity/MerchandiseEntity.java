package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchandiseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6731127978520044821L;
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
    private Integer satisfaction;
    private String mainImg;
    private String subImg;
}
