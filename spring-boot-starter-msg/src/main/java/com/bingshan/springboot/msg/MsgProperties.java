package com.bingshan.springboot.msg;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自动配置的属性配置类
 */
@ConfigurationProperties(prefix = "msg")  //对应属性的装配
public class MsgProperties {

    /**
     * 访问发送短信的Url地址
     */
    private String url;

    /**
     * 短信服务商提供的请求keyId
     */
    private String accessKeyId;

    /**
     * 短信服务商提供的 KeySecret
     */
    private String accessKeySecret;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
