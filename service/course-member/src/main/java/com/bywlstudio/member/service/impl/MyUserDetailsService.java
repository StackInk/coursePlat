package com.bywlstudio.member.service.impl;

import com.bywlstudio.member.entity.AclPermission;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.service.IAclPermissionService;
import com.bywlstudio.member.service.IAclRoleService;
import com.bywlstudio.member.service.IAclUserService;
import com.bywlstudio.security.entity.SecurityUser;
import com.bywlstudio.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/16 17:11
 * @Description:
 */
@Service("userDetailService")
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private IAclUserService userService;
    
    @Resource
    private IAclPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("获取当前用户信息，用户名为:{}",s);
        AclUser user = userService.getUserByUsername(s);
        if (!Objects.isNull(user)) {
            User user1 = new User();
            BeanUtils.copyProperties(user,user1);
            List<String> permissionList = permissionService.getPermissionValueByUserId(user.getId());
            SecurityUser securityUser = new SecurityUser();
            securityUser.setPermissionValueList(permissionList);
            securityUser.setUser(user1);
            return securityUser;
        }
        return null;
    }
}
