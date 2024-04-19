package com.nforetek.util.normal;

import android.util.Log;
/* loaded from: classes.dex */
public final class NfLog {
    private static boolean D = true;
    private static boolean E = true;
    private static boolean I = true;
    private static boolean V = true;
    private static boolean W = true;

    public static void setVerbose(boolean z) {
        V = z;
    }

    public static void setDebug(boolean z) {
        D = z;
    }

    public static void setInfo(boolean z) {
        I = z;
    }

    public static void setWarn(boolean z) {
        W = z;
    }

    public static void setError(boolean z) {
        E = z;
    }

    public static int v(String str, String str2) {
        if (V) {
            return Log.v(str, str2);
        }
        return -1;
    }

    public static int v(String str, String str2, Throwable th) {
        if (V) {
            return Log.v(str, str2, th);
        }
        return -1;
    }

    public static int d(String str, String str2) {
        if (D) {
            return Log.d(str, str2);
        }
        return -1;
    }

    public static int d(String str, String str2, Throwable th) {
        if (D) {
            return Log.d(str, str2, th);
        }
        return -1;
    }

    public static int i(String str, String str2) {
        if (I) {
            return Log.i(str, str2);
        }
        return -1;
    }

    public static int i(String str, String str2, Throwable th) {
        if (I) {
            return Log.i(str, str2, th);
        }
        return -1;
    }

    public static int w(String str, String str2) {
        if (W) {
            return Log.w(str, str2);
        }
        return -1;
    }

    public static int w(String str, String str2, Throwable th) {
        if (W) {
            return Log.w(str, str2);
        }
        return -1;
    }

    public static int w(String str, Throwable th) {
        if (W) {
            return Log.w(str, th);
        }
        return -1;
    }

    public static int e(String str, String str2) {
        if (E) {
            return Log.e(str, str2);
        }
        return -1;
    }

    public static int e(String str, String str2, Throwable th) {
        if (E) {
            return Log.e(str, str2, th);
        }
        return -1;
    }
}
