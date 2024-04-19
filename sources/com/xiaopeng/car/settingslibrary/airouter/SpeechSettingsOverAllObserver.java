package com.xiaopeng.car.settingslibrary.airouter;

import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.alibaba.fastjson.parser.JSONLexer;
import com.lzy.okgo.cache.CacheEntity;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.speech.SpeechCallbackListener;
import com.xiaopeng.car.settingslibrary.manager.speech.SpeechSystemCaller;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.speech.protocol.bean.AdjustValue;
import com.xiaopeng.speech.protocol.bean.VolumeValue;
import com.xiaopeng.speech.protocol.event.SystemEvent;
import com.xiaopeng.speech.protocol.event.query.QuerySystemEvent;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class SpeechSettingsOverAllObserver implements IServicePublisher {
    private static final String DIRECTION_KEY = "direction";
    private static final String MODE_KEY = "mode";
    private static final String SCENE_KEY = "scene";
    private static final String STYLE_KEY = "style";
    private static final String TAG = "SpeechObserver";
    private static final String VOLUME_KEY = "stream_type";
    private final SpeechCallbackListener mSpeechCallbackListener = SpeechCallbackListener.getInstance();
    private final SpeechSystemCaller mSpeechSystemCaller = SpeechSystemCaller.getInstance();

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onEvent(String str, String str2) {
        char c;
        Logs.d("SpeechObserver onEvent event== " + str + ", data:" + str2);
        int source = getSource(str2);
        String mac = getMac(str2);
        if (TextUtils.isEmpty(str)) {
            Logs.d("SpeechObserver no event ");
            return;
        }
        switch (str.hashCode()) {
            case -2061136234:
                if (str.equals("command://system.psn.screen.brightness.max")) {
                    c = 'G';
                    break;
                }
                c = 65535;
                break;
            case -2061135996:
                if (str.equals("command://system.psn.screen.brightness.min")) {
                    c = 'J';
                    break;
                }
                c = 65535;
                break;
            case -2061130348:
                if (str.equals("command://system.psn.screen.brightness.set")) {
                    c = 'F';
                    break;
                }
                c = 65535;
                break;
            case -2057953829:
                if (str.equals("command://system.polit.screen.on")) {
                    c = '=';
                    break;
                }
                c = 65535;
                break;
            case -1919368988:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_MEDIUM)) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -1625628636:
                if (str.equals("command://system.sentry.mode.setting")) {
                    c = '8';
                    break;
                }
                c = 65535;
                break;
            case -1597026812:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_UP_PERCENT)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1357249261:
                if (str.equals("command://system.lowspeed.analogtone.low")) {
                    c = '4';
                    break;
                }
                c = 65535;
                break;
            case -1357248733:
                if (str.equals("command://system.lowspeed.analogtone.max")) {
                    c = '5';
                    break;
                }
                c = 65535;
                break;
            case -1357248495:
                if (str.equals("command://system.lowspeed.analogtone.min")) {
                    c = '6';
                    break;
                }
                c = 65535;
                break;
            case -1357242847:
                if (str.equals("command://system.lowspeed.analogtone.set")) {
                    c = '7';
                    break;
                }
                c = 65535;
                break;
            case -1344595949:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_AUTO_ON)) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case -1280072714:
                if (str.equals("command://system.timeformat.setting")) {
                    c = '2';
                    break;
                }
                c = 65535;
                break;
            case -1179173036:
                if (str.equals("command://system.key.parking.on")) {
                    c = ':';
                    break;
                }
                c = 65535;
                break;
            case -1052946394:
                if (str.equals("command://system.position.reminder.on")) {
                    c = 'K';
                    break;
                }
                c = 65535;
                break;
            case -1017521433:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_SET_PERCENT)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1008260193:
                if (str.equals(SystemEvent.BLUETOOTH_OFF)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -936744901:
                if (str.equals("command://system.recordvideo.phone.off")) {
                    c = 'P';
                    break;
                }
                c = 65535;
                break;
            case -931863087:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_DOWN)) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case -783044055:
                if (str.equals("command://system.usage.restrictions.on")) {
                    c = '9';
                    break;
                }
                c = 65535;
                break;
            case -715765491:
                if (str.equals("command://system.immerse.sound.set")) {
                    c = 'O';
                    break;
                }
                c = 65535;
                break;
            case -620677335:
                if (str.equals("command://system.psn.screen.brightness.up")) {
                    c = 'C';
                    break;
                }
                c = 65535;
                break;
            case -601542604:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_DOWN)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -537146671:
                if (str.equals(SystemEvent.WIFI_SETTING_OPEN)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -453867971:
                if (str.equals(SystemEvent.BRIGHTNESS_UP_PERCENT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -448838165:
                if (str.equals("command://system.psn.screen.brightness.set.percent")) {
                    c = 'E';
                    break;
                }
                c = 65535;
                break;
            case -374212798:
                if (str.equals("command://system.icm.brightness.auto.off")) {
                    c = '@';
                    break;
                }
                c = 65535;
                break;
            case -150618708:
                if (str.equals("command://system.icm.brightness.auto.on")) {
                    c = '?';
                    break;
                }
                c = 65535;
                break;
            case -147779855:
                if (str.equals(SystemEvent.VOLUME_RESUME)) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -118876439:
                if (str.equals("command://system.pilot.bluetooth.off")) {
                    c = 'M';
                    break;
                }
                c = 65535;
                break;
            case -108504630:
                if (str.equals("command://control.bluetooth.connect")) {
                    c = 'R';
                    break;
                }
                c = 65535;
                break;
            case -95607791:
                if (str.equals(SystemEvent.SCREEN_ON)) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case -70327096:
                if (str.equals(SystemEvent.WIFI_ON)) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -53759242:
                if (str.equals(SystemEvent.VOLUME_UNMUTE)) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 60664270:
                if (str.equals(SystemEvent.BRIGHTNESS_SET_PERCENT)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 108495445:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_MAX)) {
                    c = '\'';
                    break;
                }
                c = 65535;
                break;
            case 108495683:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_MIN)) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case 134712613:
                if (str.equals("command://system.pilot.bluetooth.on")) {
                    c = 'N';
                    break;
                }
                c = 65535;
                break;
            case 165363165:
                if (str.equals("command://system.polit.screen.brightness.auto.on")) {
                    c = 'A';
                    break;
                }
                c = 65535;
                break;
            case 195917088:
                if (str.equals(SystemEvent.VOLUME_MAX)) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 195917326:
                if (str.equals(SystemEvent.VOLUME_MIN)) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 195922974:
                if (str.equals(SystemEvent.VOLUME_SET)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 448747494:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_UP)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 483297957:
                if (str.equals(SystemEvent.THEME_MODE_AUTO)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 529031600:
                if (str.equals("command://system.psn.screen.brightness.down")) {
                    c = 'H';
                    break;
                }
                c = 65535;
                break;
            case 535993595:
                if (str.equals("command://system.front.setting")) {
                    c = '1';
                    break;
                }
                c = 65535;
                break;
            case 540856756:
                if (str.equals("command://system.rear.screen.lock.off")) {
                    c = 'W';
                    break;
                }
                c = 65535;
                break;
            case 546271995:
                if (str.equals(SystemEvent.SOUND_EFFECT_DIRECTION_SET)) {
                    c = '0';
                    break;
                }
                c = 65535;
                break;
            case 605044356:
                if (str.equals(SystemEvent.BRIGHTNESS_DOWN_PERCENT)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 627940595:
                if (str.equals("command://system.polit.screen.off")) {
                    c = '>';
                    break;
                }
                c = 65535;
                break;
            case 660212143:
                if (str.equals(SystemEvent.BLUETOOTH_ON)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 662217111:
                if (str.equals(SystemEvent.SETTING_PAGE_OPEN)) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 759114577:
                if (str.equals(SystemEvent.SOUND_EFFECT_SCENE_SET)) {
                    c = '.';
                    break;
                }
                c = 65535;
                break;
            case 831290673:
                if (str.equals("command://system.polit.screen.brightness.auto.off")) {
                    c = 'B';
                    break;
                }
                c = 65535;
                break;
            case 874820547:
                if (str.equals("command://system.lowspeed.analogtone.high")) {
                    c = '3';
                    break;
                }
                c = 65535;
                break;
            case 882995785:
                if (str.equals("command://system.current.effect.equalizer.model.set")) {
                    c = 'S';
                    break;
                }
                c = 65535;
                break;
            case 900188016:
                if (str.equals("command://system.rear.screen.close")) {
                    c = 'U';
                    break;
                }
                c = 65535;
                break;
            case 968795136:
                if (str.equals(SystemEvent.SOUND_EFFECT_STYlE_SET)) {
                    c = '-';
                    break;
                }
                c = 65535;
                break;
            case 979070195:
                if (str.equals(SystemEvent.HEADREST_MODE_SET)) {
                    c = '/';
                    break;
                }
                c = 65535;
                break;
            case 985423846:
                if (str.equals(SystemEvent.THEME_MODE_DAY)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1026262393:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_MAX)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1026262631:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_MIN)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1026268279:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_SET)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1053717120:
                if (str.equals("command://system.psn.screen.brightness.up.percent")) {
                    c = 'D';
                    break;
                }
                c = 65535;
                break;
            case 1209375344:
                if (str.equals(SystemEvent.SCREEN_MODE_CLEAN)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 1264372986:
                if (str.equals("command://system.rear.screen.lock.on")) {
                    c = 'V';
                    break;
                }
                c = 65535;
                break;
            case 1267198395:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_AUTO_OFF)) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case 1276325330:
                if (str.equals("command://system.rear.screen.open")) {
                    c = 'T';
                    break;
                }
                c = 65535;
                break;
            case 1284383276:
                if (str.equals("command://system.key.parking.enhanced.off")) {
                    c = '<';
                    break;
                }
                c = 65535;
                break;
            case 1331125629:
                if (str.equals(SystemEvent.SCREEN_OFF)) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 1447822712:
                if (str.equals(SystemEvent.SOUND_EFFECT_MODE_SET)) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            case 1449440381:
                if (str.equals("command://control.system.sound.external.set")) {
                    c = 'Q';
                    break;
                }
                c = 65535;
                break;
            case 1541025931:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_DOWN_PERCENT)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 1718400008:
                if (str.equals("command://system.position.reminder.off")) {
                    c = 'L';
                    break;
                }
                c = 65535;
                break;
            case 1749108525:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_DOWN)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1749559949:
                if (str.equals(SystemEvent.SCREEN_BRIGHTNESS_STOP)) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 1778207846:
                if (str.equals(SystemEvent.VOLUME_DOWN)) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 1778481629:
                if (str.equals(SystemEvent.VOLUME_MUTE)) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 1781718930:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_MAX)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1781719168:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_MIN)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 1781724816:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_SET)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1858590381:
                if (str.equals(SystemEvent.ICM_BRIGHTNESS_UP)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1943162762:
                if (str.equals(SystemEvent.VOLUME_NOTIFICATION_UP)) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case 1945982815:
                if (str.equals(SystemEvent.VOLUME_UP)) {
                    c = JSONLexer.EOI;
                    break;
                }
                c = 65535;
                break;
            case 1990338055:
                if (str.equals("command://system.psn.screen.brightness.down.percent")) {
                    c = 'I';
                    break;
                }
                c = 65535;
                break;
            case 2100341402:
                if (str.equals("command://system.key.parking.off")) {
                    c = ';';
                    break;
                }
                c = 65535;
                break;
            case 2108970466:
                if (str.equals(SystemEvent.THEME_MODE_NIGHT)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 2114827174:
                if (str.equals(SystemEvent.WIFI_OFF)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mSpeechCallbackListener.onBluetoothOn();
                return;
            case 1:
                this.mSpeechCallbackListener.onBluetoothOff();
                return;
            case 2:
                this.mSpeechCallbackListener.onScreenBrightnessUp();
                return;
            case 3:
                this.mSpeechCallbackListener.onBrightnessUpPercent(AdjustValue.fromJson(str2));
                return;
            case 4:
                this.mSpeechCallbackListener.onBrightnessSetPercent(AdjustValue.fromJson(str2));
                return;
            case 5:
                this.mSpeechCallbackListener.onBrightnessSet(AdjustValue.fromJson(str2));
                return;
            case 6:
                this.mSpeechCallbackListener.onScreenBrightnessMax();
                return;
            case 7:
                this.mSpeechCallbackListener.onScreenBrightnessDown();
                return;
            case '\b':
                this.mSpeechCallbackListener.onBrightnessDownPercent(AdjustValue.fromJson(str2));
                return;
            case '\t':
                this.mSpeechCallbackListener.onScreenBrightnessMin();
                return;
            case '\n':
                this.mSpeechCallbackListener.onIcmBrightnessUp();
                return;
            case 11:
                this.mSpeechCallbackListener.onIcmBrightnessSet(AdjustValue.fromJson(str2));
                return;
            case '\f':
                this.mSpeechCallbackListener.onIcmBrightnessUpPercent(AdjustValue.fromJson(str2));
                return;
            case '\r':
                this.mSpeechCallbackListener.onIcmBrightnessSetPercent(AdjustValue.fromJson(str2));
                return;
            case 14:
                this.mSpeechCallbackListener.onIcmBrightnessMax();
                return;
            case 15:
                this.mSpeechCallbackListener.onIcmBrightnessDown();
                return;
            case 16:
                this.mSpeechCallbackListener.onIcmBrightnessDownPercent(AdjustValue.fromJson(str2));
                return;
            case 17:
                this.mSpeechCallbackListener.onIcmBrightnessMin();
                return;
            case 18:
                this.mSpeechCallbackListener.onThemeModeAuto();
                return;
            case 19:
                this.mSpeechCallbackListener.onThemeModeDay();
                return;
            case 20:
                this.mSpeechCallbackListener.onThemeModeNight();
                return;
            case 21:
                this.mSpeechCallbackListener.onScreenModeClean();
                return;
            case 22:
                this.mSpeechCallbackListener.onWifiOff();
                return;
            case 23:
                this.mSpeechCallbackListener.onWifiOn();
                return;
            case 24:
                this.mSpeechCallbackListener.onOpenWifiPage();
                return;
            case 25:
                this.mSpeechCallbackListener.onOpenSettingPage();
                return;
            case 26:
                this.mSpeechCallbackListener.onVolumeUp(VolumeValue.fromJson(str2));
                return;
            case 27:
                this.mSpeechCallbackListener.onVolumeDown(VolumeValue.fromJson(str2));
                return;
            case 28:
                this.mSpeechCallbackListener.onVolumeSet(VolumeValue.fromJson(str2));
                return;
            case 29:
                int gsonInt = getGsonInt(VOLUME_KEY, str2);
                if (gsonInt == -1) {
                    Logs.d("SpeechObserver VOLUME_MAX -1");
                    gsonInt = 3;
                }
                this.mSpeechCallbackListener.onVolumeMax(gsonInt);
                return;
            case 30:
                int gsonInt2 = getGsonInt(VOLUME_KEY, str2);
                if (gsonInt2 == -1) {
                    Logs.d("SpeechObserver VOLUME_MIN -1");
                    gsonInt2 = 3;
                }
                this.mSpeechCallbackListener.onVolumeMin(gsonInt2);
                return;
            case 31:
                int gsonInt3 = getGsonInt(VOLUME_KEY, str2);
                if (gsonInt3 == -1) {
                    Logs.d("SpeechObserver VOLUME_MUTE -1");
                    gsonInt3 = 3;
                }
                this.mSpeechCallbackListener.onVolumeMute(gsonInt3);
                return;
            case ' ':
                int gsonInt4 = getGsonInt(VOLUME_KEY, str2);
                if (gsonInt4 == -1) {
                    Logs.d("SpeechObserver VOLUME_UNMUTE -1");
                    gsonInt4 = 3;
                }
                this.mSpeechCallbackListener.onVolumeUnmute(gsonInt4);
                return;
            case '!':
                this.mSpeechCallbackListener.onVolumeResume();
                return;
            case '\"':
                this.mSpeechCallbackListener.onScreenOff();
                return;
            case '#':
                this.mSpeechCallbackListener.onScreenOn();
                return;
            case '$':
                this.mSpeechCallbackListener.onScreenBrightnessStop();
                return;
            case '%':
                this.mSpeechCallbackListener.onScreenBrightnessAutoOn();
                return;
            case '&':
                this.mSpeechCallbackListener.onScreenBrightnessAutoOff();
                return;
            case '\'':
                this.mSpeechCallbackListener.onVolumeNotificationMax(source);
                return;
            case '(':
                this.mSpeechCallbackListener.onVolumeNotificationMedium(source);
                return;
            case ')':
                this.mSpeechCallbackListener.onVolumeNotificationMin(source);
                return;
            case '*':
                this.mSpeechCallbackListener.onVolumeNotificationUp();
                return;
            case '+':
                this.mSpeechCallbackListener.onVolumeNotificationDown();
                return;
            case ',':
                int gsonInt5 = getGsonInt(MODE_KEY, str2);
                if (gsonInt5 == -1) {
                    Logs.d("SpeechObserver SOUND_EFFECT_MODE_SET -1");
                    gsonInt5 = 1;
                }
                this.mSpeechCallbackListener.onSoundEffectMode(gsonInt5);
                return;
            case '-':
                int gsonInt6 = getGsonInt("style", str2);
                if (gsonInt6 == -1) {
                    Logs.d("SpeechObserver SOUND_EFFECT_STYlE_SET -1");
                    gsonInt6 = 1;
                }
                this.mSpeechCallbackListener.onSoundEffectStyle(gsonInt6);
                return;
            case '.':
                int gsonInt7 = getGsonInt("scene", str2);
                if (gsonInt7 == -1) {
                    Logs.d("SpeechObserver SOUND_EFFECT_SCENE_SET -1");
                    gsonInt7 = 1;
                }
                this.mSpeechCallbackListener.onSoundEffectScene(gsonInt7);
                return;
            case '/':
                int gsonInt8 = getGsonInt(MODE_KEY, str2);
                if (gsonInt8 == -1) {
                    Logs.d("SpeechObserver HEADREST_MODE_SET -1");
                    gsonInt8 = 1;
                }
                this.mSpeechCallbackListener.onHeadsetMode(gsonInt8, source);
                return;
            case '0':
                int gsonInt9 = getGsonInt("direction", str2);
                if (gsonInt9 != -1) {
                    this.mSpeechCallbackListener.onSoundEffectField(gsonInt9);
                    return;
                }
                return;
            case '1':
                int gsonInt10 = getGsonInt(MODE_KEY, str2);
                if (gsonInt10 != -1) {
                    this.mSpeechCallbackListener.onSystemFontSetting(gsonInt10, source);
                    return;
                }
                return;
            case '2':
                int gsonInt11 = getGsonInt(MODE_KEY, str2);
                if (gsonInt11 != -1) {
                    this.mSpeechCallbackListener.onSystemTimeFormatSetting(gsonInt11, source);
                    return;
                }
                return;
            case '3':
                int gsonInt12 = getGsonInt("value", str2);
                if (gsonInt12 != -1) {
                    this.mSpeechCallbackListener.onSystemLowSpeedAnalogtoneHigh(gsonInt12);
                    return;
                }
                return;
            case '4':
                int gsonInt13 = getGsonInt("value", str2);
                if (gsonInt13 != -1) {
                    this.mSpeechCallbackListener.onSystemLowSpeedAnalogtoneLow(gsonInt13);
                    return;
                }
                return;
            case '5':
                this.mSpeechCallbackListener.onSystemLowSpeedAnalogtoneMax();
                return;
            case '6':
                this.mSpeechCallbackListener.onSystemLowSpeedAnalogtoneMin();
                return;
            case '7':
                int gsonInt14 = getGsonInt("value", str2);
                if (gsonInt14 != -1) {
                    this.mSpeechCallbackListener.onSystemLowSpeedAnalogtoneSet(gsonInt14);
                    return;
                }
                return;
            case '8':
                int gsonInt15 = getGsonInt(MODE_KEY, str2);
                if (gsonInt15 != -1) {
                    this.mSpeechCallbackListener.onSystemSentryModeSet(gsonInt15);
                    return;
                }
                return;
            case '9':
                this.mSpeechCallbackListener.onSystemUsageRestrictionsOn();
                return;
            case ':':
                this.mSpeechCallbackListener.onSystemKeyParkingOn();
                return;
            case ';':
                this.mSpeechCallbackListener.onSystemKeyParkingOff();
                return;
            case '<':
                this.mSpeechCallbackListener.onSystemKeyParkingEnhancedOff();
                return;
            case '=':
                int gsonInt16 = getGsonInt("polit", str2);
                if (gsonInt16 != -1) {
                    this.mSpeechCallbackListener.onOpenScreen(gsonInt16);
                    return;
                }
                return;
            case '>':
                int gsonInt17 = getGsonInt("polit", str2);
                if (gsonInt17 != -1) {
                    this.mSpeechCallbackListener.onCloseScreen(gsonInt17);
                    return;
                }
                return;
            case '?':
                int gsonInt18 = getGsonInt("icm", str2);
                if (gsonInt18 != -1) {
                    this.mSpeechCallbackListener.onIcmBrightnessAutoOn(gsonInt18, source);
                    return;
                }
                return;
            case '@':
                int gsonInt19 = getGsonInt("icm", str2);
                if (gsonInt19 != -1) {
                    this.mSpeechCallbackListener.onIcmBrightnessAutoOff(gsonInt19, source);
                    return;
                }
                return;
            case 'A':
                int gsonInt20 = getGsonInt("polit", str2);
                if (gsonInt20 != -1) {
                    this.mSpeechCallbackListener.onPolitScreenBrightnessAutoOn(gsonInt20, source);
                    return;
                }
                return;
            case 'B':
                int gsonInt21 = getGsonInt("polit", str2);
                if (gsonInt21 != -1) {
                    this.mSpeechCallbackListener.onPolitScreenBrightnessAutoOff(gsonInt21, source);
                    return;
                }
                return;
            case 'C':
                this.mSpeechCallbackListener.onPsnScreenBrightnessUp();
                return;
            case 'D':
                this.mSpeechCallbackListener.onPsnBrightnessUpPercent(AdjustValue.fromJson(str2));
                return;
            case 'E':
                this.mSpeechCallbackListener.onPsnBrightnessSetPercent(AdjustValue.fromJson(str2));
                return;
            case 'F':
                this.mSpeechCallbackListener.onPsnBrightnessSet(AdjustValue.fromJson(str2));
                return;
            case 'G':
                this.mSpeechCallbackListener.onPsnScreenBrightnessMax();
                return;
            case 'H':
                this.mSpeechCallbackListener.onPsnScreenBrightnessDown();
                return;
            case 'I':
                this.mSpeechCallbackListener.onPsnBrightnessDownPercent(AdjustValue.fromJson(str2));
                return;
            case 'J':
                this.mSpeechCallbackListener.onPsnScreenBrightnessMin();
                return;
            case 'K':
                this.mSpeechCallbackListener.onPositionReminderOn();
                return;
            case 'L':
                this.mSpeechCallbackListener.onPositionReminderOff();
                return;
            case 'M':
                int gsonInt22 = getGsonInt("pilot", str2);
                if (gsonInt22 != -1) {
                    this.mSpeechCallbackListener.onPilotBlueToothOff(gsonInt22);
                    return;
                }
                return;
            case 'N':
                int gsonInt23 = getGsonInt("pilot", str2);
                if (gsonInt23 != -1) {
                    this.mSpeechCallbackListener.onPilotBlueToothOn(gsonInt23);
                    return;
                }
                return;
            case 'O':
                int gsonInt24 = getGsonInt(MODE_KEY, str2);
                if (gsonInt24 != -1) {
                    this.mSpeechCallbackListener.onImmerseSoundSet(gsonInt24);
                    return;
                }
                return;
            case 'P':
                this.mSpeechCallbackListener.onRecordVideoPhoneOff();
                return;
            case 'Q':
                int gsonInt25 = getGsonInt(TypedValues.Attributes.S_TARGET, str2);
                if (gsonInt25 != -1) {
                    this.mSpeechCallbackListener.onSoundExternalSet(gsonInt25);
                    return;
                }
                return;
            case 'R':
                this.mSpeechCallbackListener.controlBluetoothConnect(mac);
                return;
            case 'S':
                int gsonInt26 = getGsonInt(MODE_KEY, str2);
                if (gsonInt26 != -1) {
                    this.mSpeechCallbackListener.onCurrentSoundEffectEqualizerMode(gsonInt26);
                    return;
                }
                return;
            case 'T':
                this.mSpeechCallbackListener.onRearScreenOpen();
                return;
            case 'U':
                this.mSpeechCallbackListener.onRearScreenClose();
                return;
            case 'V':
                this.mSpeechCallbackListener.onRearScreenLockOn();
                return;
            case 'W':
                this.mSpeechCallbackListener.onRearScreenLockOff();
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onQuery(String str, String str2, String str3) {
        char c;
        Logs.d("SpeechObserver onQuery event== " + str + ", data:" + str2 + ", callback:" + str3);
        String mac = getMac(str2);
        switch (str.hashCode()) {
            case -2146663530:
                if (str.equals(QuerySystemEvent.GET_CUR_SCREEN_BRIGHTNESS)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2111346675:
                if (str.equals(QuerySystemEvent.GET_MAX_SCREEN_BRIGHTNESS_VALUE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -2062503527:
                if (str.equals("system.psn.screen.current")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case -1976339039:
                if (str.equals(QuerySystemEvent.ISUPPORT_SOUND_POSTION)) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -1894470442:
                if (str.equals("system.sentry.mode.value")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1879469492:
                if (str.equals("system.signal.loss.frame.state")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -1788329176:
                if (str.equals("unity.page.support.settings")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1761245161:
                if (str.equals("system.xp.lab.support")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case -1613537166:
                if (str.equals(QuerySystemEvent.GET_MAX_VOLUME_VALUE)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1558964631:
                if (str.equals("system.current.sound.effect.equalizer.mode")) {
                    c = '/';
                    break;
                }
                c = 65535;
                break;
            case -1541034698:
                if (str.equals(QuerySystemEvent.GET_CURRENT_NEDC_STATUS)) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -1502647492:
                if (str.equals("system.immerse.sound.current.mode")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -1469262005:
                if (str.equals(QuerySystemEvent.ISSUPPORT_SOUND_SCENE)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1453788681:
                if (str.equals("system.lowspeed.analogtone.max.value")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case -1423951429:
                if (str.equals(QuerySystemEvent.GET_PAGE_OPEN_STATUS)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1364004968:
                if (str.equals("system.rear.screen.support")) {
                    c = '0';
                    break;
                }
                c = 65535;
                break;
            case -1338868101:
                if (str.equals(QuerySystemEvent.GET_MIN_SCREEN_BRIGHTNESS_VALUE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1197566107:
                if (str.equals("gui.page.close.settings")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1041022623:
                if (str.equals("system.sound.effect.config")) {
                    c = '.';
                    break;
                }
                c = 65535;
                break;
            case -965726840:
                if (str.equals(QuerySystemEvent.GET_CURRENT_SOUND_POSITON)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -884498389:
                if (str.equals(QuerySystemEvent.ISSUPPORT_HEADREST_MODE)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -853478350:
                if (str.equals(QuerySystemEvent.GET_CURRENT_SOUND_SCENE)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -841058592:
                if (str.equals(QuerySystemEvent.GET_MIN_VOLUME_VALUE)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -813486830:
                if (str.equals(QuerySystemEvent.GET_CURRENT_HEADREST_MODE)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -701436316:
                if (str.equals(QuerySystemEvent.GET_MAX_ICM_BRIGHTNESS_VALUE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -681310107:
                if (str.equals("system.lowspeed.analogtone.min.value")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -668448824:
                if (str.equals(QuerySystemEvent.GET_INTELLIGENT_DARK_LIGHT_ADAPT_STATUS)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -620454324:
                if (str.equals("system.lowspeed.analogtone.current.value")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -540074940:
                if (str.equals(QuerySystemEvent.GET_CUR_VOLUME)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -288490975:
                if (str.equals(QuerySystemEvent.GET_AUTO_SCREEN_STATUS)) {
                    c = JSONLexer.EOI;
                    break;
                }
                c = 65535;
                break;
            case -183670776:
                if (str.equals(QuerySystemEvent.GET_CURRENT_SOUND_EFFECT_STYLE)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -45366519:
                if (str.equals("system.rear.screen.status")) {
                    c = '1';
                    break;
                }
                c = 65535;
                break;
            case 71042258:
                if (str.equals(QuerySystemEvent.GET_MIN_ICM_BRIGHTNESS_VALUE)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 104360056:
                if (str.equals("gui.page.open.state.settings")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 141423371:
                if (str.equals("system.immerse.sound.support")) {
                    c = '\'';
                    break;
                }
                c = 65535;
                break;
            case 148702545:
                if (str.equals(QuerySystemEvent.GET_TIPS_VOLUME)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 419703057:
                if (str.equals("system.bluetooth.status")) {
                    c = '-';
                    break;
                }
                c = 65535;
                break;
            case 622742825:
                if (str.equals("system.get.bluetooth.status")) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 948401742:
                if (str.equals("bluetooth.get.connect.status")) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            case 1131894506:
                if (str.equals("system.psn.screen.brightness.max.value")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 1149339315:
                if (str.equals("query.phone.connect.devices")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case 1223644537:
                if (str.equals("system.position.reminder.support")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case 1473884338:
                if (str.equals(QuerySystemEvent.GET_SYSTEM_MUSIC_ISPLAYING)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1521446945:
                if (str.equals(QuerySystemEvent.ISSUPPORT_SOUND_EFFECT_STYLE)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 1587476973:
                if (str.equals(QuerySystemEvent.GET_SOUND_EFFECT_MODEL)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1606123190:
                if (str.equals(QuerySystemEvent.GET_CUR_ICM_BRIGHTNESS)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1693303161:
                if (str.equals("control.system.sound.external.support")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case 1726540142:
                if (str.equals("system.screen.saver.status")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 1832379462:
                if (str.equals(QuerySystemEvent.ISSUPPORT_SOUND_EFFECT_DYNAUDIO)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 1855323486:
                if (str.equals("system.rear.screen.lock.status")) {
                    c = '2';
                    break;
                }
                c = 65535;
                break;
            case 1904373080:
                if (str.equals("system.psn.screen.brightness.min.value")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurScreenBrightness()));
                return;
            case 1:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getMaxScreenBrightnessValue()));
                return;
            case 2:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getMinScreenBrightnessValue()));
                return;
            case 3:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurIcmBrightness()));
                return;
            case 4:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getMaxIcmBrightnessValue()));
                return;
            case 5:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getMinIcmBrightnessValue()));
                return;
            case 6:
                int gsonInt = getGsonInt(VOLUME_KEY, str2);
                if (gsonInt == -1) {
                    Logs.d("SpeechObserver GET_CUR_VOLUME streamType -1");
                    gsonInt = 3;
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurVolume(gsonInt)));
                return;
            case 7:
                int gsonInt2 = getGsonInt(VOLUME_KEY, str2);
                if (gsonInt2 == -1) {
                    Logs.d("SpeechObserver GET_MAX_VOLUME_VALUE streamType -1");
                    gsonInt2 = 3;
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getMaxVolumeValue(gsonInt2)));
                return;
            case '\b':
                int gsonInt3 = getGsonInt(VOLUME_KEY, str2);
                if (gsonInt3 == -1) {
                    Logs.d("SpeechObserver GET_MIN_VOLUME_VALUE streamType -1");
                    gsonInt3 = 3;
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getMinVolumeValue(gsonInt3)));
                return;
            case '\t':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getTipsVolume()));
                return;
            case '\n':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getGuiPageOpenState(str2)));
                return;
            case 11:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getUnityPageOpenState(str2)));
                return;
            case '\f':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getUnityPageShowState(str2)));
                return;
            case '\r':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.closeUnityPage(str2)));
                return;
            case 14:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurrentMusicActive()));
                return;
            case 15:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurrentSoundEffectMode()));
                return;
            case 16:
                int gsonInt4 = getGsonInt(MODE_KEY, str2);
                if (gsonInt4 == -1) {
                    Logs.d("SpeechObserver error MODE -1 ");
                    gsonInt4 = 2;
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.isSupportSoundEffectMode(gsonInt4)));
                return;
            case 17:
                int gsonInt5 = getGsonInt("style", str2);
                if (gsonInt5 == -1) {
                    Logs.d("SpeechObserver error style -1 ");
                    gsonInt5 = 0;
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.isSupportSoundEffectStyle(gsonInt5)));
                return;
            case 18:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurrentSoundEffectStyle()));
                return;
            case 19:
                int gsonInt6 = getGsonInt("scene", str2);
                if (gsonInt6 == -1) {
                    Logs.d("SpeechObserver error scene -1 ");
                    gsonInt6 = 1;
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.isSupportSoundEffectScene(gsonInt6)));
                return;
            case 20:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurrentSoundEffectScene()));
                return;
            case 21:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.isSupportHeadsetMode()));
                return;
            case 22:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurrentHeadsetMode()));
                return;
            case 23:
                int gsonInt7 = getGsonInt("direction", str2);
                if (gsonInt7 == -1) {
                    Logs.d("SpeechObserver error direction -1 ");
                    gsonInt7 = 1;
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.isSupportSoundEffectField(gsonInt7)));
                return;
            case 24:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurrentSoundEffectField()));
                return;
            case 25:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getScreenSaverStatus()));
                return;
            case 26:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getAutoScreenStatus()));
                return;
            case 27:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurrentNedcStatus()));
                return;
            case 28:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getIntelligentDarkLightAdaptStatus()));
                return;
            case 29:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getLowSpeedAnalogtoneCurrentValue()));
                return;
            case 30:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getLowSpeedAnalogtoneMaxValue()));
                return;
            case 31:
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getLowSpeedAnalogtoneMinValue()));
                return;
            case ' ':
                int gsonInt8 = getGsonInt(MODE_KEY, str2);
                if (gsonInt8 == -1) {
                    Logs.d("SpeechObserver error direction -1 ");
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getXpLabSupport(gsonInt8)));
                return;
            case '!':
                int gsonInt9 = getGsonInt(MODE_KEY, str2);
                if (gsonInt9 == -1) {
                    Logs.d("SpeechObserver error direction -1 ");
                }
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getSignalLossFrameState(gsonInt9)));
                return;
            case '\"':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getSentryModeValue()));
                return;
            case '#':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurPsnScreenBrightness()));
                return;
            case '$':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getMaxPsnScreenBrightnessValue()));
                return;
            case '%':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getMinPsnScreenBrightnessValue()));
                return;
            case '&':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getPositionReminderSupport()));
                return;
            case '\'':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getImmerseSoundSupport()));
                return;
            case '(':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getImmerseSoundCurrentMode()));
                return;
            case ')':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getSupportSystemSoundExternal()));
                return;
            case '*':
                reply(str, str3, this.mSpeechSystemCaller.getBondedDevice());
                return;
            case '+':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getBluetoothStatus()));
                return;
            case ',':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getBluetoothConnectStatus(mac)));
                return;
            case '-':
                reply(str, str3, Boolean.valueOf(this.mSpeechSystemCaller.getBluetoothSwitchStatus()));
                return;
            case '.':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getSoundEffectConfig()));
                return;
            case '/':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getCurrentSoundEffectEqualizerMode()));
                return;
            case '0':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getRearScreenSupport()));
                return;
            case '1':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getRearScreenStatus()));
                return;
            case '2':
                reply(str, str3, Integer.valueOf(this.mSpeechSystemCaller.getRearScreenLockStatus()));
                return;
            default:
                return;
        }
    }

    private void reply(String str, String str2, Object obj) {
        Logs.d("SpeechObserver reply result:" + obj);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || obj == null) {
            return;
        }
        try {
            ApiRouter.route(Uri.parse(str2).buildUpon().appendQueryParameter("result", new SpeechResult(str, obj).toString()).build());
        } catch (RemoteException e) {
            Logs.d("SpeechObserverreply: " + e.getMessage());
        }
    }

    public static void sendToPrivateCustom(String str, String str2) {
        Logs.d("SpeechObserver send event:" + str);
        try {
            ApiRouter.route(new Uri.Builder().authority("com.xiaopeng.privatecustomservice.APIRouterObserver").path("onEvent").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, str).appendQueryParameter(CacheEntity.DATA, str2).build());
        } catch (RemoteException e) {
            Logs.d("SpeechObserverremote exception : " + e.getMessage());
        }
    }

    public static void sendToPrivateCustomNoData(String str) {
        sendToPrivateCustom(str, "");
    }

    private int getGsonInt(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            Logs.d("SpeechObserver getGsonInt error data null");
            return -1;
        }
        try {
            return new JSONObject(str2).optInt(str);
        } catch (JSONException e) {
            e.printStackTrace();
            Logs.d("SpeechObserver getGsonInt data:" + str2);
            return -1;
        }
    }

    private int getSource(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            return Integer.parseInt(new JSONObject(str).optString(BuriedPointUtils.SOURCE_KEY));
        } catch (Exception e) {
            Logs.d("source: " + e.getMessage());
            return 0;
        }
    }

    private String getMac(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return new JSONObject(str).optString("mac");
        } catch (Exception e) {
            Logs.d("source: " + e.getMessage());
            return null;
        }
    }

    /* loaded from: classes.dex */
    public class SpeechResult {
        private String event;
        private Object result;

        public SpeechResult(String str, Object obj) {
            this.event = str;
            this.result = obj;
        }

        public String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(NotificationCompat.CATEGORY_EVENT, this.event);
                jSONObject.put("result", this.result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jSONObject.toString();
        }
    }
}
