package com.xiaopeng.car.settingslibrary.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/* loaded from: classes.dex */
public class ReflectUtils {
    public static int getIntValue(String str, Class<?> cls) {
        Field[] declaredFields;
        for (Field field : cls.getDeclaredFields()) {
            if (str.equals(field.getName())) {
                try {
                    return ((Integer) field.get(cls)).intValue();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public static String getStringValue(String str, Class<?> cls) {
        Field[] declaredFields;
        for (Field field : cls.getDeclaredFields()) {
            if (str.equals(field.getName())) {
                try {
                    return (String) field.get(cls);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static Object invokeMethod(Object obj, String str, Class<?>[] clsArr, Object[] objArr) {
        Method declaredMethod = getDeclaredMethod(obj, str, clsArr);
        if (declaredMethod != null) {
            declaredMethod.setAccessible(true);
            try {
                return declaredMethod.invoke(obj, objArr);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Method getDeclaredMethod(Object obj, String str, Class<?>[] clsArr) {
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            try {
                return cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object getDeclaredField(Object obj, String str, Class cls) {
        Field declaredField;
        for (Class<?> cls2 = obj.getClass(); cls2 != Object.class; cls2 = cls2.getSuperclass()) {
            try {
                declaredField = cls2.getDeclaredField(str);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
            if (cls == Boolean.class) {
                return Boolean.valueOf(declaredField.getBoolean(obj));
            }
            if (cls == Integer.class) {
                return Integer.valueOf(declaredField.getInt(obj));
            }
        }
        return null;
    }

    public static void invokeMethodSetField(Object obj, String str, Object obj2) {
        Field field = null;
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            try {
                field = cls.getDeclaredField(str);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(obj, obj2);
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static Object invokeStaticMethod(Class cls, String str, Class<?>[] clsArr, Object[] objArr) {
        try {
            Method method = cls.getMethod(str, clsArr);
            if (method != null) {
                method.setAccessible(true);
                try {
                    return method.invoke(null, objArr);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
        return null;
    }
}
