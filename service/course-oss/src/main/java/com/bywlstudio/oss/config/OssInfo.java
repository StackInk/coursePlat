package com.bywlstudio.oss.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: zl
 * @Date: Create in 2021/4/8 14:23
 * @Description:
 */
public class OssInfo implements InitializingBean {


    @Value("${aliyun.oss.accessKey}")
    private String accessKey ;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret ;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint ;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName ;

    public static String OSS_ACCESS_KEY ;

    public static String OSS_ACCESS_KEY_SECRET ;

    public static String OSS_ENDPOINT ;

    public static String OSS_BUCKET_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {
        OSS_ACCESS_KEY = this.accessKey ;
        OSS_BUCKET_NAME = this.bucketName ;
        OSS_ENDPOINT = this.endpoint ;
        OSS_ACCESS_KEY_SECRET = this.accessKeySecret ;
    }
}
