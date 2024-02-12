package com.chenhaozhe.clothe_guru_code.model.vo;

import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationAndCountVo {
    private List<ApplicationEntityVo> applicationsEntityList;
    private Integer count;
}
