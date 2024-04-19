package com.xiaopeng.car.settingslibrary.repository.net.feedback;

import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.repository.net.LiveDataCallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/* loaded from: classes.dex */
public class FeedbackApiClient {
    private static final String TAG = "FeedbackApiClient";
    private static volatile OkHttpClient okHttpClientInstance;
    private static volatile Retrofit retrofitInstance;

    public static <T> T initService(Class<T> cls) {
        return (T) getRetrofitInstance().create(cls);
    }

    private static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            synchronized (FeedbackApiClient.class) {
                if (retrofitInstance == null) {
                    retrofitInstance = new Retrofit.Builder().baseUrl(Config.getFeedbackBaseUrl()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(new LiveDataCallAdapterFactory()).client(getOkHttpClientInstance()).build();
                }
            }
        }
        return retrofitInstance;
    }

    private static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClientInstance == null) {
            synchronized (FeedbackApiClient.class) {
                if (okHttpClientInstance == null) {
                    okHttpClientInstance = FeedbackOkHttpClientGenerator.getBuilder().build();
                }
            }
        }
        return okHttpClientInstance;
    }
}
