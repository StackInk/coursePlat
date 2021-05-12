package com.bywlstudio.common.config;

import com.bywlstudio.common.config.mq.AliyunCredentialsProvider;
import com.bywlstudio.common.config.mq.RabbitConfirmCallback;
import com.bywlstudio.common.config.mq.RabbitReturnCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 15:12
 * @Description:
 */
@Configuration
@EnableRabbit
@Slf4j
public class RabbitMQConfig implements ApplicationContextAware {
    //实例Id 从阿里云AMQP控制台获取
    private static final String  INSTANCE_ID="amqp-cn-7pp2662qb00m";


    @Autowired
    private RabbitProperties rabbitProperties;


    @Bean
    public ConnectionFactory getConnectionFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory =
                new com.rabbitmq.client.ConnectionFactory();
        rabbitConnectionFactory.setHost(rabbitProperties.getHost());
        rabbitConnectionFactory.setPort(rabbitProperties.getPort());
        rabbitConnectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());

        AliyunCredentialsProvider credentialsProvider = new AliyunCredentialsProvider(
                rabbitProperties.getUsername(), rabbitProperties.getPassword(), INSTANCE_ID);
        rabbitConnectionFactory.setCredentialsProvider(credentialsProvider);
        rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
        rabbitConnectionFactory.setNetworkRecoveryInterval(5000);
        ConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConnectionFactory);
        ((CachingConnectionFactory)connectionFactory).setPublisherConfirms(rabbitProperties.isPublisherConfirms());
        ((CachingConnectionFactory)connectionFactory).setPublisherReturns(rabbitProperties.isPublisherReturns());
        return connectionFactory;
    }

    @Bean
    public Queue queue() {
        Map<String, Object> arguments = new HashMap<>(4);
        return new Queue("zl-sync-course", true, false, false, arguments);
    }

    @Bean
    public Exchange exchange() {
        Map<String, Object> arguments = new HashMap<>(4);
        return new FanoutExchange("zl-course", true, false, arguments);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("zl#sync#course").noargs();
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
