package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2141448629683360815L;
    private Integer materialId;
    private String materialName;
    private String materialDescription;
    private String reconstructCoefficient;
    private String alias;
}
