package com.xiaopeng.car.settingslibrary.repository.net.feedback;

import androidx.lifecycle.LiveData;
import com.xiaopeng.car.settingslibrary.common.net.ApiResponse;
import com.xiaopeng.car.settingslibrary.common.net.XpApiResponse;
import com.xiaopeng.car.settingslibrary.repository.net.bean.feedback.ResponseHistory;
import com.xiaopeng.car.settingslibrary.repository.net.bean.feedback.ResponseUnread;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
/* loaded from: classes.dex */
public interface FeedbackNetService {
    @POST("is-center/feedback/create")
    LiveData<ApiResponse<XpApiResponse<Void>>> createFeedback(@Body RequestBody requestBody);

    @POST("is-center/feedback/getList")
    LiveData<ApiResponse<XpApiResponse<List<ResponseHistory>>>> reviewFeedback(@Body RequestBody requestBody);

    @POST("is-center/feedback/getNewsNumber")
    LiveData<ApiResponse<XpApiResponse<ResponseUnread>>> viewUnreadFeedback(@Body RequestBody requestBody);
}
