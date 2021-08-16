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

    public String sign(java.util.Map<String, String> systemParams, java.util.Map<String, ?> bizParams,
    java.util.Map<String, String> textParams, String secretKey) throws Exception{
        Map<String, String> sortedMap = getSortedMap(systemParams, bizParams, textParams);
        StringBuilder content = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, String> pair : sortedMap.entrySet()) {
            if (!Strings.isNullOrEmpty(pair.getKey()) && !Strings.isNullOrEmpty(pair.getValue())) {
                content.append(index == 0 ? "" : "&").append(pair.getKey()).append("=").append(pair.getValue());
                index++;
            }
        }
        return Sha256Util.getsha256(secretKey + content.toString());
    }

    private Map<String, String> getSortedMap(Map<String, String> systemParams, Map<String, ?> bizParams,
                                             Map<String, String> textParams) throws Exception {

        Map<String, String> sortedMap = new TreeMap<>(systemParams);
        if (bizParams != null && !bizParams.isEmpty()) {
            sortedMap.put(ElemeConstants.BIZ_CONTENT_FIELD, JsonUtil.toJsonString(bizParams));
        }
        if (textParams != null) {
            sortedMap.putAll(textParams);
        }
        return sortedMap;
    }

}
