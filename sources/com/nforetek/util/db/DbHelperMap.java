package com.nforetek.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.lzy.okgo.model.Progress;
import com.nforetek.bt.res.MsgOutline;
import com.xiaopeng.car.settingslibrary.common.utils.BuriedPointUtils;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class DbHelperMap extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_map";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "MessageContent";
    private final String SQL_DELETE_All_MESSAGE;
    private final String SQL_DELETE_FOLDER;
    private final String SQL_DELETE_MESSAGE;
    private final String SQL_DELETE_MESSAGE_BY_FOLDER;
    private final String SQL_DELETE_ONE_MESSAGE;
    private final String SQL_MESSAGE;
    private final String SQL_SELECT_MESSAGE;
    private final String SQL_SELECT_MESSGE_BY_FOLDER_AND_HANDLE;
    private final String SQL_SELECT_ONE_MESSAGE;
    private String TAG;
    private String _id;
    private String datetime;
    private String folder;
    private String handle;
    private Object helper;
    private Context m_context;
    private String macAddress;
    private String message;
    private String read;
    private String recipient_addressing;
    private String sender_addressing;
    private String sender_name;
    private String size;
    private String subject;

    public void onUpdate(SQLiteDatabase sQLiteDatabase, String str) {
    }

    public DbHelperMap(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.TAG = "DbMapHelper";
        this._id = "_id";
        this.handle = "handle";
        this.subject = "subject";
        this.message = "message";
        this.datetime = "datetime";
        this.sender_name = "sender_name";
        this.sender_addressing = "sender_addressing";
        this.recipient_addressing = "recipient_addressing";
        this.size = BuriedPointUtils.SIZE_KEY;
        this.read = "read";
        this.macAddress = "macAddress";
        this.folder = Progress.FOLDER;
        this.SQL_MESSAGE = "select * from MessageContent where condition = ?";
        this.SQL_SELECT_MESSAGE = "select * from MessageContent where macAddress = ?";
        this.SQL_SELECT_ONE_MESSAGE = "select * from MessageContent where macAddress = ? and handle = ? and folder = ?";
        this.SQL_SELECT_MESSGE_BY_FOLDER_AND_HANDLE = "select * from MessageContent where folder = ? and handle = ? and macAddress = ?";
        this.SQL_DELETE_MESSAGE = "delete from MessageContent where macAddress = ?";
        this.SQL_DELETE_FOLDER = "delete from MessageContent where macAddress = ? and folder = ?";
        this.SQL_DELETE_ONE_MESSAGE = "delete from MessageContent where macAddress = ? and handle = ? and datetime=?";
        this.SQL_DELETE_All_MESSAGE = "delete from MessageContent";
        this.SQL_DELETE_MESSAGE_BY_FOLDER = "delete from MessageContent where folder = ? and handle = ? and macAddress = ?";
        Log.d(this.TAG, "DbHelperMap constucter");
        this.m_context = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.d(this.TAG, "onCreate() Piggy check");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS MessageContent (" + this._id + " Integer primary key autoincrement, " + this.folder + " varchar(6), " + this.handle + " varchar(256), " + this.subject + " varchar(256), " + this.message + " varchar(256), " + this.datetime + " varchar(256), " + this.sender_name + " varchar(256), " + this.sender_addressing + " varchar(256), " + this.recipient_addressing + " varchar(256), " + this.read + " varchar(3), " + this.macAddress + " varchar(17)) ");
    }

    public void insertMessageInfo(SQLiteDatabase sQLiteDatabase, String str, int i, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i2) {
        Log.e(this.TAG, "insertMessageInfo");
        ContentValues contentValues = new ContentValues();
        String str9 = this.TAG;
        Log.e(str9, "insertMessageInfo " + str);
        contentValues.put("Folder", Integer.valueOf(i));
        contentValues.put("Handle", str);
        contentValues.put("Subject", str2);
        contentValues.put("Message", str3);
        contentValues.put("Datetime", str4);
        contentValues.put("Sender_Name", str5);
        contentValues.put("Sender_Addressing", str6);
        contentValues.put("Recipient_Addressing", str7);
        contentValues.put("Read", i2 == 1 ? "yes" : "no");
        contentValues.put("macAddress", str8);
        sQLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void insertMessageInfo(SQLiteDatabase sQLiteDatabase, MsgOutline msgOutline) {
        Log.e(this.TAG, "insertMessageInfo");
        ContentValues contentValues = new ContentValues();
        if (msgOutline != null) {
            String str = this.TAG;
            Log.e(str, "insertMessageInfo " + msgOutline.getHandle());
            contentValues.put("Folder", msgOutline.getFolder());
            contentValues.put("Handle", msgOutline.getHandle());
            contentValues.put("Subject", msgOutline.getSubject());
            contentValues.put("Message", msgOutline.getMessage());
            contentValues.put("Datetime", msgOutline.getDatetime());
            contentValues.put("Sender_Name", msgOutline.getSender_name());
            contentValues.put("Sender_Addressing", msgOutline.getSender_addressing());
            contentValues.put("Recipient_Addressing", msgOutline.getRecipient_addressing());
            contentValues.put("Read", msgOutline.getRead());
            contentValues.put("macAddress", msgOutline.getMacAddress());
            sQLiteDatabase.insert(TABLE_NAME, null, contentValues);
        }
    }

    public ArrayList<String> queryMessageInfo(String str, SQLiteDatabase sQLiteDatabase) {
        Cursor query = sQLiteDatabase.query(TABLE_NAME, new String[]{str}, null, null, null, null, null);
        int count = query.getCount();
        ArrayList<String> arrayList = new ArrayList<>();
        Log.e(this.TAG, "Piggy Check rows counts : " + count);
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    query.moveToFirst();
                } else {
                    query.moveToNext();
                }
                arrayList.add(query.getString(0));
            }
            query.close();
        }
        return arrayList;
    }

    public Cursor queryMessage(SQLiteDatabase sQLiteDatabase, String str) {
        return sQLiteDatabase.rawQuery("select * from MessageContent where condition = ?", new String[]{str});
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS config");
        onCreate(sQLiteDatabase);
    }

    public void deleteAllTableContent(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.delete(TABLE_NAME, null, null);
    }

    public MsgOutline queryMessageByfolderAndHandle(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("select * from MessageContent where folder = ? and handle = ? and macAddress = ?", new String[]{str, str2, str3});
        MsgOutline msgOutline = new MsgOutline();
        rawQuery.moveToFirst();
        msgOutline.setFolder(rawQuery.getString(1));
        msgOutline.setHandle(rawQuery.getString(2));
        msgOutline.setSubject(rawQuery.getString(3));
        msgOutline.setMessage(rawQuery.getString(4));
        msgOutline.setDatetime(rawQuery.getString(5));
        msgOutline.setSender_name(rawQuery.getString(6));
        msgOutline.setSender_addressing(rawQuery.getString(7));
        msgOutline.setRecipient_addressing(rawQuery.getString(8));
        msgOutline.setSize(rawQuery.getString(9));
        msgOutline.setRead(rawQuery.getString(10));
        rawQuery.close();
        return msgOutline;
    }

    public Cursor queryMessageByMacAddress(SQLiteDatabase sQLiteDatabase, String str) {
        return sQLiteDatabase.rawQuery("select * from MessageContent where macAddress = ?", new String[]{str});
    }

    public Cursor queryOneMessageByMacAddress(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        if (!str3.equals("inbox") && !str3.equals("sent")) {
            Log.e(this.TAG, "folder parameter error !");
            return null;
        }
        return sQLiteDatabase.rawQuery("select * from MessageContent where macAddress = ? and handle = ? and folder = ?", new String[]{str, str2, str3});
    }

    public boolean isMessageInDatabase(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        String str4 = this.TAG;
        Log.e(str4, "Piggy Check isMessageInDatabase address : " + str + " handle : " + str2 + " folder : " + str3);
        if (!str3.equals("inbox") && !str3.equals("sent")) {
            Log.e(this.TAG, "folder parameter error !");
            return false;
        }
        Cursor rawQuery = sQLiteDatabase.rawQuery("select * from MessageContent where macAddress = ? and handle = ? and folder = ?", new String[]{str, str2, str3});
        String str5 = this.TAG;
        Log.e(str5, "Piggy Check isMessageInDatabase cursor count : " + rawQuery.getCount());
        rawQuery.moveToFirst();
        String string = rawQuery.getString(rawQuery.getColumnIndex("_id"));
        String string2 = rawQuery.getString(rawQuery.getColumnIndex("handle"));
        String string3 = rawQuery.getString(rawQuery.getColumnIndex("datetime"));
        String str6 = this.TAG;
        Log.e(str6, "Piggy Check ID : " + string + " handle : " + string2 + " DateTime : " + string3);
        if (rawQuery.getCount() == 1) {
            return true;
        }
        if (rawQuery.getCount() > 1) {
            Log.e(this.TAG, "Piggy Check have same handle same folder in database. don't know why .");
            return true;
        }
        return false;
    }

    public void deleteAllMessage(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL("delete from MessageContent", new String[]{str});
    }

    public void deleteMessageByMacAddress(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL("delete from MessageContent where macAddress = ?", new String[]{str});
    }

    public void deleteMessageByMacAddressAndFolder(SQLiteDatabase sQLiteDatabase, String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sQLiteDatabase.execSQL("delete from MessageContent where macAddress = ? and folder = ?", new String[]{str, sb.toString()});
    }

    public void deleteOneMessageByMacAddress(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL("delete from MessageContent where macAddress = ? and handle = ? and datetime=?", new String[]{str});
    }

    public void deleteMessageByFolderAndHandle(SQLiteDatabase sQLiteDatabase, int i, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sQLiteDatabase.execSQL("delete from MessageContent where folder = ? and handle = ? and macAddress = ?", new String[]{sb.toString(), str, str2});
    }
}
