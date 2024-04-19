package com.xiaopeng.car.settingslibrary.service;
/* loaded from: classes.dex */
public class UserScenarioReceiver extends BootReceiver {
    public static final String TAG = "UserScenarioReceiver";

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0083, code lost:
        if (r3 == 1) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008b, code lost:
        if (com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.USER_SCENARIO_WAITING_MODE.equals(r1) == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008d, code lost:
        com.xiaopeng.car.settingslibrary.common.utils.Logs.d("UserScenarioReceiver waiting mode finish!");
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(r10).sendBroadcast(new android.content.Intent(com.xiaopeng.car.settingslibrary.common.Config.WAIT_MODE_FINISH_ACTION));
        com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager.getInstance().onExitWaitMode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ac, code lost:
        if (com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.USER_SCENARIO_CLEAN_MODE.equals(r1) == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ae, code lost:
        com.xiaopeng.car.settingslibrary.common.utils.Logs.d("UserScenarioReceiver clean mode finish!");
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(r10).sendBroadcast(new android.content.Intent(com.xiaopeng.car.settingslibrary.common.Config.CLEAN_MODE_FINISH_ACTION));
        com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager.getInstance().onExitCleanMode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c8, code lost:
        com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager.getInstance().reportModeStatus(r1, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:?, code lost:
        return;
     */
    @Override // com.xiaopeng.car.settingslibrary.service.BootReceiver, android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onReceive(android.content.Context r10, android.content.Intent r11) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.car.settingslibrary.service.UserScenarioReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
