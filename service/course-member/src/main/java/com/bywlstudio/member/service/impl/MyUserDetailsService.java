package com.bywlstudio.member.service.impl;

import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.service.IAclRoleService;
import com.bywlstudio.member.service.IAclUserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/16 17:11
 * @Description:
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private IAclUserService aclUserService;
    @Resource
    private IAclRoleService aclRoleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AclUser user = aclUserService.getUserByUsername(s);
        if (!Objects.isNull(user)) {

        }


        return null;
    }
}
