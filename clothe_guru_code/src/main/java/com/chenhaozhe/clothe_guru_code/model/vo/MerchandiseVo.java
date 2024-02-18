package com.chenhaozhe.clothe_guru_code.model.vo;

import com.chenhaozhe.clothe_guru_code.model.entity.CategoryEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.MaterialEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchandiseVo {
    private Integer merchandiseId;
    private String merchandiseName;
    private String merchandiseDescription;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private List<Integer> category;
    private List<Integer> material;
    private String belongs;
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
