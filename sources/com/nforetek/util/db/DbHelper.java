package com.nforetek.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nforetek.bt.res.PhoneInfo;
import com.nforetek.bt.res.VCardList;
import com.nforetek.bt.res.VCardPack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/* loaded from: classes.dex */
public class DbHelper extends SQLiteOpenHelper {
    private static final String CALLHISTORY_CONTENT = "CallHistory";
    private static final String DATABASE_NAME = "db_pbap";
    private static final int DATABASE_VERSION = 1;
    private static final String PHONEBOOK_CONTENT = "PhoneBookContent";
    private static final String PHONENUMBER_DETAIL = "PhoneNumberDetail";
    private static final String PHONE_TYPE = "PhoneType";
    private final boolean D;
    private final String SQL_DELETE_CONTACTER;
    private final String SQL_DELETE_PHONENUMBER;
    private final String SQL_DELETE_PHONENUMBER_BY_FULLNAME;
    private final String SQL_EXPRESS_TOTAL;
    private final String SQL_QUERY_CALLHISTORY_BY_ADDRESS_STORAGETYPE;
    private final String SQL_QUERY_CALLHISTORY_BY_SPECIFIED_COLUMNS;
    private final String SQL_QUERY_CONTACTER;
    private final String SQL_QUERY_CONTACTERS;
    private final String SQL_QUERY_PHONEBOOKCONTENT;
    private final String SQL_QUERY_PHONEBOOKCONTENT_BY_PHONENUM;
    private final String SQL_QUERY_PHONEDATA_BY_PAGE;
    private final String SQL_QUERY_PHONENUMBERDETAIL;
    private final String SQL_QUERY_PHONETYPE_NAME;
    private final String SQL_QUERY_PHONE_BY_CONTENT_ID;
    private String TAG;
    private Context m_context;
    public static final String[] PHONEBOOK_CONTENT_FIELD = {"_id", "FullName", "FirstName", "LastName", "First_StreetAddress", "First_CityNameAddress", "First_FederalStateAddress", "First_ZipCodeAddress", "First_CountryAddress", "Second_StreetAddress", "Second_CityNameAddress", "Second_FederalStateAddress", "Second_ZipCodeAddress", "Second_CountryAddress", "CellPhone_Address", "StorageType"};
    public static final String[] PHONENUMBER_DETAIL_FIELD = {"_id", "Content_ID", "Type", "Number"};
    public static final String[] PHONE_TYPE_FIELD = {"Type", "TypeName"};

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.D = true;
        this.TAG = "nfore DBHelper";
        this.SQL_QUERY_PHONEDATA_BY_PAGE = "select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName limit 10 offset ?) and StorageType=? and CellPhone_Address = ? order by FullName";
        this.SQL_QUERY_CONTACTERS = "select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName) and StorageType=? and CellPhone_Address = ? order by ";
        this.SQL_QUERY_CALLHISTORY_BY_ADDRESS_STORAGETYPE = "select a._id, a.FullName, a.StorageType, a.PhoneNumber, a.PhoneType, a.HistoryDate, a.HistoryTime from CallHistory a where a.CellPhone_Address = ? and a.StorageType=? order by ";
        this.SQL_QUERY_CALLHISTORY_BY_SPECIFIED_COLUMNS = "select * from CallHistory a where a.StorageType = ? and a.CellPhone_Address = ?";
        this.SQL_QUERY_PHONE_BY_CONTENT_ID = "select a.*, b.TypeName as TypeName from PhoneNumberDetail a inner join PhoneType b on a.Type = b.Type where a.Content_ID = ?";
        this.SQL_QUERY_PHONEBOOKCONTENT = "select * from PhoneBookContent where FullName like ? and StorageType = ? and CellPhone_Address = ?";
        this.SQL_QUERY_PHONEBOOKCONTENT_BY_PHONENUM = "select * from PhoneBookContent where _id in (select Content_ID from PhoneNumberDetail where Number like ?) and StorageType = ? and CellPhone_Address = ?";
        this.SQL_QUERY_PHONENUMBERDETAIL = "select a.*, b.TypeName from PhoneNumberDetail.a inner join PhoneType b on a.Type = b.Type where Number = ?";
        this.SQL_DELETE_CONTACTER = "delete from PhoneBookContent where FullName = ?";
        this.SQL_DELETE_PHONENUMBER = "delete from PhoneNumberDetail where Number = ?";
        this.SQL_DELETE_PHONENUMBER_BY_FULLNAME = "delete from PhoneNumberDetail where Content_ID in (select _id from PhoneBookContent where FullName = ?)";
        this.SQL_QUERY_PHONETYPE_NAME = "select TypeName from PhoneType where Type = ? ";
        this.SQL_EXPRESS_TOTAL = "select a.FullName from PhoneBookContent a where a.CellPhone_Address=? and StorageType=? group by a.FullName";
        this.SQL_QUERY_CONTACTER = "select FullName from PhoneBookContent where _id = (select Content_ID from PhoneNumberDetail where Number like ? limit 1)";
        this.m_context = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS PhoneBookContent (" + PHONEBOOK_CONTENT_FIELD[0] + " INTEGER primary key autoincrement, " + PHONEBOOK_CONTENT_FIELD[1] + " varchar(16), " + PHONEBOOK_CONTENT_FIELD[2] + " varchar(8), " + PHONEBOOK_CONTENT_FIELD[3] + " varchar(8), " + PHONEBOOK_CONTENT_FIELD[4] + " varchar(20), " + PHONEBOOK_CONTENT_FIELD[5] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[6] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[7] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[8] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[9] + " varchar(20), " + PHONEBOOK_CONTENT_FIELD[10] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[11] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[12] + " varchar(12), " + PHONEBOOK_CONTENT_FIELD[13] + " varchar(30), " + PHONEBOOK_CONTENT_FIELD[14] + " varchar(30) " + PHONEBOOK_CONTENT_FIELD[15] + " varchar(10) );");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS PhoneNumberDetail (");
        sb.append(PHONENUMBER_DETAIL_FIELD[0]);
        sb.append(" INTEGER primary key autoincrement, ");
        sb.append(PHONENUMBER_DETAIL_FIELD[1]);
        sb.append(" INTEGER, ");
        sb.append(PHONENUMBER_DETAIL_FIELD[2]);
        sb.append(" nvarchar(5), ");
        sb.append(PHONENUMBER_DETAIL_FIELD[3]);
        sb.append(" nvarchar(20),");
        sb.append("FOREIGN KEY(");
        sb.append(PHONENUMBER_DETAIL_FIELD[1]);
        sb.append(") REFERENCES ");
        sb.append(PHONEBOOK_CONTENT);
        sb.append("(");
        sb.append(PHONEBOOK_CONTENT_FIELD[0]);
        sb.append(") ");
        sb.append(");");
        sQLiteDatabase.execSQL(sb.toString());
        StringBuilder sb2 = new StringBuilder("CREATE TABLE IF NOT EXISTS PhoneType (");
        sb2.append(PHONE_TYPE_FIELD[0]);
        sb2.append(" nvarchar(5) , ");
        sb2.append(PHONE_TYPE_FIELD[1]);
        sb2.append(" nvarchar(26) );");
        sQLiteDatabase.execSQL(sb2.toString());
    }

    public VCardList queryContacterInfo(SQLiteDatabase sQLiteDatabase, String str, String str2, int i, String str3) {
        boolean z = true;
        boolean z2 = str != null && str.trim().length() > 0;
        boolean z3 = str2 != null && str2.trim().length() > 0;
        if (!z2 || !z3) {
            z = false;
        }
        Cursor cursor = null;
        if (z2) {
            cursor = queryContacterByFullName(sQLiteDatabase, str, i, str3);
        } else if (z3) {
            cursor = queryContacterByPhoneNum(sQLiteDatabase, str2, i, str3);
        }
        ArrayList arrayList = new ArrayList();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Cursor queryPhoneNumberByContentId = queryPhoneNumberByContentId(sQLiteDatabase, cursor.getString(cursor.getColumnIndex("_id")));
                int columnIndex = queryPhoneNumberByContentId.getColumnIndex("Number");
                int columnIndex2 = queryPhoneNumberByContentId.getColumnIndex("TypeName");
                HashSet hashSet = new HashSet();
                while (queryPhoneNumberByContentId.moveToNext() && (!z || queryPhoneNumberByContentId.getString(columnIndex).trim().indexOf(str2) != -1)) {
                    PhoneInfo phoneInfo = new PhoneInfo();
                    phoneInfo.setPhoneNumber(queryPhoneNumberByContentId.getString(columnIndex));
                    phoneInfo.setPhoneTypeName(queryPhoneNumberByContentId.getString(columnIndex2));
                    hashSet.add(phoneInfo);
                }
                if (hashSet.size() > 0) {
                    VCardPack vCardPack = new VCardPack(cursor);
                    vCardPack.setPhoneNumbers(hashSet);
                    arrayList.add(vCardPack);
                }
            }
        }
        VCardList vCardList = new VCardList();
        vCardList.setVcardPacks(arrayList);
        return vCardList;
    }

    public Cursor queryPhoneTypeName(SQLiteDatabase sQLiteDatabase, String str) {
        if (str == null) {
            str = "C";
        }
        return sQLiteDatabase.rawQuery("select TypeName from PhoneType where Type = ? ", new String[]{str});
    }

    private Cursor queryContacterByFullName(SQLiteDatabase sQLiteDatabase, String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        return sQLiteDatabase.rawQuery("select * from PhoneBookContent where FullName like ? and StorageType = ? and CellPhone_Address = ?", new String[]{String.valueOf(str) + "%", sb.toString(), str2});
    }

    private Cursor queryContacterByPhoneNum(SQLiteDatabase sQLiteDatabase, String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        return sQLiteDatabase.rawQuery("select * from PhoneBookContent where _id in (select Content_ID from PhoneNumberDetail where Number like ?) and StorageType = ? and CellPhone_Address = ?", new String[]{"%" + str + "%", sb.toString(), str2});
    }

    public Cursor queryNumberDetailByPhoneNumber(SQLiteDatabase sQLiteDatabase, String str) {
        return sQLiteDatabase.rawQuery("select a.*, b.TypeName from PhoneNumberDetail.a inner join PhoneType b on a.Type = b.Type where Number = ?", new String[]{str});
    }

    public Cursor queryPhoneNumberByContentId(SQLiteDatabase sQLiteDatabase, String str) {
        return sQLiteDatabase.rawQuery("select a.*, b.TypeName as TypeName from PhoneNumberDetail a inner join PhoneType b on a.Type = b.Type where a.Content_ID = ?", new String[]{str});
    }

    public void deleteContacterByFullName(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL("delete from PhoneBookContent where FullName = ?", new String[]{str});
    }

    public void deletePhoneNumber(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL("delete from PhoneNumberDetail where Number = ?", new String[]{str});
    }

    public void deletePhoneNumberByFullName(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.execSQL("delete from PhoneNumberDetail where Content_ID in (select _id from PhoneBookContent where FullName = ?)", new String[]{str});
    }

    public int deleteVcardInfo(SQLiteDatabase sQLiteDatabase, String str, int i) {
        int i2;
        Cursor query = sQLiteDatabase.query(PHONEBOOK_CONTENT, new String[]{"_id"}, "CellPhone_Address=? and StorageType=?", new String[]{str, String.valueOf(i)}, null, null, null);
        if (query.getCount() <= 0 || !query.moveToNext()) {
            i2 = 0;
        } else {
            i2 = 0;
            do {
                i2 += sQLiteDatabase.delete(PHONENUMBER_DETAIL, "Content_ID=?", new String[]{query.getString(0)});
            } while (query.moveToNext());
        }
        int delete = i2 >= 0 ? 0 + sQLiteDatabase.delete(PHONEBOOK_CONTENT, "CellPhone_Address=? and StorageType=?", new String[]{str, String.valueOf(i)}) : 0;
        query.close();
        return delete;
    }

    public int deleteCallHistoryInfo(SQLiteDatabase sQLiteDatabase, String str, int i) {
        return sQLiteDatabase.delete(CALLHISTORY_CONTENT, "CellPhone_Address=? and StorageType=?", new String[]{str, String.valueOf(i)});
    }

    public void insertVcardInfo(SQLiteDatabase sQLiteDatabase, VCardPack vCardPack, int i) {
        ContentValues contentValues = new ContentValues();
        if (vCardPack != null) {
            contentValues.put("FullName", vCardPack.getFullName());
            contentValues.put("FirstName", vCardPack.getFirstName());
            contentValues.put("LastName", vCardPack.getLastName());
            contentValues.put("First_StreetAddress", vCardPack.getFirst_StreetAddress());
            contentValues.put("First_CityNameAddress", vCardPack.getFirst_CityNameAddress());
            contentValues.put("First_FederalStateAddress", vCardPack.getFirst_FederalStateAddress());
            contentValues.put("First_ZipCodeAddress", vCardPack.getFirst_ZipCodeAddress());
            contentValues.put("First_CountryAddress", vCardPack.getFirst_CountryAddress());
            contentValues.put("Second_StreetAddress", vCardPack.getSecond_StreetAddress());
            contentValues.put("Second_CityNameAddress", vCardPack.getSecond_CityNameAddress());
            contentValues.put("Second_FederalStateAddress", vCardPack.getSecond_FederalStateAddress());
            contentValues.put("Second_ZipCodeAddress", vCardPack.getSecond_ZipCodeAddress());
            contentValues.put("Second_CountryAddress", vCardPack.getSecond_CountryAddress());
            contentValues.put("CellPhone_Address", vCardPack.getCellPhone_Address());
            contentValues.put("StorageType", Integer.valueOf(i));
            long insert = sQLiteDatabase.insert(PHONEBOOK_CONTENT, null, contentValues);
            for (PhoneInfo phoneInfo : vCardPack.getPhoneNumbers()) {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("Content_ID", Long.valueOf(insert));
                contentValues2.put("Type", phoneInfo.getPhoneType());
                contentValues2.put("Number", phoneInfo.getPhoneNumber());
                sQLiteDatabase.insert(PHONENUMBER_DETAIL, null, contentValues2);
            }
        }
    }

    public void insertCallHistoryInfo(SQLiteDatabase sQLiteDatabase, VCardPack vCardPack, int i) {
        ContentValues contentValues = new ContentValues();
        if (vCardPack != null) {
            contentValues.put("FullName", vCardPack.getFullName());
            contentValues.put("FirstName", vCardPack.getFirstName());
            contentValues.put("LastName", vCardPack.getLastName());
            contentValues.put("CellPhone_Address", vCardPack.getCellPhone_Address());
            contentValues.put("StorageType", Integer.valueOf(i));
            for (PhoneInfo phoneInfo : vCardPack.getPhoneNumbers()) {
                contentValues.put(PHONE_TYPE, phoneInfo.getPhoneType());
                contentValues.put("PhoneNumber", phoneInfo.getPhoneNumber());
            }
            contentValues.put("HistoryDate", vCardPack.getHistoryDate());
            contentValues.put("HistoryTime", vCardPack.getHistoryTime());
            sQLiteDatabase.insert(CALLHISTORY_CONTENT, null, contentValues);
        }
    }

    public List<VCardPack> queryPhoneDataByPage(SQLiteDatabase sQLiteDatabase, String str, int i, int i2, String str2) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName limit 10 offset ?) and StorageType=? and CellPhone_Address = ? order by FullName", new String[]{str, str2, String.valueOf(i * i2), str2, str});
        List<VCardPack> collectionData = collectionData(rawQuery);
        rawQuery.close();
        for (VCardPack vCardPack : collectionData) {
            new HashSet();
            for (int i3 = 0; i3 < vCardPack.getPhoneNumbers().size(); i3++) {
                for (PhoneInfo phoneInfo : vCardPack.getPhoneNumbers()) {
                    Cursor queryPhoneTypeName = queryPhoneTypeName(sQLiteDatabase, phoneInfo.getPhoneType());
                    if (queryPhoneTypeName.moveToNext()) {
                        phoneInfo.setPhoneTypeName(queryPhoneTypeName.getString(0));
                    }
                    queryPhoneTypeName.close();
                }
            }
        }
        return collectionData;
    }

    public List<VCardPack> queryContactersInfo(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("select a._id, a.FullName, a.StorageType, b.Number, b.Type from PhoneBookContent a inner join PhoneNumberDetail b on a._id = b.Content_ID where a.FullName in (select FullName from PhoneBookContent where CellPhone_Address = ? and StorageType=? group by FullName) and StorageType=? and CellPhone_Address = ? order by " + str3, new String[]{str, str2, str2, str});
        List<VCardPack> collectionData = collectionData(rawQuery);
        rawQuery.close();
        for (VCardPack vCardPack : collectionData) {
            new HashSet();
            for (int i = 0; i < vCardPack.getPhoneNumbers().size(); i++) {
                for (PhoneInfo phoneInfo : vCardPack.getPhoneNumbers()) {
                    Cursor queryPhoneTypeName = queryPhoneTypeName(sQLiteDatabase, phoneInfo.getPhoneType());
                    if (queryPhoneTypeName.moveToNext()) {
                        phoneInfo.setPhoneTypeName(queryPhoneTypeName.getString(0));
                    }
                    queryPhoneTypeName.close();
                }
            }
        }
        return collectionData;
    }

    public VCardList callHistoryBySpecifiedColumns(SQLiteDatabase sQLiteDatabase, int i, String str, String str2, String str3, String str4) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.valueOf(i));
        arrayList.add(str);
        String str5 = "select * from CallHistory a where a.StorageType = ? and a.CellPhone_Address = ?";
        if (str2.trim().length() > 0) {
            str5 = "select * from CallHistory a where a.StorageType = ? and a.CellPhone_Address = ? and a.HistoryDate like ?";
            arrayList.add("%" + str2 + "%");
        }
        if (str3.trim().length() > 0) {
            str5 = String.valueOf(str5) + " and a.HistoryTime like ?";
            arrayList.add("%" + str3 + "%");
        }
        if (str4.trim().length() > 0) {
            str5 = String.valueOf(str5) + " and a.PhoneNumber like ?";
            arrayList.add("%" + str4 + "%");
        }
        Cursor rawQuery = sQLiteDatabase.rawQuery(str5, (String[]) arrayList.toArray(new String[arrayList.size()]));
        List<VCardPack> collectionData = collectionData(rawQuery);
        rawQuery.close();
        VCardList vCardList = new VCardList();
        vCardList.setVcardPacks(collectionData);
        return vCardList;
    }

    public List<VCardPack> queryCallHistoryByAddressAndStorageType(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("select a._id, a.FullName, a.StorageType, a.PhoneNumber, a.PhoneType, a.HistoryDate, a.HistoryTime from CallHistory a where a.CellPhone_Address = ? and a.StorageType=? order by " + str3, new String[]{str, str2});
        List<VCardPack> collectionData = collectionData(rawQuery);
        rawQuery.close();
        for (VCardPack vCardPack : collectionData) {
            new HashSet();
            for (int i = 0; i < vCardPack.getPhoneNumbers().size(); i++) {
                for (PhoneInfo phoneInfo : vCardPack.getPhoneNumbers()) {
                    Cursor queryPhoneTypeName = queryPhoneTypeName(sQLiteDatabase, phoneInfo.getPhoneType());
                    if (queryPhoneTypeName.moveToNext()) {
                        phoneInfo.setPhoneTypeName(queryPhoneTypeName.getString(0));
                    }
                    queryPhoneTypeName.close();
                }
            }
        }
        return collectionData;
    }

    private List<VCardPack> collectionData(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                VCardPack vCardPack = new VCardPack();
                vCardPack.setFullName(cursor.getString(cursor.getColumnIndex("FullName")));
                vCardPack.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                PhoneInfo phoneInfo = new PhoneInfo();
                HashSet hashSet = new HashSet();
                String string = cursor.getString(cursor.getColumnIndex("StorageType"));
                int parseInt = Integer.parseInt(string);
                if (parseInt < 3 && parseInt > 0) {
                    phoneInfo.setPhoneNumber(cursor.getString(cursor.getColumnIndex("Number")));
                    phoneInfo.setPhoneType(cursor.getString(cursor.getColumnIndex("Type")));
                } else if (parseInt >= 3 && parseInt <= 5) {
                    phoneInfo.setPhoneNumber(cursor.getString(cursor.getColumnIndex("PhoneNumber")));
                    phoneInfo.setPhoneType(cursor.getString(cursor.getColumnIndex(PHONE_TYPE)));
                    vCardPack.setHistoryDate(cursor.getString(cursor.getColumnIndex("HistoryDate")));
                    vCardPack.setHistoryTime(cursor.getString(cursor.getColumnIndex("HistoryTime")));
                }
                hashSet.add(phoneInfo);
                vCardPack.setPhoneNumbers(hashSet);
                vCardPack.setStorageType(string);
                arrayList.add(vCardPack);
            } while (cursor.moveToNext());
            return arrayList;
        }
        return arrayList;
    }

    public void deleteContacterById(SQLiteDatabase sQLiteDatabase, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sQLiteDatabase.delete(PHONENUMBER_DETAIL, "Content_ID=?", new String[]{sb.toString()});
        StringBuilder sb2 = new StringBuilder();
        sb2.append(i);
        sQLiteDatabase.delete(PHONEBOOK_CONTENT, "_id=?", new String[]{sb2.toString()});
    }

    public void deleteCallHistoryById(SQLiteDatabase sQLiteDatabase, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sQLiteDatabase.delete(CALLHISTORY_CONTENT, "_id=?", new String[]{sb.toString()});
    }

    public int queryTotalAmount(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("select a.FullName from PhoneBookContent a where a.CellPhone_Address=? and StorageType=? group by a.FullName", new String[]{str, str2});
        int count = rawQuery.getCount();
        rawQuery.close();
        return count;
    }

    public String queryNameByPhoneNum(String str) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("select FullName from PhoneBookContent where _id = (select Content_ID from PhoneNumberDetail where Number like ? limit 1)", new String[]{String.valueOf(str) + "%"});
        String string = (rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) ? "N/A" : rawQuery.getString(0);
        rawQuery.close();
        readableDatabase.close();
        return string;
    }

    public void deleteAllTableContent(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.delete(PHONENUMBER_DETAIL, null, null);
        sQLiteDatabase.delete(PHONEBOOK_CONTENT, null, null);
        sQLiteDatabase.delete(CALLHISTORY_CONTENT, null, null);
    }
}
