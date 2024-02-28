package com.chenhaozhe.clothe_guru_code.model.vo;

import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationAndCountVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -6640054399497834461L;
    private List<ApplicationEntityVo> applicationsEntityList;
    private Integer count;
}
