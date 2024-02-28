package com.chenhaozhe.clothe_guru_code.mapper;

import com.chenhaozhe.clothe_guru_code.model.entity.UserFriendCorrelationViewEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.UserPrivateChatViewEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatPanelCacheMapper {
    // 使用注解
    @Select("""
            select user_id,private_chat_id,target_id,join_time,nickname,avatar from user_private_chat_view where user_id = #{userId};
            """)
    List<UserPrivateChatViewEntity> getPrivateChatFromView(@Param("userId")String userId);

    @Delete("""
            delete from user_private_chat where private_chat_id = #{chatId};
            """)
    Integer deletePrivateChat(@Param("chatId")String chatId);

    @Insert("""
            insert into user_private_chat(user_id,target_id,join_time) values(#{userId},#{targetId},#{joinTime});
            """)
    Integer insertPrivateChat(@Param("userId")String userId,@Param("targetId")String targetId,@Param("joinTime")String joinTime);

    @Select("""
            select user_id,friend_id,state,join_time,id,nickname,avatar from user_friend_correlation_view where user_id = #{userId};
            """)
    List<UserFriendCorrelationViewEntity> getUserFriendFromView(@Param("userId")String userId);

    @Delete("""
            delete from user_friend_correlation where id = #{id};
            """)
    Integer deleteUserFriend(@Param("id")String id);

    @Insert("""
            insert user_friend_correlation(user_id,friend_id,state,join_time) values(#{userId},#{friendId},#{state},#{joinTime});
            """)
    Integer addNewUserFriend(@Param("userId")String userId,@Param("friend_id")String friendId,@Param("state")Short state,@Param("joinTime")String joinTime);
    // 使用生成器

    // 使用xml文件
}
