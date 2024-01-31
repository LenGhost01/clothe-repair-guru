package com.chenhaozhe.clothe_guru_code.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationRequestDTO {
    private String userId;
    private String merchantName;
    private String address;
    private String phone;
    private String email;
    private List<String> certificationText;
    private String introduce;
}
