package com.xiaopeng.car.settingslibrary.vm.feedback;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.text.TextUtils;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.common.FeedbackBean;
import com.xiaopeng.car.settingslibrary.common.net.Resource;
import com.xiaopeng.car.settingslibrary.common.net.XpApiResponse;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.feedback.FeedbackManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import com.xiaopeng.car.settingslibrary.repository.net.bean.feedback.ResponseHistory;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class FeedbackViewModel extends AndroidViewModel {
    private static final int DEFAULT_FIRST_PAGE = 1;
    public static final int FEEDBACK_HISTORY_NET_ERROR = 0;
    public static final int FEEDBACK_HISTORY_NET_SUCCESS = 1;
    private static final int PAGE_ITEM = 10;
    public static final int RESULT_DEFAULT = -1;
    public static final int RESULT_FAILED = 0;
    public static final int RESULT_SUCCESS = 1;
    private Application mApplication;
    private MediatorLiveData<Integer> mCommitResultLiveData;
    private ConnectivityManager mConnectivityManager;
    private int mCurrentPage;
    private DataRepository mDataRepository;
    private MediatorLiveData<Integer> mFeedbackBeanLiveData;
    private List<FeedbackBean> mFeedbackBeans;
    private FeedbackManager mFeedbackManager;
    boolean mIsDownloading;
    private boolean mIsNoData;
    private LiveData<Resource<XpApiResponse<List<ResponseHistory>>>> mLiveData;
    ConnectivityManager.NetworkCallback mNetworkCallback;
    private MutableLiveData<Boolean> mNetworkChangeLiveData;

    public FeedbackViewModel(Application application) {
        super(application);
        this.mFeedbackBeanLiveData = new MediatorLiveData<>();
        this.mFeedbackBeans = new CopyOnWriteArrayList();
        this.mDataRepository = DataRepository.getInstance();
        this.mCurrentPage = 1;
        this.mNetworkChangeLiveData = new MutableLiveData<>();
        this.mCommitResultLiveData = new MediatorLiveData<>();
        this.mIsDownloading = false;
        this.mIsNoData = false;
        this.mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.xiaopeng.car.settingslibrary.vm.feedback.FeedbackViewModel.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                FeedbackViewModel.this.mNetworkChangeLiveData.postValue(true);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                FeedbackViewModel.this.mNetworkChangeLiveData.postValue(false);
            }
        };
        this.mApplication = application;
        this.mFeedbackManager = FeedbackManager.getInstance();
        this.mConnectivityManager = (ConnectivityManager) this.mApplication.getSystemService(ConnectivityManager.class);
    }

    public MutableLiveData<Integer> getFeedbackBeanLiveData() {
        return this.mFeedbackBeanLiveData;
    }

    public MediatorLiveData<Integer> getCommitResultLiveData() {
        return this.mCommitResultLiveData;
    }

    public MutableLiveData<Boolean> getNetworkChangeLiveData() {
        return this.mNetworkChangeLiveData;
    }

    public void onStartVM() {
        this.mCommitResultLiveData.postValue(-1);
        this.mConnectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), this.mNetworkCallback);
    }

    public void onStopVM() {
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
    }

    public void uploadFeedbackContent(String str, String str2) {
        Logs.d("xpfeedback upload type:" + str);
        requestCreateNetFeedback(str, str2);
    }

    public boolean isNoData() {
        return this.mIsNoData;
    }

    public void resetCurrentPage() {
        this.mCurrentPage = 1;
        this.mIsNoData = false;
    }

    public void downloadFeedbackList() {
        requestHistoryFeedback(this.mCurrentPage, 10);
    }

    public void saveLocalInputFeedback(String str) {
        this.mDataRepository.setFeedbackInputContent(this.mApplication, str);
    }

    public String getInputFeedback() {
        return this.mDataRepository.getFeedbackInputContent(this.mApplication);
    }

    public void deleteFeedback(FeedbackBean feedbackBean) {
        this.mFeedbackBeans.remove(feedbackBean);
    }

    private void requestCreateNetFeedback(String str, String str2) {
        final LiveData<Resource<XpApiResponse<Void>>> requestCreateFeedback = this.mFeedbackManager.requestCreateFeedback(str, str2);
        this.mCommitResultLiveData.addSource(requestCreateFeedback, new Observer() { // from class: com.xiaopeng.car.settingslibrary.vm.feedback.-$$Lambda$FeedbackViewModel$YnmzCr71gJkHp2FmRoS79gj2rWc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FeedbackViewModel.this.lambda$requestCreateNetFeedback$0$FeedbackViewModel(requestCreateFeedback, (Resource) obj);
            }
        });
    }

    public /* synthetic */ void lambda$requestCreateNetFeedback$0$FeedbackViewModel(LiveData liveData, Resource resource) {
        if (resource.status == Resource.Status.SUCCESS) {
            Logs.d("xpfeedback net success");
            if (isServerError((XpApiResponse) resource.data)) {
                this.mCommitResultLiveData.postValue(0);
            } else {
                this.mCommitResultLiveData.postValue(1);
            }
            this.mCommitResultLiveData.removeSource(liveData);
        } else if (resource.status == Resource.Status.LOADING) {
            Logs.d("xpfeedback net loading");
        } else if (resource.status == Resource.Status.ERROR) {
            this.mCommitResultLiveData.postValue(0);
            Logs.d("xpfeedback net errorMessage");
            this.mCommitResultLiveData.removeSource(liveData);
        }
    }

    private boolean isServerError(XpApiResponse xpApiResponse) {
        return xpApiResponse != null && "-1".equals(xpApiResponse.code);
    }

    public void requestHistoryFeedback(int i, int i2) {
        Logs.d("xpfeedback requestHistoryFeedback mIsDownloading:" + this.mIsDownloading + " nodata:" + this.mIsNoData + " page:" + i);
        if (this.mIsDownloading) {
            Logs.d("xpfeedback requestHistoryFeedback mIsDownloading return!!!");
        } else if (this.mIsNoData) {
            Logs.d("xpfeedback requestHistoryFeedback nodata return!!!");
            this.mFeedbackBeanLiveData.postValue(1);
        } else {
            this.mLiveData = this.mFeedbackManager.reviewFeedback(String.valueOf(i), String.valueOf(i2));
            this.mFeedbackBeanLiveData.addSource(this.mLiveData, new Observer() { // from class: com.xiaopeng.car.settingslibrary.vm.feedback.-$$Lambda$FeedbackViewModel$acMMsR_yzqJ_8BjCr-CW4V_kD2k
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    FeedbackViewModel.this.lambda$requestHistoryFeedback$1$FeedbackViewModel((Resource) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$requestHistoryFeedback$1$FeedbackViewModel(Resource resource) {
        if (resource.status == Resource.Status.SUCCESS) {
            final XpApiResponse xpApiResponse = (XpApiResponse) resource.data;
            if (isServerError(xpApiResponse)) {
                this.mFeedbackBeanLiveData.postValue(0);
            } else if (xpApiResponse != null) {
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.feedback.FeedbackViewModel.2
                    @Override // java.lang.Runnable
                    public void run() {
                        FeedbackViewModel.this.processData(xpApiResponse);
                    }
                });
            }
            this.mIsDownloading = false;
            Logs.d("xpfeedback net history success");
            this.mFeedbackBeanLiveData.removeSource(this.mLiveData);
        } else if (resource.status == Resource.Status.LOADING) {
            Logs.d("xpfeedback net history loading");
            this.mIsDownloading = true;
        } else if (resource.status == Resource.Status.ERROR) {
            Logs.d("xpfeedback net history errorMessage");
            this.mIsDownloading = false;
            this.mFeedbackBeanLiveData.postValue(0);
            this.mFeedbackBeanLiveData.removeSource(this.mLiveData);
        }
    }

    public void cancelRequestHistory() {
        Logs.d("xpfeedback cancelRequestHistory");
        this.mFeedbackBeanLiveData.removeSource(this.mLiveData);
        this.mIsDownloading = false;
    }

    public List<FeedbackBean> getFeedbackBeans() {
        return this.mFeedbackBeans;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processData(XpApiResponse xpApiResponse) {
        List<ResponseHistory> list = (List) xpApiResponse.data;
        if (list == null) {
            Logs.d("xpfeedback processData null !!!");
            this.mFeedbackBeanLiveData.postValue(0);
        } else if (list.size() == 0) {
            Logs.d("xpfeedback processData no data!!!");
            this.mIsNoData = true;
            this.mFeedbackBeanLiveData.postValue(1);
        } else {
            for (ResponseHistory responseHistory : list) {
                FeedbackBean feedbackBean = new FeedbackBean();
                feedbackBean.setContent(TextUtils.isEmpty(responseHistory.getContent()) ? "" : responseHistory.getContent());
                feedbackBean.setCreateTime(responseHistory.getCreateTime());
                feedbackBean.setCategory(TextUtils.isEmpty(responseHistory.getType()) ? "0" : responseHistory.getType());
                feedbackBean.setRecordFilePath(responseHistory.getRecordFilePath());
                feedbackBean.setStatus(responseHistory.getStatus());
                feedbackBean.setHasRead(responseHistory.getHasRead());
                feedbackBean.setAnswer(responseHistory.getAnswer());
                feedbackBean.setAnswerTime(responseHistory.getAnswerTime());
                feedbackBean.setUpdateTime(feedbackBean.getCreateTime().compareTo(feedbackBean.getAnswerTime()) > 0 ? feedbackBean.getCreateTime() : feedbackBean.getAnswerTime());
                if (!Utils.isUserRelease()) {
                    Logs.d("xpfeedback bean:" + feedbackBean);
                }
                this.mFeedbackBeans.add(feedbackBean);
            }
            if (list.size() < 10) {
                this.mIsNoData = true;
            }
            Collections.sort(this.mFeedbackBeans, new SortFeedbackComparator());
            this.mFeedbackBeanLiveData.postValue(1);
            this.mCurrentPage++;
        }
    }

    public void clearHistory() {
        Logs.d("xpfeedback clearHistory");
        this.mFeedbackBeans.clear();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class SortFeedbackComparator implements Comparator<FeedbackBean> {
        SortFeedbackComparator() {
        }

        @Override // java.util.Comparator
        public int compare(FeedbackBean feedbackBean, FeedbackBean feedbackBean2) {
            String updateTime = feedbackBean.getUpdateTime();
            String updateTime2 = feedbackBean2.getUpdateTime();
            if (TextUtils.isEmpty(updateTime)) {
                updateTime = "";
            }
            if (TextUtils.isEmpty(updateTime2)) {
                updateTime2 = "";
            }
            return updateTime2.compareTo(updateTime);
        }
    }
}
