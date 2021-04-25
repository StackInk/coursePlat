package com.bywlstudio.member.service;

import com.bywlstudio.member.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bywlstudio.security.entity.User;
import com.google.gson.JsonArray;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author StackInk
 * @since 2021-04-08
 */
public interface IAclUserService extends IService<AclUser> {

    AclUser getUserByUsername(String username);

    Map<String,Object> getUserInfo(String username);

    JsonArray getMenu(String username);


    void deleteUser(Long userId);



}
