package com.chenhaozhe.clothe_guru_code.config;

import com.chenhaozhe.clothe_guru_code.model.dto.MessageDTO;
import com.chenhaozhe.clothe_guru_code.services.ImageProcessingServices;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ClotheMasterImageProcessingSocketHandler implements WebSocketHandler {
    private final SocketExecutorConfig socketExecutor;
    ConcurrentHashMap<String, String> receivedJson = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, SimpleMessageListenerContainer> listenerMap = new ConcurrentHashMap<>();
    @Resource
    private DynamicMessageListener dynamicMessageListener;
    @Resource
    private ImageProcessingServices imageProcessingServices;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private AmqpAdmin amqpAdmin;


    public ClotheMasterImageProcessingSocketHandler(SocketExecutorConfig socketExecutor) {
        this.socketExecutor = socketExecutor;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        socketExecutor.socketExecutor().submit(() -> {
            // 建立连接 并初始化连接所需资源
            log.info("会话标识码{}与服务器建立了连接。", session.getId());
            receivedJson.put(session.getId(), "");

            log.info("开启消费者监听,消费者:{}", session.getId());
            String queueName = "queue." + session.getId(); // 动态生成队列名
            Queue queue = new Queue(queueName, false,true,true);
            amqpAdmin.declareQueue(queue);
            // 初始化消费者队列
            SimpleMessageListenerContainer simpleMessageListenerContainer =
                    dynamicMessageListener.startListener(queueName, (msg, channel) -> {
                        // 执行送信逻辑
                        try {
                            String received = new String(msg.getBody(), StandardCharsets.UTF_8);

                            Long deliveryTag = msg.getMessageProperties().getDeliveryTag();
                            channel.basicAck(deliveryTag, false);
                        } catch (Exception e) {
                            try {
                                Long deliveryTag = msg.getMessageProperties().getDeliveryTag();
                                channel.basicNack(deliveryTag, false, true);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });

            listenerMap.put(session.getId(), simpleMessageListenerContainer);
        });
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        socketExecutor.socketExecutor().submit(() -> {
            String s = receivedJson.get(session.getId());
            receivedJson.put(session.getId(), s + message.getPayload());
            if (message.isLast()) {
                log.info("文件传输完毕");
                    s = receivedJson.get(session.getId());
                    JsonNode node = JackonUtil.getNode(s);
                    String type = node.get("type").asText();
                    String content = node.get("content").asText();

                    if (Objects.equals("changeColor", type)) {
                        JsonNode subContent = JackonUtil.getNode(content);
                        String fileName = subContent.get("fileName").asText();
                        String file = subContent.get("image").asText();
                        String color = subContent.get("targetColor").asText();
                        Boolean success = imageProcessingServices.changeColor(type, fileName, file, color);
                        if (success){
                            // 将消息发送到固定交换机的以该sessionid为名的队列的rabbitmq服务器中
                            CorrelationData correlationDataId = new CorrelationData();
                            correlationDataId.setId(UUID.randomUUID().toString()); // 设置唯一标识
                            rabbitTemplate.convertAndSend("pre-processing-image","python-receiver",fileName,correlationDataId);
                            rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                                if (ack) {
                                    log.info("Message:{} sent successfully",correlationData.getId());
                                    try {
                                        session.sendMessage(new TextMessage("发送成功"));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    log.info("Message:{} sending failed: {}",correlationData,cause);
                                    // 处理消息发送失败的情况 例如向前端发送一个错误信息告知用户发送失败

                                }
                            });

                        }
                    }
                    receivedJson.put(session.getId(), "");
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        socketExecutor.socketExecutor().submit(() -> {
            // 关闭链接时，释放资源
            receivedJson.remove(session.getId());
            listenerMap.remove(session.getId());
        });
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
