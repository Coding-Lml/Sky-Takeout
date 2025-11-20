package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aloong
 * @description 创建一个配置类，该类中定义阿里云文件上传工具类对象 并将其注册为Bean 在项目运行时
 * 自动加载该对象 用于进行阿里云文件上传
 * @since 2025/11/20 下午7:07
 */
@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云文件上传工具类对象：{}", aliOssProperties);
        return AliOssUtil.builder()
                .accessKeyId(aliOssProperties.getAccessKeyId())
                .accessKeySecret(aliOssProperties.getAccessKeySecret())
                .endpoint(aliOssProperties.getEndpoint())
                .bucketName(aliOssProperties.getBucketName())
                .build();
    }
}
