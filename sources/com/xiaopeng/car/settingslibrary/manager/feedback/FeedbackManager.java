package com.xiaopeng.car.settingslibrary.manager.feedback;

import androidx.lifecycle.LiveData;
import com.google.gson.Gson;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.net.ApiResponse;
import com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource;
import com.xiaopeng.car.settingslibrary.common.net.Resource;
import com.xiaopeng.car.settingslibrary.common.net.XpApiResponse;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.car.settingslibrary.repository.net.bean.feedback.RequestCreateInfo;
import com.xiaopeng.car.settingslibrary.repository.net.bean.feedback.RequestHistory;
import com.xiaopeng.car.settingslibrary.repository.net.bean.feedback.ResponseHistory;
import com.xiaopeng.car.settingslibrary.repository.net.feedback.FeedbackNetService;
import com.xiaopeng.car.settingslibrary.repository.net.feedback.PostJsonBody;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import java.util.List;
/* loaded from: classes.dex */
public class FeedbackManager {
    private static volatile FeedbackManager sInstance;
    private FeedbackNetService mService = CarSettingsApp.initFeedbackService();

    public static FeedbackManager getInstance() {
        if (sInstance == null) {
            synchronized (FeedbackManager.class) {
                if (sInstance == null) {
                    sInstance = new FeedbackManager();
                }
            }
        }
        return sInstance;
    }

    public LiveData<Resource<XpApiResponse<Void>>> requestCreateFeedback(String str, String str2) {
        XuiSettingsManager.getInstance().uploadAlipayLog();
        RequestCreateInfo requestCreateInfo = new RequestCreateInfo();
        requestCreateInfo.setContent(str2);
        requestCreateInfo.setType(str);
        requestCreateInfo.setVin(Config.getVin());
        requestCreateInfo.setRequestTime(System.currentTimeMillis());
        requestCreateInfo.setRequestId(String.valueOf(System.currentTimeMillis()));
        requestCreateInfo.setLogUrl(uploadLog());
        requestCreateInfo.setChannel("0");
        String json = new Gson().toJson(requestCreateInfo);
        if (!Utils.isUserRelease()) {
            Logs.v("xpfeedback create:" + json);
        }
        final PostJsonBody postJsonBody = new PostJsonBody(json);
        return new NetworkBoundResource<XpApiResponse<Void>>() { // from class: com.xiaopeng.car.settingslibrary.manager.feedback.FeedbackManager.1
            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            protected LiveData<XpApiResponse<Void>> loadFromLocal() {
                return null;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            public void saveCallResult(XpApiResponse<Void> xpApiResponse) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            public boolean shouldFetch(XpApiResponse<Void> xpApiResponse) {
                return true;
            }

            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            protected boolean shouldSaveLocal() {
                return false;
            }

            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            protected LiveData<ApiResponse<XpApiResponse<Void>>> createCall() {
                return FeedbackManager.this.mService.createFeedback(postJsonBody);
            }
        }.asLiveData();
    }

    public LiveData<Resource<XpApiResponse<List<ResponseHistory>>>> reviewFeedback(String str, String str2) {
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setVin(Config.getVin());
        requestHistory.setPage(str);
        requestHistory.setRows(str2);
        requestHistory.setRequestId(String.valueOf(System.currentTimeMillis()));
        requestHistory.setRequestTime(System.currentTimeMillis());
        requestHistory.setChannel("0");
        String json = new Gson().toJson(requestHistory);
        Logs.v("xpfeedback review:" + json);
        final PostJsonBody postJsonBody = new PostJsonBody(json);
        return new NetworkBoundResource<XpApiResponse<List<ResponseHistory>>>() { // from class: com.xiaopeng.car.settingslibrary.manager.feedback.FeedbackManager.2
            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            protected LiveData<XpApiResponse<List<ResponseHistory>>> loadFromLocal() {
                return null;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            public void saveCallResult(XpApiResponse<List<ResponseHistory>> xpApiResponse) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            public boolean shouldFetch(XpApiResponse<List<ResponseHistory>> xpApiResponse) {
                return true;
            }

            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            protected boolean shouldSaveLocal() {
                return false;
            }

            @Override // com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource
            protected LiveData<ApiResponse<XpApiResponse<List<ResponseHistory>>>> createCall() {
                return FeedbackManager.this.mService.reviewFeedback(postJsonBody);
            }
        }.asLiveData();
    }

    private String uploadLog() {
        String sendRecentSystemLog = ((IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class)).sendRecentSystemLog();
        Logs.d("xpfeed uploadLog.. the log on remote OSS is: " + sendRecentSystemLog);
        return sendRecentSystemLog;
    }
}
