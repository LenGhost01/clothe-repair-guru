package com.chenhaozhe.clothe_guru_code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

@SpringBootApplication
@Cacheable
public class ClotheGuruCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClotheGuruCodeApplication.class, args);
    }

}
