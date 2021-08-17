package me.eleme.anubis.sdk;

import com.aliyun.tea.NameInMap;
import com.aliyun.tea.TeaModel;
import com.aliyun.tea.Validation;

public class Config extends TeaModel {

    /**
     * 通信协议，通常填写https
     */
    @NameInMap("protocol")
    @Validation(required = true)
    public String protocol;

    /**
     * 网关域名
     * 线上为：openapi.alipay.com
     * 沙箱为：openapi.alipaydev.com
     */
    @NameInMap("gatewayHost")
    @Validation(required = true)
    public String gatewayHost;

    /**
     * AppId
     */
    @NameInMap("appId")
    @Validation(required = true)
    public String appId;

    /**
     * AppId
     */
    @NameInMap("merchantId")
    @Validation(required = true)
    public String merchantId;

    /**
     * AppId
     */
    @NameInMap("secretKey")
    @Validation(required = true)
    public String secretKey;

    /**
     * accessToken
     */
    @NameInMap("accessToken")
    @Validation(required = false)
    public String accessToken;


    /**
     * 代理地址（可选）
     * 例如：http://127.0.0.1:8080
     */
    @NameInMap("httpProxy")
    public String httpProxy;


    /**
     * 忽略证书校验（可选）
     */
    @NameInMap("ignoreSSL")
    public boolean ignoreSSL;


}
