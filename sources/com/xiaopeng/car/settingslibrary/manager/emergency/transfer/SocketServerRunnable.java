package com.xiaopeng.car.settingslibrary.manager.emergency.transfer;

import android.os.SystemClock;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
/* loaded from: classes.dex */
public class SocketServerRunnable implements Runnable {
    private static final int BYTE_SIZE_FILE_REQUEST = 1024;
    private static final int SOCKET_TIME_OUT = 3000;
    private static final String TAG = SocketServerRunnable.class.getSimpleName();
    private final SocketServerListener mListener;
    private int mPort = 19222;
    private boolean running = true;
    private ServerSocket serverSocket;

    /* loaded from: classes.dex */
    public interface SocketServerListener {
        void createSuccess(int i);

        void receiverData(String str, String str2);

        void shutdown();
    }

    public SocketServerRunnable(SocketServerListener socketServerListener) {
        this.mListener = socketServerListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String str = TAG;
            Logs.log(str, "Create ServerSocket at port" + this.mPort);
            this.serverSocket = new ServerSocket();
            this.serverSocket.setReuseAddress(true);
            bindPort(this.mPort);
            String str2 = TAG;
            Logs.log(str2, "Create ServerSocket success onPort:" + this.mPort);
        } catch (IOException e) {
            Logs.log(TAG, "Create server socket occur exception!");
            e.printStackTrace();
        }
        SocketServerListener socketServerListener = this.mListener;
        if (socketServerListener != null) {
            socketServerListener.createSuccess(this.mPort);
        }
        while (this.running && !Thread.currentThread().isInterrupted()) {
            try {
                receiveData(this.serverSocket.accept());
            } catch (IOException e2) {
                e2.printStackTrace();
                Logs.log(TAG, "the server socket occurs an exception");
                this.running = false;
                SocketServerListener socketServerListener2 = this.mListener;
                if (socketServerListener2 != null) {
                    socketServerListener2.shutdown();
                }
            }
        }
        SocketServerListener socketServerListener3 = this.mListener;
        if (socketServerListener3 != null) {
            socketServerListener3.shutdown();
        }
        Logs.log(TAG, "the socket server thread is over");
    }

    private void receiveData(Socket socket) {
        String inetAddress = socket.getInetAddress().toString();
        String str = TAG;
        Logs.log(str, "the tcp client is connect success. client info, ip: " + inetAddress + " ,port: " + socket.getPort());
        try {
            socket.setSoTimeout(3000);
            InputStream inputStream = socket.getInputStream();
            byte[] bArr = new byte[1024];
            int i = 0;
            do {
                int read = inputStream.read();
                if (read == -1) {
                    break;
                }
                bArr[i] = (byte) read;
                i++;
            } while (i != bArr.length);
            String byteToStr = byteToStr(bArr);
            if (this.mListener != null) {
                this.mListener.receiverData(inetAddress, byteToStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        Logs.log(TAG, "close the server socket");
        this.running = false;
        ServerSocket serverSocket = this.serverSocket;
        if (serverSocket != null) {
            try {
                serverSocket.close();
                this.serverSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindPort(int i) {
        Logs.log(TAG, "socket server bind on port: " + i);
        ServerSocket serverSocket = this.serverSocket;
        if (serverSocket == null) {
            Logs.log(TAG, "serverSocket is null");
            return;
        }
        try {
            serverSocket.bind(new InetSocketAddress(this.mPort));
        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof BindException) {
                this.mPort++;
                SystemClock.sleep(1000L);
                bindPort(this.mPort);
            }
        }
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
}
