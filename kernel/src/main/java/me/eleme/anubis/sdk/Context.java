package me.eleme.anubis.sdk;

import com.aliyun.tea.TeaModel;

import java.util.Map;

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
