package com.chenhaozhe.clothe_guru_code.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -2868195860060428191L;
    private Integer adminId;
    private String adminName;
    private String password;
    private String adminIdentity;
}
