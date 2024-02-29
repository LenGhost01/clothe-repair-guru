package com.chenhaozhe.clothe_guru_code.services;

import com.chenhaozhe.clothe_guru_code.model.entity.UserFriendCorrelationViewEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.UserPrivateChatViewEntity;

import java.util.List;

public interface ChatPanelCacheServices {
    List<UserFriendCorrelationViewEntity> useChatRoomFriendCache(String userId);

    List<UserFriendCorrelationViewEntity> updateChatRoomFriendCache(String userId);

    void clearChatRoomFriendCache(String userId);

    List<UserPrivateChatViewEntity> userChatRoomPrivateChatCache(String userId);
    List<UserPrivateChatViewEntity> updateChatRoomPrivateChatCache(String userId,String targetId);

    void clearPrivateChatCache(String UserId);
}
