package com.bywlstudio.course.service;

import com.bywlstudio.course.CourseServiceApplication;
import com.bywlstudio.course.entity.ZlCourse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: zl
 * @Date: Create in 2021/4/28 17:09
 * @Description:
 */
@SpringBootTest(classes = CourseServiceApplication.class)
class IZlCourseServiceTest {

    @Resource
    private IZlCourseService courseService;

    @Test
    public void insertCourse() {
        ZlCourse course = new ZlCourse("Java程序开发",1,null,60, Date.from(Instant.now()),
                "");
        ZlCourse course1 = new ZlCourse("Spring框架技术",2,null,60, Date.from(Instant.now()),
                "");
        ZlCourse course2 = new ZlCourse("中国近代通史",3,"6栋",60, Date.from(Instant.now()),
                "每周三9-10节");
        ZlCourse course3 = new ZlCourse("西方经济学",3,"4栋",30, Date.from(Instant.now()),
                "每周二9-10节");
        ZlCourse course4 = new ZlCourse("企业战略决策",2,null,60, Date.from(Instant.now()),
                "");
        ZlCourse course5 = new ZlCourse("教育学",3,"八栋",60, Date.from(Instant.now()),
                "每周一3-4节");
        ZlCourse course6 = new ZlCourse("财务管理",3,"二栋",60, Date.from(Instant.now()),
                "周日1-10节");
        List<ZlCourse> courses = new ArrayList<>();
        courses.add(course);
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);
        courseService.saveBatch(courses);
    }

}