package me.eleme.anubis.sdk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return df.format(new Date());
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


}
