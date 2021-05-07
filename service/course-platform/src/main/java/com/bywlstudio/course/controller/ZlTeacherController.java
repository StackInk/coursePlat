package com.bywlstudio.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.util.R;
import com.bywlstudio.course.entity.ZlCourse;
import com.bywlstudio.course.entity.ZlStudent;
import com.bywlstudio.course.entity.ZlTeacher;
import com.bywlstudio.course.service.IZlTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/teacher")
public class ZlTeacherController {

    @Resource
    private IZlTeacherService teacherService;

    @GetMapping("/{page}/{limit}")
    @ApiOperation("获取教师列表")
    public R listUser(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                      @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit) {
        Page<ZlTeacher> page1 = new Page<>(page,limit);
        IPage<ZlTeacher> page2 = teacherService.page(page1, null);
        return R.ok().data("item",page2.getRecords()).data("total",page2.getTotal());
    }

    @GetMapping("all")
    public R listTeacherAll() {
        List<ZlTeacher> list = teacherService.list(new QueryWrapper<ZlTeacher>().select("id", "name"));
        return R.ok().data("teachers",list);
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

    @GetMapping("/name/{page}/{limit}")
    @ApiOperation("根据名字获取对应的数据")
    public R getPageListByName(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                               @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit,
                               @ApiParam(name = "名字",value = "Java",required = false) @RequestParam("name") String name) {
        IPage<ZlTeacher> page1 = teacherService.getPageListByName(page,limit,name);
        return R.ok().data("items",page1.getRecords()).data("total",page1.getTotal());
    }

    @GetMapping("course/{id}")
    public R getTeachersByCourseId(@PathVariable Long id) {
        List<ZlTeacher> teacherList = teacherService.getTeachersByCourseId(id);
        return R.ok().data("teachers",teacherList);
    }

}

