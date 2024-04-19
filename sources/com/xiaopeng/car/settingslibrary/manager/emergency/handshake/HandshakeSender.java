package com.xiaopeng.car.settingslibrary.manager.emergency.handshake;

import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/* loaded from: classes.dex */
public class HandshakeSender implements Runnable {
    private static final int REPLY_TIME = 2;
    private static final String TAG = HandshakeSender.class.getSimpleName();
    private DatagramSocket mDatagramSocket;
    private final String mReplyClientIp;
    private final int mReplyClientPort;
    private final String mReplyContent;

    public HandshakeSender(String str, String str2, int i) {
        this.mReplyContent = str;
        this.mReplyClientIp = str2;
        this.mReplyClientPort = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            boolean isReachable = InetAddress.getByName(this.mReplyClientIp).isReachable(500);
            String str = TAG;
            Logs.log(str, "connection :" + this.mReplyClientIp + "isReachable:" + isReachable);
            if (!isReachable) {
                return;
            }
        } catch (IOException e) {
            Logs.log(TAG, "check the reachable occurs exception...");
            handlerException(e);
        }
        try {
            this.mDatagramSocket = new DatagramSocket();
        } catch (SocketException e2) {
            Logs.log(TAG, "create the udp sever occurs exception...");
            handlerException(e2);
        }
        byte[] bytes = this.mReplyContent.getBytes();
        for (int i = 0; i < 2; i++) {
            try {
                this.mDatagramSocket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName(this.mReplyClientIp), this.mReplyClientPort));
                String str2 = TAG;
                Logs.log(str2, "udp server send data" + this.mReplyContent);
            } catch (IOException e3) {
                Logs.log(TAG, "send the udp message occurs exception...");
                handlerException(e3);
            }
        }
    }

    private void handlerException(Exception exc) {
        exc.printStackTrace();
        DatagramSocket datagramSocket = this.mDatagramSocket;
        if (datagramSocket != null) {
            datagramSocket.close();
            this.mDatagramSocket = null;
        }
    }
}
