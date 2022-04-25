package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.RecetaPreparacion;
import com.example.easymeal.database.DbAyuda;

public class RecetaPreparacionDao {

    RecetaPreparacion recetaPreparacion;
    Context c;
    SQLiteDatabase sql;

    public RecetaPreparacionDao(Context c){
        this.c = c;
        DbAyuda dbAyuda = new DbAyuda(c);
        sql = dbAyuda.getWritableDatabase();
        recetaPreparacion = new RecetaPreparacion();
    }

    public void insertarRecetaPreparacion(RecetaPreparacion p){
        ContentValues cv = new ContentValues();
        cv.put("idPreparacion", p.getIdPreparaciones());
        cv.put("idReceta", p.getIdReceta());
        cv.put("cantidadAPreparar", p.getCantidadAPreparar());
        sql.insert("t_recetaPreparacion", null, cv);
    }
}
