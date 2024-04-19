package com.xiaopeng.car.settingslibrary.manager.speech;

import com.xiaopeng.car.settingslibrary.common.CarConfigHelper;
import com.xiaopeng.car.settingslibrary.vm.sound.SoundEffectValues;
import java.util.HashMap;
/* loaded from: classes.dex */
public class SoundEffectMaps {
    public static final int NO_SUPPORT = 0;
    public static final int SPEECH_SOUND_EFFECT_DYNAUDIO = 2;
    public static final int SPEECH_SOUND_EFFECT_ROUND = 1;
    public static final int SPEECH_SOUND_FIELD_BACK_CENTER = 5;
    public static final int SPEECH_SOUND_FIELD_BACK_LEFT = 4;
    public static final int SPEECH_SOUND_FIELD_BACK_RIGHT = 6;
    public static final int SPEECH_SOUND_FIELD_DEPUTY = 2;
    public static final int SPEECH_SOUND_FIELD_FRONT_CENTER = 3;
    public static final int SPEECH_SOUND_FIELD_MAIN = 1;
    public static final int SPEECH_SOUND_FIELD_MAIN_DEPUTY_SHARED = 7;
    public static final int SPEECH_SOUND_SCENES_KTV = 3;
    public static final int SPEECH_SOUND_SCENES_LIVE = 2;
    public static final int SPEECH_SOUND_SCENES_RECORDING = 4;
    public static final int SPEECH_SOUND_SCENES_THEATER = 1;
    public static final int SPEECH_SOUND_STYLE_CINEMA = 12;
    public static final int SPEECH_SOUND_STYLE_CLASSICAL = 6;
    public static final int SPEECH_SOUND_STYLE_COMMON = 1;
    public static final int SPEECH_SOUND_STYLE_CUSTOM = 11;
    public static final int SPEECH_SOUND_STYLE_DISCO = 5;
    public static final int SPEECH_SOUND_STYLE_DYNAMIC = 10;
    public static final int SPEECH_SOUND_STYLE_HIFI = 7;
    public static final int SPEECH_SOUND_STYLE_JAZZ = 3;
    public static final int SPEECH_SOUND_STYLE_LIVE = 3;
    public static final int SPEECH_SOUND_STYLE_ORIGINAL = 0;
    public static final int SPEECH_SOUND_STYLE_ORIGINAL_D = 0;
    public static final int SPEECH_SOUND_STYLE_PEOPLE = 2;
    public static final int SPEECH_SOUND_STYLE_REAL = 8;
    public static final int SPEECH_SOUND_STYLE_ROCK = 4;
    public static final int SPEECH_SOUND_STYLE_SOFT = 9;
    public static final int SPEECH_SOUND_STYLE_STRESS = 1;
    public static final int SPEECH_SOUND_STYLE_SURROUND = 4;
    public static final int SPEECH_SOUND_STYLE_VOICE = 2;
    public static final int SUPPORT = 1;
    public static HashMap<Integer, Integer> sSoundEffectModeMap = new HashMap<>();
    public static HashMap<Integer, Integer> sSoundEffectSceneMap;
    public static HashMap<Integer, Integer> sSoundEffectStyleMap;

    public static int convertSpeechToLocal(int i) {
        switch (i) {
            case 1:
            default:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
            case 5:
                return 3;
            case 6:
                return 4;
        }
    }

    public static int convertSpeechToLocalField(int i) {
        if (i != 3) {
            return i != 5 ? 0 : 2;
        }
        return 1;
    }

    public static int convertSpeechToLocalUnity(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 5 ? 0 : 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    static {
        sSoundEffectModeMap.put(1, 1);
        sSoundEffectModeMap.put(2, 3);
        sSoundEffectStyleMap = new HashMap<>();
        sSoundEffectStyleMap.put(0, 0);
        sSoundEffectStyleMap.put(1, 1);
        sSoundEffectStyleMap.put(2, Integer.valueOf(CarConfigHelper.hasHifiSound() ? SoundEffectValues.SOUND_STYLE_4_HIFI : 2));
        sSoundEffectStyleMap.put(3, 3);
        sSoundEffectStyleMap.put(4, 4);
        sSoundEffectStyleMap.put(5, 5);
        sSoundEffectStyleMap.put(6, 6);
        sSoundEffectStyleMap.put(7, 2);
        sSoundEffectStyleMap.put(8, Integer.valueOf(SoundEffectValues.SOUND_STYLE_1_HIFI));
        sSoundEffectStyleMap.put(9, Integer.valueOf(SoundEffectValues.SOUND_STYLE_2_HIFI));
        sSoundEffectStyleMap.put(10, Integer.valueOf(SoundEffectValues.SOUND_STYLE_3_HIFI));
        sSoundEffectSceneMap = new HashMap<>();
        sSoundEffectSceneMap.put(1, 1);
        sSoundEffectSceneMap.put(2, 2);
        sSoundEffectSceneMap.put(4, 4);
        sSoundEffectSceneMap.put(3, 3);
    }
}
