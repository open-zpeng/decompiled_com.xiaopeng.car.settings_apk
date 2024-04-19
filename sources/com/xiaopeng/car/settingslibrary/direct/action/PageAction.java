package com.xiaopeng.car.settingslibrary.direct.action;

import android.content.Context;
import android.net.Uri;
/* loaded from: classes.dex */
public interface PageAction {
    default void closePage(Context context, Uri uri) {
    }

    void doAction(Context context, Uri uri);

    default boolean isShowing(Context context, Uri uri) {
        return false;
    }
}
