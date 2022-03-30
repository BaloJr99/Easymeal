package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.easymeal.cl.model.bd.IngredienteReceta;

public class RecetaIngredienteDao {
    Context c;
    IngredienteReceta ingRec;
    SQLiteDatabase sql;
    ContentValues cv = new ContentValues();
    public boolean insertarReceta(IngredienteReceta ir){
        ContentValues cv = new ContentValues();
        cv.put("cantidad",ir.getCantidad());
        cv.put("idIngrediente", ir.getIdIngrediente());
        cv.put("idReceta", ir.getIdReceta());
        return (sql.insert("t_ingredienteReceta",null,cv) > 0);
    }
}