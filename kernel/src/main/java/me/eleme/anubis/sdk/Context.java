package me.eleme.anubis.sdk;

import com.aliyun.tea.TeaModel;
import com.google.common.base.Strings;
import me.eleme.anubis.sdk.util.JsonUtil;
import me.eleme.anubis.sdk.util.Sha256Util;

import java.util.Map;
import java.util.TreeMap;

public class Context {
    /**
     * 客户端配置参数
     */
    private final Map<String, Object> config;

    public Context(Config options) throws Exception {
        config = TeaModel.buildMap(options);
    }

    public String getConfig(String key) {
        if (String.valueOf(config.get(key)) == "null") {
            return null;
        } else {
            return String.valueOf(config.get(key));
        }
    }
}
