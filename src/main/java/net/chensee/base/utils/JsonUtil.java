package net.chensee.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * @author : shibo
 * @date : 2019/5/20 17:25
 */
public class JsonUtil {

    public static String toStr(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T toObj(String source, Class<T> tClass) {
        return JSON.parseObject(source, tClass);
    }


    public static <T> List<T> toList(String source, Class<T> tClass) {
        return JSONArray.parseArray(source, tClass);
    }
}