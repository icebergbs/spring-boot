package com.bingshan.springboot.msg;

public class MsgService {
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

    public MsgService(String url, String accessKeyId, String accessKeySecret) {
        this.url = url;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }


    public int sendMsg(String msg) {
        //调用http服务并发送消息,返回结果
        return HttpClientUtils.sendMsg(url, accessKeyId, accessKeySecret, msg);

    }

    //get set方法


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
