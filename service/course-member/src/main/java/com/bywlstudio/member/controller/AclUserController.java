package com.bywlstudio.member.controller;


import com.bywlstudio.common.util.R;
import com.bywlstudio.member.entity.AclUser;
import com.bywlstudio.member.service.IAclUserRoleService;
import com.bywlstudio.member.service.IAclUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
@Slf4j
public class AclUserController {

    @Resource
    private IAclUserService aclUserService;

    @Resource
    private Environment environment ;

    private String env;

    {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length==1) {
            env = activeProfiles[0];
        }
    }

    @PostMapping
    public R login(@RequestBody AclUser user) {

        return R.ok();
    }



}

