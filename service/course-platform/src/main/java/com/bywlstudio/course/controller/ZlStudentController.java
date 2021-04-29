package com.bywlstudio.course.controller;


import com.bywlstudio.common.util.R;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlStudent;
import com.bywlstudio.course.service.IZlStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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


    @GetMapping
    @ApiOperation("获取学生列表")
    public R listStudent() {
        List<ZlStudent> students = studentService.list(null);
        return R.ok().data("students",students);
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


}

