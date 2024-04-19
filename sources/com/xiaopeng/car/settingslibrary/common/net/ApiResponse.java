package com.xiaopeng.car.settingslibrary.common.net;

import android.util.Log;
import java.io.IOException;
import retrofit2.Response;
/* loaded from: classes.dex */
public class ApiResponse<T> {
    private static final String TAG = "ApiResponse";
    public final T body;
    public final int code;
    public final String errorMessage;

    public ApiResponse(Throwable th) {
        this.code = 500;
        this.body = null;
        this.errorMessage = th.getMessage();
    }

    public ApiResponse(Response<T> response) {
        String string;
        this.code = response.code();
        if (response.isSuccessful()) {
            this.body = response.body();
            this.errorMessage = null;
            return;
        }
        if (response.errorBody() != null) {
            try {
                string = response.errorBody().string();
            } catch (IOException unused) {
                Log.e(TAG, "error while parsing response");
            }
            this.errorMessage = (string != null || string.trim().length() == 0) ? response.message() : string;
            this.body = null;
        }
        string = null;
        this.errorMessage = (string != null || string.trim().length() == 0) ? response.message() : string;
        this.body = null;
    }

    public boolean isSuccessful() {
        int i = this.code;
        return i >= 200 && i < 300;
    }
}
