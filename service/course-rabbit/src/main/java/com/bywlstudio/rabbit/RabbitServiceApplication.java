package com.bywlstudio.rabbit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 15:59
 * @Description:
 */
@SpringBootApplication
@EnableRabbit
@EnableScheduling
@ComponentScan("com.bywlstudio")
@MapperScan(basePackages = "com.bywlstudio.rabbit.mapper")
public class RabbitServiceApplication {
    public static void main(String[] args) {
            SpringApplication.run(RabbitServiceApplication.class, args);
    }
}
