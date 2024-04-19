package com.xiaopeng.car.settingslibrary.service.work.dialog;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;
/* loaded from: classes.dex */
public class XDialogInfo implements Parcelable {
    public static final Parcelable.Creator<XDialogInfo> CREATOR = new Parcelable.Creator<XDialogInfo>() { // from class: com.xiaopeng.car.settingslibrary.service.work.dialog.XDialogInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public XDialogInfo createFromParcel(Parcel parcel) {
            return new XDialogInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public XDialogInfo[] newArray(int i) {
            return new XDialogInfo[i];
        }
    };
    private static final String EXTRA_CANCELABLE = "cancelable";
    private static final String EXTRA_DURATION = "duration";
    private static final String EXTRA_DURATION_TYPE = "duration.type";
    private static final String EXTRA_ID = "id";
    private static final String EXTRA_MSG = "msg";
    private static final String EXTRA_NEGATIVE_BUTTON = "negativeButton";
    private static final String EXTRA_NEGATIVE_INTENT = "negativeButton.click";
    private static final String EXTRA_POSITIVE_Button = "positiveButton";
    private static final String EXTRA_POSITIVE_INTENT = "positiveButton.click";
    private static final String EXTRA_TITLE = "title";
    public boolean cancelable;
    public int duration;
    public int durationType;
    public String id;
    public String msg;
    public String negativeButton;
    public PendingIntent negativeIntent;
    public String positiveButton;
    public PendingIntent positiveIntent;
    public String title;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private XDialogInfo() {
    }

    private XDialogInfo(Parcel parcel) {
        this.id = parcel.readStringNoHelper();
        this.title = parcel.readStringNoHelper();
        this.msg = parcel.readStringNoHelper();
        this.positiveButton = parcel.readStringNoHelper();
        this.negativeButton = parcel.readStringNoHelper();
        this.durationType = parcel.readInt();
        this.duration = parcel.readInt();
        this.cancelable = parcel.readByte() != 0;
        this.positiveIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
        this.negativeIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
    }

    public static XDialogInfo parse(Intent intent) {
        String stringExtra = intent.getStringExtra("id");
        String stringExtra2 = intent.getStringExtra("msg");
        if (TextUtils.isEmpty(stringExtra) || TextUtils.isEmpty(stringExtra2)) {
            return null;
        }
        XDialogInfo xDialogInfo = new XDialogInfo();
        xDialogInfo.id = stringExtra;
        xDialogInfo.title = intent.getStringExtra("title");
        xDialogInfo.msg = stringExtra2;
        xDialogInfo.positiveButton = intent.getStringExtra(EXTRA_POSITIVE_Button);
        xDialogInfo.negativeButton = intent.getStringExtra(EXTRA_NEGATIVE_BUTTON);
        xDialogInfo.cancelable = intent.getBooleanExtra(EXTRA_CANCELABLE, true);
        xDialogInfo.durationType = intent.getIntExtra(EXTRA_DURATION_TYPE, -1);
        xDialogInfo.duration = intent.getIntExtra(EXTRA_DURATION, -1);
        xDialogInfo.positiveIntent = (PendingIntent) intent.getParcelableExtra(EXTRA_POSITIVE_INTENT);
        xDialogInfo.negativeIntent = (PendingIntent) intent.getParcelableExtra(EXTRA_NEGATIVE_INTENT);
        if (xDialogInfo.duration > 90) {
            xDialogInfo.duration = 90;
        }
        if (TextUtils.isEmpty(xDialogInfo.positiveButton) && TextUtils.isEmpty(xDialogInfo.negativeButton)) {
            xDialogInfo.cancelable = true;
        }
        return xDialogInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringNoHelper(this.id);
        parcel.writeStringNoHelper(this.title);
        parcel.writeStringNoHelper(this.msg);
        parcel.writeStringNoHelper(this.positiveButton);
        parcel.writeStringNoHelper(this.negativeButton);
        parcel.writeInt(this.durationType);
        parcel.writeInt(this.duration);
        parcel.writeByte(this.cancelable ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.positiveIntent, i);
        parcel.writeParcelable(this.negativeIntent, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.id, ((XDialogInfo) obj).id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }

    public String toString() {
        return "XDialogInfo{id='" + this.id + "', msg='" + this.msg + "'}";
    }
}
