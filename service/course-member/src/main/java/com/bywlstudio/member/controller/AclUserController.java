package com.bywlstudio.member.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.util.R;
import com.bywlstudio.member.entity.AclRole;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.service.IAclPermissionService;
import com.bywlstudio.member.service.IAclRoleService;
import com.bywlstudio.member.service.IAclUserRoleService;
import com.bywlstudio.member.service.IAclUserService;
import com.bywlstudio.member.vo.UserVo;
import com.bywlstudio.security.entity.User;
import com.google.gson.JsonArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/user")
@Api("用户信息接口")
@Slf4j
public class AclUserController {

    @Autowired
    private IAclUserService userService;

    @Autowired
    private IAclRoleService roleService;


    @GetMapping("info")
    @ApiOperation("根据登陆用户的信息，获取用户的权限信息")
    public R info() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,Object> result = userService.getUserInfo(username);
        return R.ok().data(result);
    }

    @GetMapping("menu")
    @ApiOperation("根据登陆用户的信息，获取用户菜单信息")
    public R getMenu() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        JsonArray menu = userService.getMenu(username);
        List<JSONObject> menu = userService.getMenuJackson(username);
        return R.ok().data("permissionList",menu);

    }

    @PostMapping("logout")
    @ApiOperation("注销用户登陆")
    public R logout() {
        return R.ok();
    }

    @GetMapping("/{page}/{limit}")
    @ApiOperation("获取用户列表")
    public R listUser(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                      @ApiParam(name = "页面记录数",value = "10",required = true) @PathVariable Long limit,
                      AclUser user) {
        Page<AclUser> page1 = new Page<>(page,limit);
        QueryWrapper<AclUser> queryWrapper = null;
        if (!StringUtils.isEmpty(user.getUsername())) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("username",user.getUsername());
        }
        IPage<AclUser> page2 = userService.page(page1, queryWrapper);
        return R.ok().data("item",page2.getRecords()).data("total",page2.getTotal());
    }

    @GetMapping("/{id}")
    public R getUserById(@PathVariable Long id) {
        AclUser byId = userService.getById(id);
        return R.ok().data("user",byId);
    }

    @PostMapping
    @ApiOperation("新增用户")
    public R addUser(@RequestBody AclUser user) {
        userService.saveUser(user);
        return R.ok();
    }

    @PutMapping
    @ApiOperation("更新用户")
    public R updateUser(@RequestBody AclUser user) {
        userService.updateById(user);
        return R.ok();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除用户")
    public R deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return R.ok();
    }

    @DeleteMapping("/batchRemove")
    @ApiOperation("删除多个用户")
    public R deleteUsers(@RequestBody List<Long> userIds) {
        userService.deleteUsers(userIds);
        return R.ok();
    }

    @GetMapping("/role/{id}")
    @ApiOperation("根据用户获取角色数据")
    public R getRoleDataByUserId(@PathVariable Long id) {
        Map<String,Object> roleList = roleService.getRolesByUserId(id);
        return R.ok().data(roleList);
    }

    @PostMapping("/role")
    @ApiOperation("根据用户ID分配角色")
    public R setRoleByUserId(Long userId, Long[] roleIds) {
        roleService.setRoleByUserId(userId,roleIds);
        return R.ok();
    }


    @GetMapping("/roleUser")
    @ApiOperation("根据角色ID获取User信息")
    public R getUserByRoleId(@RequestParam("roleId") Long id){
        List<AclUser> userByRoleId = userService.getUserByRoleId(id);
        return R.ok().data("users",userByRoleId).message("获取用户信息成功");
    }








}

