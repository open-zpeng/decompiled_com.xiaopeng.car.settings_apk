package com.nforetek.bt.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
/* loaded from: classes.dex */
public class UsbBluetoothDevice implements Parcelable {
    public static final Parcelable.Creator<UsbBluetoothDevice> CREATOR = new Parcelable.Creator<UsbBluetoothDevice>() { // from class: com.nforetek.bt.aidl.UsbBluetoothDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbBluetoothDevice createFromParcel(Parcel parcel) {
            return new UsbBluetoothDevice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbBluetoothDevice[] newArray(int i) {
            return new UsbBluetoothDevice[i];
        }
    };
    private String mAddress;
    private String mName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UsbBluetoothDevice(String str, String str2) {
        this.mAddress = str;
        this.mName = str2;
    }

    protected UsbBluetoothDevice(Parcel parcel) {
        this.mAddress = parcel.readStringNoHelper();
        this.mName = parcel.readStringNoHelper();
    }

    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringNoHelper(this.mAddress);
        parcel.writeStringNoHelper(this.mName);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mAddress.equals(((UsbBluetoothDevice) obj).mAddress);
    }

    public int hashCode() {
        return Objects.hash(this.mAddress);
    }
}
