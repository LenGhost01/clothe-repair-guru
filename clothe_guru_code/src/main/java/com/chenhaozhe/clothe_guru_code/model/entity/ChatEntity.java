package com.chenhaozhe.clothe_guru_code.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1414552871676251969L;
    private Long senderId;
    private Long receiverId;
    private String content; // 此处存放的是包含用户发送内容和图片uri组成的一个字符串，在显示到页面前，需要通过dto转化成vo，分离其中的部分并按顺序存储。
    private String createTime;
}
