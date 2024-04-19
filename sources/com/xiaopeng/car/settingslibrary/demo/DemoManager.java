package com.xiaopeng.car.settingslibrary.demo;

import android.app.Application;
import android.content.Intent;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
/* loaded from: classes.dex */
public class DemoManager {
    public static void destroy(Application application) {
    }

    public static void init(Application application) {
        if (Utils.isUserRelease()) {
            return;
        }
        application.startService(new Intent(application, XpDemoService.class));
    }
}
