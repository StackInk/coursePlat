package com.bywlstudio.member.service;

import com.bywlstudio.member.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bywlstudio.security.entity.User;
import com.google.gson.JsonArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
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

    List<JSONObject> getMenu(String username);

    List<JSONObject> getMenuJackson(String username);

    void saveUser(AclUser user);

    void deleteUser(Long userId);


    void deleteUsers(List<Long> userIds);

    List<AclUser> getUserByRoleId(Long id);
}
