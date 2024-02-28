package com.chenhaozhe.clothe_guru_code.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChatroomServices {
    void imageProcessing(MultipartFile image,MultipartFile mask,String metaData);
    List initPeerChat(String userId);

    List initFriendPanel(String userId);
}
