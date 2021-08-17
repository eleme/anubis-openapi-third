package me.eleme.anubis.sdk;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ElemeConstants {
    public static final String BIZ_CONTENT_FIELD = "business_data";

    /**
     * 默认字符集编码，EasySDK统一固定使用UTF-8编码，无需用户感知编码，用户面对的总是String而不是bytes
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    public static final String SUCCESS_CODE = "200";


    public static final String CODE = "code";


    public static final String MSG = "msg";



}
