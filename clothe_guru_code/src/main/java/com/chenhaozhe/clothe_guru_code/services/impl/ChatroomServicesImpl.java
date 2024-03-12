package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.mapper.MessageRecordMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.UserFriendCorrelationViewConverter;
import com.chenhaozhe.clothe_guru_code.model.converter.UserPrivateChatViewConverter;
import com.chenhaozhe.clothe_guru_code.model.entity.PeerMessageRecordEntity;
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
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ChatroomServicesImpl implements ChatroomServices {
    @Resource
    private ChatPanelCacheServices chatPanelCacheServices;
    @Resource
    private LoginStateMemoryServices loginStateMemoryServices;
    @Resource
    private MessageRecordMapper messageRecordMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ResourceLoader resourceLoader;


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
                    if (loginUserSet.contains(item.getUserId()) && Objects.equals(item.getState(), "offline")) {
                        userFriendCorrelationViewVo.setState("online");
                    }
                    return userFriendCorrelationViewVo;
                })
                .toList();
        return list;
    }

    @Override
    public List initMessageRecord(String userId, String targetId) {
        log.info("初始化函数被调用了");
        List<String> members = new ArrayList<>();
        members.add(userId);
        members.add(targetId);
        List<PeerMessageRecordEntity> peerMessage = messageRecordMapper.getPeerMessage(members);
        return peerMessage;
    }


    @Override
    public List insertNewPrivateChat(String userId, String targetId) {
        return chatPanelCacheServices.updateChatRoomPrivateChatCache(userId, targetId);
    }

    @Override
    @Transactional
    public void messageRecord(String redisKey, Integer popBatch) {
        // 加载lua文件
        org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:script/ListRedisRightPop.lua");
        String luaString;
        try {
            luaString = new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read Lua script file");
        }
        RedisScript<List> script = new DefaultRedisScript<>(luaString, List.class);
        List<String> keys = Collections.emptyList(); // Lua脚本中未直接使用KEYS
        for (List<PeerMessageRecordEntity> messages = JackonUtil.jsonToList(String.valueOf(redisTemplate.execute(script, keys, redisKey, popBatch)), PeerMessageRecordEntity.class); !Objects.equals(messages.get(0), null); messages = JackonUtil.jsonToList(String.valueOf(redisTemplate.execute(script, keys, redisKey, popBatch)), PeerMessageRecordEntity.class)) {
            log.info("消息记录结果{}",messageRecordMapper.addPeerMessage(messages));
        }
    }
}
