package com.xiaopeng.car.settingslibrary.airouter;

import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.utils.LogUtils;
/* loaded from: classes.dex */
public class SettingsVuiObserver implements IServicePublisher {
    private static final String TAG = "VuiObserver";

    public void onEvent(String str, String str2) {
        LogUtils.logInfo(TAG, "event==" + str + " data=" + str2);
        VuiEngine.getInstance(CarSettingsApp.getContext()).dispatchVuiEvent(str, str2);
    }

    public String getElementState(String str, String str2) {
        String elementState = VuiEngine.getInstance(CarSettingsApp.getContext()).getElementState(str, str2);
        LogUtils.logInfo(TAG, "getElementState" + str + " elementId=" + str2 + " result == " + elementState);
        return elementState;
    }
}
