package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.IngredienteReceta;
import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class RecetaIngredienteDao {
    Context c;
    IngredienteReceta ingRec;
    SQLiteDatabase sql;
    ContentValues cv;

    public RecetaIngredienteDao (Context context){
        this.c = context;
        DbAyuda dbAyuda = new DbAyuda(c);
        sql = dbAyuda.getWritableDatabase();
        ingRec = new IngredienteReceta();
    }

    public boolean insertarReceta(IngredienteReceta ir){
        cv = new ContentValues();
        cv.put("cantidad",ir.getCantidad());
        cv.put("idIngrediente", ir.getIdIngrediente());
        cv.put("idReceta", ir.getIdReceta());
        return (sql.insert("t_ingredienteReceta",null,cv) > 0);
    }

    public ArrayList<IngredienteReceta> listaIngRec(Integer idReceta){
        System.out.println(idReceta);
        ArrayList<IngredienteReceta> IngRecetaList = null;
        Cursor cursor = sql.rawQuery("SELECT * FROM t_ingredienteReceta WHERE idReceta = ?",new String[]{String.valueOf(idReceta)});

        if(cursor.moveToFirst()){
            IngRecetaList = new ArrayList<>();
            do {
                ingRec = new IngredienteReceta();
                ingRec.setIdIngredientesReceta(cursor.getInt(0));
                ingRec.setCantidad(cursor.getFloat(1));
                ingRec.setIdReceta(cursor.getInt(2));
                ingRec.setIdIngrediente(cursor.getInt(3));

                IngRecetaList.add(ingRec);
            }while(cursor.moveToNext());
            cursor.close();
        }
        System.out.println(IngRecetaList);
        return IngRecetaList;
    }

    public ArrayList<String> listaIng(ArrayList<IngredienteReceta> ingRecetaList) {
        ArrayList<String> infoList = null;

        for(int i = 0; i <ingRecetaList.size(); i++){
            Cursor cursor = sql.rawQuery("SELECT * FROM "+"t_ingrediente WHERE idIngrediente="+ingRecetaList.get(i).getIdIngrediente(),null);
            if(cursor.moveToFirst()){
                infoList = new ArrayList<>();
                do {
                    infoList.add(String.valueOf(cursor.getString(1)));
                }while(cursor.moveToNext());
            }
            cursor.close();
        }
        return infoList;
    }

    public void modificarIng(IngredienteReceta ingRece) {
        //Metodo que modifica el ingrediente de la receta
        cv = new ContentValues();
        cv.put("idIngredientesReceta",ingRece.getIdIngredientesReceta());
        cv.put("cantidad", ingRece.getCantidad());
        cv.put("idReceta",ingRece.getIdReceta());
        cv.put("idIngrediente",ingRece.getIdIngrediente());
        sql.update("t_ingredienteReceta", cv, "idIngredientesReceta= ?", new String[]{ingRece.getIdIngredientesReceta().toString()});
    }

    public void eliminarIng(Integer ingRece) {
        //Metodo que modifica el ingrediente de la receta
        sql.delete("t_ingredienteReceta", "idIngredientesReceta=" + ingRece, null);
    }
}
