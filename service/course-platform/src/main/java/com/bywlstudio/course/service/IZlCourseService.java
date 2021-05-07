package com.bywlstudio.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bywlstudio.course.entity.ZlCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bywlstudio.course.vo.CourseVo;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface IZlCourseService extends IService<ZlCourse> {

    /**
     * 获取Course分页列表
     * @param page
     * @param limit
     * @return
     */
    IPage<CourseVo> getPageList(Long page, Long limit);

    /**
     * 根据课程ID获取对应的课程信息和教师信息
     * @param id
     * @return
     */
    CourseVo getCourseById(Long id);

    /**
     * 添加课程信息和教师对应关系
     * @param course
     */
    void saveCourse(CourseVo course);

    /**
     * 更新课程信息和教师对应关系
     * @param courseVo
     */
    void updateCourse(CourseVo courseVo);

    IPage<CourseVo> getPageListByName(Long page, Long limit, String name);
}
