package com.example.mobilproje;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kullanici_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "kullanicilar";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AD = "ad";
    private static final String COLUMN_SOYAD = "soyad";
    private static final String COLUMN_MAIL = "mail";
    private static final String COLUMN_SIFRE = "sifre";
    private static final String COLUMN_CINSIYET = "cinsiyet";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AD + " TEXT, " +
                COLUMN_SOYAD + " TEXT, " +
                COLUMN_MAIL + " TEXT, " +
                COLUMN_SIFRE + " TEXT, " +
                COLUMN_CINSIYET + " TEXT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean kullaniciEkle(String ad, String soyad, String mail, String sifre, String cinsiyet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AD, ad);
        values.put(COLUMN_SOYAD, soyad);
        values.put(COLUMN_MAIL, mail);
        values.put(COLUMN_SIFRE, sifre);
        values.put(COLUMN_CINSIYET, cinsiyet);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean kullaniciDogrula(String mail, String sifre) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { COLUMN_MAIL, COLUMN_SIFRE };
        String selection = COLUMN_MAIL + " = ? AND " + COLUMN_SIFRE + " = ?";
        String[] selectionArgs = { mail, sifre };
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        boolean dogrulandi = cursor.getCount() > 0;
        cursor.close();
        return dogrulandi;
    }
}
