package com.xzx.imitate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xzx.imitate.mapper")
public class ImitateApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImitateApplication.class, args);
    }
}
