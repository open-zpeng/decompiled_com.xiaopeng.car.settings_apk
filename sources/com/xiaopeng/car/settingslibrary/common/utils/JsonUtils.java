package com.xiaopeng.car.settingslibrary.common.utils;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import java.util.List;
/* loaded from: classes.dex */
public class JsonUtils {
    public static final String TAG = "JsonUtils";

    public static String toJSONString(Object obj) {
        Log.v(TAG, "toJSONString begin");
        String jSONString = JSON.toJSONString(obj);
        Log.v(TAG, "toJSONString end");
        return jSONString;
    }

    public static <T> T toBean(String str, Class<T> cls) {
        return (T) JSON.parseObject(str, cls);
    }

    public static <T> List<T> toList(String str, Class<T> cls) {
        Log.v(TAG, "toList begin");
        List<T> parseArray = JSON.parseArray(str, cls);
        Log.v(TAG, "toList end");
        return parseArray;
    }

    public static <T> Object[] toArray(String str, Class<T> cls) {
        return JSON.parseArray(str, cls).toArray();
    }
}
