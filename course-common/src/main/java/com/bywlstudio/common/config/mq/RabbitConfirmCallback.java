package com.bywlstudio.common.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 15:32
 * @Description:
 */
@Slf4j
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.info("被消费的数据是：{},消息确认ACK:{},返回消息为:{}",correlationData,b,s);
    }
}
