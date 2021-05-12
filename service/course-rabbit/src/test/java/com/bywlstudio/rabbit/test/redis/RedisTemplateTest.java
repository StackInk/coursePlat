package com.bywlstudio.rabbit.test.redis;

import com.bywlstudio.rabbit.RabbitServiceApplication;
import com.bywlstudio.rabbit.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 17:53
 * @Description:
 */
@SpringBootTest(classes = RabbitServiceApplication.class)

public class RedisTemplateTest {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test() {
        List<Map.Entry<Object, Object>> scan = RedisUtils.scan(redisTemplate, "zl#222");
        scan.forEach(entry->{
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        });
    }
}
