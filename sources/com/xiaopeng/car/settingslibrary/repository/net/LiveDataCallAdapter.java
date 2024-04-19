package com.xiaopeng.car.settingslibrary.repository.net;

import androidx.lifecycle.LiveData;
import com.xiaopeng.car.settingslibrary.common.net.ApiResponse;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes.dex */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;

    public LiveDataCallAdapter(Type type) {
        this.responseType = type;
    }

    @Override // retrofit2.CallAdapter
    public Type responseType() {
        return this.responseType;
    }

    @Override // retrofit2.CallAdapter
    public LiveData<ApiResponse<R>> adapt(final Call<R> call) {
        return new LiveData<ApiResponse<R>>() { // from class: com.xiaopeng.car.settingslibrary.repository.net.LiveDataCallAdapter.1
            AtomicBoolean started = new AtomicBoolean(false);

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.lifecycle.LiveData
            public void onActive() {
                super.onActive();
                if (this.started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() { // from class: com.xiaopeng.car.settingslibrary.repository.net.LiveDataCallAdapter.1.1
                        @Override // retrofit2.Callback
                        public void onResponse(Call<R> call2, Response<R> response) {
                            postValue(new ApiResponse(response));
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<R> call2, Throwable th) {
                            postValue(new ApiResponse(th));
                        }
                    });
                }
            }
        };
    }
}
