package com.xiaopeng.car.settingslibrary.direct.action;

import android.content.Context;
import android.net.Uri;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.car.settingslibrary.manager.sound.SoundManager;
/* loaded from: classes.dex */
public class XiaopengSoundEffectPageAction implements PageAction {
    @Override // com.xiaopeng.car.settingslibrary.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        SoundManager.getInstance().setSoundEffectMode(1, false);
        Utils.startSoundEffectSetting();
    }
}
