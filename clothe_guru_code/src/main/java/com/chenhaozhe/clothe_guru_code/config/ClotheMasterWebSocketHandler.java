package com.chenhaozhe.clothe_guru_code.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
public class ClotheMasterWebSocketHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //TODO 连接建立后要执行的业务
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //TODO 服务器接收到消息时执行的业务，广播或者点对点通信
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //TODO 出现传输错误时执行的业务
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //TODO 连接关闭前执行的业务，通常是清理资源
    }

    @Override
    public boolean supportsPartialMessages() {
        // 是否运行处理大型文件
        return false;
    }
}
