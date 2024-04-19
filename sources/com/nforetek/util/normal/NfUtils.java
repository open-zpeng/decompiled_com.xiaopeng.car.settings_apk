package com.nforetek.util.normal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import com.nforetek.bt.res.NfDef;
import com.xiaopeng.car.settingslibrary.manager.emergency.WifiKeyParser;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
/* loaded from: classes.dex */
public final class NfUtils {
    static final int BD_ADDR_LEN = 6;
    static final String TAG = "NfUtils";
    static int decodeCount;

    public static byte[] getBytesFromAddress(String str) {
        byte[] bArr = new byte[6];
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            if (str.charAt(i) != ':') {
                bArr[i2] = (byte) Integer.parseInt(str.substring(i, i + 2), 16);
                i2++;
                i++;
            }
            i++;
        }
        return bArr;
    }

    public static String getAddressStringFromByte(byte[] bArr) {
        if (bArr == null || bArr.length != 6) {
            return null;
        }
        return String.format("%02X:%02X:%02X:%02X:%02X:%02X", Byte.valueOf(bArr[0]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[3]), Byte.valueOf(bArr[4]), Byte.valueOf(bArr[5]));
    }

    public static String printAppVersion(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "Exception when getting package information !!!");
            packageInfo = null;
        }
        if (packageInfo != null) {
            String str = packageInfo.packageName;
            int i = packageInfo.versionCode;
            String str2 = packageInfo.versionName;
            Log.i(TAG, "+++++++++++++++++++++++++ nFore +++++++++++++++++++++++++");
            Log.i(TAG, "        Package Name : " + str);
            Log.i(TAG, "        Version Code : " + i);
            Log.i(TAG, "        Version Name : " + str2);
            Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return str2;
        }
        Log.e(TAG, "In onCreate() : mPackageInfo is null");
        return "";
    }

    private void dumpBtConfig() {
        String str;
        try {
            str = getStringFromFile("/data/misc/bluedroid/bt_config.xml");
            try {
                Log.e(TAG, "Piggy Check bt_config.xml dump:\n|" + str + "|");
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                parseAddressContent(str, "90:b9:31:14:e3:da");
            }
        } catch (Exception e2) {
            e = e2;
            str = "";
        }
        parseAddressContent(str, "90:b9:31:14:e3:da");
    }

    public static String convertStreamToString(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                sb.append(readLine);
                sb.append(WifiKeyParser.MESSAGE_END);
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }

    public static String getStringFromFile(String str) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        String convertStreamToString = convertStreamToString(fileInputStream);
        fileInputStream.close();
        return convertStreamToString;
    }

    private String parseAddressContent(String str, String str2) {
        String lowerCase = str2.toLowerCase();
        int indexOf = str.indexOf(lowerCase);
        Log.e(TAG, "Piggy Check start: " + indexOf);
        String substring = str.substring(indexOf + (-10), indexOf);
        Log.e(TAG, "Piggy Check temp: |" + substring + "|");
        int indexOf2 = substring.indexOf("<");
        int indexOf3 = substring.indexOf(" ");
        Log.e(TAG, "Piggy Check start: " + indexOf2);
        Log.e(TAG, "Piggy Check end: " + indexOf3);
        if (indexOf3 < indexOf2) {
            substring = substring.substring(indexOf2);
            Log.e(TAG, "Piggy Check temp: |" + substring + "|");
        }
        String substring2 = substring.substring(substring.indexOf("<") + 1, substring.indexOf(" "));
        Log.e(TAG, "Piggy Check addressHeader: |" + substring2 + "|");
        String str3 = "<" + substring2 + " Tag=\"" + lowerCase + "\">";
        String str4 = "</" + substring2 + ">";
        int indexOf4 = str.indexOf(str3);
        int indexOf5 = str.indexOf(str4, indexOf4);
        Log.e(TAG, "Piggy Check start: " + indexOf4);
        Log.e(TAG, "Piggy Check end: " + indexOf5);
        String substring3 = str.substring(indexOf4, str4.length() + indexOf5);
        if (substring3.indexOf("AvrcpVersion") == -1) {
            int indexOf6 = str.indexOf(str4, indexOf5 + 5);
            Log.e(TAG, "Piggy Check start: " + indexOf4);
            Log.e(TAG, "Piggy Check end: " + indexOf6);
            String substring4 = str.substring(indexOf4, indexOf6 + str4.length());
            Log.e(TAG, "Piggy Check result: |" + substring4 + "|");
            return substring4;
        }
        return substring3;
    }

    public static boolean deleteFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
            if (file.exists()) {
                NfLog.e(TAG, "File(" + str + ") delete fail.");
                return false;
            }
            return true;
        }
        NfLog.e(TAG, "File(" + str + ") not exists.");
        return false;
    }

    public static String createPhotoFile(String str, byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return "";
        }
        File file = new File(NfDef.PBAP_PHOTO_FOLDER);
        if (!file.isDirectory()) {
            file.mkdir();
        }
        if (!file.isDirectory()) {
            Log.e(TAG, "Piggy Check can't create folder !!");
            return "";
        }
        File file2 = new File(file, String.valueOf(str) + ".jpeg");
        Log.e(TAG, "Piggy Check Before check readable/writeable");
        file2.setReadable(true);
        Log.e(TAG, "Piggy Check After set readable");
        file2.setWritable(true);
        Log.e(TAG, "Piggy Check After set writeable");
        try {
            file2.createNewFile();
            Log.e(TAG, "Piggy Check File Name : " + file2.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
            bufferedOutputStream.write(bArr);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return file2.getPath();
    }

    public static byte[] processPhoto(byte[] bArr) throws IllegalArgumentException {
        try {
            String str = new String(bArr, "UTF-8");
            NfLog.d(TAG, "processPhoto(): " + str);
            File file = new File(str);
            int length = (int) file.length();
            byte[] bArr2 = new byte[length];
            NfLog.e(TAG, "Piggy Check file.length: " + length);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                bufferedInputStream.read(bArr2, 0, bArr2.length);
                bufferedInputStream.close();
                file.delete();
                NfLog.e(TAG, "Piggy Check bytes.length: " + bArr2.length);
                String str2 = new String(bArr2);
                NfLog.e(TAG, "Piggy Check photoString : " + str2);
                return decodePhoto(bArr2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        } catch (UnsupportedEncodingException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static byte[] decodePhoto(byte[] bArr) throws IllegalArgumentException {
        decodeCount++;
        Log.e(TAG, "Piggy Check decodePhoto() start. count: " + decodeCount);
        try {
            byte[] decode = Base64.decode(bArr, 0);
            if (decode != null) {
                NfLog.d(TAG, "base64_decodeArray length is " + decode.length);
            } else {
                NfLog.d(TAG, "base64_decodeArray is null.");
            }
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            if (decodeByteArray == null) {
                decodeCount--;
                Log.d(TAG, "Piggy Check decodePhoto() bitmap is null. count: " + decodeCount);
                return decode;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            decodeByteArray.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            decodeCount--;
            return byteArrayOutputStream.toByteArray();
        } catch (IllegalArgumentException e) {
            decodeCount--;
            throw e;
        }
    }

    public static boolean isFilePathValid(String str) {
        File file = new File(String.valueOf(Environment.getExternalStorageDirectory().getPath()) + str);
        if (file.isDirectory()) {
            if (file.canWrite()) {
                return true;
            }
            NfLog.e(TAG, "Path: " + str + " can't write.");
            return false;
        }
        NfLog.e(TAG, "Path: " + str + " is not folder.");
        return false;
    }
}
