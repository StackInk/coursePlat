package com.bywlstudio.member.service;

import com.bywlstudio.member.AclServiceApplication;
import com.bywlstudio.member.entity.AclUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @Author: zl
 * @Date: Create in 2021/4/24 16:53
 * @Description:
 */
@SpringBootTest(classes = AclServiceApplication.class)
public class UserServiceTest {

    @Resource
    private IAclUserService userService;

    @Test
    public void insertUser() {
        AclUser user = new AclUser();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setAvatar("https://edu-stream001.oss-cn-beijing.aliyuncs.com/2021-01-23/4a374dddfc9c48bc817cd4ef716b728e-6ecb2fd145dc7ce35cf10684462303da.jpg");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("111111"));
        userService.save(user);
    }


}
