package com.bywlstudio.member.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zl
 * @Date: Create in 2021/4/8 18:39
 * @Description:
 */
@Component
public class EnvConfig implements InitializingBean {

    @Resource
    private Environment environment;

    @Value("${spring.profiles.active}")
    private String env;


    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
