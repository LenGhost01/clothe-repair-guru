package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.services.ChatManagementServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Slf4j
public class ChatManagementServicesImpl implements ChatManagementServices {
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public Set<String> getSocketMap(String id) {
        return redisTemplate.opsForSet().members(id);
    }

    @Override
    public void putSocketMap(String id, Set<String> members) {
        for(String member : members){
            redisTemplate.opsForSet().add(id,member);
        }
    }

    @Override
    public void removeSocketMap(String id) {
        redisTemplate.delete(id);
    }
}
