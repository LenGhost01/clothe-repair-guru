package com.chenhaozhe.clothe_guru_code.controller;

import com.chenhaozhe.clothe_guru_code.model.dto.AdminLoginDTO;
import com.chenhaozhe.clothe_guru_code.model.vo.AdminVo;
import com.chenhaozhe.clothe_guru_code.services.AdminServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    //用于管理员请求的处理，目前只需处理管理员的登录请求，其他请求均分散在其他各个资源请求中。
    @Resource
    AdminServices adminServices;
    @PostMapping("/adminLogin")
    public AdminVo AdminAuthorization(@RequestBody AdminLoginDTO adminLoginDTO){

        return adminServices.getAdmin(adminLoginDTO.getAdminName(),adminLoginDTO.getPassword());
    }
}
