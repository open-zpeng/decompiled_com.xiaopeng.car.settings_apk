package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.xiaopeng.car.settings.utils.TimeZoneUtils;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.manager.emergency.WifiKeyParser;
import com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter;
import com.xiaopeng.car.settingslibrary.ui.fragment.FeedbackFragment;
import com.xiaopeng.car.settingslibrary.ui.widget.TimeZoneChooseDialog;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XRecyclerView;
import com.xiaopeng.xui.widget.XTextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes.dex */
public class TimeZoneChooseDialog {
    Adapter mAdapter;
    Context mContext;
    XDialog mDialog;
    BiConsumer<TimeZoneChooseDialog, TimeZone> mOnChooseListener;
    Consumer<TimeZoneChooseDialog> mOnDismissListener;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$init$2(XDialog xDialog, int i) {
    }

    public TimeZoneChooseDialog(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        this.mDialog = new XDialog(this.mContext);
        this.mDialog.setTitle(R.string.display_timezone_choose_dialog_title);
        this.mDialog.setCustomView(R.layout.timezone_choose);
        XRecyclerView xRecyclerView = (XRecyclerView) this.mDialog.getContentView().findViewById(R.id.root);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        TimeZone timeZone = TimeZone.getDefault();
        Context context = this.mContext;
        this.mAdapter = new Adapter(context, readTimezones(context), timeZone);
        xRecyclerView.setAdapter(this.mAdapter);
        this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$TimeZoneChooseDialog$V5IPULRpY_Xt27PcoxF1hIRbQO8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                TimeZoneChooseDialog.this.lambda$init$0$TimeZoneChooseDialog(dialogInterface);
            }
        });
        this.mDialog.setPositiveButton(R.string.confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$TimeZoneChooseDialog$14G9YzzgRP9GO7iGv0Z_2LnReRA
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                TimeZoneChooseDialog.this.lambda$init$1$TimeZoneChooseDialog(xDialog, i);
            }
        });
        this.mDialog.setNegativeButton(R.string.cancel, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$TimeZoneChooseDialog$8eG5MSntTNBcpSfk9znxb1SiPbA
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                TimeZoneChooseDialog.lambda$init$2(xDialog, i);
            }
        });
        ((LinearLayoutManager) xRecyclerView.getLayoutManager()).scrollToPositionWithOffset(this.mAdapter.findNearestLocation(timeZone), this.mContext.getResources().getDimensionPixelSize(R.dimen.timezone_dialog_init_offset));
    }

    public /* synthetic */ void lambda$init$0$TimeZoneChooseDialog(DialogInterface dialogInterface) {
        Consumer<TimeZoneChooseDialog> consumer = this.mOnDismissListener;
        if (consumer != null) {
            consumer.accept(this);
        }
    }

    public /* synthetic */ void lambda$init$1$TimeZoneChooseDialog(XDialog xDialog, int i) {
        BiConsumer<TimeZoneChooseDialog, TimeZone> biConsumer = this.mOnChooseListener;
        if (biConsumer != null) {
            biConsumer.accept(this, this.mAdapter.selectedTimeZone);
        }
    }

    public void setOnChooseListener(BiConsumer<TimeZoneChooseDialog, TimeZone> biConsumer) {
        this.mOnChooseListener = biConsumer;
    }

    public void setOnDismissListener(Consumer<TimeZoneChooseDialog> consumer) {
        this.mOnDismissListener = consumer;
    }

    private static List<String> readTimezones(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            XmlResourceParser xml = context.getResources().getXml(R.xml.timezones);
            while (xml.next() != 2) {
            }
            xml.next();
            while (xml.getEventType() != 3) {
                while (xml.getEventType() != 2) {
                    if (xml.getEventType() == 1) {
                        if (xml != null) {
                            xml.close();
                        }
                        return arrayList;
                    }
                    xml.next();
                }
                if (xml.getName().equals("timezone")) {
                    arrayList.add(xml.getAttributeValue(0));
                }
                while (xml.getEventType() != 3) {
                    xml.next();
                }
                xml.next();
            }
            if (xml != null) {
                xml.close();
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public void show() {
        this.mDialog.show();
    }

    public void dismiss() {
        this.mDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class ViewItem {
        String displayName;
        long offsetNow;
        TimeZone timeZone;

        private ViewItem() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Adapter extends BaseAdapter<ViewItem> {
        TimeZone selectedTimeZone;

        public Adapter(Context context, List<String> list, TimeZone timeZone) {
            this.selectedTimeZone = timeZone;
            lambda$setData$1$BaseAdapter(build(context, list));
        }

        private List<ViewItem> build(Context context, List<String> list) {
            long currentTimeMillis = System.currentTimeMillis();
            ArrayList arrayList = new ArrayList();
            for (String str : list) {
                ViewItem viewItem = new ViewItem();
                viewItem.timeZone = TimeZone.getTimeZone(str);
                viewItem.offsetNow = viewItem.timeZone.getOffset(currentTimeMillis);
                viewItem.displayName = TimeZoneUtils.getDisplayName(context, viewItem.timeZone.getID());
                arrayList.add(viewItem);
            }
            Collections.sort(arrayList, new Comparator<ViewItem>() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.TimeZoneChooseDialog.Adapter.1
                @Override // java.util.Comparator
                public int compare(ViewItem viewItem2, ViewItem viewItem3) {
                    if (viewItem2.offsetNow == viewItem3.offsetNow) {
                        return viewItem2.displayName.compareTo(viewItem3.displayName);
                    }
                    return Long.compare(viewItem2.offsetNow, viewItem3.offsetNow);
                }
            });
            return arrayList;
        }

        public int findNearestLocation(TimeZone timeZone) {
            int offset = timeZone.getOffset(System.currentTimeMillis());
            int size = getData().size();
            int i = 0;
            ViewItem viewItem = null;
            int i2 = 0;
            while (true) {
                if (i >= size) {
                    i = i2;
                    break;
                }
                ViewItem item = getItem(i);
                if (item.timeZone.getID().equals(timeZone.getID())) {
                    break;
                }
                if (viewItem == null || (item.offsetNow < offset && item.offsetNow != viewItem.offsetNow)) {
                    i2 = i;
                    viewItem = item;
                }
                i++;
            }
            return i + getHeaderCount();
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected int layoutId(int i) {
            return R.layout.timezone_choose_item;
        }

        @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseAdapter
        protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
            View view = viewHolder.getView(R.id.bottom_place_holder);
            XTextView xTextView = (XTextView) viewHolder.getView(R.id.prefix);
            XTextView xTextView2 = (XTextView) viewHolder.getView(R.id.offset);
            XTextView xTextView3 = (XTextView) viewHolder.getView(R.id.desc);
            boolean z = true;
            view.setVisibility(i >= getData().size() - 1 ? 0 : 8);
            final ViewItem item = getItem(i);
            int abs = (int) (Math.abs(item.offsetNow) / FeedbackFragment.ONE_HOUR_MILLIONS);
            int abs2 = (int) (((Math.abs(item.offsetNow) / 1000) / 60) % 60);
            StringBuilder sb = new StringBuilder();
            sb.append(item.offsetNow >= 0 ? MqttTopic.SINGLE_LEVEL_WILDCARD : WifiKeyParser.MESSAGE_SPLIT);
            sb.append(String.format("%02d", Integer.valueOf(abs)));
            sb.append(":");
            sb.append(String.format("%02d", Integer.valueOf(abs2)));
            xTextView2.setText(sb.toString());
            xTextView3.setText(item.displayName);
            if (this.selectedTimeZone == null || !item.timeZone.getID().equals(this.selectedTimeZone.getID())) {
                z = false;
            }
            View view2 = viewHolder.getView(R.id.itemRoot);
            view2.setSelected(z);
            view2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$TimeZoneChooseDialog$Adapter$FrxZv5ReRQYvpWZRKOc6R_dOj3o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    TimeZoneChooseDialog.Adapter.this.lambda$convert$0$TimeZoneChooseDialog$Adapter(item, view3);
                }
            });
            xTextView.setTypeface(z ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            xTextView2.setTypeface(z ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            xTextView3.setTypeface(z ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        }

        public /* synthetic */ void lambda$convert$0$TimeZoneChooseDialog$Adapter(ViewItem viewItem, View view) {
            this.selectedTimeZone = viewItem.timeZone;
            notifyDataSetChanged();
        }
    }
}
