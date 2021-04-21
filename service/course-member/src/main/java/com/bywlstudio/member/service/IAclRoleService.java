package com.bywlstudio.member.service;

import com.bywlstudio.member.entity.AclRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface IAclRoleService extends IService<AclRole> {

    /**
     * 根据用户ID获取角色信息
     * @param id
     * @return
     */
    List<AclRole> getRolesByUserId(Long id);

    /**
     * 根据用户Id分配角色
     * @param userId
     * @param roleIds
     */
    void setRoleByUserId(Long userId, Long[] roleIds);



}
