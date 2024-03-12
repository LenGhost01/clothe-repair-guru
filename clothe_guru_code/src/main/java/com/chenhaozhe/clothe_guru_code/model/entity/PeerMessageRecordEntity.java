package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeerMessageRecordEntity {
    private String senderId;
    private String receiverId;
    private String peerChatId;
    private String content;
    private String createTime;
    private String isRead;
}
