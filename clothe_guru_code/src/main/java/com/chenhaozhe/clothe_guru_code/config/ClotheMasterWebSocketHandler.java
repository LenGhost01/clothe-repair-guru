package com.chenhaozhe.clothe_guru_code.config;

import com.chenhaozhe.clothe_guru_code.model.dto.MessageDTO;
import com.chenhaozhe.clothe_guru_code.services.ChatManagementServices;
import com.chenhaozhe.clothe_guru_code.util.JackonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ClotheMasterWebSocketHandler implements WebSocketHandler {
    private final SocketExecutorConfig socketExecutor;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private AmqpAdmin amqpAdmin;

    @Resource
    private SimpleRabbitListenerEndpoint endpoint;
    // 使用redis存放房间号相关信息
    @Resource
    private ChatManagementServices chatManagementServices;

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
        //TODO 服务器接收到消息时执行的业务 根据前端传输过来的消息的type决定
        socketExecutor.socketExecutor().submit(() -> {
            String id = session.getId();
            MessageDTO messageDTO = JackonUtil.JsonToObject(message.getPayload().toString(), MessageDTO.class);
            if (Objects.equals(messageDTO.getType(), "init")) {
                // 类型是init标识对当前保存的成员集合进行初始化
                List memberList = JackonUtil.jsonToList(messageDTO.getContent(), String.class);
                // 创建一个线程安全的set用来保存用户成员信息并确保用户成员id的唯一性
                Set<String> members = ConcurrentHashMap.newKeySet();
                memberList.forEach(item -> members.add(String.valueOf(item)));
                chatManagementServices.putSocketMap(id, members);
                log.info("当前缓存中的聊天室id:{},成员有:{}", id, chatManagementServices.getSocketMap(id));
            }
            if (Objects.equals(messageDTO.getType(), "message")) {
                // 调用rabbitmq将消息发送到对应交换机 交换机的类型是直连交换机，针对每一个用户id进行匹配，路由键要和队列名匹配
                declareExchangeAndQueue(id, messageDTO.getReceiver(), messageDTO.getReceiver());

                rabbitTemplate.convertAndSend(id, messageDTO.getReceiver(), messageDTO.getContent());
            }
            if (Objects.equals(messageDTO.getType(), "queueListener")) {
                log.info("开启消费者监听,消费者:{}", messageDTO.getSender());
                // 初始化消费者队列


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

            // 最后一步：清理资源
            amqpAdmin.deleteExchange(session.getId());
            chatManagementServices.removeSocketMap(session.getId());
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
