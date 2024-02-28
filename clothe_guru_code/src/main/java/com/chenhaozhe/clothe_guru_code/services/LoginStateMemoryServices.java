package com.chenhaozhe.clothe_guru_code.services;

import java.util.Set;

public interface LoginStateMemoryServices {
    //此接口用于保存当前登录用户的用户集合信息
    Set<Long> useUsersIdCache();

    void usersIdCacheEvict(Long userId);

    void pushUsersIdCache(Long userId);
}
