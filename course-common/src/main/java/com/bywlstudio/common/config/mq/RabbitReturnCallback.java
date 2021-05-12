package com.bywlstudio.common.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 15:32
 * @Description:
 */
@Slf4j
public class RabbitReturnCallback implements RabbitTemplate.ReturnCallback{
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("message return message:"+message+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
    }
}
