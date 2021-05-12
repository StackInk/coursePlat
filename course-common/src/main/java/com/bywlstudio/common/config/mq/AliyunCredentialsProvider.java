package com.bywlstudio.common.config.mq;

import com.alibaba.mq.amqp.utils.UserUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bywlstudio.common.exception.CourseException;
import com.bywlstudio.common.exception.CourseRuntimeException;
import com.rabbitmq.client.impl.CredentialsProvider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: zl
 * @Date: Create in 2021/5/11 15:14
 * @Description:
 */
public class AliyunCredentialsProvider implements CredentialsProvider {
    /**
     * Access Key ID.
     */
    private final String accessKeyId;
    /**
     * Access Key Secret.
     */
    private final String accessKeySecret;
    /**
     * security temp token. (optional)
     */
    private final String securityToken;
    /**
     * instanceId(实例Id，可从AMQP控制台首页获取)
     */
    private final String instanceId;

    public AliyunCredentialsProvider(final String accessKeyId, final String accessKeySecret,
                                     final String instanceId) {
        this(accessKeyId, accessKeySecret, null, instanceId);
    }

    public AliyunCredentialsProvider(final String accessKeyId, final String accessKeySecret,
                                     final String securityToken, final String instanceId) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
        this.instanceId = instanceId;
    }

    @Override
    public String getUsername() {
        if(StringUtils.isNotEmpty(securityToken)) {
            return UserUtils.getUserName(accessKeyId, instanceId, securityToken);
        } else {
            return UserUtils.getUserName(accessKeyId, instanceId);
        }
    }

    @Override
    public String getPassword() {
        try {
            return UserUtils.getPassord(accessKeySecret);
        } catch (InvalidKeyException e) {
            throw new CourseRuntimeException("MQ连接失败",20010);
        } catch (NoSuchAlgorithmException e) {
            throw new CourseRuntimeException("MQ连接参数失败",20011);
        }
    }
}
