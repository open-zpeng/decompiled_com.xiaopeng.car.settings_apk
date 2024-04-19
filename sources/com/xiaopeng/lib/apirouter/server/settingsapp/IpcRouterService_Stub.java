package com.xiaopeng.lib.apirouter.server.settingsapp;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.car.settingslibrary.common.IpcRouterService;
import com.xiaopeng.lib.apirouter.server.TransactTranslator;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes.dex */
public class IpcRouterService_Stub extends Binder implements IInterface {
    public IpcRouterService provider = new IpcRouterService();
    public IpcRouterService_Manifest manifest = new IpcRouterService_Manifest();

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 0) {
            parcel.enforceInterface(IpcRouterService_Manifest.DESCRIPTOR);
            Uri uri = (Uri) Uri.CREATOR.createFromParcel(parcel);
            try {
                this.provider.onReceiverData(((Integer) TransactTranslator.read(uri.getQueryParameter(VuiConstants.ELEMENT_ID), "int")).intValue(), (String) TransactTranslator.read(uri.getQueryParameter("bundle"), "java.lang.String"));
                parcel2.writeNoException();
                TransactTranslator.reply(parcel2, null);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                parcel2.writeException(new IllegalStateException(e.getMessage()));
                return true;
            }
        } else if (i != 1) {
            if (i == 1598968902) {
                parcel2.writeString(IpcRouterService_Manifest.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        } else {
            parcel.enforceInterface(IpcRouterService_Manifest.DESCRIPTOR);
            Uri uri2 = (Uri) Uri.CREATOR.createFromParcel(parcel);
            try {
                String onGetData = this.provider.onGetData(((Integer) TransactTranslator.read(uri2.getQueryParameter(VuiConstants.ELEMENT_ID), "int")).intValue(), (String) TransactTranslator.read(uri2.getQueryParameter("bundle"), "java.lang.String"));
                parcel2.writeNoException();
                TransactTranslator.reply(parcel2, onGetData);
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                parcel2.writeException(new IllegalStateException(e2.getMessage()));
                return true;
            }
        }
    }
}
