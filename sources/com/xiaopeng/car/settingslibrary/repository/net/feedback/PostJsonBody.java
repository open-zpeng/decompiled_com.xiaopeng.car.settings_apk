package com.xiaopeng.car.settingslibrary.repository.net.feedback;

import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
/* loaded from: classes.dex */
public class PostJsonBody extends RequestBody {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String content;

    public PostJsonBody(String str) {
        this.content = str;
    }

    public String getContent() {
        return this.content;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return JSON;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        byte[] bytes = this.content.getBytes(Charset.forName("UTF-8"));
        if (bytes == null) {
            throw new NullPointerException("content == null");
        }
        Util.checkOffsetAndCount(bytes.length, 0L, bytes.length);
        bufferedSink.write(bytes, 0, bytes.length);
    }

    public static RequestBody create(String str) {
        return new PostJsonBody(str);
    }
}
