package com.xiaopeng.car.settingslibrary.manager.emergency;

import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.GsonUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.NetworkUtils;
import com.xiaopeng.car.settingslibrary.manager.car.CarSettingsManager;
import com.xiaopeng.car.settingslibrary.manager.emergency.bean.UdpHandshakeProtocol;
import com.xiaopeng.car.settingslibrary.manager.emergency.handshake.HandshakeReceiver;
import com.xiaopeng.car.settingslibrary.manager.emergency.handshake.HandshakeSender;
import com.xiaopeng.car.settingslibrary.manager.emergency.transfer.SocketServerRunnable;
/* loaded from: classes.dex */
public class EmergencyKeyManager {
    private static final String TAG = "EmergencyKeyManager";
    private HandshakeReceiver mHandShakeReceiver;
    private int mSocketServerPort = -1;
    private SocketServerRunnable mSocketServerRunnable;

    public void enterEmergencyProcess() {
        Logs.log(TAG, "enterEmergencyProcess...");
        createHandShakeListener();
    }

    public void exitEmergencyProcess() {
        Logs.log(TAG, "exitEmergencyProcess...");
        HandshakeReceiver handshakeReceiver = this.mHandShakeReceiver;
        if (handshakeReceiver != null) {
            handshakeReceiver.cleanUp();
            this.mHandShakeReceiver = null;
        }
        SocketServerRunnable socketServerRunnable = this.mSocketServerRunnable;
        if (socketServerRunnable != null) {
            socketServerRunnable.quit();
            this.mSocketServerRunnable = null;
        }
        this.mSocketServerPort = -1;
    }

    private void createHandShakeListener() {
        Logs.log(TAG, "");
        this.mHandShakeReceiver = new HandshakeReceiver();
        this.mHandShakeReceiver.setListener(new HandshakeReceiver.ApUdpAcceptListener() { // from class: com.xiaopeng.car.settingslibrary.manager.emergency.-$$Lambda$EmergencyKeyManager$fiLWnKW_ADnABQK5scKcBrGNNrA
            @Override // com.xiaopeng.car.settingslibrary.manager.emergency.handshake.HandshakeReceiver.ApUdpAcceptListener
            public final void handShake(int i, UdpHandshakeProtocol udpHandshakeProtocol) {
                EmergencyKeyManager.this.lambda$createHandShakeListener$0$EmergencyKeyManager(i, udpHandshakeProtocol);
            }
        });
        new Thread(this.mHandShakeReceiver, "HandShakeReceive-Thread").start();
    }

    public /* synthetic */ void lambda$createHandShakeListener$0$EmergencyKeyManager(int i, UdpHandshakeProtocol udpHandshakeProtocol) {
        if (i == 1) {
            createSocketServer(udpHandshakeProtocol);
        } else if (i == 2) {
            reuseSocketServer(udpHandshakeProtocol);
        } else {
            LogUtils.w(TAG, "un catch case,stage: " + i);
        }
    }

    private void reuseSocketServer(UdpHandshakeProtocol udpHandshakeProtocol) {
        Logs.log(TAG, "reuse socket, at port :" + this.mSocketServerPort + " udp message:" + udpHandshakeProtocol);
        if (this.mSocketServerPort == -1) {
            SocketServerRunnable socketServerRunnable = this.mSocketServerRunnable;
            if (socketServerRunnable != null) {
                socketServerRunnable.quit();
                this.mSocketServerRunnable = null;
            }
            createSocketServer(udpHandshakeProtocol);
            return;
        }
        sendHandShakeMessage2Client(wrapHandShakeMessage2Client(NetworkUtils.getIPAddress(true), this.mSocketServerPort, 1), udpHandshakeProtocol.getIp(), udpHandshakeProtocol.getPort());
    }

    private void createSocketServer(final UdpHandshakeProtocol udpHandshakeProtocol) {
        this.mSocketServerRunnable = new SocketServerRunnable(new SocketServerRunnable.SocketServerListener() { // from class: com.xiaopeng.car.settingslibrary.manager.emergency.EmergencyKeyManager.1
            @Override // com.xiaopeng.car.settingslibrary.manager.emergency.transfer.SocketServerRunnable.SocketServerListener
            public void createSuccess(int i) {
                Logs.log(EmergencyKeyManager.TAG, "socket server create success at port: " + i);
                EmergencyKeyManager.this.mSocketServerPort = i;
                EmergencyKeyManager.this.sendHandShakeMessage2Client(EmergencyKeyManager.this.wrapHandShakeMessage2Client(NetworkUtils.getIPAddress(true), i, 1), udpHandshakeProtocol.getIp(), udpHandshakeProtocol.getPort());
            }

            @Override // com.xiaopeng.car.settingslibrary.manager.emergency.transfer.SocketServerRunnable.SocketServerListener
            public void receiverData(String str, String str2) {
                Logs.log(EmergencyKeyManager.TAG, "receive data from client, client ip: " + str + " data:" + str2);
                String parseContent = WifiKeyParser.parseContent(str2);
                EmergencyKeyManager.this.sendHandShakeMessage2Client(EmergencyKeyManager.this.wrapHandShakeMessage2Client("", 0, TextUtils.isEmpty(parseContent) ? 4 : 3), udpHandshakeProtocol.getIp(), udpHandshakeProtocol.getPort());
                if (TextUtils.isEmpty(parseContent)) {
                    return;
                }
                CarSettingsManager.getInstance().sendEmergencyWifiBleMessage(parseContent);
            }

            @Override // com.xiaopeng.car.settingslibrary.manager.emergency.transfer.SocketServerRunnable.SocketServerListener
            public void shutdown() {
                Logs.log(EmergencyKeyManager.TAG, "socket server shutdown...");
                EmergencyKeyManager.this.mSocketServerPort = -1;
            }
        });
        new Thread(this.mSocketServerRunnable, "SocketServer-Thread").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendHandShakeMessage2Client(String str, String str2, int i) {
        new Thread(new HandshakeSender(str, str2, i), "HandShakeSend-Thread").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String wrapHandShakeMessage2Client(String str, int i, int i2) {
        UdpHandshakeProtocol udpHandshakeProtocol = new UdpHandshakeProtocol();
        udpHandshakeProtocol.setAction(i2);
        udpHandshakeProtocol.setPort(i);
        udpHandshakeProtocol.setIp(str);
        String convertVO2String = GsonUtils.convertVO2String(udpHandshakeProtocol);
        LogUtils.d(TAG, "HandShake sender content :" + convertVO2String, false);
        return convertVO2String;
    }
}
