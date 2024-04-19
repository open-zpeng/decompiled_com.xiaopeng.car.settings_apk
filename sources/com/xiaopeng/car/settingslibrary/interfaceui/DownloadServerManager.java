package com.xiaopeng.car.settingslibrary.interfaceui;

import android.app.PendingIntent;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.common.utils.JsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.DownLoadBean;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.car.settingslibrary.manager.download.DownLoadNotificationBean;
import com.xiaopeng.car.settingslibrary.vm.download.DownLoadMonitorViewModel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public class DownloadServerManager extends ServerBaseManager {
    private static final String TAG = "DownloadServerManager";
    private DownLoadMonitorViewModel mDownLoadMonitorViewModel;
    private List<DownLoadNotificationBean> mList = new CopyOnWriteArrayList();

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void startVm() {
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void stopVm() {
    }

    /* loaded from: classes.dex */
    private static class InnerFactory {
        private static final DownloadServerManager instance = new DownloadServerManager();

        private InnerFactory() {
        }
    }

    public static DownloadServerManager getInstance() {
        return InnerFactory.instance;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void observeData() {
        getViewModel().getData().setValue(null);
        getViewModel().getData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DownloadServerManager$dXxaKmVMz96K9QU0EUMkWr4GUL8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DownloadServerManager.this.lambda$observeData$0$DownloadServerManager((List) obj);
            }
        });
        getViewModel().getItemChange().setValue(null);
        getViewModel().getItemChange().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.interfaceui.-$$Lambda$DownloadServerManager$foNGE_F6oy8ch7c7UcAgKUpYQk8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DownloadServerManager.this.lambda$observeData$1$DownloadServerManager((DownLoadNotificationBean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$observeData$0$DownloadServerManager(List list) {
        if (list == null) {
            return;
        }
        this.mList.clear();
        this.mList.addAll(list);
        debugLog("DownloadServerManager onChanged");
        downloadCallback(InterfaceConstant.ON_CHANGED, JsonUtils.toJSONString(convertToBeanList(list)));
    }

    public /* synthetic */ void lambda$observeData$1$DownloadServerManager(DownLoadNotificationBean downLoadNotificationBean) {
        if (downLoadNotificationBean == null) {
            return;
        }
        debugLog("DownloadServerManager onItemChange");
        downloadCallback(InterfaceConstant.ON_DOWNLOAD_ITEM_CHANGED, JsonUtils.toJSONString(convertToBean(downLoadNotificationBean)));
    }

    private DownLoadBean convertToBean(DownLoadNotificationBean downLoadNotificationBean) {
        DownLoadBean downLoadBean = new DownLoadBean();
        downLoadBean.setActionTitle(downLoadNotificationBean.getActionTitle());
        downLoadBean.setButtonStatus(downLoadNotificationBean.getButtonStatus());
        downLoadBean.setIconUrl(downLoadNotificationBean.getIconUrl());
        downLoadBean.setId(downLoadNotificationBean.getId());
        downLoadBean.setKey(downLoadNotificationBean.getKey());
        downLoadBean.setProgress(downLoadNotificationBean.getProgress());
        downLoadBean.setProgressMax(downLoadNotificationBean.getProgressMax());
        downLoadBean.setRemainingTime(downLoadNotificationBean.getRemainingTime());
        downLoadBean.setSize(downLoadNotificationBean.getSize());
        downLoadBean.setSizeString(downLoadNotificationBean.getSizeString());
        downLoadBean.setStartTime(downLoadNotificationBean.getStartTime());
        downLoadBean.setStartTimeString(downLoadNotificationBean.getStartTimeString());
        downLoadBean.setStatus(downLoadNotificationBean.getStatus());
        downLoadBean.setStatusDesc(downLoadNotificationBean.getStatusDesc());
        downLoadBean.setTitle(downLoadNotificationBean.getTitle());
        return downLoadBean;
    }

    private CopyOnWriteArrayList<DownLoadBean> convertToBeanList(List<DownLoadNotificationBean> list) {
        CopyOnWriteArrayList<DownLoadBean> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        for (DownLoadNotificationBean downLoadNotificationBean : list) {
            DownLoadBean downLoadBean = new DownLoadBean();
            downLoadBean.setActionTitle(downLoadNotificationBean.getActionTitle());
            downLoadBean.setButtonStatus(downLoadNotificationBean.getButtonStatus());
            downLoadBean.setIconUrl(downLoadNotificationBean.getIconUrl());
            downLoadBean.setId(downLoadNotificationBean.getId());
            downLoadBean.setKey(downLoadNotificationBean.getKey());
            downLoadBean.setProgress(downLoadNotificationBean.getProgress());
            downLoadBean.setProgressMax(downLoadNotificationBean.getProgressMax());
            downLoadBean.setRemainingTime(downLoadNotificationBean.getRemainingTime());
            downLoadBean.setSize(downLoadNotificationBean.getSize());
            downLoadBean.setSizeString(downLoadNotificationBean.getSizeString());
            downLoadBean.setStartTime(downLoadNotificationBean.getStartTime());
            downLoadBean.setStartTimeString(downLoadNotificationBean.getStartTimeString());
            downLoadBean.setStatus(downLoadNotificationBean.getStatus());
            downLoadBean.setStatusDesc(downLoadNotificationBean.getStatusDesc());
            downLoadBean.setTitle(downLoadNotificationBean.getTitle());
            copyOnWriteArrayList.add(downLoadBean);
        }
        return copyOnWriteArrayList;
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    protected void init() {
        getViewModel();
    }

    private synchronized DownLoadMonitorViewModel getViewModel() {
        if (this.mDownLoadMonitorViewModel == null) {
            this.mDownLoadMonitorViewModel = new DownLoadMonitorViewModel();
        }
        return this.mDownLoadMonitorViewModel;
    }

    public void onResume() {
        log("DownloadServerManager onResume");
        getViewModel().onResume();
    }

    public void onPause() {
        log("DownloadServerManager onPause");
        getViewModel().onPause();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void enterScene() {
        super.enterScene();
    }

    @Override // com.xiaopeng.car.settingslibrary.interfaceui.ServerBaseManager
    public void exitScene() {
        super.exitScene();
    }

    private DownLoadNotificationBean getBeanByKey(String str) {
        for (DownLoadNotificationBean downLoadNotificationBean : this.mList) {
            if (downLoadNotificationBean.getKey().equals(str)) {
                return downLoadNotificationBean;
            }
        }
        return null;
    }

    public void pauseSend(String str) {
        log("DownloadServerManager pauseSend key:" + str);
        DownLoadNotificationBean beanByKey = getBeanByKey(str);
        if (beanByKey == null) {
            Logs.d("DownloadServerManager pauseSend null");
        } else if (beanByKey.getPausePendingIntent() != null) {
            try {
                beanByKey.getPausePendingIntent().send();
                Logs.log("xpdownload-fragment-onClick", "pause " + beanByKey.getPausePendingIntent().getIntent());
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else {
            Logs.log("xpdownload-fragment-onClick", "pause null action");
        }
    }

    public void resumeSend(String str) {
        log("DownloadServerManager resumeSend key:" + str);
        DownLoadNotificationBean beanByKey = getBeanByKey(str);
        if (beanByKey == null) {
            Logs.d("DownloadServerManager resumeSend null");
        } else if (beanByKey.getResumePendingIntent() != null) {
            try {
                beanByKey.getResumePendingIntent().send();
                Logs.log("xpdownload-fragment-onClick", "resume " + beanByKey.getResumePendingIntent().getIntent());
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else {
            Logs.log("xpdownload-fragment-onClick", "resume null action");
        }
    }

    public void retrySend(String str) {
        log("DownloadServerManager retrySend key:" + str);
        DownLoadNotificationBean beanByKey = getBeanByKey(str);
        if (beanByKey == null) {
            Logs.d("DownloadServerManager retrySend null");
        } else if (beanByKey.getRetryPendingIntent() != null) {
            try {
                beanByKey.getRetryPendingIntent().send();
                Logs.log("xpdownload-fragment-onClick", "retry " + beanByKey.getRetryPendingIntent().getIntent());
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else {
            Logs.log("xpdownload-fragment-onClick", "retry null action");
        }
    }

    public void successSend(String str) {
        log("DownloadServerManager successSend key:" + str);
        DownLoadNotificationBean beanByKey = getBeanByKey(str);
        if (beanByKey == null) {
            Logs.d("DownloadServerManager successSend null");
        } else if (beanByKey.getSuccessAction() != null && beanByKey.getSuccessAction().actionIntent != null) {
            try {
                beanByKey.getSuccessAction().actionIntent.send();
                Logs.log("xpdownload-fragment-onClick", "success " + beanByKey.getSuccessAction().actionIntent.getIntent());
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else {
            Logs.log("xpdownload-fragment-onClick", "success null action");
        }
    }

    public void cancelSend(String str) {
        log("DownloadServerManager cancelSend key:" + str);
        DownLoadNotificationBean beanByKey = getBeanByKey(str);
        if (beanByKey == null) {
            Logs.d("DownloadServerManager cancelSend null");
        } else if (beanByKey.getCancelPendingIntent() != null) {
            try {
                beanByKey.getCancelPendingIntent().send();
                Logs.log("xpdownload-fragment-onClick", "cancel " + beanByKey.getCancelPendingIntent().getIntent());
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else {
            Logs.log("xpdownload-fragment-onClick", "cancel null action");
        }
    }
}
