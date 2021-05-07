package com.bywlstudio.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.util.R;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlStudent;
import com.bywlstudio.course.service.IZlCourseService;
import com.bywlstudio.course.vo.CourseVo;
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
                         @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit                         ) {
        IPage<CourseVo> page1 = courseService.getPageList(page,limit);
        return R.ok().data("items",page1.getRecords()).data("total",page1.getTotal());
    }


    @GetMapping("/name/{page}/{limit}")
    @ApiOperation("根据名字获取对应的数据")
    public R getPageListByName(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                         @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit,
                         @ApiParam(name = "名字",value = "Java",required = false) @RequestParam("name") String name) {
        IPage<CourseVo> page1 = courseService.getPageListByName(page,limit,name);
        return R.ok().data("items",page1.getRecords()).data("total",page1.getTotal());
    }

    @GetMapping("{id}")
    @ApiOperation("根据课程ID获取课程")
    public R listCourseById(@PathVariable Long id) {
        CourseVo courseVo = courseService.getCourseById(id);
        return R.ok().data("course",courseVo);
    }

    @PostMapping
    @ApiOperation("创建课程")
    public R createCourse(@RequestBody @Validated CourseVo course) {
        courseService.saveCourse(course);
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

