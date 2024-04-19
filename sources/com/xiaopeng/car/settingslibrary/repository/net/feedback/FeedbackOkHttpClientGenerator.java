package com.xiaopeng.car.settingslibrary.repository.net.feedback;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpHeaders;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
import com.xiaopeng.lib.framework.netchannelmodule.common.TrafficStatsEntry;
import com.xiaopeng.lib.http.tls.SSLHelper;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import com.xiaopeng.libconfig.ipc.AccountConfig;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes.dex */
public class FeedbackOkHttpClientGenerator {
    static HttpLoggingInterceptor sHttpLoggingInterceptor = new HttpLoggingInterceptor();
    static SignIntercepter sSignIntercepter = new SignIntercepter();
    private static TrustManager[] sTrustManagers;

    public static OkHttpClient.Builder getBuilder() {
        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        newBuilder.connectionSpecs(SSLHelper.getConnectionSpecs()).connectionPool(new ConnectionPool()).writeTimeout(20L, TimeUnit.SECONDS).readTimeout(20L, TimeUnit.SECONDS);
        getTLS2SocketFactory();
        getX509TrustManager();
        try {
            newBuilder.sslSocketFactory(SSLHelper.getTLS2SocketFactory(null), SSLHelper.getX509TrustManager(null));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        newBuilder.addInterceptor(sSignIntercepter);
        sHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        newBuilder.addInterceptor(sHttpLoggingInterceptor);
        return newBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SignIntercepter implements Interceptor {
        private SignIntercepter() {
        }

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            TrafficStatsEntry.setTraficInfo();
            Request request = chain.request();
            if (request.method().equals("POST")) {
                RequestBody body = request.body();
                if (body instanceof PostJsonBody) {
                    String content = ((PostJsonBody) body).getContent();
                    Logs.v("xpfeedback intercepter content:" + content);
                    request = chain.request().newBuilder().addHeader("Content-Type", "application/json").addHeader(HttpHeaders.HEAD_KEY_ACCEPT, "application/json").addHeader(Config.FEED_BACK_APPID, "feedback").addHeader(AccountConfig.KEY_ACCESS_TOKEN, FeedbackOkHttpClientGenerator.getSign(Config.FEED_BACK_ACCESSKEY, (HashMap) new Gson().fromJson(content, (Class<Object>) HashMap.class))).build();
                    try {
                        return chain.proceed(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                return chain.proceed(request);
            } catch (Exception e2) {
                Logs.d("xpfeedback this will return Exception!!!");
                e2.printStackTrace();
                throw e2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSign(String str, HashMap hashMap) {
        Set<String> keySet = hashMap.keySet();
        ArrayList arrayList = new ArrayList();
        for (String str2 : keySet) {
            arrayList.add(str2);
        }
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder(str);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (str3.equals("requestTime")) {
                DecimalFormat decimalFormat = new DecimalFormat(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(str3);
                sb.append(decimalFormat.format((Double) hashMap.get(str3)));
            } else {
                sb.append(str3);
                sb.append(hashMap.get(str3));
            }
        }
        if (!Utils.isUserRelease()) {
            Logs.v("xpfeedback valUtf " + sb.toString());
        }
        return getValUtf(sb.toString());
    }

    public static String getValUtf(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] digest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < digest.length; i++) {
                int i2 = digest[i];
                if (i2 < 0) {
                    i2 += 256;
                }
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
            Logs.v("xpfeedback getValUtf " + ((Object) stringBuffer));
            return stringBuffer.toString().toUpperCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static List<ConnectionSpec> getConnectionSpecs() {
        ConnectionSpec build = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(build);
        arrayList.add(ConnectionSpec.COMPATIBLE_TLS);
        arrayList.add(ConnectionSpec.CLEARTEXT);
        return arrayList;
    }

    private static SSLSocketFactory getTLS2SocketFactory() {
        try {
            SSLContext sSLContext = SSLContext.getInstance(XmartV1Constants.TLS_REVISION_1_2);
            KeyStore keyStore = KeyStore.getInstance(XmartV1Constants.KEY_STORE_TYPE);
            keyStore.load(null);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            trustManagerFactory.init(keyStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, XmartV1Constants.KEY_MANAGER_PASSWORD.toCharArray());
            sSLContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            return new TLS2SocketFactory(sSLContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static X509TrustManager getX509TrustManager() {
        try {
            TrustManager[] trustManagers = getTrustManagers();
            if (trustManagers != null) {
                for (TrustManager trustManager : trustManagers) {
                    if (trustManager instanceof X509TrustManager) {
                        return (X509TrustManager) trustManager;
                    }
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static synchronized TrustManager[] getTrustManagers() {
        synchronized (FeedbackOkHttpClientGenerator.class) {
            if (sTrustManagers != null) {
                return sTrustManagers;
            }
            try {
                KeyStore keyStore = KeyStore.getInstance(XmartV1Constants.KEY_STORE_TYPE);
                keyStore.load(null);
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
                trustManagerFactory.init(keyStore);
                sTrustManagers = trustManagerFactory.getTrustManagers();
                return sTrustManagers;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class TLS2SocketFactory extends SSLSocketFactory {
        private final String[] TLS_V12_ONLY = {XmartV1Constants.TLS_REVISION_1_2};
        final SSLSocketFactory delegate;

        public TLS2SocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.delegate = sSLSocketFactory;
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public String[] getDefaultCipherSuites() {
            return this.delegate.getDefaultCipherSuites();
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public String[] getSupportedCipherSuites() {
            return this.delegate.getSupportedCipherSuites();
        }

        @Override // javax.net.ssl.SSLSocketFactory
        public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
            return patch(this.delegate.createSocket(socket, str, i, z));
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(String str, int i) throws IOException {
            return patch(this.delegate.createSocket(str, i));
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
            return patch(this.delegate.createSocket(str, i, inetAddress, i2));
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
            return patch(this.delegate.createSocket(inetAddress, i));
        }

        @Override // javax.net.SocketFactory
        public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
            return patch(this.delegate.createSocket(inetAddress, i, inetAddress2, i2));
        }

        private Socket patch(Socket socket) {
            if (socket instanceof SSLSocket) {
                ((SSLSocket) socket).setEnabledProtocols(this.TLS_V12_ONLY);
            }
            return socket;
        }
    }
}
