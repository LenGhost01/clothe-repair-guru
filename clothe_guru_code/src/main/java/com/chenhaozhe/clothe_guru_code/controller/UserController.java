package com.chenhaozhe.clothe_guru_code.controller;


import com.chenhaozhe.clothe_guru_code.model.converter.UserConverter;
import com.chenhaozhe.clothe_guru_code.model.dto.AlterUserDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.UserLoginMailDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.UserLoginNormalDTO;
import com.chenhaozhe.clothe_guru_code.model.vo.UserRegisterVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UserVo;
import com.chenhaozhe.clothe_guru_code.services.UserServices;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserServices userServices;

    //用于用户请求的处理
    @PostMapping("/register")
    void userRegister(@RequestBody UserRegisterVo registerVo){
        //对于需要参数验证的属性，均使用对象来传递参数，前端验证是否注册成功使用状态码来验证
        userServices.InsertUserToDatabase(registerVo);
    }

    @GetMapping("/getCaptcha")
    void getCaptcha(@RequestParam("email")String email){
        userServices.SendCaptcha(email);
    }

    @PostMapping("/loginByMail")
    public String loginByMail(@RequestBody UserLoginMailDTO mailDTO){
        return userServices.mailCheck(mailDTO.getEmail(),mailDTO.getCaptcha());
    }
    @PostMapping("/loginByUsername")
    public String loginByUsername(@RequestBody UserLoginNormalDTO normalDTO){
        return userServices.usernameAndPasswordCheck(normalDTO.getUsername(),normalDTO.getPassword());
    }

    @GetMapping("/deleteUser")
    public void deleteUser(@RequestParam("userId")String userId){
        userServices.deleteUser(Long.parseLong(userId));
    }

    @PostMapping("/alterUser")
    public String alterUser(@RequestBody AlterUserDTO alterUserDTO){
        return userServices.AlterUserCheckPassword(alterUserDTO.getOldPassword(), UserConverter.convertToEntity(alterUserDTO.getUserDTO()));
    }
}
