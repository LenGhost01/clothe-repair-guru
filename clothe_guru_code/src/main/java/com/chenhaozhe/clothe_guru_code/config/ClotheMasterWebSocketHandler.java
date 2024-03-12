package com.chenhaozhe.clothe_guru_code.config;

import com.chenhaozhe.clothe_guru_code.model.dto.MessageDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.PeerMessageRecordEntity;
import com.chenhaozhe.clothe_guru_code.services.ChatManagementServices;
import com.chenhaozhe.clothe_guru_code.services.ChatroomServices;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ClotheMasterWebSocketHandler implements WebSocketHandler {
    private final SocketExecutorConfig socketExecutor;
    private ConcurrentHashMap containerMap = new ConcurrentHashMap();
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private AmqpAdmin amqpAdmin;
    // 使用redis存放房间号相关信息
    @Resource
    // 这个服务类用来保存频道中的成员信息
    private ChatManagementServices chatManagementServices;
    @Resource
    private DynamicMessageListener dynamicMessageListener;

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ChatroomServices chatroomServices;

    public ClotheMasterWebSocketHandler(SocketExecutorConfig socketExecutor) {
        this.socketExecutor = socketExecutor;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //TODO 连接建立后要执行的业务
        socketExecutor.socketExecutor().submit(() -> {
            // 初始化环境
            chatManagementServices.putSocketMap(session.getId(), new HashSet<>());
            log.info("一个新的会话建立了，id是{}", session.getId());
        });
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //TODO 服务器接收到消息时执行的业务 根据前端传输过来的消息的type决定 最后尝试分析各个if语句的使用频率，分配分支语句
        socketExecutor.socketExecutor().submit(() -> {
            String id = session.getId();
            MessageDTO messageDTO = JackonUtil.JsonToObject(message.getPayload().toString(), MessageDTO.class);
            if (Objects.equals(messageDTO.getType(), "message")) {
                // 调用rabbitmq将消息发送到对应交换机 交换机的类型是直连交换机，针对每一个用户id进行匹配，路由键要和队列名匹配
                declareExchangeAndQueue(id, messageDTO.getReceiver(), messageDTO.getReceiver());
                CorrelationData correlationDataId = new CorrelationData();
                correlationDataId.setId(UUID.randomUUID().toString()); // 设置唯一标识
                rabbitTemplate.convertAndSend(id, messageDTO.getReceiver(), JackonUtil.ObjectToJSON(
                        PeerMessageRecordEntity.builder()
                                .senderId(messageDTO.getSender())
                                .receiverId(messageDTO.getReceiver())
                                .content(messageDTO.getContent())
                                .createTime(messageDTO.getSendTime())
                                .peerChatId(correlationDataId.getId())
                                .build()
                ),correlationDataId);
                // 在确认消息发送到交换机后，将新的信息追加到本地的文件中
                rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                    if (ack) {
                        log.info("Message:{} sent successfully",correlationData.getId());

                    } else {
                        log.info("Message:{} sending failed: {}",correlationData,cause);
                        // 处理消息发送失败的情况 例如向前端发送一个错误信息告知用户发送失败

                    }
                });
            }
            if (Objects.equals(messageDTO.getType(), "init")) {
                // 类型是peerInit标识 获取当前聊天室内需显示的消息
                log.info("初始化私聊组件");
                try {
                    log.info("接收到的数据:{}",messageDTO);
                    List messageRecord = chatroomServices.initMessageRecord(messageDTO.getSender(), messageDTO.getReceiver());
                    MessageDTO initMessage = MessageDTO.builder().type("initMessage").content(JackonUtil.ListToJson(messageRecord)).build();
                    session.sendMessage(new TextMessage(JackonUtil.ObjectToJSON(initMessage)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (Objects.equals(messageDTO.getType(), "queueListener")) {
                log.info("开启消费者监听,消费者:{}", messageDTO.getSender());
                // 初始化消费者队列
                SimpleMessageListenerContainer simpleMessageListenerContainer =
                        dynamicMessageListener.startListener(messageDTO.getSender(), (msg, channel) -> {
                            // 执行送信逻辑
                            try {
                                String received = new String(msg.getBody(), StandardCharsets.UTF_8);
                                session.sendMessage(new TextMessage(JackonUtil.ObjectToJSON(
                                        MessageDTO.builder().type("singleMessage").content(received).build())
                                ));
                                Long deliveryTag = msg.getMessageProperties().getDeliveryTag();
                                channel.basicAck(deliveryTag, false);
                                // 确认接收后，将消息送入缓存
//                                log.info("消息:{}被送入缓存",received);
                                redisTemplate.opsForList().leftPush(session.getId(),received);
                            } catch (Exception e) {
                                try {
                                    Long deliveryTag = msg.getMessageProperties().getDeliveryTag();
                                    channel.basicNack(deliveryTag, false, true);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                // 将对这个链接的监听加入到一个map对象中
                containerMap.put(id, simpleMessageListenerContainer);
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //TODO 出现传输错误时执行的业务
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //TODO 连接关闭前执行的业务，通常是清理资源 使用多线程来释放资源
        socketExecutor.socketExecutor().submit(() -> {
            // 业务逻辑
            log.info("一个会话关闭了连接，id是:{}", session.getId());
            // todo 进行数据持久化
            chatroomServices.messageRecord(session.getId(),10);
            // 最后一步：清理资源 移除成员信息，关闭监听，删除对应session-id的交换机
            containerMap.remove(session.getId());
            amqpAdmin.deleteExchange(session.getId());
        });
    }

    @Override
    public boolean supportsPartialMessages() {
        // 是否运行处理大型文件 当这个选项设置为true的时候，需要结合isLast来判断传输是否结束
        return false;
    }

    public ConcurrentHashMap<String, String> getQueryMap(String query) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap();
        if (query != null) {
            String uri = query.split("\\?")[1];
            String[] params = uri.split("&");
            for (String param : params) {
                String[] nameValuePair = param.split("=");
                if (nameValuePair.length > 1) {
                    map.put(nameValuePair[0], nameValuePair[1]);
                } else {
                    map.put(nameValuePair[0], "");
                }
            }
        }
        return map;
    }

    private void declareExchangeAndQueue(String exchangeName, String queueName, String routingKey) {
        // 对于要精确发送到对应用户的需求，采用直连交换机
        DirectExchange exchange = new DirectExchange(exchangeName);
        Queue queue = new Queue(queueName);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        // 根据需要添加绑定
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);
        amqpAdmin.declareBinding(binding);
    }
}
