package com.bywlstudio.member.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.util.R;
import com.bywlstudio.member.entity.ZlStudent;
import com.bywlstudio.member.entity.ZlTeacher;
import com.bywlstudio.member.service.IZlStudentService;
import com.bywlstudio.member.service.IZlTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 教师表 前端控制器
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/zl-teacher")
public class ZlTeacherController {

    @Autowired
    private IZlTeacherService teacherService;


    @GetMapping("/{page}/{limit}")
    @ApiOperation("获取教师列表")
    public R listUser(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                      @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit) {
        Page<ZlTeacher> page1 = new Page<>(page,limit);
        IPage<ZlTeacher> page2 = teacherService.page(page1, null);
        return R.ok().data("item",page2.getRecords()).data("total",page2.getTotal());
    }

    @PostMapping
    @ApiOperation("添加教师")
    public R createStudent(@RequestBody ZlTeacher teacher) {
        teacherService.save(teacher);
        return R.ok();
    }

    @PutMapping
    @ApiOperation("更新教师信息")
    public R updateStudent(@RequestBody ZlTeacher teacher) {
        teacherService.updateById(teacher);
        return R.ok();
    }

}

