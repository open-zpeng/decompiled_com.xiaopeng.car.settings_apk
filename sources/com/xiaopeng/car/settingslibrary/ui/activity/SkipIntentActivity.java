package com.xiaopeng.car.settingslibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.xui.app.ActivityUtils;
/* loaded from: classes.dex */
public class SkipIntentActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            Logs.d("xpskip action:" + action);
            if (action.equals("android.settings.WIFI_IP_SETTINGS") || action.equals("android.settings.WIFI_SETTINGS") || action.equals("android.net.wifi.PICK_WIFI_NETWORK") || action.equals("android.settings.WIFI_SAVED_NETWORK_SETTINGS") || action.equals("android.net.wifi.action.REQUEST_SCAN_ALWAYS_AVAILABLE") || action.equals("com.android.settings.WIFI_DIALOG") || action.equals("android.net.wifi.action.REQUEST_DISABLE") || action.equals("android.net.wifi.action.REQUEST_ENABLE")) {
                AppServerManager.getInstance().onJumpWifi();
            } else if (action.equals("android.settings.BLUETOOTH_SETTINGS") || action.equals("android.bluetooth.adapter.action.REQUEST_DISABLE") || action.equals("android.bluetooth.adapter.action.REQUEST_ENABLE") || action.equals("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE") || action.equals("android.bluetooth.device.action.PAIRING_REQUEST") || action.equals("android.bluetooth.devicepicker.action.LAUNCH") || action.equals("android.bluetooth.device.action.CONNECTION_ACCESS_CANCEL") || action.equals("android.bluetooth.device.action.CONNECTION_ACCESS_REQUEST")) {
                AppServerManager.getInstance().onJumpBluetooth();
            } else if (action.equals("android.settings.SOUND_SETTINGS") || action.equals("android.settings.ACTION_OTHER_SOUND_SETTINGS") || action.equals("com.android.settings.SOUND_SETTINGS")) {
                AppServerManager.getInstance().onJumpSound();
            } else if (action.equals("android.settings.DISPLAY_SETTINGS") || action.equals("android.settings.NIGHT_DISPLAY_SETTINGS") || action.equals("android.settings.DATE_SETTINGS") || action.equals("com.android.settings.DISPLAY_SETTINGS")) {
                AppServerManager.getInstance().onJumpDisplay();
            } else {
                Logs.d("xpskip no found action:" + action);
            }
        }
        ActivityUtils.finish(this);
    }
}
