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
public class UserRecordEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -492775745191729155L;
    private Long userId;
    private Integer recordId;
    private String loginIp;
    private String loginLocation;
    private String loginTime;
}
