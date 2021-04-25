package com.bywlstudio.member.util;

/**
 * @Author: zl
 * @Date: Create in 2021/4/24 14:06
 * @Description:
 */
public class UserUtils {
    /**
     * 判断当前用户是否为管理员账户
     * @param username
     * @return
     */
    public static boolean isAdmin(String username) {
        return "admin".equals(username);
    }
}
