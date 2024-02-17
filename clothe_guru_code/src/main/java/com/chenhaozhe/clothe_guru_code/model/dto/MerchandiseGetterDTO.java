package com.chenhaozhe.clothe_guru_code.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchandiseGetterDTO {
    // 模糊查询参数
    private String keyWord;
    private Integer keyWordLabel;
    // 排序参数
    private String orderLabel;
    private Boolean orderRule;
    // 按价格筛选
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    // 筛选标签
    private String[] filterParam;
    private String[] filterValue;
    // 获取页数
    private Integer page;
}
