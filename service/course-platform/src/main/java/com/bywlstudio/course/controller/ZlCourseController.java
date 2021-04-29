package com.bywlstudio.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.util.R;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlStudent;
import com.bywlstudio.course.service.IZlCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private IZlCourseService courseService;


    @GetMapping("/{page}/{limit}")
    @ApiOperation("获取全部课程")
    public R getPageList(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                         @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit) {
        Page<ZlCourse> page1 = new Page<>(page,limit);
        IPage<ZlCourse> page2 = courseService.page(page1, null);
        return R.ok().data("items",page2.getRecords()).data("total",page2.getTotal());
    }

    @GetMapping("{id}")
    @ApiOperation("根据课程ID获取课程")
    public R listCourseById(@PathVariable Long id) {
        ZlCourse course = courseService.getById(id);
        return R.ok().data("course",course);
    }

    @GetMapping
    public R listCourseByName(@RequestParam("name") String name) {
        List<ZlCourse> courses = courseService.list(new QueryWrapper<ZlCourse>().like("name", name));
        return R.ok().data("course",courses);
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

