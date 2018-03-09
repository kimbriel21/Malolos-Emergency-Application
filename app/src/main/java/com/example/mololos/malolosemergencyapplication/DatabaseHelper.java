package com.example.mololos.malolosemergencyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kimbriel on 3/2/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String db_name = "database_app";
    public static final String table_name = "tbl_user";
    public static final String col_id = "user_id";
    public static final String col_first_name = "first_name";
    public static final String col_last_name = "last_name";
    public static final String col_contact_number = "contact_number";
    public static final String col_address = "address";
    public static final String col_email = "email";



    public DatabaseHelper(Context context) {
        super(context, db_name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table_name+"(user_id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT, contact_number TEXT, address TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }


    public boolean insertUser(String first_name,String last_name, String contact_number, String address, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_first_name,first_name);
        cv.put(col_last_name,last_name);
        cv.put(col_contact_number,contact_number);
        cv.put(col_address,address);
        cv.put(col_email,email);

        long status = db.insert(table_name,null,cv);
        if (status == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void updateUser(String id,String first_name,String last_name, String contact_number, String address, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_id,id);
        cv.put(col_first_name,first_name);
        cv.put(col_last_name,last_name);
        cv.put(col_contact_number,contact_number);
        cv.put(col_address,address);
        cv.put(col_email,email);
        db.update(table_name,cv,"user_id = ?",new String[]{id});
    }

    public Cursor getUserInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+table_name, null);
    }

}
