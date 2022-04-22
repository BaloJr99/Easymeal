package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.easymeal.cl.model.bd.Compras;

import java.util.ArrayList;

public class ComprasDao {

    Context c;
    Compras compra;
    ArrayList<Compras> listaCompras;
    SQLiteDatabase sql;
    String bd = "easymeal.db";
    ContentValues cv = new ContentValues();

    public void comprasDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        compra = new Compras();
    }

    public ArrayList<Compras> listaCompras(){
        listaCompras = new ArrayList<>();
        Cursor c = sql.rawQuery("select * from t_compras",null);
        if (c.moveToFirst()){
            do {
                compra = new Compras(c.getString(1), c.getFloat(2));
                listaCompras.add(compra);
            } while(c.moveToNext());
        }
        c.close();
        return listaCompras;
    }

    public boolean insertarCompra(Compras compra) {
        cv = new ContentValues();
        cv.put("fechaCompra", compra.getFechaCompra());
        cv.put("importeGastado", compra.getImporteGasto());
        return(sql.insert("t_compras", null, cv)>0);
    }

    public boolean modificarCompra(Compras compras) {
        cv = new ContentValues();
        cv.put("fechaCompra", compras.getFechaCompra());
        cv.put("importeGastado", compras.getImporteGasto());
        return(sql.update("t_compras",cv,"idCompras = '" + compras.getIdCompra() + "'", null) > 0);
    }
}
