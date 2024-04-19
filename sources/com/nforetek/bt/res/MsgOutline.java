package com.nforetek.bt.res;

import java.io.Serializable;
/* loaded from: classes.dex */
public class MsgOutline implements Serializable {
    private static final long serialVersionUID = 1;
    private int _id;
    private String attachment_size;
    private String datetime;
    private String folder;
    private String handle;
    private String macAddress;
    private String message;
    private String priority;
    private String protect;
    private String read;
    private String reception_status;
    private String recipient_addressing;
    private String recipient_name;
    private String replyto_addressing;
    private String sender_addressing;
    private String sender_name;
    private String sent;
    private String size;
    private String subject;
    private String text;
    private String type;

    public int get_id() {
        return this._id;
    }

    public void set_id(int i) {
        this._id = i;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String str) {
        this.macAddress = str;
    }

    public String getHandle() {
        return this.handle;
    }

    public void setHandle(String str) {
        this.handle = str;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String str) {
        this.subject = str;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String str) {
        this.datetime = str;
    }

    public String getSender_name() {
        return this.sender_name;
    }

    public void setSender_name(String str) {
        this.sender_name = str;
    }

    public String getSender_addressing() {
        return this.sender_addressing;
    }

    public void setSender_addressing(String str) {
        this.sender_addressing = str;
    }

    public String getReplyto_addressing() {
        return this.replyto_addressing;
    }

    public void setReplyto_addressing(String str) {
        this.replyto_addressing = str;
    }

    public String getRecipient_name() {
        return this.recipient_name;
    }

    public void setRecipient_name(String str) {
        this.recipient_name = str;
    }

    public String getRecipient_addressing() {
        return this.recipient_addressing;
    }

    public void setRecipient_addressing(String str) {
        this.recipient_addressing = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getReception_status() {
        return this.reception_status;
    }

    public void setReception_status(String str) {
        this.reception_status = str;
    }

    public String getAttachment_size() {
        return this.attachment_size;
    }

    public void setAttachment_size(String str) {
        this.attachment_size = str;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String str) {
        this.priority = str;
    }

    public String getRead() {
        return this.read;
    }

    public void setRead(String str) {
        this.read = str;
    }

    public String getSent() {
        return this.sent;
    }

    public void setSent(String str) {
        this.sent = str;
    }

    public String getProtect() {
        return this.protect;
    }

    public void setProtect(String str) {
        this.protect = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String str) {
        this.folder = str;
    }
}
