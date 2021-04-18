package com.bywlstudio.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.mapper.AclUserMapper;
import com.bywlstudio.member.service.IAclUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public AclUser getUserByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        return this.getOne(queryWrapper);
    }
}
