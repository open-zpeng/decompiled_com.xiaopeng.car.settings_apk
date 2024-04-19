package com.xiaopeng.car.settingslibrary.vm.about;

import android.os.storage.VolumeInfo;
import com.xiaopeng.car.settingslibrary.common.utils.Utils;
/* loaded from: classes.dex */
public class StorageSettingsInfo {
    private long appAndDataSize;
    private long audioSize;
    private long cacheSize;
    private long freeSize;
    private long imageAndVideoSize;
    private long otherSize;
    private long systemSize;
    private long totalSize;
    private long usedSize;
    private VolumeInfo volumeInfo;

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long j) {
        this.totalSize = j;
    }

    public long getUsedSize() {
        return this.usedSize;
    }

    public void setUsedSize(long j) {
        this.usedSize = j;
    }

    public VolumeInfo getVolumeInfo() {
        return this.volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public long getAppAndDataSize() {
        return this.appAndDataSize;
    }

    public void setAppAndDataSize(long j) {
        this.appAndDataSize = j;
    }

    public long getImageAndVideoSize() {
        return this.imageAndVideoSize;
    }

    public void setImageAndVideoSize(long j) {
        this.imageAndVideoSize = j;
    }

    public long getAudioSize() {
        return this.audioSize;
    }

    public void setAudioSize(long j) {
        this.audioSize = j;
    }

    public long getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(long j) {
        this.cacheSize = j;
    }

    public long getOtherSize() {
        return this.otherSize;
    }

    public void setOtherSize(long j) {
        this.otherSize = j;
    }

    public long getFreeSize() {
        return this.freeSize;
    }

    public void setFreeSize(long j) {
        this.freeSize = j;
    }

    public long getSystemSize() {
        return this.systemSize;
    }

    public void setSystemSize(long j) {
        this.systemSize = j;
    }

    public String toString() {
        return "StorageSettingsInfo{, totalSize=" + Utils.getFormatSize(this.totalSize) + ", usedSize=" + Utils.getFormatSize(this.usedSize) + ", freeSize=" + Utils.getFormatSize(this.freeSize) + ", appAndDataSize=" + Utils.getFormatSize(this.appAndDataSize) + ", imageAndVideoSize=" + Utils.getFormatSize(this.imageAndVideoSize) + ", audioSize=" + Utils.getFormatSize(this.audioSize) + ", cacheSize=" + Utils.getFormatSize(this.cacheSize) + ", otherSize=" + Utils.getFormatSize(this.otherSize) + ", systemSize=" + Utils.getFormatSize(this.systemSize) + '}';
    }
}
