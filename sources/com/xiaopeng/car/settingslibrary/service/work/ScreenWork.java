package com.xiaopeng.car.settingslibrary.service.work;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.interfaceui.AppServerManager;
import com.xiaopeng.car.settingslibrary.interfaceui.DisplayServerManager;
/* loaded from: classes.dex */
public class ScreenWork implements WorkService {
    private Context mContext;
    private boolean mIsRegister = false;
    final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.service.work.ScreenWork.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logs.d("ScreenWork xpservice receiver action:" + action);
            if (Config.ACTION_SCREEN_STATUS_CHANGE.equals(action)) {
                String stringExtra = intent.getStringExtra(Config.ACTION_SCREEN_STATUS_CHANGE_DEVICE_EXTRA);
                boolean booleanExtra = intent.getBooleanExtra("status", true);
                Logs.d("ScreenWork receiver whichScreen:" + stringExtra + " status:" + booleanExtra);
                if ("xp_mt_psg".equals(stringExtra)) {
                    AppServerManager.getInstance().onPsnScreenStatus(booleanExtra);
                    DisplayServerManager.getInstance().onPsnScreenStatusOn(booleanExtra);
                }
            }
        }
    };

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onStartCommand(Context context, Intent intent) {
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onCreate(Context context) {
        this.mContext = context;
        Logs.d("xpservice subscribe ScreenWork");
        if (this.mIsRegister) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.ACTION_SCREEN_STATUS_CHANGE);
        context.registerReceiver(this.mReceiver, intentFilter);
        this.mIsRegister = true;
    }

    @Override // com.xiaopeng.car.settingslibrary.service.work.WorkService
    public void onDestroy(Context context) {
        if (this.mIsRegister) {
            context.unregisterReceiver(this.mReceiver);
            this.mIsRegister = false;
        }
    }
}
