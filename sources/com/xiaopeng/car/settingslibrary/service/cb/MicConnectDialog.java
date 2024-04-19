package com.xiaopeng.car.settingslibrary.service.cb;

import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager;
import com.xiaopeng.car.settingslibrary.manager.xui.XuiSettingsManager;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.app.XDialog;
/* loaded from: classes.dex */
public class MicConnectDialog implements XpKaraokeManager.OnKaraokePowerListener {
    private static final boolean DEG = true;
    private static final String TAG = "MicConnectDialog";
    private static MicConnectDialog sConfigHelper = new MicConnectDialog();
    private int mConnectingIndex;
    private TextView mConnectingTextView;
    private Context mContext;
    private XDialog mXDialog;
    private int[] mStepViewIDs = {R.id.step_enable, R.id.step_connecting, R.id.step_connected, R.id.step_sell};
    private XpKaraokeManager mXpKaraokeManager = XpKaraokeManager.getInstance();
    private XuiSettingsManager mXuiSettingsManager = XuiSettingsManager.getInstance();
    private int[] mConnectingString = {R.string.cb_status_connecting_to_microphone1, R.string.cb_status_connecting_to_microphone2, R.string.cb_status_connecting_to_microphone3};

    private boolean init(Context context) {
        this.mContext = context.getApplicationContext();
        this.mXpKaraokeManager.addOnKaraokePowerListener(this);
        return this.mXpKaraokeManager.isMicSdkInvalid();
    }

    public static MicConnectDialog getInstance() {
        return sConfigHelper;
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager.OnKaraokePowerListener
    public void micServiceCallBack(int i) {
        Log.d(TAG, "micServiceCallBack: " + i);
        if (isDialogShowing()) {
            updateViewByEvent(i);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager.OnKaraokePowerListener
    public void onErrorEvent(int i, int i2) {
        Log.d(TAG, "onErrorEvent, errorCode: " + i + " operation : " + i2);
        if (isDialogShowing()) {
            dismissDialog();
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.karaoke.XpKaraokeManager.OnKaraokePowerListener
    public void onConnectXui() {
        showDialog(this.mContext);
    }

    private boolean isDialogShowing() {
        XDialog xDialog = this.mXDialog;
        return xDialog != null && xDialog.getDialog().isShowing();
    }

    public void showDialog(Context context) {
        if (init(context)) {
            Logs.d("micconnect sdk null");
            return;
        }
        if (this.mXDialog == null) {
            this.mXDialog = new XDialog(this.mContext, R.style.XDialogView_Large);
            this.mXDialog.setCustomView(R.layout.layout_mic_connect_container, false);
            this.mXDialog.setSystemDialog(2008);
            this.mXDialog.setTitle(R.string.cb_title_connect_to_microphone);
            this.mXDialog.setPositiveButtonEnable(true);
            this.mXDialog.setPositiveButton(this.mContext.getString(R.string.cb_result_cancel));
            this.mXDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.cb.-$$Lambda$MicConnectDialog$ILRhqs0x2hziRfmo8l85RvgvWNg
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    MicConnectDialog.this.lambda$showDialog$0$MicConnectDialog(dialogInterface);
                }
            });
            setUpSellTextView();
            this.mConnectingTextView = (TextView) this.mXDialog.getContentView().findViewById(R.id.connecting_tips);
            this.mXDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.service.cb.MicConnectDialog.1
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    MicConnectDialog.this.mConnectingTextView.setVisibility(4);
                }
            });
            ThemeViewModel.create(this.mContext).addCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.car.settingslibrary.service.cb.-$$Lambda$MicConnectDialog$tjKrvMtDG49av5XYTfWbj_pNAz0
                @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
                public final void onThemeChanged() {
                    MicConnectDialog.this.setUpSellTextView();
                }
            });
        }
        if (this.mXDialog.getDialog().isShowing()) {
            return;
        }
        this.mXDialog.show();
        updateViewByEvent(getEventFromDevStatus());
    }

    public /* synthetic */ void lambda$showDialog$0$MicConnectDialog(DialogInterface dialogInterface) {
        this.mXDialog = null;
        this.mXpKaraokeManager.removeOnKaraokePowerListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpSellTextView() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mContext.getString(R.string.cb_status_buy_microphone));
        spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.xiaopeng.car.settingslibrary.service.cb.MicConnectDialog.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                MicConnectDialog.this.mXDialog.setTitle(MicConnectDialog.this.mContext.getText(R.string.cb_title_buy_microphone));
                ((ImageView) MicConnectDialog.this.mXDialog.getContentView().findViewById(R.id.img_for_sell)).setImageBitmap(QRCodeUtil.createQRCodeBitmap(MicConnectDialog.this.mXuiSettingsManager.getBuyMicUrl(), 200, 200));
                MicConnectDialog.this.doUpdateStepView(R.id.step_sell);
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        }, 8, 11, 33);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(this.mContext.getColor(R.color.info_title_text_color)), 8, 11, 33);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(this.mContext.getColor(R.color.main_title_text_color)), 0, 8, 33);
        TextView textView = (TextView) this.mXDialog.getContentView().findViewById(R.id.tips_go_to_buy);
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void dismissDialog() {
        this.mXDialog.dismiss();
    }

    public void updateViewByEvent(int i) {
        this.mXDialog.setTitle(this.mContext.getText(R.string.cb_title_connect_to_microphone));
        this.mXDialog.setPositiveButton(this.mContext.getResources().getText(R.string.cb_result_cancel));
        this.mConnectingTextView.setVisibility(4);
        if (i != 3) {
            if (i == 4) {
                doUpdateStepView(R.id.step_enable);
                return;
            } else if (i == 5) {
                doUpdateStepView(R.id.step_connected);
                this.mXDialog.setPositiveButton(this.mContext.getResources().getText(R.string.cb_result_ok));
                return;
            } else if (i != 6) {
                return;
            }
        }
        doUpdateStepView(R.id.step_connecting);
        this.mConnectingTextView.setVisibility(0);
        this.mConnectingIndex = 0;
        update();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update() {
        if (this.mConnectingTextView.getVisibility() == 0) {
            TextView textView = this.mConnectingTextView;
            int[] iArr = this.mConnectingString;
            textView.setText(iArr[this.mConnectingIndex % iArr.length]);
            this.mConnectingIndex++;
            this.mConnectingTextView.postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.service.cb.-$$Lambda$MicConnectDialog$yH5b8M_ox-4dwncEZDc1MPgirh8
                @Override // java.lang.Runnable
                public final void run() {
                    MicConnectDialog.this.update();
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUpdateStepView(int i) {
        ViewGroup contentView = this.mXDialog.getContentView();
        int[] iArr = this.mStepViewIDs;
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            contentView.findViewById(i3).setVisibility(i == i3 ? 0 : 8);
        }
    }

    private int getEventFromDevStatus() {
        boolean z = this.mXuiSettingsManager.getMicStatus() == 0;
        boolean z2 = this.mXuiSettingsManager.getMicPowerStatus() == 1;
        Log.d(TAG, "getEventFromDevStatus, isHubConnect: " + z + " isMicEnable : " + z2);
        if (z) {
            return z2 ? 5 : 6;
        }
        return 4;
    }
}
