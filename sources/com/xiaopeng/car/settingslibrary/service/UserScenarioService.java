package com.xiaopeng.car.settingslibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class UserScenarioService extends Service {
    private static final String TAG = "UserScenarioService";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Logs.d("UserScenarioService onCreate");
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0086, code lost:
        if (r4 == 1) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008e, code lost:
        if (com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.USER_SCENARIO_WAITING_MODE.equals(r1) == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0090, code lost:
        com.xiaopeng.car.settingslibrary.common.utils.Logs.d("UserScenarioService waiting mode finish!");
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(com.xiaopeng.car.settingslibrary.CarSettingsApp.getContext()).sendBroadcast(new android.content.Intent(com.xiaopeng.car.settingslibrary.common.Config.WAIT_MODE_FINISH_ACTION));
        com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager.getInstance().onExitWaitMode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b3, code lost:
        if (com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.USER_SCENARIO_CLEAN_MODE.equals(r1) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b5, code lost:
        com.xiaopeng.car.settingslibrary.common.utils.Logs.d("UserScenarioService clean mode finish!");
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(com.xiaopeng.car.settingslibrary.CarSettingsApp.getContext()).sendBroadcast(new android.content.Intent(com.xiaopeng.car.settingslibrary.common.Config.CLEAN_MODE_FINISH_ACTION));
        com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager.getInstance().onExitCleanMode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00d3, code lost:
        com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.getInstance().reportModeStatus(r1, 0);
     */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int onStartCommand(android.content.Intent r10, int r11, int r12) {
        /*
            Method dump skipped, instructions count: 328
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.service.UserScenarioService.onStartCommand(android.content.Intent, int, int):int");
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Logs.d("UserScenarioService onDestroy");
    }
}
