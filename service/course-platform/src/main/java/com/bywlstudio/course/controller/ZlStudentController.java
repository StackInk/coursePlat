package com.bywlstudio.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.util.R;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlStudent;
import com.bywlstudio.course.entity.ZlTeacher;
import com.bywlstudio.course.service.IZlStudentService;
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
 * 学生表 前端控制器
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/student")
@Api("学生接口")
public class ZlStudentController {

    @Resource
    private IZlStudentService studentService;


    @GetMapping("/{page}/{limit}")
    @ApiOperation("获取学生列表")
    public R listUser(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                      @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit) {
        Page<ZlStudent> page1 = new Page<>(page,limit);
        IPage<ZlStudent> page2 = studentService.page(page1, null);
        return R.ok().data("item",page2.getRecords()).data("total",page2.getTotal());
    }



    @GetMapping("{id}")
    @ApiOperation("根据课程ID获取学生信息")
    public R listStudentById(@PathVariable Long id) {
        ZlStudent student = studentService.getById(id);
        return R.ok().data("student",student);
    }

    @PostMapping
    @ApiOperation("创建学生")
    public R createStudent(@RequestBody @Validated ZlStudent student) {
        studentService.save(student);
        return R.ok();
    }

    @PutMapping
    @ApiOperation("更新学生信息")
    public R updateStudent(@RequestBody @Validated ZlStudent student) {
        studentService.updateById(student);
        return R.ok();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除学生")
    public R deleteStudent(@PathVariable Long id) {
        studentService.removeById(id);
        return R.ok();
    }

    @GetMapping("/name/{page}/{limit}")
    @ApiOperation("根据名字获取对应的数据")
    public R getPageListByName(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                               @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit,
                               @ApiParam(name = "名字",value = "Java",required = false) @RequestParam("name") String name) {
        IPage<ZlStudent> page1 = studentService.getPageListByName(page,limit,name);
        return R.ok().data("items",page1.getRecords()).data("total",page1.getTotal());
    }

    @GetMapping("course/{id}")
    public R getTeachersByCourseId(@PathVariable Long id) {
        List<ZlStudent> studentList = studentService.getStudentByCourseId(id);
        return R.ok().data("students",studentList);
    }


}

