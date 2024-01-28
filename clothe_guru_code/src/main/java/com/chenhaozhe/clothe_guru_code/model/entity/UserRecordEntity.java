package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRecordEntity {
    private Long userId;
    private Integer recordId;
    private String loginIp;
    private String loginLocation;
    private String loginTime;
}
