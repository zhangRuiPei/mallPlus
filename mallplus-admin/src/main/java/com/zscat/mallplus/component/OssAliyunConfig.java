package com.zscat.mallplus.component;

import com.zscat.mallplus.vo.OssAliyunField;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云配置类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Configuration
public class OssAliyunConfig {

    @Bean(value = "defaultOssAliyunField")
    @ConfigurationProperties("oss.aliyun.defalut")
    public OssAliyunField defaultOssAliyunField() {
        return new OssAliyunField();
    }

    @Bean(value = "firstOssAliyuField")
    @ConfigurationProperties("oss.aliyun.first")
    public OssAliyunField firstOssAliyuField() {
        return new OssAliyunField();
    }

    @Bean(value = "secondOssAliyuField")
    @ConfigurationProperties("oss.aliyun.second")
    public OssAliyunField secondOssAliyuField() {
        return new OssAliyunField();
    }
}
