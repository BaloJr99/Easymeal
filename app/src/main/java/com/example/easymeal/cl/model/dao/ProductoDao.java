package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Producto;

import java.util.ArrayList;

public class ProductoDao {

    Context c;
    Producto prod;
    ArrayList<Producto> listaProductos;
    SQLiteDatabase sql;
    String bd = "easymeal.db";

    public void productoDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        prod = new Producto();
    }

    public boolean insertarProducto(Producto p){
        System.out.println(p.getProveedor());
        ContentValues cv = new ContentValues();
        cv.put("proveedor", p.getProveedor());
        return (sql.insert("t_producto",null,cv) > 0);
    }

}
