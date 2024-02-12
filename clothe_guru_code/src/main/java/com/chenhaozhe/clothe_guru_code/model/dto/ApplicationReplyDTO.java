package com.chenhaozhe.clothe_guru_code.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationReplyDTO {
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
