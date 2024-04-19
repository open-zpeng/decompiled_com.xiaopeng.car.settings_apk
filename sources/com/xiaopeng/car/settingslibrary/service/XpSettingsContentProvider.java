package com.xiaopeng.car.settingslibrary.service;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.xiaopeng.car.settingslibrary.common.ModuleInit;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
/* loaded from: classes.dex */
public class XpSettingsContentProvider extends ContentProvider {
    private static final String TAG = "XpSettingsContentProvider";

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Logs.d("XpSettingsContentProvider onCreate");
        ModuleInit.getInstance().init(getContext());
        return false;
    }
}
