package com.xiaopeng.car.settingslibrary.manager.emergency.handshake;

import android.os.SystemClock;
import com.xiaopeng.car.settingslibrary.common.utils.GsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.manager.emergency.bean.UdpHandshakeProtocol;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
/* loaded from: classes.dex */
public class HandshakeReceiver implements Runnable {
    public static final int CREATE_SOCKET = 1;
    public static final int SOCKET_ALREADY_CREATE = 2;
    private static final String TAG = HandshakeReceiver.class.getSimpleName();
    private ApUdpAcceptListener mListener;
    private final int mPort = 19874;
    private volatile boolean mRunning = true;
    private DatagramSocket mServerSocket;
    private boolean mSocketServerCreateSuc;

    /* loaded from: classes.dex */
    public interface ApUdpAcceptListener {
        void handShake(int i, UdpHandshakeProtocol udpHandshakeProtocol);
    }

    @Override // java.lang.Runnable
    public void run() {
        ApUdpAcceptListener apUdpAcceptListener;
        Logs.log(TAG, "handshake receive thread start...");
        init(19874);
        byte[] bArr = new byte[1024];
        Logs.log(TAG, "udp server create success!!!");
        while (this.mRunning && !Thread.currentThread().isInterrupted()) {
            Arrays.fill(bArr, (byte) 0);
            Logs.log(TAG, "udp waiting data!!!");
            DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
            try {
                this.mServerSocket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
                Logs.log(TAG, "udp receive occurs an exception!");
                this.mRunning = false;
            }
            String byteToStr = byteToStr(bArr);
            if (byteToStr == null) {
                Logs.log(TAG, "udp server accept data is Empty!");
                return;
            }
            String str = TAG;
            Logs.log(str, "udp server accept data" + byteToStr);
            UdpHandshakeProtocol udpHandshakeProtocol = (UdpHandshakeProtocol) GsonUtils.convertString2Object(byteToStr, UdpHandshakeProtocol.class);
            if (udpHandshakeProtocol == null || udpHandshakeProtocol.getPort() < 0 || udpHandshakeProtocol.getPort() > 65535) {
                Logs.log(TAG, "parse udp message occurs exception!!!");
            } else {
                udpHandshakeProtocol.setIp(datagramPacket.getAddress().getHostAddress());
                try {
                    this.mServerSocket.send(new DatagramPacket(bArr, bArr.length, datagramPacket.getAddress(), udpHandshakeProtocol.getPort()));
                } catch (IOException e2) {
                    e2.printStackTrace();
                    String str2 = TAG;
                    Logs.log(str2, "send packet occurs exception!" + byteToStr);
                    this.mRunning = false;
                    DatagramSocket datagramSocket = this.mServerSocket;
                    if (datagramSocket != null) {
                        datagramSocket.close();
                    }
                }
                if (udpHandshakeProtocol.getAction() == 0 && (apUdpAcceptListener = this.mListener) != null) {
                    boolean z = this.mSocketServerCreateSuc;
                    if (!z) {
                        apUdpAcceptListener.handShake(1, udpHandshakeProtocol);
                        this.mSocketServerCreateSuc = true;
                    } else if (z) {
                        apUdpAcceptListener.handShake(2, udpHandshakeProtocol);
                    }
                }
            }
        }
        releaseSocket();
        Logs.log(TAG, "handshake receive thread release suc...");
    }

    private void init(int i) {
        String str = TAG;
        Logs.log(str, "init port " + i);
        SystemClock.sleep(1000L);
        try {
            this.mServerSocket = new DatagramSocket(i);
        } catch (SocketException e) {
            e.printStackTrace();
            if (e instanceof BindException) {
                init(i - 1);
            }
        }
    }

    private void releaseSocket() {
        DatagramSocket datagramSocket = this.mServerSocket;
        if (datagramSocket != null) {
            datagramSocket.close();
        }
    }

    public void cleanUp() {
        this.mRunning = false;
        this.mListener = null;
        releaseSocket();
        Logs.log(TAG, "handshake clean up end...");
    }

    private String byteToStr(byte[] bArr) {
        int i = 0;
        while (true) {
            try {
                if (i >= bArr.length) {
                    i = 0;
                    break;
                } else if (bArr[i] == 0) {
                    break;
                } else {
                    i++;
                }
            } catch (Exception unused) {
                return "";
            }
        }
        return new String(bArr, 0, i, StandardCharsets.UTF_8);
    }

    public void setListener(ApUdpAcceptListener apUdpAcceptListener) {
        this.mListener = apUdpAcceptListener;
    }
}
