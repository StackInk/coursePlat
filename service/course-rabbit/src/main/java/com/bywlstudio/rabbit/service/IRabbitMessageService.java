package com.bywlstudio.rabbit.service;

import java.util.Map;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 17:02
 * @Description:
 */
public interface IRabbitMessageService {


    /**
     * 监听MQ
     * @param message
     */
    void receive(Map<String, Long> message);

}
