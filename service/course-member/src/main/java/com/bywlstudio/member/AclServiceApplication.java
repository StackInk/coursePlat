package com.bywlstudio.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: zl
 * @Date: Create in 2021/4/24 16:49
 * @Description:
 */
@SpringBootApplication
@ComponentScan("com.bywlstudio")
@MapperScan(basePackages = "com.bywlstudio.member.mapper")
public class AclServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclServiceApplication.class, args);
    }
}
