package com.xiaopeng.speech.protocol.node.tts;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.TtsEvent;
/* loaded from: classes2.dex */
public class TtsNode_Processor implements ICommandProcessor {
    private TtsNode mTarget;

    public TtsNode_Processor(TtsNode ttsNode) {
        this.mTarget = ttsNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1878648165) {
            if (str.equals(TtsEvent.SPEECH_TTS_START)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != -1163978412) {
            if (hashCode == -201524934 && str.equals(TtsEvent.TTS_TIMBRE_SETTING)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals(TtsEvent.SPEECH_TTS_END)) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            this.mTarget.onTtsStart(str, str2);
        } else if (c == 1) {
            this.mTarget.onTtsEnd(str, str2);
        } else if (c != 2) {
        } else {
            this.mTarget.onTtsTimbreSetting(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{TtsEvent.SPEECH_TTS_START, TtsEvent.SPEECH_TTS_END, TtsEvent.TTS_TIMBRE_SETTING};
    }
}
