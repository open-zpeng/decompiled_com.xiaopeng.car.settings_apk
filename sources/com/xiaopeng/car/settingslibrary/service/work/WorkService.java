package com.xiaopeng.car.settingslibrary.service.work;

import android.content.Context;
import android.content.Intent;
/* loaded from: classes.dex */
public interface WorkService {
    void onCreate(Context context);

    void onDestroy(Context context);

    void onStartCommand(Context context, Intent intent);
}
