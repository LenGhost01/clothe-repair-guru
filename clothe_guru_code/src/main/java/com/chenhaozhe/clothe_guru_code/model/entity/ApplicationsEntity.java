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
public class ApplicationsEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -609271304655073592L;
    private Integer applicationId;
    private Long userId;
    private String applyTime;
    private String merchantName;
    private Short auditState;
    private String auditFeedback;
    private String address;
    private String phone;
    private String email;
    private String certification;
    private String introduce;
}
