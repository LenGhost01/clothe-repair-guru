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

    public String getAllMerchant(final Short limit,final Integer offset,final String keyWord, final String keyState,
                                 final String orderValue,final Boolean orderRule){
       return new SQL(){
           {
                SELECT("*");
                FROM("merchant");
                if(!(Objects.equals(keyWord,null)||Objects.equals(keyState,null))){
                    WHERE(keyState+ "like #{keyWord}");
                }
                if(!(Objects.equals(orderValue,null) || Objects.equals(orderRule,null)) ){
                    ORDER_BY(orderValue + (orderRule?" ASC":" DESC"));
                }
                LIMIT(limit);
                OFFSET(offset);
           }
       }.toString();
    }
}
