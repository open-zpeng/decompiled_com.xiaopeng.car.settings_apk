package com.xiaopeng.car.settingslibrary.vm.datetime;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.DateFormat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.account.XpAccountManager;
import com.xiaopeng.car.settingslibrary.repository.DataRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/* loaded from: classes.dex */
public class DatetimeSettingsViewModel extends AndroidViewModel {
    Application mApplication;
    DataRepository mDataRepository;
    private MutableLiveData<String> mDatetimeModelMutableLiveData;
    private boolean mIsAlreadyRegister;
    private String mTime;
    IntentFilter mTimeIntentFilter;
    BroadcastReceiver mTimeReceiver;

    public DatetimeSettingsViewModel(Application application) {
        super(application);
        this.mDataRepository = DataRepository.getInstance();
        this.mDatetimeModelMutableLiveData = new MutableLiveData<>();
        this.mTimeIntentFilter = new IntentFilter();
        this.mIsAlreadyRegister = false;
        this.mTime = "";
        this.mTimeReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.car.settingslibrary.vm.datetime.DatetimeSettingsViewModel.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                LogUtils.d("xpdisplay time intent:" + intent);
                DatetimeSettingsViewModel.this.setDateAndTime();
            }
        };
        this.mApplication = application;
        initData();
    }

    private void initData() {
        this.mTimeIntentFilter.addAction("android.intent.action.TIME_TICK");
        this.mTimeIntentFilter.addAction("android.intent.action.TIME_SET");
        this.mTimeIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
    }

    public MutableLiveData<String> getDatetimeModelMutableLiveData() {
        return this.mDatetimeModelMutableLiveData;
    }

    public void set24HourFormat(boolean z) {
        boolean z2 = this.mDataRepository.set24HourFormat(this.mApplication.getApplicationContext(), z, true);
        Logs.log("display", "set24HourFormat:" + z2);
    }

    public boolean is24Hour() {
        boolean is24HourFormat = XpAccountManager.getInstance().is24HourFormat();
        Logs.d("xpdatetime is24Hour:" + is24HourFormat);
        return is24HourFormat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDateAndTime() {
        Date date = new Date();
        if (CarFunction.isSupportLeadingZeroTimeFormat()) {
            this.mTime = DateFormat.getTimeFormat(this.mApplication).format(date);
        } else {
            this.mTime = getIntlTimeFormatStr();
        }
        this.mDatetimeModelMutableLiveData.postValue(this.mTime);
    }

    public String getDateAndTime() {
        Date date = new Date();
        if (CarFunction.isSupportLeadingZeroTimeFormat()) {
            this.mTime = DateFormat.getTimeFormat(this.mApplication).format(date);
        } else {
            this.mTime = getIntlTimeFormatStr();
        }
        return this.mTime;
    }

    public void registerTimeReceiver() {
        if (this.mIsAlreadyRegister) {
            return;
        }
        this.mApplication.registerReceiver(this.mTimeReceiver, this.mTimeIntentFilter, null, null);
        this.mIsAlreadyRegister = true;
    }

    public void unregisterTimeReceiver() {
        if (this.mIsAlreadyRegister) {
            this.mApplication.unregisterReceiver(this.mTimeReceiver);
            this.mIsAlreadyRegister = false;
        }
    }

    public void onStartVM() {
        setDateAndTime();
        registerTimeReceiver();
    }

    public void onStopVM() {
        unregisterTimeReceiver();
    }

    private String getIntlTimeFormatStr() {
        return new SimpleDateFormat(XpAccountManager.getInstance().is24HourFormat() ? "H:mm" : "h:mm aa", Locale.getDefault()).format(new Date());
    }
}
