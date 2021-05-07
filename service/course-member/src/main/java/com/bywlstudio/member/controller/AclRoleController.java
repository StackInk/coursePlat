package com.bywlstudio.member.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bywlstudio.common.util.R;
import com.bywlstudio.member.entity.AclPermission;
import com.bywlstudio.member.entity.AclRole;
import com.bywlstudio.member.service.IAclPermissionService;
import com.bywlstudio.member.service.IAclRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/role")
@Api("角色信息")
@Slf4j
public class AclRoleController {

    @Autowired
    private IAclRoleService roleService;

    @Resource
    private IAclPermissionService permissionService;

    @GetMapping("/{page}/{limit}")
    @ApiOperation("获取所有的角色进行分页处理")
    public R listRolePage(@ApiParam(name = "当前页",value = "1",required = true) @PathVariable Long page,
                          @ApiParam(name = "记录数",value = "10",required = true) @PathVariable Long limit,
                          AclRole role) {
        log.info("携带查询参数{}",role);
        Page<AclRole> roles = new Page<>(page,limit);
        QueryWrapper<AclRole> queryWrapper = null ;
        if(!StringUtils.isEmpty(role.getName())) {
            queryWrapper =  new QueryWrapper<AclRole>();
            queryWrapper.like("name",role.getName());
        }
        IPage<AclRole> iPage = roleService.page(roles, queryWrapper);
        return R.ok().data("items", iPage.getRecords()).data("total", iPage.getTotal());
    }

    @GetMapping("{id}")
    @ApiOperation("获取角色的详细信息")
    public R getRole(@PathVariable("id") Long roleId) {
        AclRole role = roleService.getById(roleId);
        return R.ok().data("role",role);
    }


    @PostMapping
    @ApiOperation("新增角色")
    public R saveRole(@RequestBody AclRole role) {
        roleService.saveOrUpdate(role);
        return R.ok();
    }

    @PutMapping
    @ApiOperation("更新角色")
    public R updateRole(@RequestBody AclRole role) {
        roleService.updateById(role);
        return R.ok();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除角色")
    public R deleteRole(@PathVariable Long id) {
        roleService.removeById(id);
        return R.ok();
    }

    @DeleteMapping
    @ApiOperation("删除多个角色")
    public R deleteRoleIds(List<Long> ids) {
        roleService.removeByIds(ids);
        return R.ok();
    }

    @PostMapping("/assign")
    @ApiOperation("根据角色ID给角色分配权限")
    public R setPermissionByRole(Long roleId, Long[] permissionIds) {
        permissionService.setPermissionByRoleId(roleId,permissionIds);
        return R.ok();
    }

    @GetMapping("/role/{roleId}")
    @ApiOperation("根据角色Id获取菜单信息")
    public R getPermissionByRoleId(@PathVariable Long roleId) {
        List<AclPermission> permissionList = permissionService.getPermissionByRoleId(roleId);
        return R.ok().data("permissions",permissionList);
    }



}

