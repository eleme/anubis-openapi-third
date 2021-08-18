package me.eleme.anubis.sdk;

import com.aliyun.tea.TeaModel;
import com.aliyun.tea.TeaResponse;
import com.aliyun.tea.utils.StringUtils;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import me.eleme.anubis.sdk.util.EscapeUtils;
import me.eleme.anubis.sdk.util.JsonUtil;
import me.eleme.anubis.sdk.util.Sha256Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Client {
    /**
     * 构造成本较高的一些参数缓存在上下文中
     */
    private final Context context;

    /**
     * 注入的可选额外文本参数集合
     */
    private final Map<String, String> optionalTextParams = new HashMap<>();

    /**
     * 注入的可选业务参数集合
     */
    private final Map<String, Object> optionalBizParams = new HashMap<>();

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public Client(Context context) {
        this.context = context;
    }


    /**
     * 注入额外文本参数
     *
     * @param key   参数名称
     * @param value 参数的值
     * @return 本客户端本身，便于链路调用
     */
    public Client injectTextParam(String key, String value) {
        optionalTextParams.put(key, value);
        return this;
    }

    /**
     * 注入额外业务参数
     *
     * @param key   业务参数名称
     * @param value 业务参数的值
     * @return 本客户端本身，便于链式调用
     */
    public Client injectBizParam(String key, Object value) {
        optionalBizParams.put(key, value);
        return this;
    }

    /**
     * 获取时间戳，格式yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间戳
     */
    public String getTimestamp() throws Exception {
        return System.currentTimeMillis() +"";
    }

    /**
     * 获取Config中的配置项
     *
     * @param key 配置项的名称
     * @return 配置项的值
     */
    public String getConfig(String key) throws Exception {
        return context.getConfig(key);
    }


    /**
     * json转义
     * @param input
     * @return
     * @throws Exception
     */
    public String escapeJson(String input) throws Exception {
        return EscapeUtils.escapeJson(input);
    }

    /**
     * 对象转json
     * @param teaModel
     * @return
     * @throws Exception
     */
    public String objToJSONString(TeaModel teaModel) throws Exception {
        Map<String, Object> teaModelMap = JsonUtil.getTeaModelMap(teaModel);
        return JsonUtil.toJsonString(teaModelMap);
    }


    /**
     * 将随机顺序的Map转换为有序的Map
     *
     * @param input 随机顺序的Map
     * @return 有序的Map
     */
    public java.util.Map<String, String> sortMap(java.util.Map<String, String> input) throws Exception {
        //GO语言的Map是随机顺序的，每次访问顺序都不同，才需排序
        return input;
    }


    /**
     * 将网关响应发序列化成Map，同时将API的接口名称和响应原文插入到响应Map的method和body字段中
     *
     * @param response HTTP响应
     * @return 响应反序列化的Map
     */
    public java.util.Map<String, Object> readAsJson(TeaResponse response) throws Exception {
        String responseBody = response.getResponseBody();
        Map map = new Gson().fromJson(responseBody, Map.class);
        return map;
    }

    /**
     * 从响应Map中提取返回值对象的Map，并将响应原文插入到body字段中
     *
     * @param respMap 响应Map
     * @return 返回值对象Map
     */
    public java.util.Map<String, Object> toRespModel(java.util.Map<String, Object> respMap) throws Exception {
        String code = (String) respMap.get(ElemeConstants.CODE);
        String msg = (String) respMap.get(ElemeConstants.MSG);
        //先找正常响应节点
        if (!StringUtils.isEmpty(code) && code.equals(ElemeConstants.SUCCESS_CODE)){
            String data = (String) respMap.get(ElemeConstants.BIZ_CONTENT_FIELD);
            return JsonUtil.jsonToMap(data);
        }
        throw new RuntimeException("接口访问异常，code:" +code+",msg:" + msg);
    }

    /**
     * 将业务参数和其他额外文本参数按www-form-urlencoded格式转换成HTTP Body中的字节数组，注意要做URL Encode
     *
     * @param bizParams 业务参数
     * @return HTTP Body中的字节数组
     */
    public byte[] toUrlEncodedRequestBody(java.util.Map<String, ?> bizParams) throws Exception {
        Map<String, String> sortedMap = getSortedMap(Collections.<String, String>emptyMap(), bizParams, null);
        return buildQueryString(sortedMap).getBytes(ElemeConstants.DEFAULT_CHARSET);
    }


    private String buildQueryString(Map<String, String> sortedMap) throws UnsupportedEncodingException {
        StringBuilder content = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, String> pair : sortedMap.entrySet()) {
            if (!Strings.isNullOrEmpty(pair.getKey()) && !Strings.isNullOrEmpty(pair.getValue())) {
                content.append(index == 0 ? "" : "&")
                        .append(pair.getKey())
                        .append("=")
                        .append(URLEncoder.encode(pair.getValue(), ElemeConstants.DEFAULT_CHARSET.name()));
                index++;
            }
        }
        return content.toString();
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

    public String toJSONString(Map<String, ?> param) throws Exception{
        return  JsonUtil.toJsonString(param);
    }

    private Map<String, String> getSortedMap(Map<String, String> systemParams, Map<String, ?> bizParams,
                                             Map<String, String> textParams) throws Exception {

        Map<String, String> sortedMap = new TreeMap<>(systemParams);
        if (bizParams != null && !bizParams.isEmpty()) {
            sortedMap.put(ElemeConstants.BIZ_CONTENT_FIELD, JsonUtil.toJsonString(bizParams));
        }
        if (textParams != null&& !textParams.isEmpty()) {
            sortedMap.putAll(textParams);
        }
        return sortedMap;
    }

}
