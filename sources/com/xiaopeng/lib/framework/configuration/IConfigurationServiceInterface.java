package com.xiaopeng.lib.framework.configuration;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.lib.framework.configuration.IConfigurationServiceCallback;
/* loaded from: classes.dex */
public interface IConfigurationServiceInterface extends IInterface {
    String getConfiguration(String str, String str2) throws RemoteException;

    void subscribe(String str, String str2, int i, IConfigurationServiceCallback iConfigurationServiceCallback) throws RemoteException;

    void unsubscribe(String str) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IConfigurationServiceInterface {
        private static final String DESCRIPTOR = "com.xiaopeng.lib.framework.configuration.IConfigurationServiceInterface";
        static final int TRANSACTION_getConfiguration = 3;
        static final int TRANSACTION_subscribe = 1;
        static final int TRANSACTION_unsubscribe = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IConfigurationServiceInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IConfigurationServiceInterface)) {
                return (IConfigurationServiceInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                subscribe(parcel.readString(), parcel.readString(), parcel.readInt(), IConfigurationServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                unsubscribe(parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i != 3) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel.enforceInterface(DESCRIPTOR);
                String configuration = getConfiguration(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(configuration);
                return true;
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IConfigurationServiceInterface {
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

            @Override // com.xiaopeng.lib.framework.configuration.IConfigurationServiceInterface
            public void subscribe(String str, String str2, int i, IConfigurationServiceCallback iConfigurationServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iConfigurationServiceCallback != null ? iConfigurationServiceCallback.asBinder() : null);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.lib.framework.configuration.IConfigurationServiceInterface
            public void unsubscribe(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.lib.framework.configuration.IConfigurationServiceInterface
            public String getConfiguration(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
