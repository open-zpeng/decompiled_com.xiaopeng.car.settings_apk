package com.xiaopeng.speech.protocol.node.bugreport;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.BugReportEvent;
/* loaded from: classes.dex */
public class BugReportNode_Processor implements ICommandProcessor {
    private BugReportNode mTarget;

    public BugReportNode_Processor(BugReportNode bugReportNode) {
        this.mTarget = bugReportNode;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        char c;
        switch (str.hashCode()) {
            case -1272286452:
                if (str.equals(BugReportEvent.BUG_REPORT_END)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1272269173:
                if (str.equals(BugReportEvent.BUG_REPORT_ENDTTS)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1265421229:
                if (str.equals(BugReportEvent.BUG_REPORT_BEGIN)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -508144235:
                if (str.equals(BugReportEvent.BUG_REPORT_VOLUME)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            this.mTarget.onBugReportBegin(str, str2);
        } else if (c == 1) {
            this.mTarget.onBugReportEnd(str, str2);
        } else if (c == 2) {
            this.mTarget.onBugReportVolume(str, str2);
        } else if (c != 3) {
        } else {
            this.mTarget.onBugReportEndtts(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{BugReportEvent.BUG_REPORT_BEGIN, BugReportEvent.BUG_REPORT_END, BugReportEvent.BUG_REPORT_VOLUME, BugReportEvent.BUG_REPORT_ENDTTS};
    }
}
