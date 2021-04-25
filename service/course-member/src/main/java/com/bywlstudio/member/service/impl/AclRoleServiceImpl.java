package com.bywlstudio.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bywlstudio.member.entity.AclRole;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.entity.AclUserRole;
import com.bywlstudio.member.mapper.AclRoleMapper;
import com.bywlstudio.member.service.IAclRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bywlstudio.member.service.IAclUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service("roleService")
public class AclRoleServiceImpl extends ServiceImpl<AclRoleMapper, AclRole> implements IAclRoleService {

    @Resource
    private IAclUserRoleService userRoleService;

    /**
     * 根据用户ID分配角色信息
     * @param userId
     * @param roleIds
     */
    @Override
    public void setRoleByUserId(Long userId, Long[] roleIds) {
        List<AclUserRole> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            list.add(new AclUserRole(roleId,userId));
        }
        userRoleService.saveBatch(list);
    }

    /**
     * 根据用户ID获取角色信息
     * @param id 用户ID
     * @return
     */
    @Override
    @Transactional
    public List<AclRole> getRolesByUserId(Long id) {
        List<AclUserRole> list = userRoleService.list(new QueryWrapper<AclUserRole>().eq("user_id", id).select("role_id"));
        List<Long> roles = list.stream().map(aclUserRole -> aclUserRole.getRoleId()).collect(Collectors.toList());
        List<AclRole> aclRoles = baseMapper.selectBatchIds(roles);
        return aclRoles;
    }




}
