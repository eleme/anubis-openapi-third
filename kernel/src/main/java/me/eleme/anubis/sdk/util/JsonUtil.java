package me.eleme.anubis.sdk.util;

import com.aliyun.tea.TeaModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    public static Map<String, Object> jsonToMap(String json) {
        return new Gson().fromJson(json,new TypeToken<Map<String, Object>>(){}.getType());
    }

    /**
     * 将Map转换为Json字符串，转换过程中对于TeaModel，使用标注的字段名称而不是字段的变量名
     *
     * @param input 输入的Map
     * @return Json字符串
     */
    public static String toJsonString(Map<String, ?> input) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, ?> pair : input.entrySet()) {
            if (pair.getValue() instanceof TeaModel) {
                result.put(pair.getKey(), getTeaModelMap((TeaModel) pair.getValue()));
            } else {
                result.put(pair.getKey(), pair.getValue());
            }
        }
        return new Gson().toJson(result);
    }

    public static Map<String, Object> getTeaModelMap(TeaModel teaModel) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> teaModelMap = teaModel.toMap();
        for (Map.Entry<String, Object> pair : teaModelMap.entrySet()) {
            if (pair.getValue() instanceof TeaModel) {
                result.put(pair.getKey(), getTeaModelMap((TeaModel) pair.getValue()));
            } else {
                result.put(pair.getKey(), pair.getValue());
            }
        }
        return result;
    }
}
