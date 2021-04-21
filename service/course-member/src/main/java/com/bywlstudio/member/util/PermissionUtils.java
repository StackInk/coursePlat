package com.bywlstudio.member.util;

import com.bywlstudio.member.entity.AclPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/20 20:13
 * @Description:
 */
public class PermissionUtils {


    public static AclPermission build(List<AclPermission> permissions) {
        AclPermission aclPermission = null;
        for (AclPermission permission : permissions) {
            if(permission.getPid() == 0) {
                aclPermission = permission;
                permission.setLevel(1);
                dfs(permission,permissions);
            }
        }
        return aclPermission;
    }

    private static void dfs(AclPermission permission, List<AclPermission> permissions) {
        permission.setChildren(new ArrayList<>());
        for (AclPermission aclPermission : permissions) {
            if (aclPermission.getPid() == permission.getId()) {
                aclPermission.setLevel(permission.getLevel()+1);
                permission.getChildren().add(aclPermission);
                if(!Objects.isNull(aclPermission.getChildren())) {
                    dfs(aclPermission,permissions);
                }
            }
        }
    }

}
