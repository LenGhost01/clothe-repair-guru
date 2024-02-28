package com.chenhaozhe.clothe_guru_code.services.impl;

import com.chenhaozhe.clothe_guru_code.services.LoginStateMemoryServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class LoginStateMemoryServicesImpl implements LoginStateMemoryServices {
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public Set<Long> useUsersIdCache() {
        return redisTemplate.opsForSet().members("usersSet");
    }

    @Override
    public void usersIdCacheEvict(Long userId) {
        redisTemplate.opsForSet().remove("usersSet",userId);
    }

    @Override
    public void pushUsersIdCache(Long userId) {
        redisTemplate.opsForSet().add("usersSet",userId);
    }
}
