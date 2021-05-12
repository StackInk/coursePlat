package com.bywlstudio.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.config.mq.RabbitConfirmCallback;
import com.bywlstudio.common.config.mq.RabbitReturnCallback;
import com.bywlstudio.common.constant.Constant;
import com.bywlstudio.common.constant.CourseCode;
import com.bywlstudio.common.exception.CourseException;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlCourseTeacher;
import com.bywlstudio.course.entity.ZlTeacher;
import com.bywlstudio.course.mapper.ZlCourseMapper;
import com.bywlstudio.course.mapper.ZlTeacherMapper;
import com.bywlstudio.course.service.IZlCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bywlstudio.course.service.IZlCourseTeacherService;
import com.bywlstudio.course.utils.DateTimeUtils;
import com.bywlstudio.course.vo.CourseVo;
import com.google.common.collect.Maps;
import com.mysql.cj.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.checkerframework.checker.units.qual.C;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service
@Slf4j
public class ZlCourseServiceImpl extends ServiceImpl<ZlCourseMapper, ZlCourse> implements IZlCourseService {


    @Resource
    private ZlTeacherMapper teacherMapper;

    @Resource
    private IZlCourseTeacherService courseTeacherService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    private static Lock lock = new ReentrantLock();

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(new RabbitConfirmCallback());
        rabbitTemplate.setReturnCallback(new RabbitReturnCallback());
    }



    @Override
    public CourseVo getCourseById(Long id) {
        ZlCourse course = this.getById(id);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course,courseVo);
        List<ZlTeacher> teacherList = teacherMapper.getTeacherByCourseId(id);
        courseVo.setTeachers(teacherList);
        return courseVo;
    }

    @Override
    public void saveCourse(CourseVo course) {
        ZlCourse zlCourse = new ZlCourse();
        BeanUtils.copyProperties(course,zlCourse);
        this.save(zlCourse);
        course.getTeachers().forEach(teacher -> courseTeacherService.save(new ZlCourseTeacher(course.getId(),teacher.getId())));
    }

    @Override
    public void updateCourse(CourseVo courseVo) {
        ZlCourse zlCourse = new ZlCourse();
        BeanUtils.copyProperties(courseVo,zlCourse);
        this.updateById(zlCourse);
    }

    @Override
    public Map<String,Object> judgeTime() {
        Map<String,Object> map = Maps.newHashMap();
        Long[] selectCourseTimes = getSelectCourseTimes();
        if(isDoSelectCourse()) {
            map.put("code", CourseCode.COURSE_JUDGE.getCode());
            map.put("time",selectCourseTimes);
        }else{
            map.put("code", CourseCode.COURSE_JUDGE_FAILURE.getCode());
        }
        return map;
    }

    private boolean isDoSelectCourse() {
        Long[] longs = getSelectCourseTimes();
        if(Objects.isNull(longs)) {
            return false;
        }
        return DateTimeUtils.isAfter(System.currentTimeMillis(), longs[0]) && DateTimeUtils.isBefore(System.currentTimeMillis(), longs[1]);
    }

    private Long[] getSelectCourseTimes() {
        Long[] longs = new Long[2];
        //从 redis 中获取选课的开始时间
        Set<Object> set = redisTemplate.opsForSet().members(Constant.courseStart);
        if(set.size() != 2) {
            return null;
        }
        log.info("当前选课开始时间,{}",set);
        set.toArray(longs);
        return longs;
    }



    @Override
    public IPage<CourseVo> getPageListByName(Long page, Long limit, String name) {
        Page<ZlCourse> page1 = new Page<>(page,limit);
        IPage<ZlCourse> page2 = this.page(page1, new QueryWrapper<ZlCourse>().like("name",name));
        return getCourseVoIPage(page2);
    }

    private IPage<CourseVo> getCourseVoIPage(IPage<ZlCourse> page2) {
        IPage<CourseVo>  page3 = new Page<>();
        page3.setTotal(page2.getTotal());
        page3.setRecords(new ArrayList<>());
        page2.getRecords().forEach(course -> {
            CourseVo courseVo = new CourseVo();
            BeanUtils.copyProperties(course,courseVo);
            courseVo.setTeachers(teacherMapper.getTeacherByCourseId(course.getId()));
            page3.getRecords().add(courseVo);
        });
        return page3;
    }

    @Override
    public IPage<CourseVo> getPageList(Long page, Long limit) {
        Page<ZlCourse> page1 = new Page<>(page,limit);
        IPage<ZlCourse> page2 = this.page(page1, null);
        return getCourseVoIPage(page2);
    }


    /**
     * 选课逻辑处理
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> selectCourse(Long courseId, Long userId) {
        HashMap<String,Object> map = Maps.newHashMap();
        //扣减Redis库存
        try{
            if(deRedisStock(courseId)) {
                map.put("code",CourseCode.COURSE_SELECT_SUCCESS.getCode());
                //发送消息
                sendMessage(courseId,userId);
            }else {
                map.put("code",CourseCode.COURSE_SELECT_FAILURE.getCode());
            }
        }catch (Exception e) {
            map.put("code",CourseCode.COURSE_SELECT_FAILURE.getCode());
            map.put("message",e.getMessage());
        }
        //库存扣减失败，返回错误信息
        return map;
    }

    /**
     * 发送MQ消息
     * @param courseId
     * @param userId
     */
    private void sendMessage(Long courseId, Long userId) {
        HashMap<String,Long> message = Maps.newHashMap();
        message.put("userId",userId);
        message.put("courseId",courseId);
        rabbitTemplate.convertAndSend(Constant.rabbitExchange,Constant.rabbitBinding,message);
        log.info("发送消息:{},路由Key为:{},处理Exchange:{}",message,Constant.rabbitExchange,Constant.rabbitExchange);
    }





    /**
     * 扣减Redis库存
     * @param courseId
     * @param userId
     * @return
     */
    private boolean deRedisStock(Long courseId) throws Exception{
        if(lock.tryLock()) {
            try{
                //处理逻辑
                Integer stock = (Integer) redisTemplate.opsForHash().get(Constant.courseSelect,courseId+"");
                log.info("当前编号为{}的课程剩余库存数为{}",courseId,stock);
                if(stock > 0) {
                    redisTemplate.opsForHash().increment(Constant.courseSelect,courseId+"",-1);
                    return true;
                }
            } finally {
                lock.unlock();
            }
        }
        return false;
    }



    /**
     * 增加选课信息
     * @param courseIds
     * @param times
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addSelectCourse(Long[] courseIds, Long[] times) {
        try{
            //设置当前选课信息
            baseMapper.updateCourseByIds(courseIds,1);
            //存储选课时间
            redisTemplate.opsForSet().add(Constant.courseStart,times);
            redisTemplate.expireAt(Constant.courseStart, Instant.ofEpochMilli(times[1]));
            this.startScheduleTask();
        }catch (Exception e){
            log.info("添加选课异常信息{}",e);
            return false;
        }
        return true;
    }

    /**
     * 定时任务，将将要进行选课的灌入Redis中
     */
    @Async
    public void startScheduleTask() {
        List<ZlCourse> courseList = baseMapper.selectList(new QueryWrapper<ZlCourse>().eq("is_select", 1).select("id","stock"));
        Map<Long,Integer> map = Maps.newHashMap();
        courseList.forEach(course -> {
            map.put(course.getId(),course.getStock());
        });
        //将数据灌入Redis
        redisTemplate.opsForHash().putAll(Constant.courseSelect,map);
        //设置过期时间，多加一天
        Instant expireTime = Instant.ofEpochMilli(getSelectCourseTimes()[1] + DateTimeUtils.coverDayToMill());
        redisTemplate.expireAt(Constant.courseSelect,expireTime);
    }

    @Override
    public IPage<CourseVo> getPageListBySelect(Long page, Long limit, CourseVo courseVo) {
        Page<ZlCourse> page1 = new Page<>(page,limit);
        QueryWrapper<ZlCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_select",1);
        if (!StringUtils.isEmpty(courseVo.getName())) {
            queryWrapper.eq("name",courseVo.getName());
        }

        IPage<ZlCourse> page2 = this.page(page1, queryWrapper);
        return getCourseVoIPage(page2);
    }
}
