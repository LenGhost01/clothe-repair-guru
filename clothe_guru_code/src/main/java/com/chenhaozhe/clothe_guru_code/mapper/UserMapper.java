package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.ApplicationsEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.UserEntity;
import com.chenhaozhe.clothe_guru_code.util.DynaSQLProviderBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Insert("""
            insert users(user_id,username,`password`,nickname,email) 
            values (#{id},#{username},#{password},#{nickname},#{email});
            """)
    Integer insertUserToUsers(Long id,String username,String password,String nickname,String email);

    @Select("""
            select * from users where email=#{email}
            """)
    UserEntity selectUserByEmail(@Param("email") String email);

    @Select("""
            select * from users where username=#{username}
            """)
    UserEntity selectUserByUsername(@Param("username") String username);

    @Select("""
            select * from users where user_id = #{userId}
            """)
    UserEntity selectUserByUserId(@Param("userId")Long userId);

    @Delete("""
            delete from users where user_id=#{userId}
            """)
    void deleteUser(@Param("userId")Long userId);

    @Update("""
            update users set last_login=#{lastLogin}
            """)
    Integer updateCurrentVisitTime(@Param("lastLogin")String currentTime);



    @UpdateProvider(type = DynaSQLProviderBuilder.class,method = "updateUser")
    Integer updateUser(@Param("user")UserEntity user);

}
