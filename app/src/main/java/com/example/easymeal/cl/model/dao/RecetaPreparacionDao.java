package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.RecetaPreparacion;

public class RecetaPreparacionDao {

    RecetaPreparacion recetaPreparacion;
    Context c;
    SQLiteDatabase sql;
    String bd = "easymeal.db";

    public RecetaPreparacionDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        recetaPreparacion = new RecetaPreparacion();
    }

    public boolean insertarRecetaPreparacion(RecetaPreparacion p){
        ContentValues cv = new ContentValues();
        cv.put("idPreparacion", p.getIdPreparaciones());
        cv.put("idReceta", p.getIdReceta());
        cv.put("cantidadAPreparar", p.getCantidadAPreparar());
        return (sql.insert("t_recetaPreparacion",null,cv)>0);

    }
}
