package com.nforetek.bt.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaopeng.car.settingslibrary.manager.emergency.WifiKeyParser;
/* loaded from: classes.dex */
public class NfPbapContact implements Parcelable {
    public static final int ADDRESS_TYPE_DOM = 1;
    public static final int ADDRESS_TYPE_HOME = 6;
    public static final int ADDRESS_TYPE_INTL = 2;
    public static final int ADDRESS_TYPE_NULL = 0;
    public static final int ADDRESS_TYPE_PARCEL = 4;
    public static final int ADDRESS_TYPE_POSTAL = 3;
    public static final int ADDRESS_TYPE_WORK = 5;
    public static final Parcelable.Creator<NfPbapContact> CREATOR = new Parcelable.Creator<NfPbapContact>() { // from class: com.nforetek.bt.aidl.NfPbapContact.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NfPbapContact createFromParcel(Parcel parcel) {
            return new NfPbapContact(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.createIntArray(), parcel.createStringArray(), parcel.readInt(), parcel.createByteArray(), parcel.createIntArray(), parcel.createStringArray(), parcel.createIntArray(), parcel.createStringArray(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NfPbapContact[] newArray(int i) {
            return new NfPbapContact[i];
        }
    };
    public static final int EMAIL_TYPE_HOME = 5;
    public static final int EMAIL_TYPE_INTERNET = 2;
    public static final int EMAIL_TYPE_NULL = 0;
    public static final int EMAIL_TYPE_PREF = 1;
    public static final int EMAIL_TYPE_WORK = 4;
    public static final int EMAIL_TYPE_X400 = 3;
    public static final int NUMBER_TYPE_CELL = 7;
    public static final int NUMBER_TYPE_FAX = 5;
    public static final int NUMBER_TYPE_HOME = 3;
    public static final int NUMBER_TYPE_MSG = 6;
    public static final int NUMBER_TYPE_NULL = 0;
    public static final int NUMBER_TYPE_PAGER = 8;
    public static final int NUMBER_TYPE_PREF = 1;
    public static final int NUMBER_TYPE_VOICE = 4;
    public static final int NUMBER_TYPE_WORK = 2;
    public static final int STORAGE_TYPE_CALL_LOGS = 8;
    public static final int STORAGE_TYPE_DIALED_CALLS = 7;
    public static final int STORAGE_TYPE_FAVORITE = 4;
    public static final int STORAGE_TYPE_MISSED_CALLS = 5;
    public static final int STORAGE_TYPE_PHONE_MEMORY = 2;
    public static final int STORAGE_TYPE_RECEIVED_CALLS = 6;
    public static final int STORAGE_TYPE_SIM = 1;
    public static final int STORAGE_TYPE_SPEEDDIAL = 3;
    private final String[] mAddress;
    private final int[] mAddressType;
    private final String[] mEmail;
    private final int[] mEmailType;
    private final String mFirstName;
    private final String mLastName;
    private final String mMiddleName;
    private final String[] mNumber;
    private final String mOrg;
    private final int[] mPhoneType;
    private final byte[] mPhoto;
    private final int mPhotoType;
    private final String mRemoteAddress;
    private final int mStorageType;

    private String getAddressTypeString(int i) {
        switch (i) {
            case 0:
                return "Null";
            case 1:
                return "Pref";
            case 2:
                return "International";
            case 3:
                return "Postal";
            case 4:
                return "Parcel";
            case 5:
                return "Work";
            case 6:
                return "Home";
            default:
                return "Unknown";
        }
    }

    private String getEmailTypeString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "Unknown" : "Home" : "Work" : "X400" : "Internet" : "Pref" : "Null";
    }

    private String getNumberTypeString(int i) {
        switch (i) {
            case 0:
                return "Null";
            case 1:
                return "Pref";
            case 2:
                return "Work";
            case 3:
                return "Home";
            case 4:
                return "Voice";
            case 5:
                return "Fax";
            case 6:
                return "Msg";
            case 7:
                return "Cell";
            case 8:
                return "Pager";
            default:
                return "Unknown";
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NfPbapContact(String str, int i, String str2, String str3, String str4, int[] iArr, String[] strArr, int i2, byte[] bArr, int[] iArr2, String[] strArr2, int[] iArr3, String[] strArr3, String str5) {
        this.mRemoteAddress = str;
        this.mStorageType = i;
        this.mFirstName = str2;
        this.mMiddleName = str3;
        this.mLastName = str4;
        this.mPhoneType = iArr;
        this.mNumber = strArr;
        this.mPhotoType = i2;
        this.mPhoto = (bArr == null || bArr.length <= 0) ? null : null;
        this.mEmailType = iArr2;
        this.mEmail = strArr2;
        this.mAddressType = iArr3;
        this.mAddress = strArr3;
        this.mOrg = str5;
    }

    public String getRemoteAddress() {
        return this.mRemoteAddress;
    }

    public int getStorageType() {
        return this.mStorageType;
    }

    public String getFirstName() {
        return this.mFirstName;
    }

    public String getMiddleName() {
        return this.mMiddleName;
    }

    public String getLastName() {
        return this.mLastName;
    }

    public int[] getPhoneTypeArray() {
        return this.mPhoneType;
    }

    public String[] getNumberArray() {
        return this.mNumber;
    }

    public int getPhotoType() {
        return this.mPhotoType;
    }

    public byte[] getPhotoByteArray() {
        return this.mPhoto;
    }

    public int[] getEmailTypeArray() {
        return this.mEmailType;
    }

    public String[] getEmailArray() {
        return this.mEmail;
    }

    public int[] getAddressType() {
        return this.mAddressType;
    }

    public String[] getAddressArray() {
        return this.mAddress;
    }

    public String getOrg() {
        return this.mOrg;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("===NfPbapContact ");
        int i = this.mStorageType;
        switch (i) {
            case 1:
                sb.append("Sim");
                break;
            case 2:
                sb.append("Memory");
                break;
            case 3:
                sb.append("Speed Dial");
                break;
            case 4:
                sb.append("Favorite");
                break;
            case 5:
                sb.append("Missed Calls");
                break;
            case 6:
                sb.append("Received Calls");
                break;
            case 7:
                sb.append("Dialed Calls");
                break;
            case 8:
                sb.append("Combine Calllogs");
                break;
            default:
                sb.append(i);
                break;
        }
        sb.append("===\n   RemoteAddress: ");
        sb.append(String.valueOf(this.mRemoteAddress) + WifiKeyParser.MESSAGE_END);
        sb.append("   Name: " + this.mFirstName + "|" + this.mMiddleName + "|" + this.mLastName + WifiKeyParser.MESSAGE_END);
        if (this.mNumber != null) {
            for (int i2 = 0; i2 < this.mNumber.length; i2++) {
                sb.append("   Number: (" + getNumberTypeString(this.mPhoneType[i2]) + ") " + this.mNumber[i2] + WifiKeyParser.MESSAGE_END);
            }
        }
        byte[] bArr = this.mPhoto;
        if (bArr != null && bArr.length > 0) {
            sb.append("   Photo: yes\n");
        } else {
            sb.append("   Photo: no\n");
        }
        if (this.mEmail != null) {
            for (int i3 = 0; i3 < this.mEmail.length; i3++) {
                sb.append("   e-mail: (" + getEmailTypeString(this.mEmailType[i3]) + ") " + this.mEmail[i3] + WifiKeyParser.MESSAGE_END);
            }
        }
        if (this.mAddress != null) {
            for (int i4 = 0; i4 < this.mAddress.length; i4++) {
                sb.append("   Address: (" + getAddressTypeString(this.mAddressType[i4]) + ") " + this.mAddress[i4] + WifiKeyParser.MESSAGE_END);
            }
        }
        if (this.mOrg != null) {
            sb.append("   Org: " + this.mOrg + WifiKeyParser.MESSAGE_END);
        }
        int i5 = 32;
        switch (this.mStorageType) {
            case 1:
                i5 = 23;
                break;
            case 2:
                i5 = 26;
                break;
            case 3:
                i5 = 30;
                break;
            case 4:
                i5 = 28;
                break;
            case 5:
            case 7:
                break;
            case 6:
                i5 = 34;
                break;
            case 8:
                i5 = 36;
                break;
            default:
                i5 = 21;
                break;
        }
        for (int i6 = 0; i6 < i5; i6++) {
            sb.append("=");
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mRemoteAddress);
        parcel.writeInt(this.mStorageType);
        parcel.writeString(this.mFirstName);
        parcel.writeString(this.mMiddleName);
        parcel.writeString(this.mLastName);
        parcel.writeIntArray(this.mPhoneType);
        parcel.writeStringArray(this.mNumber);
        parcel.writeInt(this.mPhotoType);
        byte[] bArr = this.mPhoto;
        if (bArr == null) {
            bArr = new byte[0];
        }
        parcel.writeByteArray(bArr);
        parcel.writeIntArray(this.mEmailType);
        parcel.writeStringArray(this.mEmail);
        parcel.writeIntArray(this.mAddressType);
        parcel.writeStringArray(this.mAddress);
        parcel.writeString(this.mOrg);
    }
}
