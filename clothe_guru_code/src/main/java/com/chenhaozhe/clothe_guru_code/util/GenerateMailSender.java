package com.chenhaozhe.clothe_guru_code.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class GenerateMailSender {
    private static String mailSenderPath;
    private static MailSender sender;
    @Resource
    private MailSender mailSender;


    @Value("${spring.mail.username}")
    private String mail;
    @PostConstruct
    public void init(){
        GenerateMailSender.mailSenderPath = mail;
        GenerateMailSender.sender = mailSender;
    }

    public static void send(String subject,String text, String ...mailReceiver){ // 设定为可以有多个接收者
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSenderPath);
        message.setTo(mailReceiver);
        message.setSubject(subject);
        message.setText(text);

        sender.send(message);
    }
}
