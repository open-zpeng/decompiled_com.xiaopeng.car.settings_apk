package com.xiaopeng.car.settingslibrary.manager.emergency;

import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.GsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class WifiKeyParser {
    public static final String MESSAGE_END = "\n";
    public static final String MESSAGE_HEADER = "WIFI_BLE_EMER";
    public static final String MESSAGE_RETURN_FAILED = "failed";
    public static final String MESSAGE_RETURN_SUCCESS = "succeed";
    public static final int MESSAGE_SEGMENT3_LEN = 6;
    public static final String MESSAGE_SPLIT = "-";
    public static final String TAG = "WifiKeyParser";

    public static String parse(String str) {
        if (TextUtils.isEmpty(str)) {
            Logs.d("WifiKeyParser data empty!");
            return "";
        } else if (!str.startsWith(MESSAGE_HEADER)) {
            Logs.d("WifiKeyParser not wifi key message! start error");
            return "";
        } else if (!str.endsWith(MESSAGE_END)) {
            Logs.d("WifiKeyParser not wifi key message! end error");
            return "";
        } else {
            try {
                String[] split = str.split(MESSAGE_SPLIT);
                if (split != null && split.length >= 3) {
                    String str2 = split[1];
                    String substring = split[2].substring(0, split[2].length() - 1);
                    int parseInt = Integer.parseInt(str2, 16);
                    if (TextUtils.isEmpty(substring) || parseInt == substring.length()) {
                        Logs.d("WifiKeyParser keyLen:" + str2 + " keyContent:" + substring + " keyLength:" + parseInt);
                        return substring;
                    }
                    return "";
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
            return "";
        }
    }

    public static String parseContent(String str) {
        if (TextUtils.isEmpty(str)) {
            Logs.d("WifiKeyParser data empty!");
            return "";
        } else if (!str.startsWith("WIFI_BLE_EMER-")) {
            Logs.d("WifiKeyParser not wifi key message! start error");
            return "";
        } else if (!str.endsWith(MESSAGE_END)) {
            Logs.d("WifiKeyParser not wifi key message! end error");
            return "";
        } else {
            try {
                String substring = str.substring(14, str.length());
                if (TextUtils.isEmpty(substring)) {
                    Logs.d("WifiKeyParser parse error ");
                    return "";
                }
                String substring2 = substring.substring(0, 6);
                if (TextUtils.isEmpty(substring2)) {
                    Logs.d("WifiKeyParser parse error lenHexStr");
                    return "";
                } else if (substring2.length() != 6) {
                    Logs.d("WifiKeyParser not wifi key message! length error:" + substring2);
                    return "";
                } else {
                    String substring3 = substring.substring(6, 7);
                    if (!MESSAGE_SPLIT.equals(substring3)) {
                        Logs.d("WifiKeyParser not wifi key message! split - error:" + substring3);
                        return "";
                    }
                    String substring4 = substring.substring(7, substring.length() - 1);
                    if (TextUtils.isEmpty(substring4)) {
                        Logs.d("WifiKeyParser parse error content");
                        return "";
                    } else if (!GsonUtils.isJson(substring4)) {
                        Logs.d("WifiKeyParser parse error content not json! " + substring4);
                        return "";
                    } else {
                        int parseInt = Integer.parseInt(substring2, 16);
                        if (parseInt != substring4.length()) {
                            Logs.d("WifiKeyParser not wifi key message! content length error");
                            return "";
                        }
                        Logs.d("WifiKeyParser keyLen:" + substring2 + " keyLength:" + parseInt + " keyContent:" + substring4);
                        return substring4;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                Logs.d("WifiKeyParser parse error IndexOutOfBoundsException");
                e.printStackTrace();
                return "";
            } catch (NumberFormatException e2) {
                Logs.d("WifiKeyParser parse error NumberFormatException");
                e2.printStackTrace();
                return "";
            }
        }
    }

    public static String returnMsg(String str) {
        return generateMsg(!TextUtils.isEmpty(str) ? "succeed" : MESSAGE_RETURN_FAILED);
    }

    public static String generateMsg(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        String hexString = Integer.toHexString(str.length());
        if (TextUtils.isEmpty(hexString)) {
            return "";
        }
        int length = 6 - hexString.length();
        if (length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append("0");
            }
            hexString = sb.toString() + hexString;
        } else {
            Logs.d("WifiKeyParser generateMsg error too long");
        }
        return "WIFI_BLE_EMER-" + hexString + MESSAGE_SPLIT + str + MESSAGE_END;
    }
}
