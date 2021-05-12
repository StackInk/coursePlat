package com.bywlstudio.rabbit.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 17:47
 * @Description:
 */
@Slf4j
public class RedisUtils {


    public static List<Map.Entry<Object,Object>> scan(RedisTemplate<String,Object> redisTemplate, String key) {
        List<Map.Entry<Object,Object>> list = Lists.newLinkedList();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions().build());
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        try {
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("参与选课的课程数为:{}",list.size());
        return list;
    }
}
