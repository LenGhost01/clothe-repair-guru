package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.vo.AdminVo;

public interface AdminServices {
    AdminVo getAdmin(String adminIdentity,String password);
}
