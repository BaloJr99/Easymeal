package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.Preparaciones;

import java.util.ArrayList;

public class PreparacionesDao {
    Preparaciones preparaciones;
    Context c;
    SQLiteDatabase sql;
    String bd = "easymeal.db";

    public PreparacionesDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        preparaciones = new Preparaciones();
    }

    public PreparacionesDao() {

    }

    public int insertarPreparacion(Preparaciones p){
        ContentValues cv = new ContentValues();
        cv.put("idPreparacion", p.getIdPreparaciones());
        cv.put("tipoComida", p.getTipoComida());
        cv.put("fechaPreparacion", p.getfechaPreparacion());
        sql.insert("t_preparaciones",null,cv);

        Cursor c = sql.rawQuery("select max(idPreparacion) from t_preparaciones",null);
        if (c.moveToFirst()){
            return c.getInt(0);
        }
        return 0;
    }

    public ArrayList<Horario> listaHorario(String tipo) {
        ArrayList<Horario> lista = new ArrayList<>();
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
        c.close();

        return lista;
    }
}
