package com.alan.moviles.contactossqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDB.db";

    public static class Contacts implements BaseColumns {
        public static final String TABLE_NAME = "contact_table";

        public static final String COL_ID = "_ncontrol";
        public static final String COL_NAME = "_name";
        public static final String COL_SUR = "_surname";
        public static final String COL_CAREER = "_career";
        public static final String COL_AGE = "_age";
        public static final String COL_WEIGTH = "_weigth";
        public static final String COL_HEIGTH = "_heitgh";
        public static final String COL_DESC = "_description";
    }

    //Creacion de script de creacion de tabla
    //Esto es igual a CREATE TABLE CONTACT_TABLE (COLUMNAS)
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contacts.TABLE_NAME + " (" +
                    Contacts.COL_ID + " INTEGER PRIMARY KEY," +
                    Contacts.COL_NAME + " TEXT," +
                    Contacts.COL_SUR + " TEXT," +
                    Contacts.COL_CAREER + " TEXT," +
                    Contacts.COL_AGE + " TEXT," +
                    Contacts.COL_HEIGTH + " TEXT," +
                    Contacts.COL_WEIGTH + " TEXT," +
                    Contacts.COL_DESC + " TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contacts.TABLE_NAME;


    private static final String SQL_READ_QUERY  =
            "SELECT * FROM "+ Contacts.TABLE_NAME+" WHERE COL_ID == ";



    //Creacion del archivo .db de nuestra base de datos
    public DataBaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creacion de la tabla en el archivo local
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Update a la tabla
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public Boolean insertData(int ncontrol, String name, String surname, String age, String career, String weigth, String heigth, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contacts.COL_ID, ncontrol);
        contentValues.put(Contacts.COL_NAME, name);
        contentValues.put(Contacts.COL_SUR, surname);
        contentValues.put(Contacts.COL_CAREER, career);
        contentValues.put(Contacts.COL_AGE, age);
        contentValues.put(Contacts.COL_WEIGTH, weigth);
        contentValues.put(Contacts.COL_HEIGTH, heigth);
        contentValues.put(Contacts.COL_DESC, description);


        long result = db.insert(Contacts.TABLE_NAME, null, contentValues);
        db.close();

        if(result ==-1 ){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean updateData(int ncontrol, String name, String surname, String age, String career, String weigth, String heigth, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Contacts.COL_NAME, name);
        contentValues.put(Contacts.COL_SUR, surname);
        contentValues.put(Contacts.COL_CAREER, career);
        contentValues.put(Contacts.COL_AGE, age);
        contentValues.put(Contacts.COL_WEIGTH, weigth);
        contentValues.put(Contacts.COL_HEIGTH, heigth);
        contentValues.put(Contacts.COL_DESC, description);

        String[] selectionArgs = {String.valueOf(ncontrol)};

        long result = db.update(Contacts.TABLE_NAME, contentValues, "_ncontrol =?", selectionArgs);
        db.close();

        if(result > 0 ){
            return true;
        }
        else {
            return false;
        }
    }

    public Cursor readData(int ncontro){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"_name", "_surname", "_career", "_age", "_heitgh", "_weigth", "_description"};
        String selection = "_ncontrol = ?";
        String[] selectionArgs = {String.valueOf(ncontro)};
        Cursor cursor = db.query(Contacts.TABLE_NAME, columns, selection, selectionArgs , null, null, null, null);

        Log.i("cursor", cursor.getColumnNames().toString());

        return cursor;
    }

    public Integer deleteData(int ncontro){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = {String.valueOf(ncontro)};
        int i  = db.delete(Contacts.TABLE_NAME, "_ncontrol =?", selectionArgs);
        return i;
    }
}
