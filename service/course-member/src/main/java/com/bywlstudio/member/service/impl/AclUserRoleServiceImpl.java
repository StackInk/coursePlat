package com.bywlstudio.member.service.impl;

import com.bywlstudio.member.entity.AclUserRole;
import com.bywlstudio.member.mapper.AclUserRoleMapper;
import com.bywlstudio.member.service.IAclUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@Service("userRoleService")
public class AclUserRoleServiceImpl extends ServiceImpl<AclUserRoleMapper, AclUserRole> implements IAclUserRoleService {

}
