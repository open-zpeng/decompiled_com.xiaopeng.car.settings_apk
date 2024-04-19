package com.xiaopeng.speech.protocol.query.media;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryMediaEvent;
/* loaded from: classes2.dex */
public class MediaQuery_Processor implements IQueryProcessor {
    private MediaQuery mTarget;

    public MediaQuery_Processor(MediaQuery mediaQuery) {
        this.mTarget = mediaQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        if (((str.hashCode() == -931837998 && str.equals(QueryMediaEvent.GET_INFO_QUERY)) ? (char) 0 : (char) 65535) != 0) {
            return null;
        }
        return this.mTarget.getMediaInfo(str, str2);
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryMediaEvent.GET_INFO_QUERY};
    }
}
