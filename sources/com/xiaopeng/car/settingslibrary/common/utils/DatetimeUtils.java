package com.xiaopeng.car.settingslibrary.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/* loaded from: classes.dex */
public class DatetimeUtils {
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_TIME_HOUR = "HH:mm";
    public static final String FORMAT_DATE_TIME_SHORT = "yyyy-MM-dd";

    public static String getTimeFrom(int i) {
        String str;
        String str2;
        if (i <= 0) {
            return "00:00";
        }
        int i2 = i / 1000;
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        if (i3 >= 10) {
            str = String.valueOf(i3);
        } else {
            str = "0" + String.valueOf(i3);
        }
        if (i4 >= 10) {
            str2 = String.valueOf(i4);
        } else {
            str2 = "0" + String.valueOf(i4);
        }
        return str + ":" + str2;
    }

    public static long dateToTimeStamp(String str) {
        try {
            return new SimpleDateFormat(FORMAT_DATE_TIME).parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static long getStartDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTimeInMillis();
    }
}
