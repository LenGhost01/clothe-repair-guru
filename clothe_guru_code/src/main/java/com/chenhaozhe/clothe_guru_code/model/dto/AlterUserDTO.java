package com.chenhaozhe.clothe_guru_code.model.dto;

import com.chenhaozhe.clothe_guru_code.model.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlterUserDTO {
    private String oldPassword;
    private UserDTO userDTO;
}
