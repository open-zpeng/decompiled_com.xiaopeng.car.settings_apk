package com.xiaopeng.car.settingslibrary.repository.net;

import androidx.lifecycle.LiveData;
import com.xiaopeng.car.settingslibrary.common.net.ApiResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
/* loaded from: classes.dex */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    @Override // retrofit2.CallAdapter.Factory
    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        if (getRawType(type) != LiveData.class) {
            return null;
        }
        Type parameterUpperBound = getParameterUpperBound(0, (ParameterizedType) type);
        if (getRawType(parameterUpperBound) != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }
        if (!(parameterUpperBound instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        return new LiveDataCallAdapter(getParameterUpperBound(0, (ParameterizedType) parameterUpperBound));
    }
}
