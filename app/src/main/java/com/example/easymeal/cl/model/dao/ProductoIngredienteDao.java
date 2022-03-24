package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.Producto;
import com.example.easymeal.cl.model.bd.ProductoIngrediente;

public class ProductoIngredienteDao {

    Context c;
    SQLiteDatabase sql;
    String bd = "easymeal.db";
    ContentValues cv = new ContentValues();
    ProductoIngrediente proing;
    Producto prod;

    public void productoIngredienteDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        proing = new ProductoIngrediente();
    }

    public boolean insertarProductoIngredienteDao(int idProducto, int idIngrediente){
        if(repetidos(idProducto, idIngrediente)){
            return true;
        }else{
            cv = new ContentValues();
            cv.put("idProducto", idProducto);
            cv.put("idIngrediente", idIngrediente);
            return (sql.insert("t_productoIngrediente",null,cv)>0);
        }
    }

    public boolean repetidos(int idProducto, int idIngrediente){
        Cursor c = sql.rawQuery("select * from t_productoIngrediente WHERE idIngrediente = ? AND idProducto = ? ",new String[] {String.valueOf(idProducto), String.valueOf(idIngrediente)});
        if (c.moveToFirst()){
            return true;
        }
        return false;
    }
}
