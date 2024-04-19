package com.xiaopeng.car.settingslibrary.common.utils;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class GsonUtils {
    private static final String TAG = GsonUtils.class.getSimpleName();

    public static String convertVO2String(Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception unused) {
            LogUtils.e(TAG, "convertVO2String \r\n  error = \r\n    ", false);
            return null;
        }
    }

    public static <T> T convertString2Object(String str, Class<T> cls) {
        try {
            Gson gson = new Gson();
            if (isJson(str)) {
                return (T) gson.fromJson(str, (Class<Object>) cls);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "convertString2Object \r\n error = \r\n ", false);
            return null;
        }
    }

    public static <T> T convertString2Collection(String str, TypeToken<T> typeToken) {
        try {
            Gson gson = new Gson();
            if (isJson(str)) {
                return (T) gson.fromJson(str, typeToken.getType());
            }
            return null;
        } catch (Exception unused) {
            LogUtils.e(TAG, "convertString2Collection \r\n  error = \r\n    ");
            return null;
        }
    }

    public static boolean isJson(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            new JSONObject(str);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            String str2 = TAG;
            LogUtils.e(str2, "isJson \r\n  error = \r\n    " + str);
            return false;
        }
    }

    public static ArrayList<String> convertJson2Array(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(jSONArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
