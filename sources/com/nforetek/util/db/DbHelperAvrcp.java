package com.nforetek.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/* loaded from: classes.dex */
public class DbHelperAvrcp extends SQLiteOpenHelper {
    private static boolean D = true;
    private static final String DATABASE_NAME = "db_avrcp14";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "nFore_DbHelperAvrcp";
    public final String TABLE_FOLDER_ITEMS;
    public final String TABLE_MEDIA_ITEMS;
    public final String TABLE_MEDIA_PLAYER_ITEMS;
    public String clearFolderItems;
    public String clearMediaItems;
    public String clearMediaItemsByScopeId;
    public String clearMediaPlayerItems;
    public String insertFolderItems;
    public String insertMediaItems;
    public String insertMediaPlayerItems;
    public String selectFolderItems;
    public String selectMediaItems;
    public String selectMediaPlayerItems;
    public String updateMediaItems;

    public DbHelperAvrcp(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.TABLE_MEDIA_PLAYER_ITEMS = "MediaPlayerItems";
        this.TABLE_FOLDER_ITEMS = "FolderItems";
        this.TABLE_MEDIA_ITEMS = "MediaItems";
        this.clearMediaPlayerItems = "delete from MediaPlayerItems;";
        this.clearFolderItems = "delete from FolderItems;";
        this.clearMediaItems = "delete from MediaItems;";
        this.clearMediaItemsByScopeId = "delete from MediaItems where ScopeId = ";
        this.insertMediaPlayerItems = "insert into MediaPlayerItems value(?, ?, ?, ?, ?, ?, ?);";
        this.insertFolderItems = "insert into FolderItems value(?, ?, ?, ?, ?);";
        this.insertMediaItems = "insert into MediaItems(UIDcounter, UID, MediaType, Name) value(?, ?, ?, ?);";
        this.updateMediaItems = "update MediaItems set Title = ? where uid = ?;";
        this.selectMediaPlayerItems = "select * from MediaPlayerItems order by MajorPlayerType, Name;";
        this.selectFolderItems = "select * from FolderItems order by FolderType, Name;";
        this.selectMediaItems = "select * from MediaItems where ScopeId = ? order by MediaType, Name;";
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        if (D) {
            Log.d(TAG, "+++ Begin of onCreate() +++");
        }
        sQLiteDatabase.execSQL("create table if not exists MediaPlayerItems( _ID INTEGER primary key autoincrement, UIDcounter smallint, PlayerId smallint, MajorPlayerType blob, PlayerSubType int, PlayStatus blob, FeatureBitMask blob, Name nvarchar(20));");
        sQLiteDatabase.execSQL("create table if not exists FolderItems(_ID INTEGER primary key autoincrement, UIDcounter smallint, UID bigint, FolderType blob, IsPlayable blob, Name nvarchar(20));");
        sQLiteDatabase.execSQL("create table if not exists MediaItems(_ID INTEGER primary key autoincrement, ScopeId smallint, UIDcounter smallint, UID bigint, MediaType blob, Name nvarchar(20), Title nvarchar(20), Artist nvarchar(20), Album nvarchar(20), TrackNumber nvarchar(5), TotalTracks nvarchar(6), Genre nvarchar(10), Time_ms nvarchar(10));");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (D) {
            Log.d(TAG, "+++ Begin of onUpgrade() +++");
        }
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS MediaPlayerItems");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS FolderItems");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS MediaItems");
    }
}
