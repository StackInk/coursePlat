package com.bywlstudio.course.controller;


import com.bywlstudio.common.util.R;
import com.bywlstudio.course.entity.ZlTeacher;
import com.bywlstudio.course.service.IZlTeacherService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 教师表 前端控制器
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/course/zl-teacher")
public class ZlTeacherController {

    @Resource
    private IZlTeacherService teacherService;

    @GetMapping
    public R listTeacher() {
        List<ZlTeacher> teachers = teacherService.list(null);
        return R.ok().data("teachers",teachers);
    }

    @GetMapping("{id}")
    public R listTeacherById(@PathVariable Long id) {
        ZlTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }


    @PostMapping
    public R createTeacher(@RequestBody @Validated ZlTeacher teacher) {
        teacherService.save(teacher);
        return R.ok();
    }

    @PutMapping
    public R updateTeacher(@RequestBody @Validated ZlTeacher teacher) {
        teacherService.updateById(teacher);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable Long id) {
        teacherService.removeById(id);
        return R.ok();
    }

}

