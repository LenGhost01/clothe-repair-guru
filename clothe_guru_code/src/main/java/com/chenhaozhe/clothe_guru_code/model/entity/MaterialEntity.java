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
public class MaterialEntity {
    private Integer materialId;
    private String materialName;
    private String materialDescription;
    private BigDecimal reconstructCoefficient;
}
