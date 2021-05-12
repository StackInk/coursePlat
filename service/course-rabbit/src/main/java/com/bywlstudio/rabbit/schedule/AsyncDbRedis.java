package com.bywlstudio.rabbit.schedule;

import com.bywlstudio.common.constant.Constant;
import com.bywlstudio.rabbit.mapper.ZlCourseMapper;
import com.bywlstudio.rabbit.service.IZlCourseService;
import com.bywlstudio.rabbit.utils.RedisUtils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 17:16
 * @Description: 同步数据库和Redis
 */
@Component
public class AsyncDbRedis {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private ZlCourseMapper courseMapper;

    /**
     * 通过一个定时任务，从redis中拿值，和数据库进行同步数据
     */
    @Scheduled(cron = "0 */1 * * * ?")
    @Async
    public void scheduleAsync() {
        List<Map.Entry<Object, Object>> entryList = RedisUtils.scan(redisTemplate, Constant.courseSelect);
        if(entryList.isEmpty()) return;
        entryList.forEach(entry->{
            Long courseId = (Long)entry.getKey();
            Integer stock = (Integer)entry.getValue();
            courseMapper.updateStockById(courseId,stock);
        });
    }


}
