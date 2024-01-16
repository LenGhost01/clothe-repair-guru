package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.entity.UserEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.UserRegisterVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UserVo;
import org.springframework.stereotype.Service;


public interface UserServices {
    void InsertUserToDatabase(UserRegisterVo registerVo);

    void SendCaptcha(String mail);

    String mailCheck(String mail,String captcha);

    String usernameAndPasswordCheck(String username,String password);

    void deleteUser(Long userId);

    String AlterUserCheckPassword(String oldPassword, UserEntity user);
}
