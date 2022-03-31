package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.Producto;
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

    public int insertarLista(Ingrediente i){
        if(repetidos(i) != 0){
            cv = new ContentValues();
            cv.put("descripcion", i.getDescripcion());
            cv.put("mandado", i.getMandado());
            cv.put("cantidadAcomprar", i.getCantidadAComprar());
            cv.put("unidadDeMedida", i.getUnidadDeMedida());
            cv.put("imagen", i.getImagen());
            sql.update("t_ingrediente",cv,"idIngrediente = '" + repetidos(i) + "'", null);
            return repetidos(i);
        }else{
            cv = new ContentValues();
            cv.put("descripcion", i.getDescripcion());
            cv.put("unidadDeMedida", i.getUnidadDeMedida());
            cv.put("cantidad", i.getCantidad());
            cv.put("fechaDeCaducidad", i.getCantidad());
            cv.put("mandado", i.getMandado());
            cv.put("cantidadAcomprar", i.getCantidadAComprar());
            cv.put("imagen", i.getImagen());
            return (int) sql.insert("t_ingrediente",null,cv);
        }
    }

    public int repetidos(Ingrediente i){
        String[] args = new String[]{i.getDescripcion(), i.getUnidadDeMedida()};

        Cursor c = sql.rawQuery("select * from t_ingrediente WHERE descripcion = ? AND unidadDeMedida = ?",args);
        if (c.moveToFirst()){
            return c.getInt(0);
        }
        return 0;
    }

    public boolean eliminarLista(int id){
        cv = new ContentValues();
        cv.put("mandado", 0);
        return (sql.update("t_ingrediente",cv,"idIngrediente = '" + id + "'", null)>0);
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

    public ArrayList<Ingrediente> listaMandado(String tipo) {
        ArrayList<Ingrediente> lista = new ArrayList<>();
        Cursor c;

        if(tipo.equals("mandado")){
            c = sql.rawQuery("SELECT * FROM t_ingrediente WHERE mandado = 1", null);
        }else{
            c = sql.rawQuery("SELECT * FROM t_ingrediente", null);
        }
        if (c.moveToFirst()){
            do {
                if(tipo.equals("mandado")){
                    ing = new Ingrediente(c.getInt(0), c.getString(1), c.getFloat(6), c.getString(2));
                }else{
                    ing = new Ingrediente(c.getInt(0), c.getString(1), c.getString(2), c.getFloat(3));
                }
                lista.add(ing);
            } while(c.moveToNext());
        }

        return lista;
    }

}
