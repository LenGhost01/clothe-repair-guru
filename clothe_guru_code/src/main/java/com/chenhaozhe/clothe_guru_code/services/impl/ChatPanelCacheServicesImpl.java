package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.mapper.ChatPanelCacheMapper;
import com.chenhaozhe.clothe_guru_code.model.entity.UserFriendCorrelationViewEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.UserPrivateChatViewEntity;
import com.chenhaozhe.clothe_guru_code.services.ChatPanelCacheServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class ChatPanelCacheServicesImpl implements ChatPanelCacheServices {
    @Resource
    private ChatPanelCacheMapper chatPanelCacheMapper;

    @Override
    @Cacheable(key = "#userId", value = "friendCorrelationCache")
    public List<UserFriendCorrelationViewEntity> useChatRoomFriendCache(String userId) {
        return chatPanelCacheMapper.getUserFriendFromView(userId);
    }

    @Override
    @CachePut(key = "#userId", value = "friendCorrelationCache")
    public List<UserFriendCorrelationViewEntity> updateChatRoomFriendCache(String userId) {
        return chatPanelCacheMapper.getUserFriendFromView(userId);
    }

    @Override
    @CacheEvict(key = "#userId", value = "friendCorrelationCache")
    public void clearChatRoomFriendCache(String userId) {

    }

    @Override
    @Cacheable(key = "#userId", value = "privateChatCache")
    public List<UserPrivateChatViewEntity> userChatRoomPrivateChatCache(String userId) {
        return chatPanelCacheMapper.getPrivateChatFromView(userId);
    }

    @Override
    @CachePut(key = "#userId", value = "privateChatCache")
    @Transactional
    public List<UserPrivateChatViewEntity> updateChatRoomPrivateChatCache(String userId,String targetId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        chatPanelCacheMapper.insertPrivateChat(userId,targetId,LocalDateTime.now().format(formatter));
        return chatPanelCacheMapper.getPrivateChatFromView(userId);
    }

    @Override
    @CacheEvict(key = "#userId", value = "privateChatCache")
    public void clearPrivateChatCache(String UserId) {

    }
}
