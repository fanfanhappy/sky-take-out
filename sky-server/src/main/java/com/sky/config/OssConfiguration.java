package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: sky-take-out
 * @package: com.sky.config
 * @className: OssConfiguration
 * @author: 姬紫衣
 * @description: 配置类，用于创建AliOssUtil对象
 * @date: 2024/5/9 15:30
 * @version: 1.0
 */

@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties)
    {
        log.info("开始创建阿里云文件上传工具类对象 {}" , aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint() ,
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
