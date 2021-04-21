package com.bywlstudio.member.controller;


import com.bywlstudio.common.util.R;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.service.IAclUserRoleService;
import com.bywlstudio.member.service.IAclUserService;
import com.bywlstudio.security.entity.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/acl-user")
@Api("用户信息接口")
@Slf4j
public class AclUserController {

    @Resource
    private IAclUserService aclUserService;


    @GetMapping
    public R info() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,Object> result = aclUserService.getUserInfo(username);
        return R.ok().data("info",result);
    }






}

