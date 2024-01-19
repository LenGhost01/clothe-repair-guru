package com.chenhaozhe.clothe_guru_code.model.dto;

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
    private String keyWord;
    private Integer keyWordLabel;
    private Integer orderLabel;
    private Boolean orderRule;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private Integer page;
}
