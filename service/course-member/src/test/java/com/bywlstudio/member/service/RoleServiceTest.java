package com.bywlstudio.member.service;

import com.bywlstudio.member.AclServiceApplication;
import com.bywlstudio.member.entity.AclRole;
import com.bywlstudio.member.entity.AclUserRole;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: zl
 * @Date: Create in 2021/4/25 20:01
 * @Description:
 */
@SpringBootTest(classes = AclServiceApplication.class)
public class RoleServiceTest {

    @Resource
    private IAclRoleService roleService;

    @Resource
    private IAclUserRoleService userRoleService;

    @Test
    public void insertRole() {
        AclRole role = new AclRole();
        role.setName("管理员");
        role.setCode("10000");
        role.setRemark("所有的权限信息");
        roleService.save(role);
    }

    @Test
    public void insertRoleUser() {
        AclUserRole userRole = new AclUserRole(1L,1L);
        userRoleService.save(userRole);
    }

}
