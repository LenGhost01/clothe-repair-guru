package com.chenhaozhe.clothe_guru_code.controller;

import com.chenhaozhe.clothe_guru_code.model.dto.ChatChannelDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.ChatPeerToPeerDTO;
import com.chenhaozhe.clothe_guru_code.services.ChatroomServices;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    // todo 用户图片处理功能实现
    @Resource
    private ChatroomServices chatroomServices;

    @PostMapping("/imageProcessing")
    void imageProcessing(@RequestPart("image") MultipartFile image,
                         @RequestPart(required = false, value = "mask") MultipartFile mask,
                         @RequestParam("metaData") String metaData) {
        //根据掩膜和选取的功能来判断用户使用的那种类型的图像处理服务，在不使用其他技术的情况下opencv的图像修复技术需要提供一张掩膜
    }

    @GetMapping("/initPeerChat")
    List initChatPanel(@RequestParam("userId") Long userId) {
        return chatroomServices.initPeerChat(String.valueOf(userId));
    }

    @GetMapping("/insertNewPrivateChat")
    List insertNewPrivateChat(@RequestParam("userId") String userId,@RequestParam("targetId")String targetId) {
        return chatroomServices.insertNewPrivateChat(userId,targetId);
    }

    @GetMapping("/initFriendPanel")
    List initFriendPanel(@RequestParam("userId") Long userId) {
        return chatroomServices.initFriendPanel(String.valueOf(userId));
    }

    // todo 频道聊天功能实现
    @PostMapping("/Channel")
    void ChatByChannel(@RequestBody ChatChannelDTO chatChannelDTO) {

    }

    // todo 用户登录更新状态实现 通过用户id获取对应的关系表，并广播一段讯息到对应的客户端 应当写成service
    void updateUserLoginState() {

    }

    // todo 记录好友请求
    @GetMapping("sendFriendRequest")
    void sendFriendRequests(@RequestParam("sender") String sender, @RequestParam("receiver") String receiver, @RequestParam("mode") Integer mode) {

    }

    // todo 记录黑名单
    @GetMapping("blackListSetter")
    void blackListRecord(@RequestParam("sender") String sender, @RequestParam("receiver") String receiver, @RequestParam("mode") Integer mode) {

    }
}
