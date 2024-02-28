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
public class AdminEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -5161030315550896047L;
    private Integer adminId;
    private String adminName;
    private String password;
    private String adminIdentity;
}
