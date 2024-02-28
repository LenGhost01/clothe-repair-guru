package com.chenhaozhe.clothe_guru_code.model.vo;

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
public class UsersAndCountVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -1797111207446945102L;
    List<UserVo> userVoList;
    Integer count;
}
