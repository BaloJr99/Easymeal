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
    SQLiteDatabase sql;
    String bd = "easymeal.db";
    ContentValues cv = new ContentValues();

    Cursor cursor;

    public IngredienteDao (Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd, Context.MODE_PRIVATE,null);
        ing = new Ingrediente();
    }

    public int insertarLista(Ingrediente i){
        try {
            if(repetidos(i) != 0){
                cv = new ContentValues();
                cv.put("descripcion", i.getDescripcion());
                if(i.getMandado() == 1){
                    cv.put("mandado", i.getMandado());
                    cv.put("cantidadAcomprar", i.getCantidadAComprar());
                }else{
                    cv.put("cantidad", i.getCantidad());
                    cv.put("fechaDeCaducidad", i.getFechaCaducidad());
                }
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

        cursor = sql.rawQuery("select * from t_ingrediente WHERE descripcion = ? AND unidadDeMedida = ? AND proveedor = ?",args);
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        cursor.close();
        return 0;
    }

    public boolean eliminarLista(int id){
        cv = new ContentValues();
        cv.put("mandado", 0);
        cv.put("cantidadAcomprar", 0);
        return (sql.update("t_ingrediente",cv,"idIngrediente = '" + id + "'", null)>0);
    }

    public ArrayList<Ingrediente> listaIngredientes(){
        ArrayList<Ingrediente> lista = new ArrayList<>();
        cursor = sql.rawQuery("select * from t_ingrediente",null);
        if (cursor.moveToFirst()){
            do {
                ing = new Ingrediente(cursor.getInt(0), cursor.getInt(5), cursor.getString(2), cursor.getString(1), cursor.getString(4), cursor.getFloat(3), cursor.getFloat(6),cursor.getBlob(8), cursor.getString(7));
                lista.add(ing);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public ArrayList<Ingrediente> listaMandado(String tipo) {
        ArrayList<Ingrediente> lista = new ArrayList<>();

        if(tipo.equals("mandado")){
            cursor = sql.rawQuery("SELECT * FROM t_ingrediente WHERE mandado = 1", null);
        }else{
            cursor = sql.rawQuery("SELECT * FROM t_ingrediente", null);
        }

        if (cursor.moveToFirst()){
            do {
                if(tipo.equals("mandado")){
                    ing = new Ingrediente(cursor.getInt(0), cursor.getString(1), cursor.getFloat(6), cursor.getString(2), cursor.getString(7));
                }else{
                    ing = new Ingrediente(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getString(7));
                }
                lista.add(ing);
            } while(cursor.moveToNext());
        }
        cursor.close();

        return lista;
    }

    public Ingrediente buscarIngrediente(int idIngrediente) {
        Ingrediente ing = new Ingrediente();

        cursor = sql.rawQuery("SELECT * FROM t_ingrediente WHERE idIngrediente = '" + idIngrediente + "'", null);

        if (cursor.moveToFirst()){
            ing = new Ingrediente(cursor.getInt(0), cursor.getInt(5), cursor.getString(2), cursor.getString(1), cursor.getString(4), cursor.getFloat(3), cursor.getFloat(6),cursor.getBlob(8), cursor.getString(7));
        }
        cursor.close();
        return ing;
    }

    public void marcarComprado(ArrayList<Integer> listaIngSeleccionados) {
        for (Integer lista: listaIngSeleccionados) {
            cursor = sql.rawQuery("SELECT cantidad, cantidadAcomprar FROM t_ingrediente WHERE idIngrediente = ?", new String[]{String.valueOf(lista)});
            cursor.moveToFirst();
            cv = new ContentValues();
            cv.put("mandado", 0);
            cv.put("cantidadAcomprar", 0);
            cv.put("cantidad", cursor.getInt(0) + cursor.getInt(1));
            sql.update("t_ingrediente",cv,"idIngrediente = ?", new String[]{String.valueOf(lista)});
        }
    }
}
