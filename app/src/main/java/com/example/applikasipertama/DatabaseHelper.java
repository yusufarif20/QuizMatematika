package com.example.applikasipertama;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 2;  // Naikkan versi database untuk memicu onUpgrade
    private static final String TABLE_NAME = "nilai";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NILAI = "nilai";
    private static final String COLUMN_DIFFICULTY = "difficulty"; // Kolom baru untuk menyimpan difficulty

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NILAI + " INTEGER, " +
                COLUMN_DIFFICULTY + " TEXT)";  // Tambahkan kolom difficulty
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_DIFFICULTY + " TEXT");
        }
    }

    public void updateNilai(int id, int nilai, String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NILAI, nilai);
        values.put(COLUMN_DIFFICULTY, difficulty);  // Simpan nilai difficulty

        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (rowsAffected == 0) {
            values.put(COLUMN_ID, id);
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public int getNilai() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NILAI}, COLUMN_ID + " = ?", new String[]{"1"}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_NILAI);
            if (columnIndex != -1) {
                int nilai = cursor.getInt(columnIndex);
                cursor.close();
                db.close();
                return nilai;
            }
            cursor.close();
        }
        db.close();
        return 0;
    }

    public String getDifficulty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_DIFFICULTY}, COLUMN_ID + " = ?", new String[]{"1"}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_DIFFICULTY);
            if (columnIndex != -1) {
                String difficulty = cursor.getString(columnIndex);
                cursor.close();
                db.close();
                return difficulty;
            }
            cursor.close();
        }
        db.close();
        return null;
    }
}
