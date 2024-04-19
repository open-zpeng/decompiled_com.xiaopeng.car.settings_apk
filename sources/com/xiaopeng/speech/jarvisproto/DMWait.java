package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class DMWait extends JarvisProto {
    public static final String EVENT = "jarvis.dm.wait";
    public static final String STATUS_END = "end";
    public static final String STATUS_END_ASR = "end_asr";
    public static final String STATUS_ENTER = "enter";
    public static final String STATUS_FEED_NLU = "feednlu";
    public static final String STATUS_INVALID_SPEECH = "invalid_speech";
    public static final String STATUS_START_ASR = "start_asr";
    public static final String STATUS_TIME = "timeout";
    public static final String STATUS_TTS_END = "tts_end";
    public static final String STATUS_UPDATE = "update";
    public String expression;
    public String reason;
    public String sessionId;
    public int soundArea;
    public boolean speaking;
    public String tips;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    public DMWait() {
    }

    public DMWait(String str, String str2, String str3, String str4, boolean z) {
        this.reason = str;
        this.tips = str3;
        this.sessionId = str2;
        this.expression = str4;
        this.speaking = z;
    }

    public DMWait(String str, String str2, String str3, String str4, boolean z, int i) {
        this.reason = str;
        this.tips = str3;
        this.sessionId = str2;
        this.expression = str4;
        this.speaking = z;
        this.soundArea = i;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("reason", this.reason);
            jSONObject.put("tips", this.tips);
            jSONObject.put("sessionId", this.sessionId);
            jSONObject.put("expression", this.expression);
            jSONObject.put("speaking", this.speaking);
            jSONObject.put("soundArea", this.soundArea);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static DMWait fromJson(String str) {
        String str2;
        String str3;
        int i;
        String str4 = "";
        String str5 = DMEnd.REASON_NORMAL;
        boolean z = false;
        try {
            JSONObject jSONObject = new JSONObject(str);
            str5 = jSONObject.optString("reason");
            str2 = jSONObject.optString("tips");
            try {
                str3 = jSONObject.optString("sessionId");
                try {
                    str4 = jSONObject.optString("expression");
                    z = jSONObject.optBoolean("speaking");
                    i = jSONObject.optInt("soundArea");
                } catch (JSONException e) {
                    e = e;
                    e.printStackTrace();
                    i = -1;
                    return new DMWait(str5, str3, str2, str4, z, i);
                }
            } catch (JSONException e2) {
                e = e2;
                str3 = "";
            }
        } catch (JSONException e3) {
            e = e3;
            str2 = "";
            str3 = str2;
        }
        return new DMWait(str5, str3, str2, str4, z, i);
    }
}
