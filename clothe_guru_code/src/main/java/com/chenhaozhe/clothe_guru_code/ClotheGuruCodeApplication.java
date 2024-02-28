package com.chenhaozhe.clothe_guru_code;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableRabbit
public class ClotheGuruCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClotheGuruCodeApplication.class, args);
    }

}
    