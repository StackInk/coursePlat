package com.bywlstudio.rabbit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bywlstudio.common.constant.Constant;
import com.bywlstudio.rabbit.entity.ZlCourseStudent;
import com.bywlstudio.rabbit.entity.ZlStudent;
import com.bywlstudio.rabbit.service.IRabbitMessageService;

import com.bywlstudio.rabbit.service.IZlCourseStudentService;
import com.bywlstudio.rabbit.service.IZlStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 17:05
 * @Description:
 */
@Service("rabbitMessageService")
@Slf4j
public class RabbitMessageServiceImpl implements IRabbitMessageService {

    @Resource
    private IZlCourseStudentService courseStudentService;

    @Resource
    private IZlStudentService studentService;
    /**
     * 同步数据库
     * @param message
     */
    @Override
    @RabbitListener(queues = Constant.rabbitQueue)
    public void receive(Map<String, Long> message) {
        log.info("接收到消息:{}",message);
        Long courseId = message.get("courseId");
        Long userId = message.get("userId");
        if(!Objects.isNull(courseId) && !Objects.isNull(userId)) {
            asyncDb(courseId,userId);
        }
    }

    private void asyncDb(Long userId, Long courseId) {
        ZlStudent student = studentService.getOne(new QueryWrapper<ZlStudent>().eq("uid", userId).select("id"));
        ZlCourseStudent courseStudent = new ZlCourseStudent();
        courseStudent.setCid(courseId);
        courseStudent.setSid(student.getId());
        courseStudentService.save(courseStudent);
    }
}
