package com.mihaia.ecamin.localDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Created by mihaia on 3/7/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "camin.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_UTILIZATORI = "utilizatori";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_PAROLA = "parola";


    public static Context context;

    // Database creation sql statement
    public static final String CREATE_TABLE_UTILIZATORI = "create table "
            + TABLE_UTILIZATORI + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_USER
            + " text not null, " + COLUMN_PAROLA + " text not null" + ");";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DBHelper.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //if(!checkDataBase()) {
        database.execSQL(CREATE_TABLE_UTILIZATORI);
        //}
    }

    private static boolean checkDataBase() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILIZATORI);
        onCreate(db);
    }
}
