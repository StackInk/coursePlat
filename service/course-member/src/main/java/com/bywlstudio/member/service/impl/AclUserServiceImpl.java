package com.bywlstudio.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bywlstudio.common.util.JwtUtil;
import com.bywlstudio.member.entity.AclPermission;
import com.bywlstudio.member.entity.AclRole;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.entity.AclUserRole;
import com.bywlstudio.member.mapper.AclUserMapper;
import com.bywlstudio.member.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bywlstudio.security.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements IAclUserService {

    @Resource
    private IAclRoleService roleService;

    @Resource
    private IAclPermissionService permissionService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public AclUser getUserByUsername(String username) {
        QueryWrapper<AclUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        return this.getOne(queryWrapper);
    }

    /**
     * 获取用户的个人信息，包括用户信息，用户角色信息、用户权限信息
     * @param username
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {
        AclUser aclUser = this.getUserByUsername(username);
        //获取当前用户所有的角色信息
        List<AclRole> roles = roleService.getRolesByUserId(aclUser.getId());
        List<Long> roleIds = roles.stream().map(role -> role.getId()).collect(Collectors.toList());

        //获取当前用户所有的权限信息
        List<AclPermission> permissions = permissionService.getPermissionByRoleIds(roleIds);
        List<String> permissionIds = permissions.stream().map(permission -> permission.getName()).collect(Collectors.toList());

        //权限信息
        redisTemplate.opsForValue().set(aclUser.getId().toString(),permissionIds);

        Map<String,Object> map = new HashMap<>();

        map.put("name", aclUser.getUsername());
        map.put("avatar", aclUser.getAvatar());
        map.put("roles", roles);
        map.put("permissionValueList", permissions);

        return map;

    }



}
