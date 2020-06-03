package com.example.miis200;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "PATIENTNAME";
    public static final String COL3 = "PATIENTID";
    public static final String COL4 = "PATIENTPHONENUM";
    public static final String COL5 = "PATIENTGENDER";
    public static final String COL6 = "PATIENTBIRTHDAY";
    public static final String COL7 = "LEFTIMAGEPATH";
    public static final String COL8 = "RIGHTIMAGEPATH";
    public static final String COL9 = "STATUS";
    public static final String COL10 = "DOCTORDESC";
    public static final String COL11 = "EXTIME";

    public static final String COL12 = "EYEPATH1";
    public static final String COL13 = "EYEPATH2";
    public static final String COL14 = "EYEPATH3";
    public static final String COL15 = "EYEPATH4";
    public static final String COL16 = "EYEPATH5";
    public static final String COL17 = "EYEPATH6";
    public static final String COL18 = "EYEPATH7";
    public static final String COL19 = "EYEPATH8";

    public static final String COL20 = "PATIENTAGE";


    public static final String COL21 = "AIIMG1";
    public static final String COL22 = "AIIMG2";
    public static final String COL23 = "AIIMG3";
    public static final String COL24 = "AIIMG4";
    public static final String COL25 = "AIIMG5";
    public static final String COL26 = "AIIMG6";
    public static final String COL27 = "AIIMG7";
    public static final String COL28 = "AIIMG8";
    public static final String COL29 = "AIIMG9";
    public static final String COL30 = "AIIMG10";
    public static final String COL31 = "AIIMG11";
    public static final String COL32 = "AIIMG12";
    public static final String COL33 = "AIIMG13";
    public static final String COL34 = "AIIMG14";
    public static final String COL35 = "AIIMG15";
    public static final String COL36 = "AIIMG16";


    public static final String COL37 = "DOCTORDESC2";
    public static final String COL38 = "DOCTORDESC3";
    public static final String COL39 = "DOCTORDESC4";
    public static final String COL40 = "DOCTORDESC5";
    public static final String COL41 = "DOCTORDESC6";
    public static final String COL42 = "DOCTORDESC7";
    public static final String COL43 = "DOCTORDESC8";
    public static final String COL44 = "DOCTORDESC9";
    public static final String COL45 = "DOCTORDESC10";
    public static final String COL46 = "DOCTORDESC11";
    public static final String COL47 = "DOCTORDESC12";
    public static final String COL48 = "DOCTORDESC13";
    public static final String COL49 = "DOCTORDESC14";
    public static final String COL50 = "DOCTORDESC15";
    public static final String COL51 = "DOCTORDESC16";
    public static final String COL52 = "DOCTORDESC17";
    public static final String COL53 = "DOCTORDESC18";
    public static final String COL54 = "DOCTORDESC19";
    public static final String COL55 = "DOCTORDESC20";
    public static final String COL56 = "DOCTORDESC21";
    public static final String COL57 = "DOCTORDESC22";
    public static final String COL58 = "DOCTORDESC23";
    public static final String COL59 = "DOCTORDESC24";
    public static final String COL60 = "DOCTORDESC25";
    public static final String COL61 = "DOCTORDESC26";










    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " PATIENTNAME TEXT, PATIENTID TEXT, PATIENTPHONENUM TEXT,PATIENTGENDER TEXT,PATIENTBIRTHDAY TEXT,LEFTIMAGEPATH TEXT,RIGHTIMAGEPATH TEXT,STATUS TEXT,DOCTORDESC TEXT,EXTIME TEXT," +
                "EYEPATH1 TEXT,EYEPATH2 TEXT,EYEPATH3 TEXT,EYEPATH4 TEXT,EYEPATH5 TEXT,EYEPATH6 TEXT,EYEPATH7 TEXT,EYEPATH8 TEXT, PATIENTAGE TEXT" +
                ", AIIMG1 TEXT, AIIMG2 TEXT, AIIMG3 TEXT, AIIMG4 TEXT, AIIMG5 TEXT, AIIMG6 TEXT, AIIMG7 TEXT, AIIMG8 TEXT, AIIMG9 TEXT, AIIMG10 TEXT, AIIMG11 TEXT, AIIMG12 TEXT, AIIMG13 TEXT, AIIMG14 TEXT, AIIMG15 TEXT, AIIMG16 TEXT" +
                ",DOCTORDESC2 TEXT,DOCTORDESC3 TEXT,DOCTORDESC4 TEXT,DOCTORDESC5 TEXT,DOCTORDESC6 TEXT,DOCTORDESC7 TEXT,DOCTORDESC8 TEXT,DOCTORDESC9 TEXT,DOCTORDESC10 TEXT,DOCTORDESC11 TEXT,DOCTORDESC12 TEXT,DOCTORDESC13 TEXT" +
                ",DOCTORDESC14 TEXT,DOCTORDESC15 TEXT,DOCTORDESC16 TEXT,DOCTORDESC17 TEXT,DOCTORDESC18 TEXT,DOCTORDESC19 TEXT,DOCTORDESC20 TEXT,DOCTORDESC21 TEXT,DOCTORDESC22 TEXT,DOCTORDESC23 TEXT,DOCTORDESC24 TEXT,DOCTORDESC25 TEXT,DOCTORDESC26 TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String pName, String pId, String pPnum, String pGender, String pBir , String eyeLpath , String eyeRpath, String PStatus, String PDesc, String Extime,
                           String Eyepath1, String Eyepath2, String Eyepath3, String Eyepath4, String Eyepath5, String Eyepath6, String Eyepath7, String Eyepath8, String pAge,
                           String Aiimg1, String Aiimg2, String Aiimg3, String Aiimg4, String Aiimg5, String Aiimg6, String Aiimg7, String Aiimg8, String Aiimg9, String Aiimg10,
                           String Aiimg11, String Aiimg12, String Aiimg13, String Aiimg14, String Aiimg15, String Aiimg16, String PDesc2, String PDesc3, String PDesc4, String PDesc5,
                           String PDesc6, String PDesc7, String PDesc8, String PDesc9, String PDesc10, String PDesc11, String PDesc12, String PDesc13, String PDesc14, String PDesc15, String PDesc16,
                           String PDesc17, String PDesc18, String PDesc19, String PDesc20, String PDesc21, String PDesc22, String PDesc23, String PDesc24, String PDesc25, String PDesc26) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, pName);
        contentValues.put(COL3, pId);
        contentValues.put(COL4, pPnum);
        contentValues.put(COL5, pGender);
        contentValues.put(COL6, pBir);
        contentValues.put(COL7, eyeLpath);
        contentValues.put(COL8, eyeRpath);
        contentValues.put(COL9, PStatus);
        contentValues.put(COL10, PDesc);
        contentValues.put(COL11, Extime);

        contentValues.put(COL12, Eyepath1);
        contentValues.put(COL13, Eyepath2);
        contentValues.put(COL14, Eyepath3);
        contentValues.put(COL15, Eyepath4);
        contentValues.put(COL16, Eyepath5);
        contentValues.put(COL17, Eyepath6);
        contentValues.put(COL18, Eyepath7);
        contentValues.put(COL19, Eyepath8);

        contentValues.put(COL20, pAge);

        contentValues.put(COL21, Aiimg1);
        contentValues.put(COL22, Aiimg2);
        contentValues.put(COL23, Aiimg3);
        contentValues.put(COL24, Aiimg4);
        contentValues.put(COL25, Aiimg5);
        contentValues.put(COL26, Aiimg6);
        contentValues.put(COL27, Aiimg7);
        contentValues.put(COL28, Aiimg8);
        contentValues.put(COL29, Aiimg9);
        contentValues.put(COL30, Aiimg10);
        contentValues.put(COL31, Aiimg11);
        contentValues.put(COL32, Aiimg12);
        contentValues.put(COL33, Aiimg13);
        contentValues.put(COL34, Aiimg14);
        contentValues.put(COL35, Aiimg15);
        contentValues.put(COL36, Aiimg16);

        contentValues.put(COL37, PDesc2);
        contentValues.put(COL38, PDesc3);
        contentValues.put(COL39, PDesc4);
        contentValues.put(COL40, PDesc5);
        contentValues.put(COL41, PDesc6);
        contentValues.put(COL42, PDesc7);
        contentValues.put(COL43, PDesc8);
        contentValues.put(COL44, PDesc9);
        contentValues.put(COL45, PDesc10);
        contentValues.put(COL46, PDesc11);
        contentValues.put(COL47, PDesc12);
        contentValues.put(COL48, PDesc13);
        contentValues.put(COL49, PDesc14);
        contentValues.put(COL50, PDesc15);
        contentValues.put(COL51, PDesc16);
        contentValues.put(COL52, PDesc17);
        contentValues.put(COL53, PDesc18);
        contentValues.put(COL54, PDesc19);
        contentValues.put(COL55, PDesc20);
        contentValues.put(COL56, PDesc21);
        contentValues.put(COL57, PDesc22);
        contentValues.put(COL58, PDesc23);
        contentValues.put(COL59, PDesc24);
        contentValues.put(COL60, PDesc25);
        contentValues.put(COL61, PDesc26);



        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //query for 1 week repeats
    public Cursor getListContents0() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE STATUS==0", null);
        return data;
    }

    public Cursor getListContents1() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE STATUS==1", null);
        return data;
    }

    public Cursor getListContents2() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE STATUS==2", null);
        return data;
    }

    public Cursor getListContents3() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE STATUS==3", null);
        return data;
    }
}
