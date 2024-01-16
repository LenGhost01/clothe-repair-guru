package com.chenhaozhe.clothe_guru_code.util;


import com.chenhaozhe.clothe_guru_code.model.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

import java.util.Objects;

@Slf4j
public class DynaSQLProviderBuilder {
   public String updateUser(final UserEntity user){
       return new SQL(){
           {
               UPDATE("users");
               SET("password=#{user.password}","nickname=#{user.nickname}","phone=#{user.phone}",
                       "email=#{user.email}","receiver=#{user.receiver}","location=#{user.location}",
                       "pay_mesg=#{user.payMesg}");
               WHERE("user_id=#{user.userId}");
           }
       }.toString();
   }
}
