package com.chenhaozhe.clothe_guru_code.services;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface ChatManagementServices {
    Set getSocketMap(String id);

    void putSocketMap(String id,Set<String> members);

    void removeSocketMap(String id);

}
