package com.bywlstudio.member.service;

import com.bywlstudio.common.exception.CourseException;
import com.bywlstudio.common.util.JsonUtil;
import com.bywlstudio.member.AclServiceApplication;
import com.bywlstudio.member.entity.AclPermission;
import com.bywlstudio.member.util.MenuUtils;
import com.bywlstudio.member.util.PermissionUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: zl
 * @Date: Create in 2021/4/24 20:02
 * @Description:
 */
@SpringBootTest(classes = AclServiceApplication.class)
@Slf4j
class AclPermissionServiceTest {

    @Resource
    private IAclPermissionService permissionService;

    /**
     * 根据角色ID获取权限信息
     */
    @Test
    void getPermissionByRoleId() throws CourseException {

    }

    @Test
    void getPermissionByRoleIds() {
    }

    @Test
    void getMenus() throws CourseException {
        List<AclPermission> permissionByRoleId = permissionService.getPermissionByRoleId(1L);
        List<AclPermission> permissions = PermissionUtils.build(permissionByRoleId);
        System.out.println(JsonUtil.toJson(permissions));
        System.out.println(JsonUtil.toJson(MenuUtils.buildCommon(permissions)));
    }

    @Test
    void getMenuByRoleId() {
    }

    @Test
    void setPermissionByRoleId() {
        permissionService.setPermissionByRoleId(1L,
                new Long[]{1L,2L,3L,4L,5L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L,16L,17L,18L});
    }

    @Test
    void deletePermission() {
    }

    @Test
    void getMenuByUserId() {
    }

    @Test
    void getMenuByUsername() {
    }
}