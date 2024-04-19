package com.xiaopeng.car.settingslibrary.common.net;
/* loaded from: classes.dex */
public class Resource<T> {
    public final T data;
    public final String message;
    public final Status status;

    /* loaded from: classes.dex */
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    private Resource(Status status, T t, String str) {
        this.status = status;
        this.data = t;
        this.message = str;
    }

    public static <T> Resource<T> success(T t) {
        return new Resource<>(Status.SUCCESS, t, null);
    }

    public static <T> Resource<T> error(String str, T t) {
        return new Resource<>(Status.ERROR, t, str);
    }

    public static <T> Resource<T> loading(T t) {
        return new Resource<>(Status.LOADING, t, null);
    }
}
