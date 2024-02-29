package com.chenhaozhe.clothe_guru_code.services.callback;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@FunctionalInterface
public interface MessageCallback {
    void handleMessage(Message message, Channel channel);
}
