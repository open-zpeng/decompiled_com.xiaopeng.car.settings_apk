package com.xiaopeng.car.settingslibrary.manager.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.audioConfig.AudioConfig;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.xiaopeng.car.settingslibrary.common.Config;
import com.xiaopeng.car.settingslibrary.common.utils.LogUtils;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.common.utils.ReflectUtils;
import com.xiaopeng.speech.speechwidget.ListWidget;
/* loaded from: classes.dex */
public class PlaySoundHelper implements Handler.Callback {
    private static final String MEDIA_PATH = "/system/media/audio/xiaopeng/cdu/wav/CDU_volume_slider.wav";
    private static final int MSG_INIT_SAMPLE = 3;
    private static final int MSG_SET_STREAM_VOLUME = 0;
    private static final int MSG_START_SAMPLE = 1;
    private static final int MSG_STOP_SAMPLE = 2;
    AudioAttributes mAudioAttributes;
    private AudioConfig mAudioConfig;
    AudioManager mAudioManager;
    private final Context mContext;
    private final Uri mDefaultUri;
    private Handler mHandler;
    private Ringtone mRingtone;
    private final int mStreamType;
    private boolean mIsExcludeAnyActive = true;
    private boolean mIsHeadrest = false;
    AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.PlaySoundHelper.1
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            Logs.d("xpsound focusChange:" + i);
            if (i == -2) {
            }
        }
    };

    public PlaySoundHelper(Context context, int i) {
        this.mContext = context;
        this.mStreamType = i;
        this.mDefaultUri = getMediaVolumeUri(i);
        this.mAudioManager = (AudioManager) context.getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        this.mAudioConfig = new AudioConfig(context);
    }

    public PlaySoundHelper(Context context, int i, Uri uri) {
        this.mContext = context;
        this.mStreamType = i;
        this.mDefaultUri = uri;
        this.mAudioManager = (AudioManager) context.getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        this.mAudioConfig = new AudioConfig(context);
    }

    public void setExcludeAnyActive(boolean z) {
        this.mIsExcludeAnyActive = z;
    }

    public void setHeadrest(boolean z) {
        this.mIsHeadrest = z;
    }

    public int getStreamType() {
        return this.mStreamType;
    }

    private Uri getMediaVolumeUri(int i) {
        return Uri.parse(MEDIA_PATH);
    }

    public boolean requestAudioFocus() {
        return (this.mAudioAttributes != null ? this.mAudioManager.requestAudioFocus(new AudioFocusRequest.Builder(2).setAudioAttributes(this.mAudioAttributes).setOnAudioFocusChangeListener(this.mAudioFocusListener).build()) : 0) == 1;
    }

    public void start() {
        if (this.mHandler != null) {
            return;
        }
        HandlerThread handlerThread = new HandlerThread(".CallbackHandler");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper(), this);
        this.mHandler.sendEmptyMessage(3);
    }

    public void stop() {
        if (this.mHandler == null) {
            return;
        }
        postStopSample();
        this.mHandler.getLooper().quitSafely();
        this.mHandler = null;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        Logs.d("xpsound play finalize");
        stop();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i != 0) {
            if (i == 1) {
                onStartSample();
            } else if (i == 2) {
                onStopSample();
            } else if (i == 3) {
                onInitSample();
            }
        }
        return true;
    }

    private void onInitSample() {
        synchronized (this) {
            this.mRingtone = RingtoneManager.getRingtone(this.mContext, this.mDefaultUri);
            if (this.mRingtone != null) {
                int i = -1;
                if (this.mStreamType == 3) {
                    i = 1;
                } else if (this.mStreamType == 10) {
                    i = 16;
                } else if (this.mStreamType == 9) {
                    i = 12;
                } else if (this.mStreamType == Config.AVAS_STREAM) {
                    i = ReflectUtils.getIntValue("USAGE_AVAS", AudioAttributes.class);
                } else if (this.mStreamType == 13) {
                    i = 19;
                }
                this.mAudioAttributes = new AudioAttributes.Builder().setUsage(i).setFlags(this.mIsHeadrest ? 1048704 : 128).build();
                this.mRingtone.setAudioAttributes(this.mAudioAttributes);
            }
        }
    }

    private void onStartSample() {
        Logs.d("xpsound play isSamplePlaying:" + isSamplePlaying());
        if (this.mIsExcludeAnyActive && this.mAudioConfig.isAnyStreamActive()) {
            LogUtils.d("xpsound play isAnyStreamActive true return!");
        } else if (!isSamplePlaying()) {
            synchronized (this) {
                if (this.mRingtone != null) {
                    try {
                        this.mRingtone.play();
                        Logs.d("xpsound play start");
                    } catch (Throwable unused) {
                    }
                }
            }
        } else {
            postStopSample();
            postStartSample();
        }
    }

    public boolean isSamplePlaying() {
        boolean z;
        synchronized (this) {
            z = this.mRingtone != null && this.mRingtone.isPlaying();
        }
        return z;
    }

    public void onStopSample() {
        synchronized (this) {
            if (this.mRingtone != null) {
                this.mRingtone.stop();
                Logs.d("xpsound play stop");
            }
        }
    }

    public void postStartSample() {
        start();
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(1);
        Handler handler2 = this.mHandler;
        handler2.sendMessage(handler2.obtainMessage(1));
    }

    public void postStopSample() {
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(1);
        this.mHandler.removeMessages(2);
        Handler handler2 = this.mHandler;
        handler2.sendMessage(handler2.obtainMessage(2));
    }
}
