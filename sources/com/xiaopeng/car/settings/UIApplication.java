package com.xiaopeng.car.settings;

import android.content.Context;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.lib.apirouter.server.ApiPublisherProvider;
import com.xiaopeng.lib.apirouter.server.IManifestHandler;
import com.xiaopeng.lib.apirouter.server.IManifestHelper;
import com.xiaopeng.lib.apirouter.server.ManifestHelper_SettingsApp;
/* loaded from: classes.dex */
public class UIApplication extends CarSettingsApp {
    private static final String TAG = "UIApplication";

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        ApiPublisherProvider.addManifestHandler(new IManifestHandler() { // from class: com.xiaopeng.car.settings.-$$Lambda$UIApplication$EnvWiDK4Cw_J2n7-1iqtDM2TJpQ
            @Override // com.xiaopeng.lib.apirouter.server.IManifestHandler
            public final IManifestHelper[] getManifestHelpers() {
                return UIApplication.lambda$attachBaseContext$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ IManifestHelper[] lambda$attachBaseContext$0() {
        return new IManifestHelper[]{new ManifestHelper_SettingsApp()};
    }

    @Override // com.xiaopeng.car.settingslibrary.CarSettingsApp, android.app.Application
    public void onCreate() {
        super.onCreate();
    }
}
