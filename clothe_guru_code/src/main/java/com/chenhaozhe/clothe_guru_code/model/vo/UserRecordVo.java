package com.chenhaozhe.clothe_guru_code.model.vo;

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
public class UserRecordVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -6532005116013800828L;
    private Integer recordId;
    private String loginIp;
    private String loginLocation;
    private String loginTime;
}
