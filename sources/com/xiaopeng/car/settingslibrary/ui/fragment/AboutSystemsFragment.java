package com.xiaopeng.car.settingslibrary.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.IntervalControl;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ThreadUtils;
import com.xiaopeng.car.settingslibrary.service.UIGlobalService;
import com.xiaopeng.car.settingslibrary.service.work.RestoreCheckedDialogWork;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.ui.base.ViewModelUtils;
import com.xiaopeng.car.settingslibrary.ui.dialog.storage.StorageMainDialog;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsInfo;
import com.xiaopeng.car.settingslibrary.vm.about.StorageSettingsViewModel;
import com.xiaopeng.privacymanager.PrivacyServiceManager;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XLoadingDialog;
import com.xiaopeng.xui.utils.XDialogUtils;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XProgressBar;
import com.xiaopeng.xui.widget.XTextView;
import java.text.DecimalFormat;
/* loaded from: classes.dex */
public class AboutSystemsFragment extends BaseFragment {
    private static final int AGREEMENT = 3;
    private static final int CLEAN = 4;
    private static final int POLICY = 2;
    private static final int RESTORE = 1;
    private static final int STORAGE = 5;
    private XButton mAgreementBtn;
    private XButton mCleanBtn;
    private XDialog mCleanDialog;
    private XTextView mFreeSpaceTv;
    private XLoadingDialog mLoadingDialog;
    private AboutItemClickListener mOnClickListener;
    private XButton mPolicyBtn;
    private PrivacyServiceManager mPrivacyServiceManager;
    private XProgressBar mProgressBar;
    private XDialog mResetDialog;
    private XButton mRestoreBtn;
    private XButton mStorageBtn;
    private StorageSettingsViewModel mStorageSettingsViewModel;
    private String mProtocolId = "";
    private String mAgreementId = "";
    private final IntervalControl mClickIntervalControl = new IntervalControl("clean");

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.fragment_about_system;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mRootView = view;
        this.mOnClickListener = new AboutItemClickListener();
        XListSingle xListSingle = (XListSingle) view.findViewById(R.id.about_restore);
        this.mRestoreBtn = (XButton) xListSingle.findViewById(R.id.x_list_action_button);
        this.mRestoreBtn.setText(getString(R.string.about_execute));
        this.mRestoreBtn.setOnClickListener(this.mOnClickListener);
        XListSingle xListSingle2 = (XListSingle) view.findViewById(R.id.about_storage);
        this.mStorageBtn = (XButton) xListSingle2.findViewById(R.id.x_list_action_button);
        this.mStorageBtn.setText(getString(R.string.about_check));
        this.mStorageBtn.setOnClickListener(this.mOnClickListener);
        this.mProgressBar = (XProgressBar) view.findViewById(R.id.view_free_space);
        this.mFreeSpaceTv = (XTextView) view.findViewById(R.id.xtv_free_space);
        XListSingle xListSingle3 = (XListSingle) view.findViewById(R.id.about_clean);
        this.mCleanBtn = (XButton) xListSingle3.findViewById(R.id.x_list_action_button);
        this.mCleanBtn.setText(getString(R.string.about_execute));
        XButton xButton = this.mCleanBtn;
        xButton.setVuiLabel(getString(R.string.about_execute) + "|" + getString(R.string.about_execute) + "" + getString(R.string.about_clean));
        this.mCleanBtn.setOnClickListener(this.mOnClickListener);
        if (CarFunction.isSupportStorageManage()) {
            xListSingle2.setVisibility(0);
        } else if (CarFunction.isSupportOneKeyCleanCaches()) {
            view.findViewById(R.id.clean_layout).setVisibility(0);
        }
        XListSingle xListSingle4 = (XListSingle) view.findViewById(R.id.about_policy);
        this.mPolicyBtn = (XButton) xListSingle4.findViewById(R.id.x_list_action_button);
        this.mPolicyBtn.setText(getString(R.string.about_check));
        this.mPolicyBtn.setOnClickListener(this.mOnClickListener);
        XListSingle xListSingle5 = (XListSingle) view.findViewById(R.id.about_user_agreement);
        this.mAgreementBtn = (XButton) xListSingle5.findViewById(R.id.x_list_action_button);
        this.mAgreementBtn.setText(getString(R.string.about_check));
        this.mAgreementBtn.setOnClickListener(this.mOnClickListener);
        initVui(xListSingle, xListSingle4, xListSingle5, xListSingle3);
    }

    private void initVui(final XListSingle xListSingle, final XListSingle xListSingle2, final XListSingle xListSingle3, final XListSingle xListSingle4) {
        if (this.mVuiHandler != null) {
            this.mVuiHandler.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.AboutSystemsFragment.1
                @Override // java.lang.Runnable
                public void run() {
                    AboutSystemsFragment aboutSystemsFragment = AboutSystemsFragment.this;
                    aboutSystemsFragment.vuiChangeTarget(xListSingle, String.valueOf(aboutSystemsFragment.mRestoreBtn.getId()));
                    String str = xListSingle2.getId() + "_" + AboutSystemsFragment.this.mPolicyBtn.getId();
                    AboutSystemsFragment.this.mPolicyBtn.setVuiElementId(str);
                    AboutSystemsFragment.this.vuiChangeTarget(xListSingle2, str);
                    String str2 = xListSingle3.getId() + "_" + AboutSystemsFragment.this.mAgreementBtn.getId();
                    AboutSystemsFragment.this.mAgreementBtn.setVuiElementId(str2);
                    AboutSystemsFragment.this.vuiChangeTarget(xListSingle3, str2);
                    String str3 = xListSingle4.getId() + "_" + AboutSystemsFragment.this.mCleanBtn.getId();
                    AboutSystemsFragment.this.mCleanBtn.setVuiElementId(str3);
                    AboutSystemsFragment.this.vuiChangeTarget(xListSingle4, str3);
                }
            });
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
        this.mStorageSettingsViewModel = (StorageSettingsViewModel) ViewModelUtils.getViewModel(this, StorageSettingsViewModel.class);
        this.mStorageSettingsViewModel.getStorageSettingsInfoLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$AboutSystemsFragment$smpGXBYLCSriNHYCxgs-qlhIBX0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AboutSystemsFragment.this.lambda$init$0$AboutSystemsFragment((StorageSettingsInfo) obj);
            }
        });
        this.mStorageSettingsViewModel.getCleanFinishLiveData().observe(this, new Observer() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$AboutSystemsFragment$cySICWdxCeUyhJdLq1RxEr_khRc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AboutSystemsFragment.this.lambda$init$1$AboutSystemsFragment((Boolean) obj);
            }
        });
        this.mPrivacyServiceManager = new PrivacyServiceManager(CarSettingsApp.getContext());
        this.mPrivacyServiceManager.connect(null);
    }

    public /* synthetic */ void lambda$init$0$AboutSystemsFragment(StorageSettingsInfo storageSettingsInfo) {
        this.mFreeSpaceTv.setText(getString(R.string.about_clean_space_used, new DecimalFormat("00%").format((((float) storageSettingsInfo.getUsedSize()) * 1.0f) / ((float) storageSettingsInfo.getTotalSize()))));
        int round = Math.round((((float) storageSettingsInfo.getUsedSize()) * 100.0f) / ((float) storageSettingsInfo.getTotalSize()));
        this.mProgressBar.setProgressDrawable(getContext().getDrawable(storageSettingsInfo.getTotalSize() - storageSettingsInfo.getUsedSize() < OSSConstants.DEFAULT_FILE_SIZE_LIMIT ? R.drawable.bg_free_space_full : R.drawable.bg_free_space));
        this.mProgressBar.setProgress(round);
    }

    public /* synthetic */ void lambda$init$1$AboutSystemsFragment(Boolean bool) {
        Logs.d("xpabout clean data end " + bool);
        if (bool.booleanValue()) {
            XLoadingDialog xLoadingDialog = this.mLoadingDialog;
            if (xLoadingDialog != null) {
                xLoadingDialog.dismiss();
                this.mLoadingDialog = null;
            }
            ToastUtils.get().showText(R.string.clean_settings_done);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onItemClick(int i) {
        Logs.d("choose type = " + i);
        if (i == 1) {
            showCheckedInfoDialog();
        } else if (i == 2) {
            showDialog(101);
        } else if (i == 3) {
            showDialog(102);
        } else if (i != 4) {
            if (i != 5) {
                return;
            }
            showStorageManageDialog();
        } else if (!this.mStorageSettingsViewModel.isGearP()) {
            ToastUtils.get().showText(R.string.about_clean_p_toast);
        } else {
            showCleanDialog();
        }
    }

    private void showStorageManageDialog() {
        new StorageMainDialog(CarSettingsApp.getContext(), BuriedPointUtils.DisplaySource.TYPE_CLICK).show();
    }

    private void showCleanDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mCleanDialog == null) {
            this.mCleanDialog = new XDialog(getContext());
            this.mCleanDialog.setTitle(getString(R.string.clean_settings_title)).setMessage(getString(R.string.clean_settings_content)).setPositiveButton(getString(R.string.continue_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$AboutSystemsFragment$x3NzElWMl_gX2fpm10gBbS6Rwr8
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    AboutSystemsFragment.this.lambda$showCleanDialog$2$AboutSystemsFragment(xDialog, i);
                }
            }).setNegativeButton(getString(R.string.cancel));
            this.mCleanDialog.setOnCountDownListener(new XDialogInterface.OnCountDownListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.AboutSystemsFragment.2
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnCountDownListener
                public boolean onCountDown(XDialog xDialog, int i) {
                    AboutSystemsFragment.this.mCleanDialog.setPositiveButtonEnable(true);
                    return true;
                }
            });
        }
        this.mCleanDialog.setPositiveButtonEnable(false);
        this.mCleanDialog.show(10, 0);
        VuiManager.instance().initVuiDialog(this.mCleanDialog, getContext(), VuiManager.SCENE_CLEAN_DIALOG);
    }

    public /* synthetic */ void lambda$showCleanDialog$2$AboutSystemsFragment(XDialog xDialog, int i) {
        if (this.mClickIntervalControl.isFrequently(500)) {
            return;
        }
        Logs.d("xpabout clean data start ");
        showCleanLoadingDialog();
        this.mStorageSettingsViewModel.cleanFiles(getString(R.string.clean_tts), getString(R.string.clean_tts_tips));
    }

    private void showCleanLoadingDialog() {
        this.mLoadingDialog = new XLoadingDialog(getContext(), R.style.XAppTheme_XDialog_Loading);
        XDialogUtils.requestFullScreen(this.mLoadingDialog);
        this.mLoadingDialog.create();
        this.mLoadingDialog.setMessage(getString(R.string.clean_settings_doing));
        this.mLoadingDialog.setCancelable(false);
        this.mLoadingDialog.show();
    }

    private void showDialog(int i) {
        if (this.mPrivacyServiceManager.isConnected()) {
            String showPrivacyDialog = this.mPrivacyServiceManager.showPrivacyDialog(i, 0, null);
            if (i == 101) {
                this.mProtocolId = showPrivacyDialog;
                return;
            } else if (i == 102) {
                this.mAgreementId = showPrivacyDialog;
                return;
            } else {
                return;
            }
        }
        Logs.d("not connected, showDialog type:" + i);
    }

    private void showCheckedInfoDialog() {
        if (getContext() == null) {
            return;
        }
        if (this.mResetDialog == null) {
            this.mResetDialog = new XDialog(getContext());
            this.mResetDialog.setTitle(getString(R.string.restore_settings_title)).setMessage(getString(R.string.restore_init_title)).setPositiveButton(getString(R.string.continue_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$AboutSystemsFragment$1n5zkqHuJcGMF4NgEZxPhrEKAOo
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    AboutSystemsFragment.this.lambda$showCheckedInfoDialog$4$AboutSystemsFragment(xDialog, i);
                }
            }).setNegativeButton(getString(R.string.cancel));
        }
        this.mResetDialog.show();
        VuiManager.instance().initVuiDialog(this.mResetDialog, getContext(), VuiManager.SCENE_RESTORE_DIALOG);
    }

    public /* synthetic */ void lambda$showCheckedInfoDialog$4$AboutSystemsFragment(XDialog xDialog, int i) {
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$AboutSystemsFragment$gaBSGSPlFBHOmnh2lld9CoChuGE
            @Override // java.lang.Runnable
            public final void run() {
                AboutSystemsFragment.this.lambda$null$3$AboutSystemsFragment();
            }
        }, 50L);
    }

    public /* synthetic */ void lambda$null$3$AboutSystemsFragment() {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), UIGlobalService.class);
            intent.setAction(RestoreCheckedDialogWork.ACTION);
            getContext().startService(intent);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            refreshAboutData();
            return;
        }
        unRefreshAboutData();
        dismissDialog();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        refreshAboutData();
    }

    @Override // com.xiaopeng.car.settingslibrary.speech.VuiFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        unRefreshAboutData();
    }

    private void refreshAboutData() {
        this.mStorageSettingsViewModel.startVm();
    }

    private void unRefreshAboutData() {
        this.mStorageSettingsViewModel.stopVm();
    }

    private void dismissDialog() {
        XDialog xDialog = this.mCleanDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
        XLoadingDialog xLoadingDialog = this.mLoadingDialog;
        if (xLoadingDialog != null) {
            xLoadingDialog.dismiss();
        }
        XDialog xDialog2 = this.mResetDialog;
        if (xDialog2 != null) {
            xDialog2.dismiss();
        }
        if (!TextUtils.isEmpty(this.mAgreementId)) {
            this.mPrivacyServiceManager.dismissPrivacyDialog(this.mAgreementId);
            this.mAgreementId = null;
        }
        if (TextUtils.isEmpty(this.mProtocolId)) {
            return;
        }
        this.mPrivacyServiceManager.dismissPrivacyDialog(this.mProtocolId);
        this.mProtocolId = null;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.ui.base._LifeFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();
        this.mPrivacyServiceManager.disconnect();
    }

    /* loaded from: classes.dex */
    private class AboutItemClickListener implements View.OnClickListener {
        private AboutItemClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view == AboutSystemsFragment.this.mRestoreBtn) {
                AboutSystemsFragment.this.onItemClick(1);
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.ABOUT_SYS_PAGE_ID, "B001");
            } else if (view == AboutSystemsFragment.this.mStorageBtn) {
                AboutSystemsFragment.this.onItemClick(5);
            } else if (view == AboutSystemsFragment.this.mCleanBtn) {
                AboutSystemsFragment.this.onItemClick(4);
            } else if (view == AboutSystemsFragment.this.mPolicyBtn) {
                AboutSystemsFragment.this.onItemClick(2);
            } else if (view == AboutSystemsFragment.this.mAgreementBtn) {
                AboutSystemsFragment.this.onItemClick(3);
            }
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected String[] supportSecondPageForElementDirect() {
        return new String[]{"/about/agreement", "/about/privacy"};
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment, com.xiaopeng.car.settingslibrary.direct.OnPageDirectShowListener
    public void onPageDirectShow(final String str) {
        ThreadUtils.postOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.fragment.-$$Lambda$AboutSystemsFragment$ujBmHe8yJCtc728Ms5HwdDQQVZ4
            @Override // java.lang.Runnable
            public final void run() {
                AboutSystemsFragment.this.lambda$onPageDirectShow$5$AboutSystemsFragment(str);
            }
        }, 300L);
        super.onPageDirectShow(str);
    }

    public /* synthetic */ void lambda$onPageDirectShow$5$AboutSystemsFragment(String str) {
        if ("/about/agreement".equals(str)) {
            showDialog(102);
        } else if ("/about/privacy".equals(str)) {
            showDialog(101);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void dismissShowingDialog() {
        dismissDialog();
    }
}
