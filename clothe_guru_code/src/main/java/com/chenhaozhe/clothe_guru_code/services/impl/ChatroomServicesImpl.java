package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.model.converter.UserFriendCorrelationViewConverter;
import com.chenhaozhe.clothe_guru_code.model.converter.UserPrivateChatViewConverter;
import com.chenhaozhe.clothe_guru_code.model.entity.UserFriendCorrelationViewEntity;
import com.chenhaozhe.clothe_guru_code.model.entity.UserPrivateChatViewEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.UserFriendCorrelationViewVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UserPrivateChatViewVo;
import com.chenhaozhe.clothe_guru_code.services.ChatPanelCacheServices;
import com.chenhaozhe.clothe_guru_code.services.ChatroomServices;
import com.chenhaozhe.clothe_guru_code.services.LoginStateMemoryServices;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Service
@Slf4j
public class ChatroomServicesImpl implements ChatroomServices {
    @Resource
    private ChatPanelCacheServices chatPanelCacheServices;
    @Resource
    private LoginStateMemoryServices loginStateMemoryServices;

    @Override
    public void imageProcessing(MultipartFile image, MultipartFile mask, String metaData) {

    }

    @Override
    public List initPeerChat(String userId) {
        // 从数据库中获取到用户当前的私信和好友 并存入缓存中
        List<UserPrivateChatViewEntity> userPrivateChatViewEntities = chatPanelCacheServices.userChatRoomPrivateChatCache(userId);
        List<UserPrivateChatViewVo> list = userPrivateChatViewEntities.stream().map(item
                -> UserPrivateChatViewConverter.userPrivateChatViewEntityToVo(item)).toList();

        return list;
    }

    @Override
    public List initFriendPanel(String userId) {
        List<UserFriendCorrelationViewEntity> userFriendCorrelationViewEntities = chatPanelCacheServices.useChatRoomFriendCache(userId);
        Set<Long> loginUserSet = loginStateMemoryServices.useUsersIdCache();
        List<UserFriendCorrelationViewVo> list = userFriendCorrelationViewEntities.stream()
                .map(item -> {
                    UserFriendCorrelationViewVo userFriendCorrelationViewVo = UserFriendCorrelationViewConverter.UserFriendCorrelationEntityToVo(item);
                    if(loginUserSet.contains(item.getUserId()) && Objects.equals(item.getState(),"offline")){
                        userFriendCorrelationViewVo.setState(String.valueOf("online"));
                    }
                    return userFriendCorrelationViewVo;
                })
                .toList();
        return list;
    }
}
