package com.chenhaozhe.clothe_guru_code.config;

import com.chenhaozhe.clothe_guru_code.services.callback.MessageCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
public class DynamicMessageListener {

    private final ConnectionFactory connectionFactory;

    // 通过构造器注入ConnectionFactory
    @Autowired
    public DynamicMessageListener(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    //使用回调函数

    private MessageCallback messageCallback;

    public void setMessageCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

    // 启动监听器
    public SimpleMessageListenerContainer startListener(String queueName,MessageCallback messageCallback) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        // 使用一个回调函数实现高度模块化
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            messageCallback.handleMessage(message,channel);
        });
        container.start();
        return container;
    }

    // 停止监听器
    public void stopListener(SimpleMessageListenerContainer container) {
        if (container != null && container.isRunning()) {
            container.stop();
        }
    }
}