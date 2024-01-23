package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.mapper.AdminMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.AdminConverter;
import com.chenhaozhe.clothe_guru_code.model.entity.AdminEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.AdminVo;
import com.chenhaozhe.clothe_guru_code.services.AdminServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Objects;

@Service
@Slf4j
public class AdminServicesImpl implements AdminServices {
    @Resource
    AdminMapper adminMapper;
    @Override
    public AdminVo getAdmin(String adminIdentity,String password) {
        AdminEntity admin = adminMapper.getAdmin(adminIdentity);
        if(Objects.equals(admin,null)){
            throw new InputMismatchException("此管理员不存在！！！");
        }

        if(!Objects.equals(admin.getPassword(),password)){
            throw new InputMismatchException("输入密码不正确！！！");
        }

        return AdminConverter.EntityToVo(admin);
    }
}
