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
    // 通过传输过来的主图片和副图片的uri来判断是否需要更新数据
    private String[] oldSubImg;
}
