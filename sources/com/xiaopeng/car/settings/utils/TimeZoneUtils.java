package com.xiaopeng.car.settings.utils;

import android.content.Context;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes.dex */
public class TimeZoneUtils {
    public static String getDisplayName(Context context, String str) {
        if (str.contains(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
            String[] split = str.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
            StringBuilder sb = new StringBuilder();
            for (String str2 : split) {
                if (sb.length() > 0) {
                    sb.append(MqttTopic.TOPIC_LEVEL_SEPARATOR);
                }
                int identifier = context.getResources().getIdentifier("tz_" + str2.toLowerCase(), TypedValues.Custom.S_STRING, context.getPackageName());
                if (identifier != 0) {
                    sb.append(context.getResources().getString(identifier));
                } else {
                    sb.append(str2);
                }
            }
            return sb.toString();
        }
        int identifier2 = context.getResources().getIdentifier("tz_" + str, TypedValues.Custom.S_STRING, context.getPackageName());
        return identifier2 != 0 ? context.getResources().getString(identifier2) : str;
    }
}
