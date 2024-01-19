package com.chenhaozhe.clothe_guru_code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    // todo 使用多线程

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(clotheMasterHandler(),"/chatRoom").setAllowedOrigins("*");
//    }
//    @Bean
//    public WebSocketHandler clotheMasterHandler() {return new ClotheMasterWebSocketHandler();}
//    @Bean
//    public ServletServerContainerFactoryBean createWebSocketContainer() {
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxTextMessageBufferSize(8192);  //文本消息最大缓存
//        container.setMaxBinaryMessageBufferSize(8192);  //二进制消息大战缓存
//        container.setAsyncSendTimeout(10L * 1000); //异步发送超时时间
//        return container;
//    }
}
