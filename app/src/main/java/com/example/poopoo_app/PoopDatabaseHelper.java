package com.example.poopoo_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PoopDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pooplogs.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "poop_logs";
    public static final String COL_ID = "id";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_SHAPE = "shape";
    public static final String COL_COLOR = "color";
    public static final String COL_SIZE = "size";
    public static final String COL_NOTES = "notes";

    public PoopDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT, " +
                COL_SHAPE + " TEXT, " +
                COL_COLOR + " TEXT, " +
                COL_SIZE + " TEXT, " +
                COL_NOTES + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertPoopLog(String date, String time, String shape, String color, String size, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_SHAPE, shape);
        values.put(COL_COLOR, color);
        values.put(COL_SIZE, size);
        values.put(COL_NOTES, notes);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<PoopLog> getLogsByDate(String date) {
        List<PoopLog> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_DATE + " = ?", new String[]{date});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
                String shape = cursor.getString(cursor.getColumnIndexOrThrow(COL_SHAPE));
                String color = cursor.getString(cursor.getColumnIndexOrThrow(COL_COLOR));
                String size = cursor.getString(cursor.getColumnIndexOrThrow(COL_SIZE));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES));
                list.add(new PoopLog(id, date, time, shape, color, size, notes));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<PoopLog> getAllPoopLogs() {
        List<PoopLog> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
                String shape = cursor.getString(cursor.getColumnIndexOrThrow(COL_SHAPE));
                String color = cursor.getString(cursor.getColumnIndexOrThrow(COL_COLOR));
                String size = cursor.getString(cursor.getColumnIndexOrThrow(COL_SIZE));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES));
                list.add(new PoopLog(id, date, time, shape, color, size, notes));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void updatePoopLog(PoopLog log) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, log.getDate());
        values.put(COL_TIME, log.getTime());
        values.put(COL_SHAPE, log.getShape());
        values.put(COL_COLOR, log.getColor());
        values.put(COL_SIZE, log.getSize());
        values.put(COL_NOTES, log.getNotes());

        db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{String.valueOf(log.getId())});
        db.close();
    }

    public void deletePoopLog(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
