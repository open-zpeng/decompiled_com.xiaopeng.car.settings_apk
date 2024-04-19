package com.xiaopeng.car.settingslibrary.ui.dialog;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.CarFunction;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LocaleUtils;
import com.xiaopeng.car.settingslibrary.manager.about.IRestoreCheckCallback;
import com.xiaopeng.car.settingslibrary.manager.about.XpAboutManager;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.emergency.WifiKeyParser;
import com.xiaopeng.car.settingslibrary.speech.VuiManager;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class RestoreCheckedDialog extends XDialog {
    public static final int IS_ELIGIBLE = 3;
    public static final int NOT_CAR_GEAR_P = 1;
    public static final int NOT_ELECTRICITY_PLENTY = 2;
    private IRestoreCheckCallback mCheckedCallback;
    private XTextView mContentExplainTv;
    private XTextView mContentExplainTv2;
    private TextView mContentTv;
    Context mContext;

    public RestoreCheckedDialog(IRestoreCheckCallback iRestoreCheckCallback, Context context) {
        super(context, R.style.XDialogView_Large);
        this.mContext = context;
        this.mCheckedCallback = iRestoreCheckCallback;
        setCustomView();
        getDialog().getWindow().setType(2008);
        getDialog().setCanceledOnTouchOutside(false);
    }

    public void setCustomView() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.about_restore_checked_info, (ViewGroup) null);
        setCustomView(inflate, true);
        initView(inflate);
    }

    private void initView(View view) {
        this.mContentTv = (TextView) view.findViewById(R.id.content_tv);
        this.mContentExplainTv = (XTextView) view.findViewById(R.id.content_explain);
        this.mContentExplainTv2 = (XTextView) view.findViewById(R.id.content_explain2);
    }

    public void showSafetyTips() {
        setTitle(this.mContext.getString(R.string.restore_safety_alert));
        setPositiveButton(this.mContext.getString(R.string.start_recovery_btn), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$RestoreCheckedDialog$y7ddqX8YLnn-pL50pV-uSrQXoxs
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                RestoreCheckedDialog.this.lambda$showSafetyTips$0$RestoreCheckedDialog(xDialog, i);
            }
        });
        setNegativeButton(this.mContext.getString(R.string.cancel));
        this.mContentTv.setTextColor(this.mContext.getColor(R.color.x_theme_primary_normal));
        this.mContentTv.setText(this.mContext.getString(R.string.restore_safety_alert_title));
        this.mContentExplainTv.setVisibility(0);
        setContentExplainTvTextAndColor(this.mContext.getString(R.string.restore_safety_alert_title_2), false);
        show();
        VuiManager.instance().initVuiDialog(this, this.mContext, VuiManager.SCENE_RESTORE_CHECK_DIALOG);
    }

    public /* synthetic */ void lambda$showSafetyTips$0$RestoreCheckedDialog(XDialog xDialog, int i) {
        if (!CarSettingsManager.getInstance().isEbsBatterySatisfied()) {
            this.mCheckedCallback.reChecked(2);
        } else if (!CarSettingsManager.getInstance().isCarGearP()) {
            this.mCheckedCallback.reChecked(1);
        } else {
            XpAboutManager.getInstance().restoreFactory();
        }
    }

    public void showRestoreWarning(int i) {
        String string;
        if (i == 1) {
            string = this.mContext.getString(R.string.restore_warning_1);
            this.mContentExplainTv.setVisibility(0);
            setContentExplainTvTextAndColor(this.mContext.getString(R.string.restore_warning_1_explain), false);
        } else if (i != 2) {
            string = "";
        } else {
            string = this.mContext.getString(R.string.restore_warning_2);
            if (CarFunction.isSupportXPengCustomerService()) {
                this.mContentExplainTv.setVisibility(0);
                setContentExplainTvTextAndColor(this.mContext.getString(R.string.restore_warning_2_explain, CarFunction.isSupportRegionSet() ? LocaleUtils.getRescueCalls()[0] : this.mContext.getString(R.string.customer_service_number1)), true);
            } else {
                this.mContentExplainTv.setVisibility(4);
                this.mContentExplainTv2.setVisibility(4);
            }
        }
        this.mContentTv.setTextColor(this.mContext.getColor(R.color.x_theme_primary_negative_normal));
        this.mContentTv.setText(string);
        setTitle(this.mContext.getString(R.string.restore_warning_title));
        if (i == 2) {
            setPositiveButton(this.mContext.getString(R.string.user_know), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$RestoreCheckedDialog$30vNu-1RQG9e3HjAZj-g6QSu5AU
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i2) {
                    RestoreCheckedDialog.this.lambda$showRestoreWarning$1$RestoreCheckedDialog(xDialog, i2);
                }
            });
            setNegativeButton((CharSequence) null);
        } else if (i == 1) {
            setPositiveButton(this.mContext.getString(R.string.restore_retry), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.-$$Lambda$RestoreCheckedDialog$aDM9PSeJjiYLBwiAvpscWjHVDVo
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i2) {
                    RestoreCheckedDialog.this.lambda$showRestoreWarning$2$RestoreCheckedDialog(xDialog, i2);
                }
            });
            setNegativeButton(this.mContext.getString(R.string.cancel));
        }
        show();
        VuiManager.instance().initVuiDialog(this, this.mContext, VuiManager.SCENE_RESTORE_CHECK_DIALOG);
    }

    public /* synthetic */ void lambda$showRestoreWarning$1$RestoreCheckedDialog(XDialog xDialog, int i) {
        dismiss();
    }

    public /* synthetic */ void lambda$showRestoreWarning$2$RestoreCheckedDialog(XDialog xDialog, int i) {
        if (!CarSettingsManager.getInstance().isCarGearP()) {
            this.mCheckedCallback.reChecked(1);
        } else {
            this.mCheckedCallback.reChecked(3);
        }
    }

    private void setContentExplainTvTextAndColor(String str, boolean z) {
        this.mContentExplainTv.setTextColor(this.mContext.getColor(R.color.x_theme_text_03));
        if (z) {
            final String string = CarFunction.isSupportRegionSet() ? LocaleUtils.getRescueCalls()[0] : this.mContext.getString(R.string.customer_service_number1);
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new ClickableSpan() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.RestoreCheckedDialog.1
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    RestoreCheckedDialog.this.diaPhoneNum(string);
                    RestoreCheckedDialog.this.dismiss();
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    super.updateDrawState(textPaint);
                    textPaint.setColor(RestoreCheckedDialog.this.mContext.getResources().getColor(R.color.x_theme_primary_normal, null));
                    textPaint.setUnderlineText(false);
                }
            }, (str.length() - string.length()) - 2, str.length() - 2, 33);
            this.mContentExplainTv.setMovementMethod(LinkMovementMethod.getInstance());
            this.mContentExplainTv.setText(spannableString);
            this.mContentExplainTv.setVuiLabel(vuiPhone(string));
            this.mContentExplainTv.setHighlightColor(0);
            this.mContentExplainTv.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.RestoreCheckedDialog.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (view instanceof IVuiElement) {
                        IVuiElement iVuiElement = (IVuiElement) view;
                        if (iVuiElement.isPerformVuiAction()) {
                            iVuiElement.setPerformVuiAction(false);
                            RestoreCheckedDialog.this.diaPhoneNum(string);
                            RestoreCheckedDialog.this.dismiss();
                        }
                    }
                }
            });
            final String string2 = this.mContext.getString(R.string.customer_service_number2);
            SpannableString spannableString2 = new SpannableString(this.mContext.getString(R.string.restore_warning_2_explain_ex, string2));
            spannableString2.setSpan(new ClickableSpan() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.RestoreCheckedDialog.3
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    RestoreCheckedDialog.this.diaPhoneNum(string2);
                    RestoreCheckedDialog.this.dismiss();
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    super.updateDrawState(textPaint);
                    textPaint.setColor(RestoreCheckedDialog.this.mContext.getResources().getColor(R.color.x_theme_primary_normal, null));
                    textPaint.setUnderlineText(false);
                }
            }, 0, string2.length(), 33);
            this.mContentExplainTv2.setMovementMethod(LinkMovementMethod.getInstance());
            this.mContentExplainTv2.setText(spannableString2);
            this.mContentExplainTv2.setVuiLabel(vuiPhone(string2));
            this.mContentExplainTv2.setHighlightColor(0);
            this.mContentExplainTv2.setVisibility(0);
            this.mContentExplainTv2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.dialog.RestoreCheckedDialog.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (view instanceof IVuiElement) {
                        IVuiElement iVuiElement = (IVuiElement) view;
                        if (iVuiElement.isPerformVuiAction()) {
                            iVuiElement.setPerformVuiAction(false);
                            RestoreCheckedDialog.this.diaPhoneNum(string2);
                            RestoreCheckedDialog.this.dismiss();
                        }
                    }
                }
            });
            if (CarFunction.isSupportRegionSet()) {
                this.mContentExplainTv2.setVisibility(4);
                return;
            }
            return;
        }
        this.mContentExplainTv.setText(str);
        this.mContentExplainTv2.setVisibility(8);
    }

    private String vuiPhone(String str) {
        String replaceAll = str.replaceAll(WifiKeyParser.MESSAGE_SPLIT, "");
        String substring = str.substring(0, 3);
        String string = this.mContext.getString(R.string.restore_warning_phone_tts);
        return str + "|" + string + replaceAll + "|" + replaceAll + "|" + string + substring + "|" + substring;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void diaPhoneNum(String str) {
        try {
            Intent intent = new Intent("android.intent.action.CALL");
            intent.setFlags(268435456);
            intent.setData(Uri.parse("tel:" + str));
            this.mContext.startActivity(intent);
            if (this.mContext.getString(R.string.customer_service_number1).equals(str)) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.ABOUT_SYS_PAGE_ID, "B003");
            } else if (this.mContext.getString(R.string.customer_service_number2).equals(str)) {
                BuriedPointUtils.sendButtonDataLog(BuriedPointUtils.ABOUT_SYS_PAGE_ID, "B002");
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
