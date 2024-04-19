package com.xiaopeng.car.settingslibrary.vm.app.entry;
/* loaded from: classes.dex */
public class StorageSize {
    long freeSize;
    long totalSize;
    long usedSize;

    public StorageSize(long j, long j2) {
        this.totalSize = 0L;
        this.freeSize = 0L;
        this.usedSize = 0L;
        this.totalSize = j;
        this.freeSize = j2;
        this.usedSize = j - j2;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long j) {
        this.totalSize = j;
    }

    public long getFreeSize() {
        return this.freeSize;
    }

    public void setFreeSize(long j) {
        this.freeSize = j;
    }

    public long getUsedSize() {
        return this.usedSize;
    }

    public void setUsedSize(long j) {
        this.usedSize = j;
    }

    public String toString() {
        return "StorageSize{totalSize=" + this.totalSize + ", freeSize=" + this.freeSize + ", usedSize=" + this.usedSize + '}';
    }
}
