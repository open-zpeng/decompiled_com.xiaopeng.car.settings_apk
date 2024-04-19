package com.xiaopeng.car.settingslibrary.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.FileUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack;
import com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager;
import com.xiaopeng.car.settingslibrary.ui.adapter.StorageListAdapter;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.common.AppStorageBean;
import com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageAppDetailDialog;
import com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageLoadingDialog;
import com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageSysAppDetailDialog;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsInfo;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsViewModel;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XLoading;
import com.xiaopeng.xui.widget.XProgressBar;
import com.xiaopeng.xui.widget.XRecyclerView;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.XTitleBarLight;
import com.xiaopeng.xvs.xid.utils.ResourceUtils;
import java.text.NumberFormat;
/* loaded from: classes.dex */
public class StorageView extends XConstraintLayout implements StorageOptionCallBack {
    long allCacheSize;
    XConstraintLayout cl_cache_item;
    Context context;
    private boolean isInitAppList;
    private boolean isInitCache;
    private boolean isInitStoragePercent;
    XLoading l_cache_item;
    XLoading l_storage_list_loading_view;
    XLoading l_storage_usage_loading_view;
    LifecycleOwner lifecycleOwner;
    StorageSettingsViewModel mStorageSettingsViewModel;
    OnStorageShowListener onStorageShowListener;
    int progress;
    XRecyclerView rv_storage_list;
    String source;
    StorageAppDetailDialog storageAppDetailDialog;
    StorageListAdapter storageListAdapter;
    StorageSysAppDetailDialog storageSysAppDetailDialog;
    XTextView tv_storage_size;
    XTextView tv_used_storage_percent;
    XProgressBar view_free_space;
    StorageLoadingDialog xLoadingDialog;
    XTitleBarLight x_title_bar;

    /* loaded from: classes.dex */
    public interface OnStorageShowListener {
        void onHandleDismiss();
    }

    public StorageView(Context context) {
        this(context, null);
    }

    public StorageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StorageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isInitStoragePercent = false;
        this.isInitAppList = false;
        this.isInitCache = false;
        if (CarFunction.isXmartOS5()) {
            LayoutInflater.from(context).inflate(R.layout.activity_view_storage, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.dialog_view_storage, this);
        }
        this.context = context;
        this.mStorageSettingsViewModel = new StorageSettingsViewModel(CarSettingsApp.getApp());
    }

    public void setOnStorageShowListener(OnStorageShowListener onStorageShowListener) {
        this.onStorageShowListener = onStorageShowListener;
    }

    public void init(String str, LifecycleOwner lifecycleOwner) {
        this.source = str;
        this.lifecycleOwner = lifecycleOwner;
        if (isInEditMode()) {
            return;
        }
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        this.l_storage_usage_loading_view = (XLoading) findViewById(R.id.l_storage_usage_loading_view);
        this.tv_used_storage_percent = (XTextView) findViewById(R.id.tv_used_storage_percent);
        this.view_free_space = (XProgressBar) findViewById(R.id.view_free_space);
        this.rv_storage_list = (XRecyclerView) findViewById(R.id.rv_storage_list);
        this.x_title_bar = (XTitleBarLight) findViewById(R.id.x_title_bar);
        this.tv_storage_size = (XTextView) findViewById(R.id.tv_storage_size);
        this.cl_cache_item = (XConstraintLayout) findViewById(R.id.cl_cache_item);
        this.l_storage_list_loading_view = (XLoading) findViewById(R.id.l_storage_list_loading_view);
        this.l_cache_item = (XLoading) findViewById(R.id.l_cache_item);
        this.l_storage_list_loading_view.setVisibility(0);
        this.l_cache_item.setVisibility(0);
    }

    private void initData() {
        this.storageListAdapter = new StorageListAdapter();
        this.rv_storage_list.setAdapter(this.storageListAdapter);
        this.mStorageSettingsViewModel.getStorageSettingsInfoLiveData().observe(this.lifecycleOwner, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$StorageView$7MoWaL1zxtOE50sa-JW1ZKi2Skw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                StorageView.this.lambda$initData$1$StorageView((StorageSettingsInfo) obj);
            }
        });
        this.mStorageSettingsViewModel.isAppsInfoLoaded().observe(this.lifecycleOwner, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$StorageView$MhI_2xK_KBa3b5qhwY2ckfKaGAk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                StorageView.this.lambda$initData$3$StorageView((Boolean) obj);
            }
        });
        this.mStorageSettingsViewModel.getAllCacheSize().observe(this.lifecycleOwner, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$StorageView$3CLfgk8kCtJgsKQ7SqUveNGVZKM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                StorageView.this.lambda$initData$5$StorageView((Long) obj);
            }
        });
        this.mStorageSettingsViewModel.startVm();
        this.mStorageSettingsViewModel.getAppsInfo();
        this.mStorageSettingsViewModel.computeFilesBackground();
    }

    public /* synthetic */ void lambda$initData$1$StorageView(StorageSettingsInfo storageSettingsInfo) {
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$StorageView$kai94_7hJxJEtYb066mi6Ojh4e0
            @Override // java.lang.Runnable
            public final void run() {
                StorageView.this.lambda$null$0$StorageView();
            }
        }, 500L);
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(0);
        this.tv_used_storage_percent.setText(ResourceUtils.getString(R.string.about_clean_space_used, percentInstance.format((((float) storageSettingsInfo.getUsedSize()) * 1.0f) / ((float) storageSettingsInfo.getTotalSize()))));
        this.progress = Math.round((((float) storageSettingsInfo.getUsedSize()) * 100.0f) / ((float) storageSettingsInfo.getTotalSize()));
        this.view_free_space.setProgress(this.progress);
        if (this.isInitStoragePercent) {
            return;
        }
        this.isInitStoragePercent = true;
        if (this.isInitAppList && this.isInitCache) {
            this.mStorageSettingsViewModel.uploadDataLog(this.source, this.progress, this.storageListAdapter.getData(), this.allCacheSize);
        }
    }

    public /* synthetic */ void lambda$null$0$StorageView() {
        this.l_storage_usage_loading_view.setVisibility(8);
        this.view_free_space.setVisibility(0);
        this.tv_used_storage_percent.setVisibility(0);
    }

    public /* synthetic */ void lambda$initData$3$StorageView(Boolean bool) {
        if (!bool.booleanValue() || this.mStorageSettingsViewModel.getAppStorageBeans().size() == 0) {
            return;
        }
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$StorageView$NR4gSivORKBDk0sKKVwWdYg0XeI
            @Override // java.lang.Runnable
            public final void run() {
                StorageView.this.lambda$null$2$StorageView();
            }
        }, 500L);
        this.storageListAdapter.lambda$setData$1$BaseAdapter(this.mStorageSettingsViewModel.getAppStorageBeans());
        if (this.isInitAppList) {
            return;
        }
        this.isInitAppList = true;
        if (this.isInitStoragePercent && this.isInitCache) {
            this.mStorageSettingsViewModel.uploadDataLog(this.source, this.progress, this.storageListAdapter.getData(), this.allCacheSize);
        }
    }

    public /* synthetic */ void lambda$null$2$StorageView() {
        this.l_storage_list_loading_view.setVisibility(8);
        this.rv_storage_list.setVisibility(0);
    }

    public /* synthetic */ void lambda$initData$5$StorageView(Long l) {
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$StorageView$0RzuRC-eECfbp4r_Zgsv-7YWkus
            @Override // java.lang.Runnable
            public final void run() {
                StorageView.this.lambda$null$4$StorageView();
            }
        }, 500L);
        this.allCacheSize = l.longValue();
        this.tv_storage_size.setText(FileUtils.getFileSizeDescription(l.longValue()));
        if (this.isInitCache) {
            return;
        }
        this.isInitCache = true;
        if (this.isInitStoragePercent && this.isInitAppList) {
            this.mStorageSettingsViewModel.uploadDataLog(this.source, this.progress, this.storageListAdapter.getData(), l.longValue());
        }
    }

    public /* synthetic */ void lambda$null$4$StorageView() {
        this.l_cache_item.setVisibility(8);
        this.cl_cache_item.setVisibility(0);
    }

    private void initEvent() {
        this.storageListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$StorageView$LnM4GhBugPW1AdBIW3-18MhG-KU
            @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter.OnItemClickListener
            public final void onItemClick(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, Object obj) {
                StorageView.this.lambda$initEvent$6$StorageView(baseAdapter, viewHolder, (AppStorageBean) obj);
            }
        });
        this.x_title_bar.setOnTitleBarClickListener(new XTitleBarLight.OnTitleBarClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.StorageView.1
            @Override // com.xiaopeng.xui.widget.XTitleBarLight.OnTitleBarClickListener
            public void onTitleBarCloseClick() {
                StorageView.this.onStorageShowListener.onHandleDismiss();
            }

            @Override // com.xiaopeng.xui.widget.XTitleBarLight.OnTitleBarClickListener
            public void onTitleBarBackClick() {
                StorageView.this.onStorageShowListener.onHandleDismiss();
            }
        });
        this.cl_cache_item.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.view.-$$Lambda$StorageView$pW2_g9UxjdI_lrWVS8HCIO6qLo4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StorageView.this.lambda$initEvent$7$StorageView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initEvent$6$StorageView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, AppStorageBean appStorageBean) {
        if (XpAboutManager.isSystemApp(appStorageBean.getPackageName())) {
            this.storageSysAppDetailDialog = new StorageSysAppDetailDialog(this.context, appStorageBean, this.mStorageSettingsViewModel);
            this.storageSysAppDetailDialog.setStorageOptionCallBack(this);
            this.storageSysAppDetailDialog.show();
            return;
        }
        this.storageAppDetailDialog = new StorageAppDetailDialog(this.context, appStorageBean, this.mStorageSettingsViewModel);
        this.storageAppDetailDialog.setStorageOptionCallBack(this);
        this.storageAppDetailDialog.show();
    }

    public /* synthetic */ void lambda$initEvent$7$StorageView(View view) {
        this.storageSysAppDetailDialog = new StorageSysAppDetailDialog(this.context, this.mStorageSettingsViewModel.getCacheStorageBean(), this.mStorageSettingsViewModel);
        this.storageSysAppDetailDialog.setStorageOptionCallBack(this);
        this.storageSysAppDetailDialog.show();
    }

    public void onShowUI() {
        AppServerManager.getInstance().addPopupDialogShowing(Config.POPUP_STORAGE, 0);
    }

    public void onDismissUI() {
        AppServerManager.getInstance().removePopupDialogShowing(Config.POPUP_STORAGE);
        this.mStorageSettingsViewModel.stopVm();
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack
    public void onStartUnInstall(String str) {
        Logs.d("onStartUnInstall " + str);
        Context context = this.context;
        this.xLoadingDialog = StorageLoadingDialog.show(context, context.getString(R.string.clean_settings_doing2));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack
    public void onFinishUnInstall(String str) {
        Logs.d("onFinishUnInstall " + str);
        int positionByPkgName = this.storageListAdapter.getPositionByPkgName(str);
        XpAboutManager.sendAppDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B015", this.storageListAdapter.getData().get(positionByPkgName).getName(), Long.valueOf(FileUtils.parseByte2KB(this.storageListAdapter.getData().get(positionByPkgName).getTotalSize())));
        this.storageListAdapter.lambda$removeData$6$BaseAdapter(positionByPkgName);
        if (XpAboutManager.isSystemApp(str)) {
            StorageSysAppDetailDialog storageSysAppDetailDialog = this.storageSysAppDetailDialog;
            if (storageSysAppDetailDialog != null) {
                storageSysAppDetailDialog.dismiss();
            }
        } else {
            StorageAppDetailDialog storageAppDetailDialog = this.storageAppDetailDialog;
            if (storageAppDetailDialog != null) {
                storageAppDetailDialog.dismiss();
            }
        }
        this.xLoadingDialog.dismiss();
        ToastUtils.get().showText(R.string.clean_settings_app_uninstall_finish);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack
    public void onStartAppClean(String str) {
        Logs.d("onStartAppClean " + str);
        Context context = this.context;
        this.xLoadingDialog = StorageLoadingDialog.show(context, context.getString(R.string.clean_settings_doing1));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack
    public void onFinishAppClean(String str) {
        Logs.d("onStopAppClean " + str);
        this.xLoadingDialog.dismiss();
        if (XpAboutManager.isMusicApp(str)) {
            int positionByPkgName = this.storageListAdapter.getPositionByPkgName("music");
            long totalSize = this.storageListAdapter.getData().get(positionByPkgName).getTotalSize();
            this.mStorageSettingsViewModel.updateSysAppSize(this.storageListAdapter.getData().get(positionByPkgName), XpAboutManager.computeMusicAppSize());
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B010", BuriedPointUtils.SIZE_KEY, Long.valueOf(FileUtils.parseByte2KB(totalSize - this.storageListAdapter.getData().get(positionByPkgName).getTotalSize())));
            this.storageListAdapter.lambda$refreshItem$10$BaseAdapter(positionByPkgName);
            StorageSysAppDetailDialog storageSysAppDetailDialog = this.storageSysAppDetailDialog;
            if (storageSysAppDetailDialog != null) {
                storageSysAppDetailDialog.refreshData(this.storageListAdapter.getData().get(positionByPkgName));
            }
        } else if (XpAboutManager.isCameraApp(str)) {
            int positionByPkgName2 = this.storageListAdapter.getPositionByPkgName(str);
            long totalSize2 = this.storageListAdapter.getData().get(positionByPkgName2).getTotalSize();
            this.mStorageSettingsViewModel.updateSysAppSize(this.storageListAdapter.getData().get(positionByPkgName2), XpAboutManager.computeCameraAppSize());
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B007", BuriedPointUtils.SIZE_KEY, Long.valueOf(FileUtils.parseByte2KB(totalSize2 - this.storageListAdapter.getData().get(positionByPkgName2).getTotalSize())));
            this.storageListAdapter.lambda$refreshItem$10$BaseAdapter(positionByPkgName2);
            StorageSysAppDetailDialog storageSysAppDetailDialog2 = this.storageSysAppDetailDialog;
            if (storageSysAppDetailDialog2 != null) {
                storageSysAppDetailDialog2.refreshData(this.storageListAdapter.getData().get(positionByPkgName2));
            }
        } else {
            int positionByPkgName3 = this.storageListAdapter.getPositionByPkgName(str);
            long totalSize3 = this.storageListAdapter.getData().get(positionByPkgName3).getTotalSize();
            this.mStorageSettingsViewModel.updateCleanedAppSize(this.storageListAdapter.getData().get(positionByPkgName3));
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B013", BuriedPointUtils.SIZE_KEY, Long.valueOf(FileUtils.parseByte2KB(totalSize3 - this.storageListAdapter.getData().get(positionByPkgName3).getTotalSize())));
            this.storageListAdapter.lambda$refreshItem$10$BaseAdapter(positionByPkgName3);
            StorageAppDetailDialog storageAppDetailDialog = this.storageAppDetailDialog;
            if (storageAppDetailDialog != null) {
                storageAppDetailDialog.refreshData(this.storageListAdapter.getData().get(positionByPkgName3));
            }
        }
        ToastUtils.get().showText(R.string.clean_settings_app_clean_finish);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack
    public void onStartAllCacheClean() {
        Logs.d("onStartAllCacheClean ");
        Context context = this.context;
        this.xLoadingDialog = StorageLoadingDialog.show(context, context.getString(R.string.clean_settings_doing1));
        XpAboutManager.getInstance().disableSpeech(ResourceUtils.getString(R.string.clean_tts, new Object[0]), ResourceUtils.getString(R.string.clean_tts_tips1, new Object[0]));
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.about.StorageOptionCallBack
    public void onFinishAllCacheClean() {
        Logs.d("onStopAllCacheClean");
        this.xLoadingDialog.dismiss();
        XpAboutManager.getInstance().wakeupSpeech(ResourceUtils.getString(R.string.clean_tts, new Object[0]));
        StorageSysAppDetailDialog storageSysAppDetailDialog = this.storageSysAppDetailDialog;
        if (storageSysAppDetailDialog != null) {
            storageSysAppDetailDialog.refreshData(this.mStorageSettingsViewModel.getCacheStorageBean());
        }
        BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, "B004");
        ToastUtils.get().showText(R.string.storage_clean_cache_finish);
    }
}
