package com.bywlstudio.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: zl
 * @Date: Create in 2021/4/28 15:48
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.bywlstudio")
@MapperScan(basePackages = "com.bywlstudio.course.mapper")
@EnableAsync
public class CourseServiceApplication {
    public static void main(String[] args) {
            SpringApplication.run(CourseServiceApplication.class, args);
    }
}
