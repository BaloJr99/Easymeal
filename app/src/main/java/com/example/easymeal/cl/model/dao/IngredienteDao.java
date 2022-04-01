package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Ingrediente;

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
        try {
            if(repetidos(i) != 0){
                cv = new ContentValues();
                cv.put("descripcion", i.getDescripcion());
                cv.put("mandado", i.getMandado());
                if(i.getMandado() == 1){
                    cv.put("cantidadAcomprar", i.getCantidadAComprar());
                }else{
                    cv.put("cantidad", i.getCantidad());
                }
                cv.put("fechaDeCaducidad", i.getFechaCaducidad());
                cv.put("unidadDeMedida", i.getUnidadDeMedida());
                cv.put("imagen", i.getImagen());
                cv.put("proveedor", i.getProveedor());
                sql.update("t_ingrediente",cv,"idIngrediente = '" + repetidos(i) + "'", null);
                return repetidos(i);
            }else{
                cv = new ContentValues();
                cv.put("descripcion", i.getDescripcion());
                cv.put("unidadDeMedida", i.getUnidadDeMedida());
                cv.put("cantidad", i.getCantidad());
                cv.put("fechaDeCaducidad", i.getFechaCaducidad());
                cv.put("mandado", i.getMandado());
                cv.put("cantidadAcomprar", i.getCantidadAComprar());
                cv.put("imagen", i.getImagen());
                cv.put("proveedor", i.getProveedor());
                return (int) sql.insert("t_ingrediente",null,cv);
            }
        }catch (SQLException sql){
            Toast.makeText(c, sql.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    public int repetidos(Ingrediente i){
        String[] args = new String[]{i.getDescripcion(), i.getUnidadDeMedida(), i.getProveedor()};

        Cursor c = sql.rawQuery("select * from t_ingrediente WHERE descripcion = ? AND unidadDeMedida = ? AND proveedor = ?",args);
        if (c.moveToFirst()){
            return c.getInt(0);
        }
        return 0;
    }

    public boolean eliminarLista(int id){
        cv = new ContentValues();
        cv.put("mandado", 0);
        cv.put("cantidadAComprar", 0);
        return (sql.update("t_ingrediente",cv,"idIngrediente = '" + id + "'", null)>0);
    }

    public ArrayList<Ingrediente> listaIngredientes(){
        ArrayList<Ingrediente> lista = new ArrayList<>();
        Cursor c = sql.rawQuery("select * from t_ingrediente",null);
        if (c.moveToFirst()){
            do {
                ing = new Ingrediente(c.getInt(0), c.getInt(5), c.getString(2), c.getString(1), c.getString(4), c.getFloat(3), c.getFloat(6),c.getBlob(8), c.getString(7));
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
                    ing = new Ingrediente(c.getInt(0), c.getString(1), c.getFloat(6), c.getString(2), c.getString(7));
                }else{
                    ing = new Ingrediente(c.getInt(0), c.getString(1), c.getString(2), c.getFloat(3), c.getString(7));
                }
                lista.add(ing);
            } while(c.moveToNext());
        }

        return lista;
    }

    public Ingrediente buscarIngrediente(int idIngrediente) {
        Ingrediente ing = new Ingrediente();

        Cursor c;

        c = sql.rawQuery("SELECT * FROM t_ingrediente WHERE idIngrediente = '" + idIngrediente + "'", null);

        if (c.moveToFirst()){
            ing = new Ingrediente(c.getInt(0), c.getInt(5), c.getString(2), c.getString(1), c.getString(4), c.getFloat(3), c.getFloat(6),c.getBlob(8), c.getString(7));
        }

        return ing;
    }
}
