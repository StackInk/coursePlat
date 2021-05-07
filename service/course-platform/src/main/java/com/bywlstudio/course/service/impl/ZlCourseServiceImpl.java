package com.bywlstudio.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlCourseTeacher;
import com.bywlstudio.course.entity.ZlTeacher;
import com.bywlstudio.course.mapper.ZlCourseMapper;
import com.bywlstudio.course.mapper.ZlTeacherMapper;
import com.bywlstudio.course.service.IZlCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bywlstudio.course.service.IZlCourseTeacherService;
import com.bywlstudio.course.vo.CourseVo;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service
public class ZlCourseServiceImpl extends ServiceImpl<ZlCourseMapper, ZlCourse> implements IZlCourseService {


    @Resource
    private ZlTeacherMapper teacherMapper;

    @Resource
    private IZlCourseTeacherService courseTeacherService;

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
        course.getTeachers().forEach(teacher -> {
            courseTeacherService.save(new ZlCourseTeacher(course.getId(),teacher.getId()));
        });
    }

    @Override
    public void updateCourse(CourseVo courseVo) {
        ZlCourse zlCourse = new ZlCourse();
        BeanUtils.copyProperties(courseVo,zlCourse);
        this.updateById(zlCourse);
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
}
