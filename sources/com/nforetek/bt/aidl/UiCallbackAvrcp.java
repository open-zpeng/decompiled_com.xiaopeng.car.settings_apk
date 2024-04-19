package com.nforetek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
/* loaded from: classes.dex */
public interface UiCallbackAvrcp extends IInterface {
    void onAvrcp13EventBatteryStatusChanged(byte b) throws RemoteException;

    void onAvrcp13EventPlaybackPosChanged(long j) throws RemoteException;

    void onAvrcp13EventPlaybackStatusChanged(byte b) throws RemoteException;

    void onAvrcp13EventPlayerSettingChanged(byte[] bArr, byte[] bArr2) throws RemoteException;

    void onAvrcp13EventSystemStatusChanged(byte b) throws RemoteException;

    void onAvrcp13EventTrackChanged(long j) throws RemoteException;

    void onAvrcp13EventTrackReachedEnd() throws RemoteException;

    void onAvrcp13EventTrackReachedStart() throws RemoteException;

    void onAvrcp13RegisterEventWatcherFail(byte b) throws RemoteException;

    void onAvrcp13RegisterEventWatcherSuccess(byte b) throws RemoteException;

    void onAvrcp14EventAddressedPlayerChanged(int i, int i2) throws RemoteException;

    void onAvrcp14EventAvailablePlayerChanged() throws RemoteException;

    void onAvrcp14EventNowPlayingContentChanged() throws RemoteException;

    void onAvrcp14EventUidsChanged(int i) throws RemoteException;

    void onAvrcp14EventVolumeChanged(byte b) throws RemoteException;

    void onAvrcpErrorResponse(int i, int i2, byte b) throws RemoteException;

    void onAvrcpServiceReady() throws RemoteException;

    void onAvrcpStateChanged(String str, int i, int i2) throws RemoteException;

    void retAvrcp13CapabilitiesSupportEvent(byte[] bArr) throws RemoteException;

    void retAvrcp13ElementAttributesPlaying(int[] iArr, String[] strArr) throws RemoteException;

    void retAvrcp13PlayStatus(long j, long j2, byte b) throws RemoteException;

    void retAvrcp13PlayerSettingAttributesList(byte[] bArr) throws RemoteException;

    void retAvrcp13PlayerSettingCurrentValues(byte[] bArr, byte[] bArr2) throws RemoteException;

    void retAvrcp13PlayerSettingValuesList(byte b, byte[] bArr) throws RemoteException;

    void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException;

    void retAvrcp14AddToNowPlayingSuccess() throws RemoteException;

    void retAvrcp14ChangePathSuccess(long j) throws RemoteException;

    void retAvrcp14FolderItems(int i, long j) throws RemoteException;

    void retAvrcp14ItemAttributes(int[] iArr, String[] strArr) throws RemoteException;

    void retAvrcp14MediaItems(int i, long j) throws RemoteException;

    void retAvrcp14PlaySelectedItemSuccess() throws RemoteException;

    void retAvrcp14SearchResult(int i, long j) throws RemoteException;

    void retAvrcp14SetAbsoluteVolumeSuccess(byte b) throws RemoteException;

    void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException;

    void retAvrcp14SetBrowsedPlayerSuccess(String[] strArr, int i, long j) throws RemoteException;

    void retAvrcpUpdateSongStatus(String str, String str2, String str3) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements UiCallbackAvrcp {
        private static final String DESCRIPTOR = "com.nforetek.bt.aidl.UiCallbackAvrcp";
        static final int TRANSACTION_onAvrcp13EventBatteryStatusChanged = 17;
        static final int TRANSACTION_onAvrcp13EventPlaybackPosChanged = 16;
        static final int TRANSACTION_onAvrcp13EventPlaybackStatusChanged = 12;
        static final int TRANSACTION_onAvrcp13EventPlayerSettingChanged = 19;
        static final int TRANSACTION_onAvrcp13EventSystemStatusChanged = 18;
        static final int TRANSACTION_onAvrcp13EventTrackChanged = 13;
        static final int TRANSACTION_onAvrcp13EventTrackReachedEnd = 14;
        static final int TRANSACTION_onAvrcp13EventTrackReachedStart = 15;
        static final int TRANSACTION_onAvrcp13RegisterEventWatcherFail = 11;
        static final int TRANSACTION_onAvrcp13RegisterEventWatcherSuccess = 10;
        static final int TRANSACTION_onAvrcp14EventAddressedPlayerChanged = 22;
        static final int TRANSACTION_onAvrcp14EventAvailablePlayerChanged = 21;
        static final int TRANSACTION_onAvrcp14EventNowPlayingContentChanged = 20;
        static final int TRANSACTION_onAvrcp14EventUidsChanged = 23;
        static final int TRANSACTION_onAvrcp14EventVolumeChanged = 24;
        static final int TRANSACTION_onAvrcpErrorResponse = 35;
        static final int TRANSACTION_onAvrcpServiceReady = 1;
        static final int TRANSACTION_onAvrcpStateChanged = 2;
        static final int TRANSACTION_retAvrcp13CapabilitiesSupportEvent = 3;
        static final int TRANSACTION_retAvrcp13ElementAttributesPlaying = 8;
        static final int TRANSACTION_retAvrcp13PlayStatus = 9;
        static final int TRANSACTION_retAvrcp13PlayerSettingAttributesList = 4;
        static final int TRANSACTION_retAvrcp13PlayerSettingCurrentValues = 6;
        static final int TRANSACTION_retAvrcp13PlayerSettingValuesList = 5;
        static final int TRANSACTION_retAvrcp13SetPlayerSettingValueSuccess = 7;
        static final int TRANSACTION_retAvrcp14AddToNowPlayingSuccess = 33;
        static final int TRANSACTION_retAvrcp14ChangePathSuccess = 29;
        static final int TRANSACTION_retAvrcp14FolderItems = 27;
        static final int TRANSACTION_retAvrcp14ItemAttributes = 30;
        static final int TRANSACTION_retAvrcp14MediaItems = 28;
        static final int TRANSACTION_retAvrcp14PlaySelectedItemSuccess = 31;
        static final int TRANSACTION_retAvrcp14SearchResult = 32;
        static final int TRANSACTION_retAvrcp14SetAbsoluteVolumeSuccess = 34;
        static final int TRANSACTION_retAvrcp14SetAddressedPlayerSuccess = 25;
        static final int TRANSACTION_retAvrcp14SetBrowsedPlayerSuccess = 26;
        static final int TRANSACTION_retAvrcpUpdateSongStatus = 36;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static UiCallbackAvrcp asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof UiCallbackAvrcp)) {
                return (UiCallbackAvrcp) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcpServiceReady();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcpStateChanged(parcel.readString(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp13CapabilitiesSupportEvent(parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp13PlayerSettingAttributesList(parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp13PlayerSettingValuesList(parcel.readByte(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp13PlayerSettingCurrentValues(parcel.createByteArray(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp13SetPlayerSettingValueSuccess();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp13ElementAttributesPlaying(parcel.createIntArray(), parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp13PlayStatus(parcel.readLong(), parcel.readLong(), parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13RegisterEventWatcherSuccess(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13RegisterEventWatcherFail(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13EventPlaybackStatusChanged(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13EventTrackChanged(parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13EventTrackReachedEnd();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13EventTrackReachedStart();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13EventPlaybackPosChanged(parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13EventBatteryStatusChanged(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13EventSystemStatusChanged(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp13EventPlayerSettingChanged(parcel.createByteArray(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp14EventNowPlayingContentChanged();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp14EventAvailablePlayerChanged();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp14EventAddressedPlayerChanged(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 23:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp14EventUidsChanged(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 24:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcp14EventVolumeChanged(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 25:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14SetAddressedPlayerSuccess();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14SetBrowsedPlayerSuccess(parcel.createStringArray(), parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14FolderItems(parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 28:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14MediaItems(parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 29:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14ChangePathSuccess(parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 30:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14ItemAttributes(parcel.createIntArray(), parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                case 31:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14PlaySelectedItemSuccess();
                    parcel2.writeNoException();
                    return true;
                case 32:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14SearchResult(parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 33:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14AddToNowPlayingSuccess();
                    parcel2.writeNoException();
                    return true;
                case 34:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcp14SetAbsoluteVolumeSuccess(parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 35:
                    parcel.enforceInterface(DESCRIPTOR);
                    onAvrcpErrorResponse(parcel.readInt(), parcel.readInt(), parcel.readByte());
                    parcel2.writeNoException();
                    return true;
                case 36:
                    parcel.enforceInterface(DESCRIPTOR);
                    retAvrcpUpdateSongStatus(parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements UiCallbackAvrcp {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcpServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcpStateChanged(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13CapabilitiesSupportEvent(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13PlayerSettingAttributesList(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13PlayerSettingValuesList(byte b, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13PlayerSettingCurrentValues(byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13SetPlayerSettingValueSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13ElementAttributesPlaying(int[] iArr, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp13PlayStatus(long j, long j2, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeByte(b);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13RegisterEventWatcherSuccess(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13RegisterEventWatcherFail(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventPlaybackStatusChanged(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventTrackChanged(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventTrackReachedEnd() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventTrackReachedStart() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventPlaybackPosChanged(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventBatteryStatusChanged(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventSystemStatusChanged(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp13EventPlayerSettingChanged(byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventNowPlayingContentChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventAvailablePlayerChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventAddressedPlayerChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventUidsChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcp14EventVolumeChanged(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14SetAddressedPlayerSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14SetBrowsedPlayerSuccess(String[] strArr, int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14FolderItems(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14MediaItems(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14ChangePathSuccess(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14ItemAttributes(int[] iArr, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14PlaySelectedItemSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14SearchResult(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14AddToNowPlayingSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcp14SetAbsoluteVolumeSuccess(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void onAvrcpErrorResponse(int i, int i2, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByte(b);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCallbackAvrcp
            public void retAvrcpUpdateSongStatus(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
