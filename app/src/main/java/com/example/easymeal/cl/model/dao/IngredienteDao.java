package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.Usuario;

import java.util.ArrayList;

public class IngredienteDao {

    Context c;
    Ingrediente ing;
    ArrayList<Ingrediente> listaIngredientes;
    SQLiteDatabase sql;
    String bd = "easymeal.db";
    ContentValues cv = new ContentValues();

    public void ingredienteDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        ing = new Ingrediente();
    }

    public boolean insertarLista(Ingrediente i){
        cv = new ContentValues();
        cv.put("descripcion", i.getDescripcion());
        cv.put("unidadDeMedida", i.getUnidadDeMedida());
        cv.put("cantidad", i.getCantidad());
        cv.put("fechaDeCaducidad", i.getCantidad());
        cv.put("mandado", i.getMandado());
        cv.put("cantidadAcomprar", i.getCantidadAComprar());
        cv.put("imagen", i.getImagen());
        return (sql.insert("t_ingrediente",null,cv)>0);
    }

    public ArrayList<Ingrediente> listaIngredientes(){
        ArrayList<Ingrediente> lista = new ArrayList<>();
        Cursor c = sql.rawQuery("select * from t_ingrediente",null);
        if (c.moveToFirst()){
            do {
                ing = new Ingrediente(c.getInt(0), c.getInt(5), c.getString(2), c.getString(1), c.getString(4), c.getFloat(3), c.getFloat(6),c.getBlob(7));
                lista.add(ing);
            } while(c.moveToNext());
        }
        return lista;
    }

    public ArrayList<Ingrediente> listaMandado() {
        ArrayList<Ingrediente> lista = new ArrayList<>();
        Cursor c = sql.rawQuery("SELECT * FROM t_ingrediente WHERE mandado = 0", null);

        if (c.moveToFirst()){
            do {
                ing = new Ingrediente(c.getInt(0), c.getString(1), c.getFloat(6), c.getString(2));
                lista.add(ing);
            } while(c.moveToNext());
        }

        return lista;
    }
}
