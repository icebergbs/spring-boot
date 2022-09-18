package com.bingshan.springboot.msg;

/**
 * 工具类， 生成根据具体场景实现
 */
public class HttpClientUtils {

    public static int sendMsg(String url, String accessKeyId, String accessKeySecret, String msg) {
        //TODO 调用指定url 进行请求的业务逻辑
        System.out.println("Http请求, url=" + url + "; accessKeyId=" + accessKeyId + ";accesssKeySecret=" + accessKeySecret
            + "; mage=" + msg);
        return 0;
    }
}
