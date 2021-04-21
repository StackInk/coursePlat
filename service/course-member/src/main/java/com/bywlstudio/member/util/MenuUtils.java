package com.bywlstudio.member.util;

import com.bywlstudio.member.entity.AclPermission;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/20 19:45
 * @Description:
 */
public class MenuUtils {


    public static JsonArray build(AclPermission permissions) {
        JsonArray menus = new JsonArray();
        dfs(permissions,menus);
        return menus;
    }

    private static void dfs(AclPermission permission, JsonArray menus) {
        if(Objects.isNull(permission)) return ;
        JsonObject menu = new JsonObject();
        menu.addProperty("path",permission.getPath());
        menu.addProperty("component",permission.getComponent());
        menu.addProperty("redirect","noredirect");
        menu.addProperty("name",permission.getName());
        menu.addProperty("hidden",false);
        JsonObject meta = new JsonObject();
        meta.addProperty("title",permission.getName());
        meta.addProperty("icon",permission.getIcon());
        menu.add("meta",meta);
        List<AclPermission> children = permission.getChildren();
        if(!Objects.isNull(children) || children.size() > 0) {
            children.forEach(permission1 -> {
                JsonArray childrenMenu = new JsonArray();
                dfs(permission1,childrenMenu);
                menu.add("children",childrenMenu);
            });
        }
        menus.add(menu);
    }
}
