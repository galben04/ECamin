package com.mihaia.ecamin.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by mihaia on 3/7/2017.
 */

public class UtilizatoriDB {
    // Database fields
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = { DBHelper.COLUMN_ID,
            DBHelper.COLUMN_USER,
            DBHelper.COLUMN_PAROLA };


    public UtilizatoriDB(Context context) {
        dbHelper = new DBHelper(context);
    }


    public boolean open() {
        try{
            database = dbHelper.getWritableDatabase();
            database.execSQL(DBHelper.CREATE_TABLE_UTILIZATORI);
            return  true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void close() {
        dbHelper.close();
    }


    public UtilizatorModel insertUtilizator(String user, String parola) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER, user);
        values.put(DBHelper.COLUMN_PAROLA, parola);

        long insertId = database.insert(DBHelper.TABLE_UTILIZATORI, null,
                values);

        Cursor cursor = database.query(DBHelper.TABLE_UTILIZATORI,
                allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        UtilizatorModel newUtilizator = cursorToUtilizator(cursor);
        cursor.close();

        return  newUtilizator;
    }


    public void deleteUtilizator(UtilizatorModel tara) {
        long id = tara.getId();
        System.out.println("UtilizatorModel deleted with id: " + id);
        database.delete(DBHelper.TABLE_UTILIZATORI, DBHelper.COLUMN_ID
                + " = " + id, null);
    }


    public void  deleteAllUtilizatori(){
        database.delete(DBHelper.TABLE_UTILIZATORI, null , null);
    }


    public UtilizatorModel getUtilizator(String nume, String parola){
        UtilizatorModel utilizator = null;

        Cursor c = database.rawQuery("SELECT "+ DBHelper.COLUMN_ID + "," +
                DBHelper.COLUMN_USER + ", " + DBHelper.COLUMN_PAROLA + " FROM " + DBHelper.TABLE_UTILIZATORI +  " WHERE " +
                DBHelper.COLUMN_USER + " = '" + nume + "' AND " + DBHelper.COLUMN_PAROLA + "= '" + parola + "'", null);

        if(c.moveToFirst()){
            utilizator.setId(c.getLong(0));
            utilizator.setUser(c.getString(1));
            utilizator.setParola(c.getString(2));
        }
        c.close();

//        Cursor cursor = database.query(DBHelper.TABLE_UTILIZATORI,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            UtilizatorModel utilizator = cursorToUtilizator(cursor);
//            utilizatori.add(utilizator);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
        return utilizator;
    }


    public ArrayList<UtilizatorModel> getAllUtilizatori() {
        ArrayList<UtilizatorModel> utilizatori = new ArrayList<UtilizatorModel>();

        Cursor cursor = database.query(DBHelper.TABLE_UTILIZATORI,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UtilizatorModel utilizator = cursorToUtilizator(cursor);
            utilizatori.add(utilizator);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return utilizatori;
    }

    private UtilizatorModel cursorToUtilizator(Cursor cursor) {
        UtilizatorModel utilizator = new UtilizatorModel();
        utilizator.setId(cursor.getLong(0));
        utilizator.setUser(cursor.getString(1));
        utilizator.setParola(cursor.getString(2));

        return utilizator;
    }


    public class UtilizatorModel{
        private long id;
        private String user;
        private String parola;

        public UtilizatorModel(int id, String user, String parola) {
            this.id = id;
            this.user = user;
            this.parola = parola;
        }

        public UtilizatorModel(String user, String parola) {
            this.user = user;
            this.parola = parola;
        }

        public UtilizatorModel() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getParola() {
            return parola;
        }

        public void setParola(String parola) {
            this.parola = parola;
        }

        @Override
        public String toString() {
            return "UtilizatorModel{" +
                    "id=" + id +
                    ", nume='" + user + '\'' +
                    ", parola='" + parola + '\'' +
                    '}';
        }
    }
}
