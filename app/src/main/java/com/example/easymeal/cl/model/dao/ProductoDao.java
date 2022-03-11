package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.Producto;

import java.util.ArrayList;

public class ProductoDao {

    Context c;
    Producto prod;
    SQLiteDatabase sql;
    String bd = "easymeal.db";

    public void productoDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        prod = new Producto();
    }

    public boolean insertarProducto(Producto p){
        ContentValues cv = new ContentValues();
        cv.put("proveedor", p.getProveedor());
        return (sql.insert("t_producto",null,cv) > 0);
    }

    public ArrayList<Producto> listaProducto(){
        ArrayList<Producto> lista = new ArrayList<>();
        lista.clear();
        Cursor c = sql.rawQuery("select * from t_producto",null);
        if (c.moveToFirst()){
            do {
                prod = new Producto(c.getInt(0), c.getString(1));
                lista.add(prod);
            } while(c.moveToNext());
        }
        return lista;
    }

}
