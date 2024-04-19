package com.xiaopeng.speech.protocol.node.speech;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.SpeechDialogEvent;
/* loaded from: classes2.dex */
public class SpeechDialogNode_Processor implements ICommandProcessor {
    private SpeechDialogNode mTarget;

    public SpeechDialogNode_Processor(SpeechDialogNode speechDialogNode) {
        this.mTarget = speechDialogNode;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        char c;
        switch (str.hashCode()) {
            case -1817558719:
                if (str.equals(SpeechDialogEvent.REFRESH_CARSPEECHSERVICE_UI)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1676326235:
                if (str.equals(SpeechDialogEvent.SWITCH_SPEECH_CONTINUOUS_ENABLE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1440570874:
                if (str.equals(SpeechDialogEvent.SWITCH_SPEECH_CONTINUOUS_DISABLED)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1181317093:
                if (str.equals(SpeechDialogEvent.SYSTEM_LISTENTIME_ACCOMPANY_CLOSE)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -412443633:
                if (str.equals(SpeechDialogEvent.ROUTE_OPEN_SCENE_GUIDE_WINDOW)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -265464658:
                if (str.equals(SpeechDialogEvent.ROUTE_CLOSE_SCENE_GUIDE_WINDOW)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -176140078:
                if (str.equals(SpeechDialogEvent.ROUTE_CLOSE_SPEECH_WINDOW)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 239348679:
                if (str.equals(SpeechDialogEvent.SYSTEM_LISTENTIME_ACCOMPANY_OPEN)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1975136465:
                if (str.equals(SpeechDialogEvent.ROUTE_OPEN_SPEECH_WINDOW)) {
                    c = 1;
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
                this.mTarget.onCloseWindow(str, str2);
                return;
            case 1:
                this.mTarget.onOpenWindow(str, str2);
                return;
            case 2:
                this.mTarget.onOpenSceneGuideWindow(str, str2);
                return;
            case 3:
                this.mTarget.onCloseSceneGuideWindow(str, str2);
                return;
            case 4:
                this.mTarget.onOpenSpeechSceneSetting(str, str2);
                return;
            case 5:
                this.mTarget.onCloseSpeechSceneSetting(str, str2);
                return;
            case 6:
                this.mTarget.onOpenSuperDialogue(str, str2);
                return;
            case 7:
                this.mTarget.onCloseSuperDialogue(str, str2);
                return;
            case '\b':
                this.mTarget.onRefreshUi(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{SpeechDialogEvent.ROUTE_CLOSE_SPEECH_WINDOW, SpeechDialogEvent.ROUTE_OPEN_SPEECH_WINDOW, SpeechDialogEvent.ROUTE_OPEN_SCENE_GUIDE_WINDOW, SpeechDialogEvent.ROUTE_CLOSE_SCENE_GUIDE_WINDOW, SpeechDialogEvent.SWITCH_SPEECH_CONTINUOUS_ENABLE, SpeechDialogEvent.SWITCH_SPEECH_CONTINUOUS_DISABLED, SpeechDialogEvent.SYSTEM_LISTENTIME_ACCOMPANY_OPEN, SpeechDialogEvent.SYSTEM_LISTENTIME_ACCOMPANY_CLOSE, SpeechDialogEvent.REFRESH_CARSPEECHSERVICE_UI};
    }
}
