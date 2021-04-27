package com.bywlstudio.member.util;

import com.alibaba.nacos.client.utils.JSONUtils;
import com.bywlstudio.member.entity.AclPermission;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/20 19:45
 * @Description:
 */
@Slf4j
public class MenuUtils {


    public static JsonArray build(List<AclPermission> permissions) {
        JsonArray menus = new JsonArray();
        permissions.forEach(permission -> dfs(permission,menus));
        return menus;
    }

    public static List<JSONObject> buildJackson(List<AclPermission> permissions) {
        List<JSONObject> jsonArray = new LinkedList<>();
        if(permissions.size() == 1) {
            AclPermission permission = permissions.get(0);
            permission.getChildren().forEach(permission1 -> {
                dfs2(permission1,jsonArray);
            });
        }
        return jsonArray;
    }

    private static void dfs2(AclPermission permission, List<JSONObject> menus) {
        if(Objects.isNull(permission)) return ;
        JSONObject menu = new JSONObject();
        menu.put("path",permission.getPath());
        menu.put("component",permission.getComponent());
        menu.put("redirect","noredirect");
        menu.put("name",permission.getName());
        menu.put("hidden",false);
        JSONObject meta = new JSONObject();
        meta.put("title",permission.getName());
        meta.put("icon",permission.getIcon());
        menu.put("meta",meta);
        List<AclPermission> children = permission.getChildren();
        List<JSONObject> childrenMenu = new LinkedList<>();
        if(Objects.nonNull(children) || !children.isEmpty()) {
            children.forEach(permission1 -> {
                dfs2(permission1,childrenMenu);
                menu.put("children",childrenMenu);
            });
        }
        menus.add(menu);
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
        JsonArray childrenMenu = new JsonArray();
        if(Objects.nonNull(children) || !children.isEmpty()) {
            children.forEach(permission1 -> {
                dfs(permission1,childrenMenu);
                menu.add("children",childrenMenu);
            });
        }
        menus.add(menu);
    }

    public static List<JSONObject> buildCommon(List<AclPermission> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            AclPermission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<AclPermission> oneMeunList = topNode.getChildren();
            for(AclPermission one :oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_"+one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<AclPermission> twoMeunList = one.getChildren();
                for(AclPermission two :twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_"+two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);

                    List<AclPermission> threeMeunList = two.getChildren();
                    for(AclPermission three :threeMeunList) {
                        if(StringUtils.isEmpty(three.getPath())) continue;

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_"+three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);
                    }
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        try {
            log.info("菜单信息整理完毕:{}", JSONUtils.serializeObject(meuns));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return meuns;
    }


}
