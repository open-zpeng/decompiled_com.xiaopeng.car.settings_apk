package com.xiaopeng.car.settingslibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager;
/* loaded from: classes.dex */
public class StoreWarningReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Logs.d("StoreWarningReceiver action:" + intent.getAction());
        if (Config.ACTION_DEVICE_STORAGE_LOW.equals(intent.getAction())) {
            XpAboutManager.pushAIStorageLow();
        } else if (Config.ACTION_DEVICE_STORAGE_OK.equals(intent.getAction())) {
            Utils.cancelStoreMsgToMessageCenter();
        } else if (Config.ACTION_DEVICE_STORAGE_FULL.equals(intent.getAction())) {
            XpAboutManager.setPushStorageFullTime(System.currentTimeMillis());
            XpAboutManager.pushAIStorageFull();
            BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.STORAGE_MANAGE_PAGE_ID, BuriedPointUtils.STORAGE_STORE_FULL_ID);
        }
    }
}
