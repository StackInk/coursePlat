package com.bywlstudio.member.service;

import com.bywlstudio.member.entity.AclPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.gson.JsonArray;
import com.alibaba.fastjson.JSONObject;

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

    List<AclPermission> getMenus();

    List<JSONObject> getMenuJackson();

    List<AclPermission> getMenuByRoleId(Long roleId);

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
    List<JSONObject> getPermissionByUserId(Long userId);

    /**
     * 根据用户名获取对应的权限信息
     * @param username
     * @return
     */
    List<JSONObject> getMenuByUsername(String username);

    /**
     * 根据角色ID获取对应的权限值
     * @param roleId
     * @return
     */
    List<String> getPermissionValueByRoleId(Long roleId);

    List<String> getPermissionValueByUserId(Long userId);

    List<String> getAllPermissionValue();

    List<JSONObject> getAllMenus();
}
