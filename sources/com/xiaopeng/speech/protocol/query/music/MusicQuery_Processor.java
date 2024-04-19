package com.xiaopeng.speech.protocol.query.music;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryMusicEvent;
/* loaded from: classes2.dex */
public class MusicQuery_Processor implements IQueryProcessor {
    private MusicQuery mTarget;

    public MusicQuery_Processor(MusicQuery musicQuery) {
        this.mTarget = musicQuery;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        char c;
        switch (str.hashCode()) {
            case -1560481235:
                if (str.equals("music.is.kugou.authed")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1316092175:
                if (str.equals("music.info.query")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -697358738:
                if (str.equals("music.is.collect.empty")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -565545210:
                if (str.equals("music.get.usb.state")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -481069680:
                if (str.equals("music.is.can.collected")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -289541544:
                if (str.equals("music.is.history.empty")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 196105311:
                if (str.equals(QueryMusicEvent.MUSIC_ACCOUNT_LOGIN)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 291286140:
                if (str.equals(QueryMusicEvent.IS_XIMALAYA_ACCOUNT_LOGIN)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 506081708:
                if (str.equals("music.is.play.similar")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1084784151:
                if (str.equals("music.playtype")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1356318952:
                if (str.equals("music.is.bt.connected")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1377565924:
                if (str.equals("music.info.query.artist")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1429715250:
                if (str.equals("music.info.query.album")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1447189787:
                if (str.equals("music.info.query.title")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1732874385:
                if (str.equals(QueryMusicEvent.IS_CAN_OPEN_QUALITY_PAGE)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 2071296723:
                if (str.equals("music.is.playing")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 2146212582:
                if (str.equals("music.has.bluetooth.musiclist")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return this.mTarget.getPlayInfo(str, str2);
            case 1:
                return this.mTarget.getInfoTite(str, str2);
            case 2:
                return this.mTarget.getInfoArtist(str, str2);
            case 3:
                return this.mTarget.getInfoAlbum(str, str2);
            case 4:
                return Integer.valueOf(this.mTarget.getPlayType(str, str2));
            case 5:
                return Boolean.valueOf(this.mTarget.isPlaying(str, str2));
            case 6:
                return Boolean.valueOf(this.mTarget.hasBluetoothMusicList(str, str2));
            case 7:
                return Boolean.valueOf(this.mTarget.isHistoryEmpty(str, str2));
            case '\b':
                return Boolean.valueOf(this.mTarget.isPlaySimilar(str, str2));
            case '\t':
                return Boolean.valueOf(this.mTarget.isCollectListEmpty(str, str2));
            case '\n':
                return Boolean.valueOf(this.mTarget.isCanCollected(str, str2));
            case 11:
                return Boolean.valueOf(this.mTarget.isBtConnected(str, str2));
            case '\f':
                return Boolean.valueOf(this.mTarget.isKuGouAuthed(str, str2));
            case '\r':
                return Integer.valueOf(this.mTarget.getUsbState(str, str2));
            case 14:
                return Boolean.valueOf(this.mTarget.isMusicAccountLogin(str, str2));
            case 15:
                return Boolean.valueOf(this.mTarget.isQualityPageOpend(str, str2));
            case 16:
                return Boolean.valueOf(this.mTarget.isXimalayaAccountLogin(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{"music.info.query", "music.info.query.title", "music.info.query.artist", "music.info.query.album", "music.playtype", "music.is.playing", "music.has.bluetooth.musiclist", "music.is.history.empty", "music.is.play.similar", "music.is.collect.empty", "music.is.can.collected", "music.is.bt.connected", "music.is.kugou.authed", "music.get.usb.state", QueryMusicEvent.MUSIC_ACCOUNT_LOGIN, QueryMusicEvent.IS_CAN_OPEN_QUALITY_PAGE, QueryMusicEvent.IS_XIMALAYA_ACCOUNT_LOGIN};
    }
}
