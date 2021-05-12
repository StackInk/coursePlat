package com.bywlstudio.rabbit.test.rabbit;

import com.bywlstudio.common.constant.Constant;
import com.bywlstudio.rabbit.RabbitServiceApplication;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author: zl
 * @Date: Create in 2021/5/12 19:53
 * @Description:
 */
@SpringBootTest(classes = RabbitServiceApplication.class)
public class RabbitTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test01() {
        HashMap<String,Long> message = Maps.newHashMap();
        message.put("userId",1L);
        message.put("courseId",1L);
        rabbitTemplate.convertAndSend(Constant.rabbitExchange,Constant.rabbitBinding,message);
    }
}
