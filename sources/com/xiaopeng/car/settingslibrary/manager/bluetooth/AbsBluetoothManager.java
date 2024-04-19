package com.xiaopeng.car.settingslibrary.manager.bluetooth;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.xiaopeng.car.settingslibrary.airouter.SpeechSettingsOverAllObserver;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public abstract class AbsBluetoothManager implements IXpBluetoothInterface {
    public Context mContext;
    private int mLastBluetoothState = -1;
    private ContentObserver mBluetoothOnChangeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaopeng.car.settingslibrary.manager.bluetooth.AbsBluetoothManager.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (uri.equals(Settings.Global.getUriFor("bluetooth_on"))) {
                int i = Settings.Global.getInt(AbsBluetoothManager.this.mContext.getContentResolver(), "bluetooth_on", -1);
                Logs.d("xpbluetooth nf adapter state:" + i + " lastState:" + AbsBluetoothManager.this.mLastBluetoothState);
                if (i != 0 || AbsBluetoothManager.this.mLastBluetoothState == 2) {
                    if (i == 1 && AbsBluetoothManager.this.mLastBluetoothState != 2) {
                        SpeechSettingsOverAllObserver.sendToPrivateCustomNoData("trigger.bluetooth.open");
                    }
                } else {
                    SpeechSettingsOverAllObserver.sendToPrivateCustomNoData("trigger.bluetooth.close");
                }
                AbsBluetoothManager.this.mLastBluetoothState = i;
            }
        }
    };

    public AbsBluetoothManager(Context context) {
        this.mContext = context;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void registerBluetoothOnChange() {
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("bluetooth_on"), true, this.mBluetoothOnChangeObserver);
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.bluetooth.IXpBluetoothInterface
    public void unregisterBluetoothOnChange() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mBluetoothOnChangeObserver);
    }
}
