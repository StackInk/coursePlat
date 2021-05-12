package com.bywlstudio.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bywlstudio.member.entity.AclPermission;
import com.bywlstudio.member.entity.AclRole;
import com.bywlstudio.member.entity.AclRolePermission;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.mapper.AclPermissionMapper;
import com.bywlstudio.member.service.IAclPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bywlstudio.member.service.IAclRolePermissionService;
import com.bywlstudio.member.service.IAclRoleService;
import com.bywlstudio.member.service.IAclUserService;
import com.bywlstudio.member.util.MenuUtils;
import com.bywlstudio.member.util.PermissionUtils;
import com.bywlstudio.member.util.UserUtils;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.awt.*;
import java.security.acl.Acl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service("permissionService")
@Slf4j
public class AclPermissionServiceImpl extends ServiceImpl<AclPermissionMapper, AclPermission> implements IAclPermissionService {

    @Resource
    private IAclRolePermissionService rolePermissionService;

    @Resource
    private IAclRoleService roleService;

    @Resource
    private IAclUserService userService;


    /**
     * 根据角色ID获取权限信息
     * @param id
     * @return
     */
    @Override
    public List<AclPermission> getPermissionByRoleId(Long id) {
        List<AclPermission> allPermissionList = baseMapper.selectList(new QueryWrapper<AclPermission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<AclRolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<AclRolePermission>().eq("role_id",id));
        //转换给角色id与角色权限对应Map对象
        List<Long> permissionIdList = rolePermissionList.stream().map(AclRolePermission::getPermissionId).collect(Collectors.toList());
        allPermissionList.forEach(permission -> {
            if(permissionIdList.contains(permission.getId())) {
                permission.setSelect(true);
            } else {
                permission.setSelect(false);
            }
        });
        log.info("当前角色Id:{},拥有的权限ID为：{}",id,permissionIdList);
        return PermissionUtils.build(allPermissionList);
    }

    /**
     * 根据多个角色ID 获取权限信息
     * @param ids
     * @return
     */
    @Override
    public List<AclPermission> getPermissionByRoleIds(List<Long> ids) {
        List<AclPermission> list = new ArrayList<>();
        ids.forEach(id->list.addAll(this.getPermissionByRoleId(id)));
        return list;
    }


    /**
     * 获取所有的菜单
     * @return 返回层级已经确定的菜单
     */
    @Override
    public List<AclPermission> getMenus() {
        List<AclPermission> permissions = baseMapper.selectList(null);
        return PermissionUtils.build(permissions);
    }

    @Override
    public List<JSONObject> getMenuJackson() {
        List<AclPermission> permissions = baseMapper.selectList(null);
        return MenuUtils.buildCommon(PermissionUtils.build(permissions));
    }

    /**
     * 根据角色ID获取对应的权限信息
     * @param roleId
     * @return
     */
    @Override
    public List<AclPermission> getMenuByRoleId(Long roleId) {
        val permissions = baseMapper.getPermissionByRoleId(roleId);
        return PermissionUtils.build(permissions);
    }


    /**
     * 给角色分配权限信息
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void setPermissionByRoleId(Long roleId, Long[] permissionIds) {
        //删除当前角色的权限
        rolePermissionService.remove(new QueryWrapper<AclRolePermission>().eq("role_id",roleId));
        List<AclRolePermission> lists = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            lists.add(new AclRolePermission(roleId,permissionId));
        }
        rolePermissionService.saveBatch(lists);
    }

    /**
     * 删除权限，需要删除当前权限下的子权限（菜单信息）
     * @param permissionId
     */
    @Override
    public void deletePermission(Long permissionId) {
         List<Long> list = new ArrayList<>();
         this.getPermissionById(permissionId,list);
         list.add(permissionId);
         baseMapper.deleteBatchIds(list);
    }

    /**
     * 根据permissionId获取其子菜单
     * @param permissionId
     * @param permissions 获取的子菜单被封装到这个集合中
     */
    private void getPermissionById(Long permissionId,List<Long> permissions) {
        List<AclPermission> aclPermissions = baseMapper.selectList(new QueryWrapper<AclPermission>().eq("pid", permissionId).select("id"));
        aclPermissions.forEach(permission -> {
            permissions.add(permission.getId());
            getPermissionById(permission.getId(),permissions);
        });
    }

    /**
     * 根据用户ID获取对应的菜单信息
     *  1.获取当前用户的角色信息；
     *  2.根据角色信息获取对应的菜单信息；
     *  3.封装菜单信息
     * @param userId
     * @return
     */
    @Override
    public List<JSONObject> getPermissionByUserId(Long userId) {

        List<AclPermission> permissions = null ;
        if(isAdmin(userId)) {
            permissions = getAllPermission();
        }else {
            permissions = getNumPermission(userId);
        }
        List<AclPermission> aclPermission = PermissionUtils.build(permissions);
        return MenuUtils.buildCommon(aclPermission);
    }

    private List<AclPermission> getAllPermission() {
        return baseMapper.selectList(null);
    }

    private List<AclPermission> getNumPermission(Long userId) {
        //获取所有的角色信息

        List<AclRole> roleList = roleService.getRoleByUserId(userId);
        List<AclPermission> list = new ArrayList<>();
        roleList.forEach(role->{
            List<AclPermission> permissions = baseMapper.getPermissionByRoleId(role.getId());
            list.addAll(permissions);
        });
        return list;
    }

    /**
     * 根据用户名获取菜单
     * @param username
     * @return
     */
    @Override
    public List<JSONObject> getMenuByUsername(String username) {
        AclUser aclUser = userService.getUserByUsername(username);
        return this.getPermissionByUserId(aclUser.getId());
    }

    /**
     * 根据角色ID获取对应的角色信息
     * @param roleId
     * @return
     */
    @Override
    public List<String> getPermissionValueByRoleId(Long roleId) {
        return baseMapper.getPermissionValueByRoleId(roleId);
    }

    /**
     * 获取所有的菜单信息
     * @return
     */
    @Override
    public List<String> getAllPermissionValue() {
        List<AclPermission> selectList = baseMapper.selectList(new QueryWrapper<AclPermission>().eq("type", 2).select("permission_value"));
        return selectList.stream().map(AclPermission::getPermissionValue).collect(Collectors.toList());
    }

    @Override
    public List<JSONObject> getAllMenus() {
        List<AclPermission> permissions = baseMapper.selectList(null);
        return MenuUtils.buildCommon(PermissionUtils.build(permissions));
    }

    /**
     * 获取权限的值
     * @param userId
     * @return
     */
    @Override
    public List<String> getPermissionValueByUserId(Long userId) {
        List<String> permissionList = null ;
        if(isAdmin(userId)) {
            permissionList = getAllPermissionValue();
        }else {
            permissionList = getNumPermissionValue(userId);
        }
        return permissionList;
    }

    private List<String> getNumPermissionValue(Long userId) {
        List<AclRole> roleList = roleService.getRoleByUserId(userId);
        List<Long> roleIds = roleList.stream().map(AclRole::getId).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        roleIds.forEach(roleId->{
            list.addAll(this.getPermissionValueByRoleId(roleId));
        });
        return list;
    }

    private boolean isAdmin(Long userId) {
        AclUser user = userService.getById(userId);
        return UserUtils.isAdmin(user.getUsername());
    }


}
