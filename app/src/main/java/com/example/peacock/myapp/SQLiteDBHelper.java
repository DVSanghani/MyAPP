package com.example.peacock.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by peacock on 8/4/17.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "info.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "profile";

    public static final String COL_ID = "userid";
    public static final String COL_NAME = "name";
    public static final String COL_DOB = "dob";
    public static final String COL_Gender = "gender";
    public static final String COL_photo = "photo";
    public static final String COL_Country = "country";
    public static final String COL_Hobbies = "hobbies";

    SQLiteDatabase db;

    // Modified Constructor
    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        db = this.getWritableDatabase();
    }

    public void close() {
        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_DOB + " TEXT, " +
                COL_photo + " BLOB, " + //photo is get by Blob
                COL_Gender + " TEXT, " +
                COL_Country + " TEXT, " +
                COL_Hobbies + " TEXT " + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);

    }

    // Insert the data into SQLitedatabase
    public void adduser(Contact user) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_DOB, user.getDob());
        values.put(COL_photo, user.get_photo());
        values.put(COL_Gender, user.get_gender());
        values.put(COL_Country, user.get_country());
        values.put(COL_Hobbies, user.get_hobbies());

        db.insert(TABLE_NAME, null, values);
        close();
    }
    // Update the data to SQLitedatabase
    public int Updateuser(Contact user) {
        open();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, user.getName());
        values.put(COL_DOB, user.getDob());
        values.put(COL_Gender, user.get_gender());
        values.put(COL_photo, user.get_photo());
        values.put(COL_Country, user.get_country());
        values.put(COL_Hobbies, user.get_hobbies());

        int i = db.update(TABLE_NAME, values, COL_ID + " = " + user.get_id(), null);
        if (i > 0) {
            Log.e("UPATE", "update data" + String.valueOf(i));
        }
        close();
        return i;
    }
    // Delete the data into SQLitedatabase
    public void Deleteuser(Contact user) {
        open();
        db.delete(TABLE_NAME, COL_ID + " =? ", new String[]{String.valueOf(user.get_id())});
        close();
    }

    //Display all Data
    public ArrayList<Contact> getalluserinfo() {
        ArrayList<Contact> userinfo = new ArrayList<>();
         /*db = this.getWritableDatabase();*/
        open();
        Cursor c1 = db.rawQuery("SELECT * FROM  " + TABLE_NAME, null);
        if (c1.moveToFirst()) {
            do {
                Contact u = new Contact();
                u.set_id(c1.getInt(c1.getColumnIndex(COL_ID)));
                u.set_name(c1.getString(c1.getColumnIndex(COL_NAME)));
                u.set_dob(c1.getString(c1.getColumnIndex(COL_DOB)));
                u.set_photo(c1.getBlob(c1.getColumnIndex(COL_photo)));
                u.set_gender(c1.getString(c1.getColumnIndex(COL_Gender)));
                u.set_country(c1.getString(c1.getColumnIndex(COL_Country)));
                u.set_hobbies(c1.getString(c1.getColumnIndex(COL_Hobbies)));

                userinfo.add(u);
            } while (c1.moveToNext());
        }
        c1.close();
        close();
        return userinfo;
    }

    // Upgrade the data to SQLitedatabase
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}


