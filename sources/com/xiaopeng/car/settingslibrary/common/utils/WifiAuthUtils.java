package com.xiaopeng.car.settingslibrary.common.utils;

import android.util.Base64;
import com.nforetek.bt.res.NfDef;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.compress.archivers.tar.TarConstants;
/* loaded from: classes.dex */
public class WifiAuthUtils {
    private static final String salt_s = "10EE63A8CA6D8E50";
    private static final byte[] salt = {NfDef.AVRCP_BROWSING_STATUS_SEARCH_NOT_SUPPORT, -18, 99, -88, -54, 109, -114, 80};
    private static final byte[] aes_cbc_key1 = {NfDef.AVRCP_BROWSING_STATUS_PLAYER_NOT_BROWSABLE, -74, 56, 12, 67, -68, 27, -59, 89, 30, 78, 72, -42, -69, -3, 64, -85, 34, -69, 40, 45, 94, 106, -98, 29, 82, 6, -7, NfDef.AVRCP_BROWSING_STATUS_NO_RESAULTS, 41, -76, 79};
    private static final byte[] aes_vector1 = {NfDef.AVRCP_BROWSING_STATUS_SEARCH_IN_PROGRESS, -104, -102, -86, 24, -17, -116, -40, TarConstants.LF_SYMLINK, 34, 86, 72, -81, 119, -99, 119};
    private static final byte[] aes_cbc_key2 = {59, 12, 123, 119, -54, 91, -57, 101, -26, 47, 62, 11, -92, -28, 93, 68, 39, 58, -66, -11, TarConstants.LF_BLK, 57, 99, 69, -72, 40, -116, 102, TarConstants.LF_NORMAL, -96, -8, -114};
    private static final byte[] aes_vector2 = {TarConstants.LF_FIFO, 69, TarConstants.LF_CHR, -32, -7, 115, -125, 124, -50, 39, -29, 98, 113, -9, -108, 107};

    public static byte[] getHashSum(byte[] bArr, String str) {
        try {
            return MessageDigest.getInstance(str).digest(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] aes_cbc_enc(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr3);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(1, new SecretKeySpec(bArr2, "AES"), ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String vin2WifiEssid(String str) {
        try {
            return Base64.encodeToString(aes_cbc_enc(getHashSum((str + salt_s).getBytes(), "MD5"), aes_cbc_key1, aes_vector1), 2).substring(0, 8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String vin2WifiPassword(String str) {
        try {
            byte[] bArr = new byte[16];
            System.arraycopy(getHashSum((str + salt_s).getBytes(), "SHA-1"), 0, bArr, 0, 16);
            return Base64.encodeToString(aes_cbc_enc(bArr, aes_cbc_key2, aes_vector2), 2).substring(0, 8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
