package com.xiaopeng.car.settingslibrary.vm.download;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.common.utils.TimeConsumingLog;
import com.xiaopeng.car.settingslibrary.manager.download.DownLoadMonitorManager;
import com.xiaopeng.car.settingslibrary.manager.download.DownLoadNotificationBean;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class DownLoadMonitorViewModel extends ViewModel implements LifecycleObserver, DownLoadMonitorManager.OnDataChangeCallBack {
    private MutableLiveData<List<DownLoadNotificationBean>> mLiveDataAll = new MutableLiveData<>();
    private MutableLiveData<DownLoadNotificationBean> mLiveDataItemChange = new MutableLiveData<>();
    private SortByStatusAndTime mSortByStatusAndTime = new SortByStatusAndTime();

    public MutableLiveData<List<DownLoadNotificationBean>> getData() {
        return this.mLiveDataAll;
    }

    public MutableLiveData<DownLoadNotificationBean> getItemChange() {
        return this.mLiveDataItemChange;
    }

    private void load() {
        TimeConsumingLog.get().start("DownLoadMonitorViewModel-load");
        ArrayList<DownLoadNotificationBean> load = DownLoadMonitorManager.get().load();
        load.sort(this.mSortByStatusAndTime);
        this.mLiveDataAll.postValue(load);
        TimeConsumingLog.get().end("DownLoadMonitorViewModel-load");
    }

    public void onResume() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.download.-$$Lambda$DownLoadMonitorViewModel$xLgA31y5oYNHGnABBHfF8i4YUhQ
            @Override // java.lang.Runnable
            public final void run() {
                DownLoadMonitorViewModel.this.lambda$onResume$0$DownLoadMonitorViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$onResume$0$DownLoadMonitorViewModel() {
        load();
        DownLoadMonitorManager.get().startMonitor(this);
    }

    public void onPause() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.vm.download.-$$Lambda$DownLoadMonitorViewModel$tHYAmrhrGYwRv1T1SntntcSr4SQ
            @Override // java.lang.Runnable
            public final void run() {
                DownLoadMonitorViewModel.this.lambda$onPause$1$DownLoadMonitorViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$onPause$1$DownLoadMonitorViewModel() {
        DownLoadMonitorManager.get().stopMonitor(this);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.download.DownLoadMonitorManager.OnDataChangeCallBack
    public void onPost(DownLoadNotificationBean downLoadNotificationBean) {
        Logs.log("xpdownload-ViewModel", "onPost-:" + downLoadNotificationBean.toString());
        List<DownLoadNotificationBean> value = this.mLiveDataAll.getValue();
        if (value != null) {
            int indexOf = value.indexOf(downLoadNotificationBean);
            if (indexOf > -1) {
                DownLoadNotificationBean downLoadNotificationBean2 = value.get(indexOf);
                downLoadNotificationBean.setStartTimeString(downLoadNotificationBean2.getStartTimeString());
                downLoadNotificationBean.setStartTime(downLoadNotificationBean2.getStartTime());
                value.set(indexOf, downLoadNotificationBean);
                if (ThreadUtils.isMainThread()) {
                    this.mLiveDataItemChange.setValue(downLoadNotificationBean);
                } else {
                    this.mLiveDataItemChange.postValue(downLoadNotificationBean);
                }
            } else {
                value.add(downLoadNotificationBean);
            }
            value.sort(this.mSortByStatusAndTime);
            if (ThreadUtils.isMainThread()) {
                this.mLiveDataAll.setValue(value);
            } else {
                this.mLiveDataAll.postValue(value);
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.download.DownLoadMonitorManager.OnDataChangeCallBack
    public void onRemove(String str, DownLoadNotificationBean downLoadNotificationBean) {
        Logs.log("xpdownload-ViewModel", "onRemove-:" + str);
        List<DownLoadNotificationBean> value = this.mLiveDataAll.getValue();
        if (value != null) {
            Iterator<DownLoadNotificationBean> it = value.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DownLoadNotificationBean next = it.next();
                if (next.getKey().equals(str)) {
                    value.remove(next);
                    break;
                }
            }
            this.mLiveDataAll.setValue(value);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class SortByStatusAndTime implements Comparator<DownLoadNotificationBean> {
        SortByStatusAndTime() {
        }

        @Override // java.util.Comparator
        public int compare(DownLoadNotificationBean downLoadNotificationBean, DownLoadNotificationBean downLoadNotificationBean2) {
            if (downLoadNotificationBean.getStatus() == downLoadNotificationBean2.getStatus()) {
                return downLoadNotificationBean.getStartTime() > downLoadNotificationBean2.getStartTime() ? -1 : 1;
            } else if (downLoadNotificationBean.getStatus() == 4) {
                return -1;
            } else {
                return (downLoadNotificationBean2.getStatus() != 4 && downLoadNotificationBean.getStartTime() > downLoadNotificationBean2.getStartTime()) ? -1 : 1;
            }
        }
    }
}
