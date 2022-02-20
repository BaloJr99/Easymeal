package com.example.easymeal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbAyuda extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "easymeal.db";
    private static final String TABLE_PREPARACIONES = "t_preparaciones";

    public DbAyuda(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PREPARACIONES+"("+
                "idPreparaciones INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombreComida VARCHAR(30))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_PREPARACIONES);
        onCreate(sqLiteDatabase);
    }
}
