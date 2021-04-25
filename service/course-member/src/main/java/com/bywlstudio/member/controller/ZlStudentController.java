package com.bywlstudio.member.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.util.R;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.entity.ZlStudent;
import com.bywlstudio.member.service.IZlStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @Autowired
    private IZlStudentService studentService;


    @GetMapping("/{page}/{limit}")
    @ApiOperation("获取学生列表")
    public R listUser(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                      @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit) {
        Page<ZlStudent> page1 = new Page<>(page,limit);
        IPage<ZlStudent> page2 = studentService.page(page1, null);
        return R.ok().data("item",page2.getRecords()).data("total",page2.getTotal());
    }

    @PostMapping
    @ApiOperation("添加学生")
    public R createStudent(@RequestBody ZlStudent student) {
        studentService.save(student);
        return R.ok();
    }

    @PutMapping
    @ApiOperation("更新学生信息")
    public R updateStudent(@RequestBody ZlStudent student) {
        studentService.updateById(student);
        return R.ok();
    }





}

