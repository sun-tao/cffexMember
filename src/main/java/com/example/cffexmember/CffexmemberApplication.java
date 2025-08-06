package com.example.cffexmember;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.cffexmember.mapper")
public class CffexmemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(CffexmemberApplication.class, args);
    }

}
