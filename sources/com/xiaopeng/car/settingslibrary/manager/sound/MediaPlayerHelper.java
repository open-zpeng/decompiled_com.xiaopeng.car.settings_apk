package com.xiaopeng.car.settingslibrary.manager.sound;

import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.CarSettingsApp;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.speech.speechwidget.ListWidget;
/* loaded from: classes.dex */
public class MediaPlayerHelper {
    private static final int PLAY_STATE_END = 0;
    private static final int PLAY_STATE_START = 1;
    private static final String TAG = "MediaPlayerHelper";
    private AudioAttributes mAudioAttributes;
    private String mCurrentPlayPath;
    private MediaPlayer mMediaPlayer;
    private OnPlayStateChangeListener mOnPlayStateChangeListener;
    AudioManager.OnAudioFocusChangeListener focusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.MediaPlayerHelper.1
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            Logs.d("MediaPlayerHelper media focusChangeListener focusChange:" + i);
            if (i != -2) {
                if (i == -1) {
                    try {
                        if (MediaPlayerHelper.this.mMediaPlayer.isPlaying()) {
                            MediaPlayerHelper.this.mMediaPlayer.stop();
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    MediaPlayerHelper.this.mMediaPlayer.release();
                    MediaPlayerHelper.this.mMediaPlayer = null;
                } else if (i != 1) {
                } else {
                    try {
                        if (MediaPlayerHelper.this.mMediaPlayer != null) {
                            if (!MediaPlayerHelper.this.mMediaPlayer.isPlaying()) {
                                MediaPlayerHelper.this.mMediaPlayer.start();
                            }
                        } else {
                            MediaPlayerHelper.this.mMediaPlayer = new MediaPlayer();
                        }
                    } catch (IllegalStateException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    };
    private AudioManager mAudioManager = (AudioManager) CarSettingsApp.getContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);

    /* loaded from: classes.dex */
    public interface OnPlayStateChangeListener {
        void onPlayStateChanged(String str, int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class InnerFactory {
        private static final MediaPlayerHelper instance = new MediaPlayerHelper();

        private InnerFactory() {
        }
    }

    public static MediaPlayerHelper getInstance() {
        return InnerFactory.instance;
    }

    private void initMediaPlayer() {
        if (this.mMediaPlayer == null) {
            this.mMediaPlayer = new MediaPlayer();
            this.mAudioAttributes = new AudioAttributes.Builder().setLegacyStreamType(1).build();
            try {
                this.mMediaPlayer.setAudioAttributes(this.mAudioAttributes);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            this.mMediaPlayer.setLooping(false);
            this.mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$MediaPlayerHelper$kWNDnB16JIq0Xuh5GzCkvALl-qQ
                @Override // android.media.MediaPlayer.OnErrorListener
                public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    return MediaPlayerHelper.lambda$initMediaPlayer$0(mediaPlayer, i, i2);
                }
            });
            this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$MediaPlayerHelper$Y_QLuxv2uyc6UlCb1hVCNBV74J8
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer) {
                    MediaPlayerHelper.this.lambda$initMediaPlayer$1$MediaPlayerHelper(mediaPlayer);
                }
            });
            this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.car.settingslibrary.manager.sound.-$$Lambda$MediaPlayerHelper$FuS78MiolydGt3UoxY-rT7yoIeE
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    MediaPlayerHelper.this.lambda$initMediaPlayer$2$MediaPlayerHelper(mediaPlayer);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initMediaPlayer$0(MediaPlayer mediaPlayer, int i, int i2) {
        Logs.d("MediaPlayerHelper player onError:" + i + " " + i2);
        try {
            mediaPlayer.reset();
            return false;
        } catch (Exception e) {
            Logs.d("MediaPlayerHelper Exception onError:" + e);
            return false;
        }
    }

    public /* synthetic */ void lambda$initMediaPlayer$1$MediaPlayerHelper(MediaPlayer mediaPlayer) {
        Logs.d("MediaPlayerHelper play completion ");
        this.mAudioManager.abandonAudioFocus(this.focusChangeListener);
        OnPlayStateChangeListener onPlayStateChangeListener = this.mOnPlayStateChangeListener;
        if (onPlayStateChangeListener != null) {
            onPlayStateChangeListener.onPlayStateChanged(this.mCurrentPlayPath, 0);
            releaseOnPlayStateChangeListener();
        }
    }

    public /* synthetic */ void lambda$initMediaPlayer$2$MediaPlayerHelper(MediaPlayer mediaPlayer) {
        boolean requestAudioFocus = requestAudioFocus();
        Logs.d("MediaPlayerHelper play onPrepared " + requestAudioFocus);
        if (requestAudioFocus) {
            try {
                this.mMediaPlayer.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            Logs.d("MediaPlayerHelper play duration:" + this.mMediaPlayer.getDuration());
            OnPlayStateChangeListener onPlayStateChangeListener = this.mOnPlayStateChangeListener;
            if (onPlayStateChangeListener != null) {
                onPlayStateChangeListener.onPlayStateChanged(this.mCurrentPlayPath, 1);
            }
        }
    }

    public boolean requestAudioFocus() {
        int i;
        if (this.mAudioAttributes != null) {
            i = this.mAudioManager.requestAudioFocus(new AudioFocusRequest.Builder(3).setAudioAttributes(this.mAudioAttributes).setOnAudioFocusChangeListener(this.focusChangeListener).build());
            Logs.d("MediaPlayerHelper requestAudioFocus result:" + i);
        } else {
            i = 0;
        }
        return i == 1;
    }

    public void play(String str) {
        if (this.mMediaPlayer == null) {
            initMediaPlayer();
        } else {
            resetMediaPlayer();
        }
        try {
            this.mCurrentPlayPath = str;
            this.mMediaPlayer.setDataSource(str);
            Logs.d("MediaPlayerHelper play path:" + str);
            try {
                this.mMediaPlayer.prepareAsync();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            Logs.d("MediaPlayerHelper set data source occurs an exception,play failure");
            e2.printStackTrace();
            try {
                this.mMediaPlayer.reset();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public boolean isPlaying(String str) {
        boolean z;
        MediaPlayer mediaPlayer;
        if (!TextUtils.isEmpty(str) && str.equals(this.mCurrentPlayPath) && (mediaPlayer = this.mMediaPlayer) != null) {
            try {
                z = mediaPlayer.isPlaying();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            Logs.d("MediaPlayerHelper isPlaying playing:" + z + " path:" + str + " mCurrentPlayPath:" + this.mCurrentPlayPath);
            return z;
        }
        z = false;
        Logs.d("MediaPlayerHelper isPlaying playing:" + z + " path:" + str + " mCurrentPlayPath:" + this.mCurrentPlayPath);
        return z;
    }

    private void resetMediaPlayer() {
        try {
            if (this.mMediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        try {
            this.mMediaPlayer.reset();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void pause() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.mMediaPlayer.pause();
    }

    public void stop() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    this.mMediaPlayer.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mAudioManager.abandonAudioFocus(this.focusChangeListener);
        }
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        try {
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer.stop();
                this.mMediaPlayer.release();
                this.mMediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnPlayStateChangeListener(OnPlayStateChangeListener onPlayStateChangeListener) {
        this.mOnPlayStateChangeListener = onPlayStateChangeListener;
    }

    public void releaseOnPlayStateChangeListener() {
        this.mOnPlayStateChangeListener = null;
    }

    public OnPlayStateChangeListener getOnPlayStateChangeListener() {
        return this.mOnPlayStateChangeListener;
    }
}
