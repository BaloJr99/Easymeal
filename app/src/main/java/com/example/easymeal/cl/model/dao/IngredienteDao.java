package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.Usuario;

import java.util.ArrayList;

public class IngredienteDao {

    Context c;
    Ingrediente ing;
    ArrayList<Ingrediente> listaIngredientes;
    SQLiteDatabase sql;
    String bd="easymeal.db";

    public void IngredienteDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        ing = new Ingrediente();
    }

    public boolean insertarIngrediente(Usuario u){
        ContentValues cv = new ContentValues();
        cv.put("username", u.getUsername());
        cv.put("clave", u.getClave());
        cv.put("nombre", u.getNombre());
        cv.put("apellidoPaterno", u.getApellidoPaterno());
        cv.put("apellidoMaterno", u.getApellidoMaterno());
        cv.put("fechaNacimiento", u.getFechaNacimiento());
        return (sql.insert("t_usuarios",null,cv)>0);
    }
}
