package com.xiaopeng.car.settingslibrary.demo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.demo.XpUIDemoService;
import com.xiaopeng.car.settingslibrary.direct.ElementDirectManager;
import com.xiaopeng.car.settingslibrary.ui.activity.MainActivity;
import com.xiaopeng.car.settingslibrary.ui.activity.TabFragment;
import com.xiaopeng.xui.app.XToast;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class XpUIDemoService extends Service {
    private static final String TAG = "XpUIDemoService";
    private BroadcastReceiver mDemoFastTabChangeReceiver = new AnonymousClass1();
    private BroadcastReceiver mDemoPopReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpUIDemoService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("index", 0);
            Logs.d("xppopup onReceive--" + intExtra);
            ArrayList arrayList = new ArrayList();
            arrayList.add(Config.POPUP_BLUETOOTH);
            arrayList.add(Config.POPUP_WLAN);
            arrayList.add(Config.POPUP_DOWNLOAD);
            arrayList.add(Config.POPUP_USB);
            Intent intent2 = new Intent((String) arrayList.get(intExtra % arrayList.size()));
            intent2.setFlags(268435456);
            context.startActivity(intent2);
        }
    };
    private BroadcastReceiver mElementDirectReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.demo.XpUIDemoService.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra("url");
            if (ElementDirectManager.get().checkSupport(stringExtra)) {
                ElementDirectManager.get().showPageDirect(context, stringExtra);
            } else {
                XToast.showLong("无此功能---元素直达 : '注意配置'");
            }
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        registerReceiver(this.mDemoFastTabChangeReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_SKIPINTENT"));
        registerReceiver(this.mDemoPopReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_POPUP"));
        registerReceiver(this.mElementDirectReceiver, new IntentFilter("com.xiaopeng.settings.action.DEMO_ELEMENT_DIRECT"));
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mDemoFastTabChangeReceiver);
        unregisterReceiver(this.mDemoPopReceiver);
        unregisterReceiver(this.mElementDirectReceiver);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.demo.XpUIDemoService$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 extends BroadcastReceiver {
        private Handler mHandler = new Handler();

        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            auto(0, intent.getIntExtra(BuriedPointUtils.COUNT_KEY, 1));
        }

        private void auto(final int i, final int i2) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.demo.-$$Lambda$XpUIDemoService$1$LVbVMhzm_tZ22Ux5WJ408xr7rL8
                @Override // java.lang.Runnable
                public final void run() {
                    XpUIDemoService.AnonymousClass1.this.lambda$auto$0$XpUIDemoService$1(i, i2);
                }
            }, 10L);
            start(i % TabFragment.sTabInfo.size());
        }

        public /* synthetic */ void lambda$auto$0$XpUIDemoService$1(int i, int i2) {
            if (i < TabFragment.sTabInfo.size() * i2) {
                auto(i + 1, i2);
            }
        }

        private void start(int i) {
            Intent intent = new Intent(CarSettingsApp.getContext(), MainActivity.class);
            intent.setFlags(268435456);
            intent.putExtra("fragment", TabFragment.sTabInfo.get(i).getFragmentClass().getName());
            CarSettingsApp.getContext().startActivity(intent);
        }
    }

    private void log(String str) {
        Log.i(TAG, str);
    }
}
