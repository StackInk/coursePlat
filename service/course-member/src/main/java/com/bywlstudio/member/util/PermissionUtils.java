package com.bywlstudio.member.util;

import com.bywlstudio.member.entity.AclPermission;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/20 20:13
 * @Description:
 */
@Slf4j
public class PermissionUtils {


    public static List<AclPermission> build(List<AclPermission> permissions) {
        log.info("权限格式化大小：{}，当前时间：{}",permissions.size(), LocalDateTime.now());
        List<AclPermission> aclPermission = new ArrayList<>();
        for (AclPermission permission : permissions) {
            if(permission.getPid() == 0) {
                permission.setLevel(1);
                aclPermission.add(dfs(permission,permissions));
            }
        }
        return aclPermission;
    }

    private static AclPermission dfs(AclPermission permission, List<AclPermission> permissions) {
        permission.setChildren(new ArrayList<>());
        for (AclPermission aclPermission : permissions) {
            if (aclPermission.getPid().equals(permission.getId())) {
                aclPermission.setLevel(permission.getLevel()+1);
                if(Objects.isNull(permission.getChildren())) {
                    permission.setChildren(new ArrayList<>());
                }
                permission.getChildren().add(dfs(aclPermission,permissions));
            }
        }
        return permission;
    }

}
