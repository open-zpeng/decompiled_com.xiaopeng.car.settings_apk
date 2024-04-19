package com.xiaopeng.car.settingslibrary.direct.action;

import android.content.Context;
import android.net.Uri;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
/* loaded from: classes.dex */
public class SoundEffectPageAction implements PageAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        Utils.startSoundEffectSetting();
    }
}
