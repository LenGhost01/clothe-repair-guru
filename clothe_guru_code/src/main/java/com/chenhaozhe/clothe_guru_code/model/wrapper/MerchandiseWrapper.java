package com.chenhaozhe.clothe_guru_code.model.wrapper;

import com.chenhaozhe.clothe_guru_code.model.dto.MerchandiseGetterDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchandiseWrapper {
    private String keyWord;
    private String keyWordLabel;
    private String orderLabel;
    //根据DTO中的orderRule的真假判断正序还是逆序
    private String orderRule;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    // 筛选标签
    private Map<String,String> filterPair;
    private Integer offset;
    private Short pageSize;
}
