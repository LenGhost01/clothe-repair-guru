package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -2793202638193608266L;
    private Integer categoryId;
    private String categoryName;
    private String alias;
}
