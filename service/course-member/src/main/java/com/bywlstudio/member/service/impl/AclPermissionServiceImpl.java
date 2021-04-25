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
import com.bywlstudio.member.util.PermissionUtils;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
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
        List<AclRolePermission> list = rolePermissionService.list(new QueryWrapper<AclRolePermission>().eq("role_id", id).select("permission_id"));
        List<Long> permissionIds = list.stream().map(aclRolePermission -> aclRolePermission.getPermissionId()).collect(Collectors.toList());
        return baseMapper.selectBatchIds(permissionIds);
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
    public AclPermission getMenus() {
        List<AclPermission> aclPermissions = baseMapper.selectList(null);
        return PermissionUtils.build(aclPermissions);
    }

    /**
     * 根据角色ID获取对应的权限信息
     * @param roleId
     * @return
     */
    @Override
    public AclPermission getMenuByRoleId(Long roleId) {
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
    public AclPermission getMenuByUserId(Long userId) {
        //获取所有的角色信息
        List<AclRole> roleList = roleService.getRolesByUserId(userId);
        List<AclPermission> list = new ArrayList<>();
        roleList.forEach(role->{
            List<AclPermission> permissions = baseMapper.getPermissionByRoleId(role.getId());
            list.addAll(permissions);
        });

        return PermissionUtils.build(list);
    }

    @Override
    public AclPermission getMenuByUsername(String username) {
        AclUser aclUser = userService.getUserByUsername(username);
        return this.getMenuByUserId(aclUser.getId());
    }

    @Override
    public List<String> getPermissionValueByRoleId(Long roleId) {
        return baseMapper.getPermissionValueByRoleId(roleId);
    }
    @Override
    public List<String> getPermissionValueByUserId(Long userId) {
        List<AclRole> roleList = roleService.getRolesByUserId(userId);
        List<Long> roleIds = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        roleIds.forEach(roleId->{
            list.addAll(this.getPermissionValueByRoleId(roleId));
        });
        return list;
    }


}
