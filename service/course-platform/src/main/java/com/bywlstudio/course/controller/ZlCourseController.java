package com.bywlstudio.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.constant.CourseCode;
import com.bywlstudio.common.util.R;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlStudent;
import com.bywlstudio.course.service.IZlCourseService;
import com.bywlstudio.course.utils.DateTimeUtils;
import com.bywlstudio.course.vo.CourseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/select/{page}/{limit}")
    @ApiOperation("根据名字获取对应的数据")
    public R getPageListBySelect(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                                 @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit,
                                 @ApiParam(name = "课程信息",value = "{}",required = false)  CourseVo courseVo) {
        IPage<CourseVo> page1 = courseService.getPageListBySelect(page,limit,courseVo);
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


    @GetMapping("/judgeTime")
    @ApiOperation("判断当前日期是否可以进行选课")
    public R judgeTime() {
        Map<String,Object> time = courseService.judgeTime();
        if(time.size() == 2) {
            return R.ok().message("课程选课已开始").data(time);
        }
        return R.ok().data("code",30000).message("课程选课未开始");
    }

    @PostMapping("select")
    @ApiOperation("进行选课操作")
    public R selectCourse(Long courseId, Long userId){
        Map<String,Object> map = courseService.selectCourse(courseId,userId);
        if((Integer) map.get("code") == CourseCode.COURSE_SELECT_SUCCESS.getCode()) {
            return R.ok().message("选课成功").data(map);
        }
        return R.ok().message("选课失败").data(map);
    }

    @PostMapping("select/add")
    @ApiOperation("添加选课信息")
    public R addSelectCourse(Long[] courseIds, Long[] times) {
        boolean flag = courseService.addSelectCourse(courseIds,times);
        if(flag) {
            return R.ok().message("添加选课信息成功");
        }
        return R.ok().message("添加选课信息失败");
    }












}

