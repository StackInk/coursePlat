package com.bywlstudio.member.service;

import com.bywlstudio.member.entity.AclPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface IAclPermissionService extends IService<AclPermission> {

    List<AclPermission> getPermissionByRoleId(Long id);

    List<AclPermission> getPermissionByRoleIds(List<Long> ids);

    /**
     * 获取所有的菜单
     * @return
     */
    AclPermission getMenus();

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    AclPermission getMenuByRoleId(Long roleId);


    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionIds
     */
    void setPermissionByRoleId(Long roleId, Long[] permissionIds);

    /**
     * 删除菜单
     * @param permissionId
     */
    void deletePermission(Long permissionId);


    /**
     * 根据用户Id获取权限信息
     * @param userId
     * @return
     */
    AclPermission getMenuByUserId(Long userId);



}
