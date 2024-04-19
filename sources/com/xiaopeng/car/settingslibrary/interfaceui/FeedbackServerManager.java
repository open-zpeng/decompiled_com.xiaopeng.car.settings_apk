package com.xiaopeng.car.settingslibrary.interfaceui;

import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.FeedbackBean;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.vm.feedback.FeedbackViewModel;
import java.util.List;
/* loaded from: classes.dex */
public class FeedbackServerManager extends ServerBaseManager {
    private static final String TAG = "FeedbackServerManager";
    private FeedbackViewModel mFeedbackViewModel;

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final FeedbackServerManager instance = new FeedbackServerManager();

        private InnerFactory() {
        }
    }

    public static FeedbackServerManager getInstance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getCommitResultLiveData().setValue(-2);
        getViewModel().getCommitResultLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$FeedbackServerManager$yYllCKpThrsXaztkTWDICN0kTdw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FeedbackServerManager.this.lambda$observeData$0$FeedbackServerManager((Integer) obj);
            }
        });
        getViewModel().getFeedbackBeanLiveData().setValue(-1);
        getViewModel().getFeedbackBeanLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$FeedbackServerManager$Lsu0t34MhTDn6e6yYOVzCf0Tje8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FeedbackServerManager.this.lambda$observeData$1$FeedbackServerManager((Integer) obj);
            }
        });
        getViewModel().getNetworkChangeLiveData().setValue(null);
        getViewModel().getNetworkChangeLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$FeedbackServerManager$XAGxTQ9yvQN-V5lc0Ai9BLdRdtk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FeedbackServerManager.this.lambda$observeData$2$FeedbackServerManager((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$FeedbackServerManager(Integer num) {
        if (num.intValue() == -2) {
            return;
        }
        debugLog("FeedbackServerManager onFeedbackCommitResult " + num);
        feedbackCallback(InterfaceConstant.ON_FEEDBACK_COMMIT_RESULT, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$1$FeedbackServerManager(Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        debugLog("FeedbackServerManager onFeedbackResult " + num);
        feedbackCallback(InterfaceConstant.ON_FEEDBACK_RESULT, String.valueOf(num));
    }

    public /* synthetic */ void lambda$observeData$2$FeedbackServerManager(Boolean bool) {
        if (bool == null) {
            return;
        }
        feedbackCallback(InterfaceConstant.ON_NETWORK_CHANGED, String.valueOf(bool));
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
    }

    private synchronized FeedbackViewModel getViewModel() {
        if (this.mFeedbackViewModel == null) {
            this.mFeedbackViewModel = new FeedbackViewModel(CarSettingsApp.getApp());
        }
        return this.mFeedbackViewModel;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
        log("FeedbackServerManager startVm");
        getViewModel().onStartVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
        log("FeedbackServerManager stopVm");
        getViewModel().onStopVM();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    public List<FeedbackBean> getFeedbackBeans() {
        log("FeedbackServerManager getFeedbackBeans ");
        return getViewModel().getFeedbackBeans();
    }

    public boolean isNoData() {
        log("FeedbackServerManager isNoData ");
        return getViewModel().isNoData();
    }

    public void saveLocalInputFeedback(String str) {
        log("FeedbackServerManager saveLocalInputFeedback content:" + str);
        getViewModel().saveLocalInputFeedback(str);
    }

    public String getInputFeedback() {
        log("FeedbackServerManager getInputFeedback ");
        return getViewModel().getInputFeedback();
    }

    public void uploadFeedbackContent(final String str, final String str2) {
        log("FeedbackServerManager uploadFeedbackContent type:" + str + " content:" + str2);
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$FeedbackServerManager$KleHYzBv9fCRFoIzmJeoEa2cFms
            @Override // java.lang.Runnable
            public final void run() {
                FeedbackServerManager.this.lambda$uploadFeedbackContent$3$FeedbackServerManager(str, str2);
            }
        });
    }

    public /* synthetic */ void lambda$uploadFeedbackContent$3$FeedbackServerManager(String str, String str2) {
        getViewModel().uploadFeedbackContent(str, str2);
    }

    public void clearHistory() {
        log("FeedbackServerManager clearHistory ");
        getViewModel().clearHistory();
    }

    public void resetCurrentPage() {
        log("FeedbackServerManager resetCurrentPage ");
        getViewModel().resetCurrentPage();
    }

    public void downloadFeedbackList() {
        log("FeedbackServerManager downloadFeedbackList ");
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$FeedbackServerManager$7Lxsa4oNwh0Hm1y75phYCX5bIEU
            @Override // java.lang.Runnable
            public final void run() {
                FeedbackServerManager.this.lambda$downloadFeedbackList$4$FeedbackServerManager();
            }
        });
    }

    public /* synthetic */ void lambda$downloadFeedbackList$4$FeedbackServerManager() {
        getViewModel().downloadFeedbackList();
    }

    public void cancelRequestHistory() {
        log("FeedbackServerManager cancelRequestHistory ");
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$FeedbackServerManager$yESDG1F4Pg8QlwtfsCK82smJksc
            @Override // java.lang.Runnable
            public final void run() {
                FeedbackServerManager.this.lambda$cancelRequestHistory$5$FeedbackServerManager();
            }
        });
    }

    public /* synthetic */ void lambda$cancelRequestHistory$5$FeedbackServerManager() {
        getViewModel().cancelRequestHistory();
    }
}
