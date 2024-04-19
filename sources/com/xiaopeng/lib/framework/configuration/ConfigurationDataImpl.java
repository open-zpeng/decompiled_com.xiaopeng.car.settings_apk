package com.xiaopeng.lib.framework.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfigurationData;
/* loaded from: classes.dex */
public class ConfigurationDataImpl implements IConfigurationData, Parcelable {
    public static final Parcelable.Creator<ConfigurationDataImpl> CREATOR = new Parcelable.Creator<ConfigurationDataImpl>() { // from class: com.xiaopeng.lib.framework.configuration.ConfigurationDataImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationDataImpl createFromParcel(Parcel parcel) {
            return new ConfigurationDataImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationDataImpl[] newArray(int i) {
            return new ConfigurationDataImpl[i];
        }
    };
    private String mKey;
    private String mValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfigurationData
    public String getKey() {
        return this.mKey;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.configurationmodule.IConfigurationData
    public String getValue() {
        return this.mValue;
    }

    public ConfigurationDataImpl(String str, String str2) {
        this.mKey = str;
        this.mValue = str2;
    }

    protected ConfigurationDataImpl(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mKey);
        parcel.writeString(this.mValue);
    }

    private void readFromParcel(Parcel parcel) {
        this.mKey = parcel.readString();
        this.mValue = parcel.readString();
    }
}
