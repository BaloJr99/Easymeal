package com.example.easymeal.cl.model.dao;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.example.easymeal.cl.model.bd.Receta;
import java.util.ArrayList;
import android.content.Context;


public class RecetaDao {
    Context c;
    Receta receta;
    ArrayList<Receta> listaRecetas;
    SQLiteDatabase sql;
    String bd = "easymeal.db";

    public void productoDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        receta = new Receta();
    }

    public boolean insertarProducto(Receta r){
        ContentValues cv = new ContentValues();
        cv.put("pasos", r.getPasos());
        return (sql.insert("t_receta",null,cv) > 0);
    }

}
