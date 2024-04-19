package com.nforetek.bt.aidl;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes.dex */
public final class NfHfpClientCall implements Parcelable {
    public static final int CALL_STATE_ACTIVE = 0;
    public static final int CALL_STATE_ALERTING = 3;
    public static final int CALL_STATE_DIALING = 2;
    public static final int CALL_STATE_HELD = 1;
    public static final int CALL_STATE_HELD_BY_RESPONSE_AND_HOLD = 6;
    public static final int CALL_STATE_INCOMING = 4;
    public static final int CALL_STATE_TERMINATED = 7;
    public static final int CALL_STATE_WAITING = 5;
    public static final Parcelable.Creator<NfHfpClientCall> CREATOR = new Parcelable.Creator<NfHfpClientCall>() { // from class: com.nforetek.bt.aidl.NfHfpClientCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NfHfpClientCall createFromParcel(Parcel parcel) {
            return new NfHfpClientCall(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt() == 1, parcel.readInt() == 1);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NfHfpClientCall[] newArray(int i) {
            return new NfHfpClientCall[i];
        }
    };
    private final int mId;
    private boolean mMultiParty;
    private String mNumber;
    private final boolean mOutgoing;
    private int mState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NfHfpClientCall(int i, int i2, String str, boolean z, boolean z2) {
        this.mId = i;
        this.mState = i2;
        this.mNumber = str == null ? "" : str;
        this.mMultiParty = z;
        this.mOutgoing = z2;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public void setNumber(String str) {
        this.mNumber = str;
    }

    public void setMultiParty(boolean z) {
        this.mMultiParty = z;
    }

    public int getId() {
        return this.mId;
    }

    public int getState() {
        return this.mState;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public boolean isMultiParty() {
        return this.mMultiParty;
    }

    public boolean isOutgoing() {
        return this.mOutgoing;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NfHeadsetClientCall{mId: ");
        sb.append(this.mId);
        sb.append(", mState: ");
        int i = this.mState;
        switch (i) {
            case 0:
                sb.append("ACTIVE");
                break;
            case 1:
                sb.append("HELD");
                break;
            case 2:
                sb.append("DIALING");
                break;
            case 3:
                sb.append("ALERTING");
                break;
            case 4:
                sb.append("INCOMING");
                break;
            case 5:
                sb.append("WAITING");
                break;
            case 6:
                sb.append("HELD_BY_RESPONSE_AND_HOLD");
                break;
            case 7:
                sb.append("TERMINATED");
                break;
            default:
                sb.append(i);
                break;
        }
        sb.append(", mNumber: ");
        sb.append(this.mNumber);
        sb.append(", mMultiParty: ");
        sb.append(this.mMultiParty);
        sb.append(", mOutgoing: ");
        sb.append(this.mOutgoing);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mState);
        parcel.writeString(this.mNumber);
        parcel.writeInt(this.mMultiParty ? 1 : 0);
        parcel.writeInt(this.mOutgoing ? 1 : 0);
    }
}
