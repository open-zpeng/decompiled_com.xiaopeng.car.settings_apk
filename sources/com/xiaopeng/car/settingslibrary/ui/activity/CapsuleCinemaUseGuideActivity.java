package com.xiaopeng.car.settingslibrary.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.manager.bluetooth.XpBluetoothManger;
import com.xiaopeng.car.settingslibrary.ui.fragment.ProjectorConnectFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.ProjectorSetupFragment;
import com.xiaopeng.car.settingslibrary.ui.fragment.ScreenSetupFragment;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.app.delegate.XActivityBind;
import com.xiaopeng.xui.utils.XActivityUtils;
import java.util.HashMap;
@XActivityBind(1)
/* loaded from: classes.dex */
public class CapsuleCinemaUseGuideActivity extends XActivity implements ICapsuleCinemaGuideInterface {
    private String mCurrentFragmentTag = null;
    private boolean mFromUser;
    private boolean mLayoutFlat;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        StartPerfUtils.onCreateBegin();
        super.onCreate(bundle);
        setContentView(R.layout.space_capsule_cinema_use_guide_activity);
        getWindowAttributes().setGravity(17).setFlags(1024).setSystemUiVisibility(1538);
        this.mLayoutFlat = getIntent().getBooleanExtra(ProjectorConnectFragment.FROM_LAYOUT_FLAT, false);
        replaceFragment(getString(R.string.cinema_screen_title));
        findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.activity.-$$Lambda$CapsuleCinemaUseGuideActivity$HkoCiDwTeBSd0HchnTkF-NgeP2E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CapsuleCinemaUseGuideActivity.this.lambda$onCreate$0$CapsuleCinemaUseGuideActivity(view);
            }
        });
        uploadCinemaGuideBI(true);
        StartPerfUtils.onCreateEnd();
    }

    public /* synthetic */ void lambda$onCreate$0$CapsuleCinemaUseGuideActivity(View view) {
        XActivityUtils.finish(this);
    }

    private void replaceFragment(String str) {
        Fragment newInstance;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.capsule_cinema_guide_enter, R.anim.capsule_cinema_guide_exit);
        String str2 = this.mCurrentFragmentTag;
        Fragment findFragmentByTag = str2 != null ? supportFragmentManager.findFragmentByTag(str2) : null;
        if (findFragmentByTag == null) {
            findFragmentByTag = supportFragmentManager.findFragmentById(R.id.capsule_cinema_guide_container);
        }
        if (findFragmentByTag != null && !str.equals(this.mCurrentFragmentTag)) {
            beginTransaction.hide(findFragmentByTag);
        }
        this.mCurrentFragmentTag = str;
        Fragment findFragmentByTag2 = supportFragmentManager.findFragmentByTag(str);
        if (findFragmentByTag2 == null) {
            if (getString(R.string.cinema_screen_title).equals(str)) {
                newInstance = new ScreenSetupFragment();
            } else if (getString(R.string.cinema_projector_title).equals(str)) {
                newInstance = new ProjectorSetupFragment();
            } else {
                newInstance = ProjectorConnectFragment.newInstance(this.mLayoutFlat, this.mFromUser);
            }
            beginTransaction.add(R.id.capsule_cinema_guide_container, newInstance, str);
        } else {
            beginTransaction.show(findFragmentByTag2);
        }
        beginTransaction.commitNowAllowingStateLoss();
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.activity.ICapsuleCinemaGuideInterface
    public void onScreenSetup() {
        replaceFragment(getString(R.string.cinema_screen_title));
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.activity.ICapsuleCinemaGuideInterface
    public void onProjectorSetup() {
        replaceFragment(getString(R.string.cinema_projector_title));
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.activity.ICapsuleCinemaGuideInterface
    public void onProjectorBt(boolean z) {
        this.mFromUser = z;
        replaceFragment(getString(R.string.cinema_projector_bt_title));
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.activity.ICapsuleCinemaGuideInterface
    public void onProjectorBtConnected() {
        XActivityUtils.finish(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        int i;
        super.onDestroy();
        if (XpBluetoothManger.getInstance().isConnectingWhiteList()) {
            i = 3;
        } else {
            i = XpBluetoothManger.getInstance().isAlreadyBoundedWhiteList() ? 2 : 1;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("result", Integer.valueOf(i));
        BuriedPointUtils.sendDataLog(BuriedPointUtils.SYSTEMUI_MODULE, BuriedPointUtils.CINEMA_GUIDE_PAGE, "B002", hashMap);
        uploadCinemaGuideBI(false);
    }

    private void uploadCinemaGuideBI(boolean z) {
        HashMap hashMap = new HashMap();
        int i = 1;
        hashMap.put(BuriedPointUtils.SOURCE_KEY, Integer.valueOf(this.mLayoutFlat ? 1 : 2));
        hashMap.put("type", 2);
        if (z) {
            BuriedPointUtils.sendDataLog(BuriedPointUtils.SYSTEMUI_MODULE, BuriedPointUtils.CAPSULE_PAGE, "B005", hashMap);
            return;
        }
        if (getString(R.string.cinema_projector_title).equals(this.mCurrentFragmentTag)) {
            i = 2;
        } else if (getString(R.string.cinema_projector_bt_title).equals(this.mCurrentFragmentTag)) {
            i = 3;
        }
        hashMap.put("result", Integer.valueOf(i));
        BuriedPointUtils.sendDataLog(BuriedPointUtils.SYSTEMUI_MODULE, BuriedPointUtils.CAPSULE_PAGE, "B006", hashMap);
    }
}
