package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.entity.UserEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.UserRecordAndCountVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UserRegisterVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UserVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UsersAndCountVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserServices {
    void InsertUserToDatabase(UserRegisterVo registerVo);

    void SendCaptcha(String mail);

    String mailCheck(String mail, String captcha);

    String usernameAndPasswordCheck(String username, String password);

    void deleteUser(Long userId);

    String AlterUserCheckPassword(String oldPassword, UserEntity user, MultipartFile avatar);

    UserVo getUserById(Long userId);

    void userEvict(Long userId);

    UsersAndCountVo getAllUsers(Integer page);

    UsersAndCountVo getAllUsersByKeyWord(String keyWord, Integer page);

    void getUserByUsername(String username);
    void getUserByMail(String mail);

    void insertUserLoginRecord(String host,String location,String time,String userId);

    UserRecordAndCountVo getUserRecords(Long userId,Integer page);

}
