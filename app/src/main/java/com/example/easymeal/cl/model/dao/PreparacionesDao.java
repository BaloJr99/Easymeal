package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.Preparaciones;

public class PreparacionesDao {
    Preparaciones preparaciones;
    Context c;
    SQLiteDatabase sql;
    String bd = "easymeal.db";

    public PreparacionesDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        preparaciones = new Preparaciones();
    }

    public PreparacionesDao() {

    }

    public int insertarPreparacion(Preparaciones p){
        ContentValues cv = new ContentValues();
        cv.put("idPreparacion", p.getIdPreparaciones());
        cv.put("tipoComida", p.getTipoComida());
        cv.put("fechaPreparacion", p.getfechaPreparacion());
        sql.insert("t_preparaciones",null,cv);

        Cursor c = sql.rawQuery("select max(idPreparacion) from t_preparaciones",null);
        if (c.moveToFirst()){
            return c.getInt(0);
        }
        return 0;
    }
}
