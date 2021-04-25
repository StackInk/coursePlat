package com.bywlstudio.member.controller;


import com.bywlstudio.common.util.R;
import com.bywlstudio.member.entity.AclPermission;
import com.bywlstudio.member.service.IAclPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/permission")
@Api("权限接口")
public class AclPermissionController {

    @Autowired
    private IAclPermissionService permissionService;

    @GetMapping
    @ApiOperation("查询所有的菜单信息")
    public R getAllMenu() {
        AclPermission permission = permissionService.getMenus();
        return R.ok().data("permission",permission);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除当前菜单")
    public R removeMenu(@PathVariable Long id) {
        permissionService.deletePermission(id);
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

    @PostMapping
    @ApiOperation("新增菜单")
    public R createPermission(@RequestBody AclPermission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @PutMapping
    @ApiOperation("更新菜单")
    public R updatePermission(@RequestBody AclPermission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }



}
