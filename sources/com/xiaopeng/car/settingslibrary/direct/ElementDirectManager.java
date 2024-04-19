package com.xiaopeng.car.settingslibrary.direct;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.manager.speech.IElementDirectQuery;
import com.xiaopeng.speech.protocol.bean.stats.SkillStatisticsBean;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.Arrays;
import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes.dex */
public class ElementDirectManager implements IElementDirectQuery {
    private HashMap<String, ElementDirectItem> mItemHashMap;
    private HashMap<String, PageDirectItem> mPageHashMap;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final ElementDirectManager INSTANCE = new ElementDirectManager();

        private SingletonHolder() {
        }
    }

    public void loadData(HashMap<String, PageDirectItem> hashMap, HashMap<String, ElementDirectItem> hashMap2) {
        this.mPageHashMap = hashMap;
        this.mItemHashMap = hashMap2;
    }

    public static ElementDirectManager get() {
        return SingletonHolder.INSTANCE;
    }

    public boolean isPageDirectFromIntent(Intent intent) {
        if ("android.intent.action.VIEW".equals(intent.getAction())) {
            return isPageDirect(intent.getData());
        }
        return false;
    }

    public boolean isPageDirect(Uri uri) {
        if (uri != null) {
            return "xiaopeng".equals(uri.getScheme()) && SkillStatisticsBean.PAGE_SETTING.equals(uri.getAuthority());
        }
        return false;
    }

    public boolean isElementDirect(Intent intent) {
        return isPageDirectFromIntent(intent) && isElementDirect(intent.getData());
    }

    public boolean isElementDirect(Uri uri) {
        if (uri != null) {
            return !TextUtils.isEmpty(uri.getQueryParameter("position"));
        }
        return false;
    }

    public PageDirectItem getPage(Uri uri) {
        if (uri != null) {
            PageDirectItem pageDirectItem = this.mPageHashMap.get(uri.getPath());
            if (pageDirectItem != null) {
                return pageDirectItem;
            }
            return null;
        }
        return null;
    }

    public ElementDirectItem getElement(Uri uri) {
        if (uri != null) {
            String queryParameter = uri.getQueryParameter("position");
            String path = uri.getPath();
            HashMap<String, ElementDirectItem> hashMap = this.mItemHashMap;
            return hashMap.get(path + MqttTopic.TOPIC_LEVEL_SEPARATOR + queryParameter);
        }
        return null;
    }

    public boolean showSecondPageDirect(Intent intent, String[] strArr, OnPageDirectShowListener onPageDirectShowListener) {
        PageDirectItem page;
        if (strArr != null && (page = getPage(intent.getData())) != null && page.getData() != null) {
            String secondName = page.getSecondName();
            if (TextUtils.isEmpty(secondName)) {
                return false;
            }
            log("showSecondPageDirect name :" + secondName + " ,supportSecondPage :" + Arrays.toString(strArr));
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                if (secondName.equals(strArr[i])) {
                    log("showSecondPageDirect name :" + secondName);
                    if (onPageDirectShowListener != null) {
                        onPageDirectShowListener.onPageDirectShow(secondName);
                        return true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean showElementItemView(Intent intent, String str, View view) {
        if (isElementDirect(intent)) {
            PageDirectItem page = getPage(intent.getData());
            if (page == null || page.getData() == null || str.equals(page.getData().toString())) {
                ElementDirectItem element = getElement(intent.getData());
                StringBuilder sb = new StringBuilder();
                sb.append("showElementItemView 1 : ");
                sb.append(element == null ? " item is null " : element.toString());
                sb.append(view == null ? " rootView is null " : view);
                log(sb.toString());
                if (element != null && view != null) {
                    if (element.getParentId() > 0) {
                        View findViewById = view.findViewById(element.getParentId());
                        if (findViewById != null) {
                            showElementItemView(findViewById.findViewById(element.getId()));
                            return true;
                        }
                    } else {
                        showElementItemView(view.findViewById(element.getId()));
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public void showElementItemView(final View view) {
        StringBuilder sb = new StringBuilder();
        sb.append("showElementItemView 2 : ");
        sb.append(view == null ? " view is null " : " ");
        log(sb.toString());
        if (view != null) {
            view.post(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.direct.-$$Lambda$ElementDirectManager$MVxlF7Q6x8iIwvrNChU-k-bNrI0
                @Override // java.lang.Runnable
                public final void run() {
                    VuiFloatingLayerManager.show(view, 1);
                }
            });
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.speech.IElementDirectQuery
    public void showPageDirect(Context context, String str) {
        Uri parse;
        if (str == null || !isPageDirect(Uri.parse(str)) || (parse = Uri.parse(str)) == null) {
            return;
        }
        PageDirectItem pageDirectItem = this.mPageHashMap.get(parse.getPath());
        if (pageDirectItem != null) {
            log("showPageDirect " + pageDirectItem.toString());
            if (pageDirectItem.getPageAction() != null) {
                pageDirectItem.getPageAction().doAction(context, parse);
            }
        }
    }

    public void showUnityPageDirect(Context context, String str) {
        if (str != null) {
            Uri parse = Uri.parse(str);
            String authority = parse.getAuthority();
            HashMap<String, PageDirectItem> hashMap = this.mPageHashMap;
            PageDirectItem pageDirectItem = hashMap.get(MqttTopic.TOPIC_LEVEL_SEPARATOR + authority);
            if (pageDirectItem == null || pageDirectItem.getPageAction() == null) {
                return;
            }
            pageDirectItem.getPageAction().doAction(context, parse);
        }
    }

    @Override // com.xiaopeng.car.settingslibrary.manager.speech.IElementDirectQuery
    public boolean checkSupport(String str) {
        if (str != null) {
            Uri parse = Uri.parse(str);
            PageDirectItem page = getPage(parse);
            if (page != null && page.getSupportAction() != null) {
                return page.getSupportAction().checkSupport();
            }
            ElementDirectItem element = getElement(parse);
            if (element == null || element.getSupportAction() == null) {
                return true;
            }
            return element.getSupportAction().checkSupport();
        }
        return true;
    }

    public boolean checkUnitySupport(String str) {
        if (str != null) {
            Uri parse = Uri.parse(str);
            String authority = parse.getAuthority();
            HashMap<String, PageDirectItem> hashMap = this.mPageHashMap;
            PageDirectItem pageDirectItem = hashMap.get(MqttTopic.TOPIC_LEVEL_SEPARATOR + authority);
            if (pageDirectItem != null) {
                if (pageDirectItem.getSupportAction() != null) {
                    return pageDirectItem.getSupportAction().checkSupport();
                }
                return true;
            }
            String queryParameter = parse.getQueryParameter("position");
            String authority2 = parse.getAuthority();
            HashMap<String, ElementDirectItem> hashMap2 = this.mItemHashMap;
            ElementDirectItem elementDirectItem = hashMap2.get(MqttTopic.TOPIC_LEVEL_SEPARATOR + authority2 + MqttTopic.TOPIC_LEVEL_SEPARATOR + queryParameter);
            if (elementDirectItem != null && elementDirectItem.getSupportAction() != null) {
                return elementDirectItem.getSupportAction().checkSupport();
            }
        }
        return true;
    }

    public boolean isShowingUnityPage(Context context, String str) {
        if (str != null) {
            Uri parse = Uri.parse(str);
            String authority = parse.getAuthority();
            HashMap<String, PageDirectItem> hashMap = this.mPageHashMap;
            PageDirectItem pageDirectItem = hashMap.get(MqttTopic.TOPIC_LEVEL_SEPARATOR + authority);
            if (pageDirectItem == null || pageDirectItem.getPageAction() == null) {
                return false;
            }
            return pageDirectItem.getPageAction().isShowing(context, parse);
        }
        return false;
    }

    public void closeUnityPage(Context context, String str) {
        if (str != null) {
            Uri parse = Uri.parse(str);
            String authority = parse.getAuthority();
            HashMap<String, PageDirectItem> hashMap = this.mPageHashMap;
            PageDirectItem pageDirectItem = hashMap.get(MqttTopic.TOPIC_LEVEL_SEPARATOR + authority);
            if (pageDirectItem == null || pageDirectItem.getPageAction() == null) {
                return;
            }
            pageDirectItem.getPageAction().closePage(context, parse);
        }
    }

    private void log(String str) {
        LogUtils.d("element direct :" + str);
    }
}
