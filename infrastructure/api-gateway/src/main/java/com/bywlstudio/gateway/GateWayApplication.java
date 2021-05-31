package com.bywlstudio.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: zl
 * @Date: Create in 2021/4/9 15:52
 * @Description:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GateWayApplication {
    public static void main(String[] args) {
            SpringApplication.run(GateWayApplication.class, args);
    }
}
