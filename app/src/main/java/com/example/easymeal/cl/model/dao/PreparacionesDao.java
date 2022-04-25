package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.easymeal.cl.model.bd.Preparaciones;
import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class PreparacionesDao {
    Preparaciones preparaciones;
    Context c;
    SQLiteDatabase sql;

    public PreparacionesDao(Context c){
        this.c = c;
        DbAyuda dbAyuda = new DbAyuda(c);
        sql = dbAyuda.getWritableDatabase();
        preparaciones = new Preparaciones();
    }

    public int insertarPreparacion(Preparaciones p){
        ContentValues cv = new ContentValues();
        cv.put("idPreparacion", p.getIdPreparaciones());
        cv.put("tipoComida", p.getTipoComida());
        cv.put("semanaPreparacion", p.getfechaPreparacion());
        sql.insert("t_preparaciones",null,cv);

        Cursor cursor = sql.rawQuery("select max(idPreparacion) from t_preparaciones",null);
        if (cursor.moveToFirst()){
            int aux = cursor.getInt(0);
            cursor.close();
            return aux;
        }
        return 0;
    }

    public void eliminarPreparacion(String semana){
        sql.execSQL("DELETE FROM t_recetaPreparacion WHERE idPreparacion IN(SELECT idPreparacion FROM t_preparaciones WHERE semanaPreparacion = ?)",new String[]{semana});
        sql.execSQL("DELETE FROM t_preparaciones WHERE semanaPreparacion = ?",new String[]{semana});
    }



    public ArrayList<Object[]> buscarHorario(String semana) {
        ArrayList<Object[]> preparaciones = null;

        Cursor cursor = sql.rawQuery("SELECT * FROM t_preparaciones AS P INNER JOIN t_recetaPreparacion AS R ON P.idPreparacion = R.idPreparacion WHERE semanaPreparacion = ?", new String[]{semana});
        if(cursor.moveToFirst()){
            preparaciones = new ArrayList<>();
            do {
                preparaciones.add(new Object[]{cursor.getString(1), cursor.getInt(5), cursor.getInt(6)});
            }while(cursor.moveToNext());
            cursor.close();
        }
        return preparaciones;
    }

    public ArrayList<Object[]> buscarHorarioSemanal(String semana) {
        ArrayList<Object[]> preparaciones = null;

        Cursor cursor = sql.rawQuery("SELECT tipoComida, nombre FROM t_preparaciones AS P INNER JOIN t_recetaPreparacion AS RP ON P.idPreparacion = RP.idPreparacion INNER JOIN t_receta AS R ON R.idReceta = RP.idReceta WHERE semanaPreparacion = ?", new String[]{semana});
        if(cursor.moveToFirst()){
            preparaciones = new ArrayList<>();
            do {
                preparaciones.add(new Object[]{cursor.getString(0), cursor.getString(1)});
            }while(cursor.moveToNext());
            cursor.close();
        }
        return preparaciones;
    }
}
