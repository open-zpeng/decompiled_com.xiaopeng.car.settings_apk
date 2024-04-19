package com.xiaopeng.car.settingslibrary.common;

import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.car.settingslibrary.common.utils.LocaleUtils;
/* loaded from: classes.dex */
public class CarFunction {
    public static final String D21 = "Q2";
    public static final String D22 = "Q6";
    public static final String D55 = "Q3";
    public static final String D55A = "Q3A";
    public static final String E28 = "Q1";
    public static final String E28A = "Q8";
    public static final String E38 = "Q7";
    public static final String EU = "EU";
    public static final String F30 = "Q9";
    public static final String H93 = "QB";
    public static final String Q5 = "Q5";
    public static final int TYPE_MCU = 2;
    public static final int TYPE_TBOX = 1;

    public static String getProduct() {
        return CarConfigHelper.getXpCduType();
    }

    public static String getRegionType() {
        return CarConfigHelper.getRegionType();
    }

    private static String getXpCduType() {
        return CarConfigHelper.getXpCduType();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportXTitle() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 4;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 7;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2 || c == 3 || c == 4;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportAdjustMeterBrightnessProtect() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportRemotePark() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return false;
            default:
                return D2CarConfigHelper.isHighConfig() || D2CarConfigHelper.isMiddleConfig();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportSoldierModeCamera() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return CarConfigHelper.hasAvm();
            case 3:
            case 4:
            case 5:
            case 6:
                return CarConfigHelper.hasXpu();
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportSoldierModeCameraNewDialog() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 3;
            }
            c = 65535;
        }
        return c == 0 || c == 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportSoldierModeLevel() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportLowSpeedVolumeSwitch() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 7;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return false;
            default:
                return true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportLowSpeedVolumeSlider() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 7;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isNeedStartOOBE() {
        char c;
        String product = getProduct();
        String regionType = getRegionType();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        if (c == 0 || c == 1 || c == 2) {
            return EU.equals(regionType);
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportNearUnlock() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c != 1) {
                if (c == 2) {
                    return true;
                }
            } else if ("0".equals(CarConfigHelper.getCarLevel())) {
                return true;
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportLeaveLock() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c != 1) {
                if (c == 2) {
                    return true;
                }
            } else if ("0".equals(CarConfigHelper.getCarLevel())) {
                return true;
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportEffectFixPoint() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 4;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 7;
            }
            c = 65535;
        }
        return (c == 0 || c == 1 || c == 2 || c == 3 || c == 4) ? false : true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getAutoPowerDevice() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return 1;
            default:
                return 2;
        }
    }

    public static boolean isSupportMcuAutoPower() {
        return getAutoPowerDevice() == 2;
    }

    public static boolean isSupportTboxAutoPower() {
        return getAutoPowerDevice() == 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportChangeCdu() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 7;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return false;
            default:
                return "Q5".equals(getXpCduType());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportKeyPark() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 2;
            }
            c = 65535;
        }
        if (c == 0 || c == 1 || c == 2) {
            return CarConfigHelper.hasKeyRemotePark();
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportRemoteParkAdvanced() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 2;
            }
            c = 65535;
        }
        if (c == 0 || c == 1 || c == 2) {
            return CarConfigHelper.hasXpu();
        }
        return false;
    }

    public static boolean isSupportCarCallAdvanced() {
        String str;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            str = H93;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    str = "Q1";
                    break;
                case 2561:
                    str = "Q2";
                    break;
                case 2562:
                    str = "Q3";
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            str = "Q6";
                            break;
                        case 2566:
                            str = "Q7";
                            break;
                        case 2567:
                            str = "Q8";
                            break;
                        case 2568:
                            str = F30;
                            break;
                        default:
                            return false;
                    }
            }
        } else {
            str = D55A;
        }
        product.equals(str);
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportInductionLock() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 7;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return false;
            default:
                return true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportAutoDriveTts() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return CarConfigHelper.hasAutoDrive();
            case 5:
            case 6:
                return D2CarConfigHelper.isHighConfig() || D2CarConfigHelper.isMiddleConfig();
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportLCC() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return CarConfigHelper.hasAutoDrive();
            default:
                return D2CarConfigHelper.isHighConfig() || D2CarConfigHelper.isMiddleConfig();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isDSeries() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 4;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = '\b';
            }
            c = 65535;
        }
        return (c == 0 || c == 1 || c == 2 || c == 3 || c == 4) ? false : true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportWaitMode() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 5;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSupportGuestGroup() {
        String regionType = getRegionType();
        String product = getProduct();
        char c = 65535;
        if ((regionType.hashCode() == 2224 && regionType.equals(EU)) ? false : true) {
            return false;
        }
        if (product.hashCode() == 2566 && product.equals("Q7")) {
            c = 0;
        }
        return c != 0;
    }

    public static boolean isSupportXpengLaboratory() {
        String regionType = getRegionType();
        return ((regionType.hashCode() == 2224 && regionType.equals(EU)) ? (char) 0 : (char) 65535) != 0;
    }

    public static boolean isSupportFeedback() {
        String regionType = getRegionType();
        return ((regionType.hashCode() == 2224 && regionType.equals(EU)) ? (char) 0 : (char) 65535) != 0;
    }

    public static boolean isSupportAboutSystem() {
        String regionType = getRegionType();
        return ((regionType.hashCode() == 2224 && regionType.equals(EU)) ? (char) 0 : (char) 65535) != 0;
    }

    public static boolean isSupportPrivacyPolicy() {
        String regionType = getRegionType();
        return ((regionType.hashCode() == 2224 && regionType.equals(EU)) ? (char) 0 : (char) 65535) == 0;
    }

    public static boolean isSupportNewPrivacyProtocol() {
        String regionType = getRegionType();
        String product = getProduct();
        if (EU.equals(regionType)) {
            char c = 65535;
            int hashCode = product.hashCode();
            if (hashCode != 79487) {
                switch (hashCode) {
                    case 2560:
                        if (product.equals("Q1")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 2561:
                        if (product.equals("Q2")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 2562:
                        if (product.equals("Q3")) {
                            c = 2;
                            break;
                        }
                        break;
                    default:
                        switch (hashCode) {
                            case 2565:
                                if (product.equals("Q6")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case 2566:
                                if (product.equals("Q7")) {
                                    c = 6;
                                    break;
                                }
                                break;
                            case 2567:
                                if (product.equals("Q8")) {
                                    c = 5;
                                    break;
                                }
                                break;
                        }
                }
            } else if (product.equals(D55A)) {
                c = 3;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    public static boolean isSupportLeadingZeroTimeFormat() {
        String regionType = getRegionType();
        return ((regionType.hashCode() == 2224 && regionType.equals(EU)) ? (char) 0 : (char) 65535) != 0;
    }

    public static boolean isSupportSoundEffectPreviewShow() {
        String product = getProduct();
        if (EU.equalsIgnoreCase(getRegionType())) {
            char c = 65535;
            int hashCode = product.hashCode();
            if (hashCode != 2561) {
                if (hashCode != 2562) {
                    if (hashCode != 2565) {
                        if (hashCode == 79487 && product.equals(D55A)) {
                            c = 2;
                        }
                    } else if (product.equals("Q6")) {
                        c = 3;
                    }
                } else if (product.equals("Q3")) {
                    c = 1;
                }
            } else if (product.equals("Q2")) {
                c = 0;
            }
            return (c == 0 || c == 1 || c == 2 || c == 3) ? false : true;
        }
        return true;
    }

    public static boolean isSupportSoundTtsSettingShow() {
        String regionType = getRegionType();
        String product = getProduct();
        char c = 65535;
        if ((regionType.hashCode() == 2224 && regionType.equals(EU)) ? false : true) {
            return true;
        }
        if (product.hashCode() == 2566 && product.equals("Q7")) {
            c = 0;
        }
        return c == 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportMeterVolumeSp() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSupportXPengCustomerService() {
        String regionType = getRegionType();
        String product = getProduct();
        if (EU.equals(regionType)) {
            char c = 65535;
            int hashCode = product.hashCode();
            if (hashCode != 2561) {
                if (hashCode != 2562) {
                    if (hashCode != 2566) {
                        if (hashCode != 2567) {
                            if (hashCode == 79487 && product.equals(D55A)) {
                                c = 2;
                            }
                        } else if (product.equals("Q8")) {
                            c = 3;
                        }
                    } else if (product.equals("Q7")) {
                        c = 0;
                    }
                } else if (product.equals("Q3")) {
                    c = 1;
                }
            } else if (product.equals("Q2")) {
                c = 4;
            }
            if (c == 0 || c == 1 || c == 2 || c == 3) {
                return true;
            }
            return c == 4 ? LocaleUtils.isNorway() : false;
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportWifiKey() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 5;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSoundEffectLowSpeaker() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 5;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0 || c == 1) {
            String speakerCount = CarConfigHelper.getSpeakerCount();
            if (TextUtils.isEmpty(speakerCount)) {
                return false;
            }
            return speakerCount.equals("2");
        } else if (c != 2) {
            return false;
        } else {
            String speakerCount2 = CarConfigHelper.getSpeakerCount();
            if (TextUtils.isEmpty(speakerCount2)) {
                return false;
            }
            return speakerCount2.equals("4");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSoundEffectHighSpeaker() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 6;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
                String speakerCount = CarConfigHelper.getSpeakerCount();
                if (!TextUtils.isEmpty(speakerCount)) {
                    return speakerCount.equals("8");
                }
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                String speakerCount2 = CarConfigHelper.getSpeakerCount();
                if (!TextUtils.isEmpty(speakerCount2)) {
                    return speakerCount2.equals("18");
                }
                break;
            default:
                return true;
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getMeterDefaultVolume() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 7;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return 1;
            default:
                return 2;
        }
    }

    public static boolean isSupportEventTracking() {
        String regionType = getRegionType();
        String product = getProduct();
        if (EU.equals(regionType)) {
            char c = 65535;
            int hashCode = product.hashCode();
            if (hashCode != 2560) {
                if (hashCode == 2567 && product.equals("Q8")) {
                    c = 1;
                }
            } else if (product.equals("Q1")) {
                c = 0;
            }
            return c == 0 || c == 1;
        }
        return true;
    }

    public static boolean isSupportRegionSet() {
        String regionType = getRegionType();
        String product = getProduct();
        if (EU.equals(regionType)) {
            char c = 65535;
            int hashCode = product.hashCode();
            if (hashCode != 2560) {
                if (hashCode != 2562) {
                    if (hashCode != 79487) {
                        if (hashCode != 2566) {
                            if (hashCode == 2567 && product.equals("Q8")) {
                                c = 3;
                            }
                        } else if (product.equals("Q7")) {
                            c = 0;
                        }
                    } else if (product.equals(D55A)) {
                        c = 2;
                    }
                } else if (product.equals("Q3")) {
                    c = 1;
                }
            } else if (product.equals("Q1")) {
                c = 4;
            }
            return c == 0 || c == 1 || c == 2 || c == 3;
        }
        return false;
    }

    public static boolean isSupportLanguageSet() {
        String regionType = getRegionType();
        String product = getProduct();
        if (EU.equals(regionType)) {
            char c = 65535;
            int hashCode = product.hashCode();
            if (hashCode != 79487) {
                switch (hashCode) {
                    case 2560:
                        if (product.equals("Q1")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 2561:
                        if (product.equals("Q2")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 2562:
                        if (product.equals("Q3")) {
                            c = 1;
                            break;
                        }
                        break;
                    default:
                        switch (hashCode) {
                            case 2565:
                                if (product.equals("Q6")) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case 2566:
                                if (product.equals("Q7")) {
                                    c = 6;
                                    break;
                                }
                                break;
                            case 2567:
                                if (product.equals("Q8")) {
                                    c = 3;
                                    break;
                                }
                                break;
                        }
                }
            } else if (product.equals(D55A)) {
                c = 2;
            }
            return c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5;
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportDoubleScreen() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        return c == 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isBackSeatScreen() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        return c == 0;
    }

    public static boolean isSupportOneKeyCleanCaches() {
        String product = getProduct();
        char c = 65535;
        if (EU.equals(getRegionType())) {
            int hashCode = product.hashCode();
            if (hashCode != 2560) {
                if (hashCode != 2566) {
                    if (hashCode == 2567 && product.equals("Q8")) {
                        c = 1;
                    }
                } else if (product.equals("Q7")) {
                    c = 2;
                }
            } else if (product.equals("Q1")) {
                c = 0;
            }
            return c == 0 || c == 1 || c == 2;
        }
        int hashCode2 = product.hashCode();
        if (hashCode2 != 2577) {
            if (hashCode2 != 79487) {
                switch (hashCode2) {
                    case 2560:
                        if (product.equals("Q1")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 2561:
                        if (product.equals("Q2")) {
                            c = 7;
                            break;
                        }
                        break;
                    case 2562:
                        if (product.equals("Q3")) {
                            c = 3;
                            break;
                        }
                        break;
                    default:
                        switch (hashCode2) {
                            case 2565:
                                if (product.equals("Q6")) {
                                    c = '\b';
                                    break;
                                }
                                break;
                            case 2566:
                                if (product.equals("Q7")) {
                                    c = 5;
                                    break;
                                }
                                break;
                            case 2567:
                                if (product.equals("Q8")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case 2568:
                                if (product.equals(F30)) {
                                    c = 2;
                                    break;
                                }
                                break;
                        }
                }
            } else if (product.equals(D55A)) {
                c = 4;
            }
        } else if (product.equals(H93)) {
            c = 6;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportStorageManage() {
        char c;
        String product = getProduct();
        if (EU.equals(getRegionType())) {
            return false;
        }
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 5;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 2;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportAutoClean() {
        char c;
        String product = getProduct();
        if (EU.equals(getRegionType())) {
            return false;
        }
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 4;
            }
            c = 65535;
        }
        return c == 0 || c == 1;
    }

    public static boolean isSupportAPIRouterPush() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2561) {
            if (product.equals("Q2")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode == 2562) {
            if (product.equals("Q3")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 2565) {
            if (hashCode == 79487 && product.equals(D55A)) {
                c = 3;
            }
            c = 65535;
        } else {
            if (product.equals("Q6")) {
                c = 1;
            }
            c = 65535;
        }
        return (c == 0 || c == 1 || c == 2 || c == 3) ? false : true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSharedMusicData() {
        char c;
        String product = getProduct();
        if (EU.equals(getRegionType())) {
            return false;
        }
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2;
    }

    public static boolean isSupportSharedDisplay() {
        return SystemProperties.getInt("persist.sys.xp.shared_display.enable", -1) == 1;
    }

    public static boolean isE38V() {
        return EU.equals(getRegionType()) && "Q7".equals(getProduct());
    }

    public static boolean isEU() {
        return EU.equals(getRegionType());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportPositionSystemTips() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        if (c == 0 || c == 1 || c == 2 || c == 3) {
            return CarConfigHelper.hasAMP();
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportAvasSpeaker() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportXopera() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        if (c == 0 || c == 1 || c == 2 || c == 3) {
            return CarConfigHelper.hasAMP();
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportEffectScene() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        if (c == 0 || c == 1 || c == 2 || c == 3) {
            return false;
        }
        if (c != 4) {
            return true;
        }
        return !CarConfigHelper.isLowSpeaker();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportHifiNewEffectStyle() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2 || c == 3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportBlueToothWifiDialogRightInfoIcon() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode != 2577) {
            switch (hashCode) {
                case 2566:
                    if (product.equals("Q7")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2567:
                    if (product.equals("Q8")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2568:
                    if (product.equals(F30)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } else {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2 || c == 3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getLowSpeedVolumeDefaultValue() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        if (c != 0) {
            return (c == 1 || c == 2 || c == 3) ? 77 : 89;
        }
        return 84;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isNeedStopXiaoP() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        return (c == 0 || c == 1 || c == 2 || c == 3) ? false : true;
    }

    public static boolean isSupportDts() {
        String product = getProduct();
        String regionType = getRegionType();
        char c = 65535;
        if ((regionType.hashCode() == 2224 && regionType.equals(EU)) ? false : true) {
            return true;
        }
        int hashCode = product.hashCode();
        if (hashCode != 2560) {
            if (hashCode != 2566) {
                if (hashCode == 2567 && product.equals("Q8")) {
                    c = 1;
                }
            } else if (product.equals("Q7")) {
                c = 2;
            }
        } else if (product.equals("Q1")) {
            c = 0;
        }
        return (c == 0 || c == 1 || c == 2) ? false : true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isNonSelfPageUI() {
        char c;
        String product = getProduct();
        String regionType = getRegionType();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2 || (c == 3 && !EU.equals(regionType));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportRecentEffectMode() {
        char c;
        String product = getProduct();
        getRegionType();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2 || c == 3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isDecreaseVolume() {
        char c;
        String product = getProduct();
        String regionType = getRegionType();
        if (EU.equals(regionType) && product.equals("Q6")) {
            return true;
        }
        if (EU.equals(regionType)) {
            return false;
        }
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 7;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 2;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2 || c == 3 || c == 4;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportSpeedVolumeLink() {
        char c;
        String product = getProduct();
        String regionType = getRegionType();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        if ((c == 0 || c == 1 || c == 2 || c == 3) && !EU.equals(regionType)) {
            return CarConfigHelper.hasAMP();
        }
        return false;
    }

    public static boolean isSupportSoundVolumeAdjustBySpeed() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2566) {
            if (product.equals("Q7")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 2567) {
            if (hashCode == 2577 && product.equals(H93)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (product.equals("Q8")) {
                c = 1;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2;
    }

    public static boolean isSupportSoundOutsideLowSpeedSimulate() {
        char c;
        String product = getProduct();
        String regionType = getRegionType();
        int hashCode = product.hashCode();
        if (hashCode == 2566) {
            if (product.equals("Q7")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 2567) {
            if (hashCode == 2577 && product.equals(H93)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (product.equals("Q8")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0 || c == 1 || c == 2) {
            return !EU.equals(regionType);
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportAppScreenFlow() {
        char c;
        String product = getProduct();
        String regionType = getRegionType();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        return c == 0 && !EU.equals(regionType);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportRearRowReminder() {
        char c;
        String product = getProduct();
        getRegionType();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 6;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2 || c == 3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isWifiKeybyVin() {
        char c;
        String product = getProduct();
        getRegionType();
        int hashCode = product.hashCode();
        if (hashCode == 2562) {
            if (product.equals("Q3")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 5;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2566:
                    if (product.equals("Q7")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2567:
                    if (product.equals("Q8")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2568:
                    if (product.equals(F30)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } else {
            if (product.equals(D55A)) {
                c = 3;
            }
            c = 65535;
        }
        return (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5) ? false : true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportXpAutoConnect() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode == 2577) {
            if (product.equals(H93)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 79487) {
            switch (hashCode) {
                case 2560:
                    if (product.equals("Q1")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (product.equals("Q2")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (product.equals("Q3")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2565:
                            if (product.equals("Q6")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (product.equals("Q7")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (product.equals("Q8")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (product.equals(F30)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (product.equals(D55A)) {
                c = 5;
            }
            c = 65535;
        }
        return c == 0 || c == 1 || c == 2;
    }

    public static boolean isMainPsnSharedSoundField() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode != 2567) {
            if (hashCode == 2568 && product.equals(F30)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (product.equals("Q8")) {
                c = 0;
            }
            c = 65535;
        }
        return c == 0 || c == 1;
    }

    public static boolean isSupportDyna() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode != 2560) {
            if (hashCode == 2567 && product.equals("Q8")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (product.equals("Q1")) {
                c = 0;
            }
            c = 65535;
        }
        if (c != 0) {
            return false;
        }
        return CarConfigHelper.hasHifiSound();
    }

    public static boolean isUnityEffectPopupDialog() {
        char c;
        String product = getProduct();
        int hashCode = product.hashCode();
        if (hashCode != 2566) {
            if (hashCode == 2567 && product.equals("Q8")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (product.equals("Q7")) {
                c = 0;
            }
            c = 65535;
        }
        return c == 0 || c == 1;
    }

    public static boolean isXmartOS5() {
        String product = getProduct();
        return ((product.hashCode() == 2577 && product.equals(H93)) ? (char) 0 : (char) 65535) == 0;
    }

    public static boolean isSupportSayHi() {
        String product = getProduct();
        if (((product.hashCode() == 2577 && product.equals(H93)) ? (char) 0 : (char) 65535) != 0) {
            return false;
        }
        return CarConfigHelper.hasLightLanguage();
    }

    public static boolean isVehicleD55A() {
        return getProduct().equals(D55A);
    }
}
