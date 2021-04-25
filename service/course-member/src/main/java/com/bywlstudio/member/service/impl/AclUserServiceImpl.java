package com.bywlstudio.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bywlstudio.common.util.JwtUtil;
import com.bywlstudio.member.entity.*;
import com.bywlstudio.member.mapper.AclUserMapper;
import com.bywlstudio.member.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bywlstudio.member.util.MenuUtils;
import com.bywlstudio.member.util.UserUtils;
import com.bywlstudio.security.entity.User;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
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
@Service("userService")
@Slf4j
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements IAclUserService {

    @Resource
    private IAclRoleService roleService;

    @Resource
    private IAclUserRoleService userRoleService;

    @Resource
    private IAclPermissionService permissionService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private IZlStudentService studentService;

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

        //获取当前用户所有的权限信息
        List<String> permissions = permissionService.getPermissionValueByUserId(aclUser.getId());

        //权限信息
        redisTemplate.opsForValue().set(aclUser.getId().toString(),permissions);

        Map<String,Object> map = new HashMap<>();
        map.put("id",aclUser.getId());
        map.put("name", aclUser.getUsername());
        map.put("avatar", aclUser.getAvatar());
        map.put("roles", roles);
        map.put("permissionValueList", permissions);
        log.info("获取当前用户权限信息,{}",map);
        return map;

    }

    @Override
    public JsonArray getMenu(String username) {
        AclPermission menus = null ;
        if(UserUtils.isAdmin(username)) {
            menus = permissionService.getMenus();
        }else {
            menus = permissionService.getMenuByUsername(username);
        }

        return MenuUtils.build(menus);
    }

    @Override
    public void deleteUser(Long userId) {
        //删除当前用户
        baseMapper.deleteById(userId);
        //删除当前用户对应的角色信息
        userRoleService.remove(new QueryWrapper<AclUserRole>().eq("user_id",userId));
        //删除当前用户对应的身份详细信息
        studentService.remove(new QueryWrapper<ZlStudent>().eq("uid",userId));
    }
}
