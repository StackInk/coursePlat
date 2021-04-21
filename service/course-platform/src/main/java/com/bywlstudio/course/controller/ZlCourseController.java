package com.bywlstudio.course.controller;


import com.bywlstudio.common.util.R;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.service.IZlCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/course")
@Api("课程接口")
public class ZlCourseController {

    private IZlCourseService courseService;

    @GetMapping
    @ApiOperation("获取全部课程")
    public R listCourses() {
        List<ZlCourse> courses = courseService.list(null);
        return R.ok().data("courses",courses);
    }

    @GetMapping("{id}")
    @ApiOperation("根据课程ID获取课程")
    public R listCourseById(@PathVariable Long id) {
        ZlCourse course = courseService.getById(id);
        return R.ok().data("course",course);
    }


    @PostMapping
    @ApiOperation("创建课程")
    public R createCourse(@RequestBody @Validated ZlCourse course) {
        courseService.save(course);
        return R.ok();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除课程")
    public R deleteCourse(@PathVariable Long id) {
        courseService.removeById(id);
        return R.ok();
    }


    @PutMapping
    @ApiOperation("更新课程")
    public R updateCourse(@RequestBody @Validated ZlCourse course) {
        courseService.updateById(course);
        return R.ok();
    }








}

