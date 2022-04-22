package com.example.easymeal.cl.model.dao;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.easymeal.cl.model.bd.Receta;

import java.util.ArrayList;
import android.content.Context;


public class RecetaDao {
    Context c;
    Receta receta;
    ArrayList<Receta> lista;
    SQLiteDatabase sql;
    String bd = "easymeal.db";

    public RecetaDao(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);
        receta = new Receta();
    }

    public boolean insertarReceta(Receta r){
        if(buscar(r.getNombre())==0) {
            ContentValues cv = new ContentValues();
            cv.put("nombre", r.getNombre());
            cv.put("pasos", r.getPasos());
            return (sql.insert("t_receta", null, cv) > 0);
        }else{

            return false;
        }
    }
    public ArrayList<Receta> selectRec(){
        ArrayList<Receta> lista =new ArrayList<>();
        Cursor cr = sql.rawQuery("select * from t_receta",null);
        if(cr != null && cr.moveToFirst()){
            do{
                Receta r= new Receta();
                r.setIdReceta(cr.getInt(0));
                r.setNombre(cr.getString(1));
                r.setPasos(cr.getString(2));
                lista.add(r);
            }while(cr.moveToNext());
            cr.close();
        }
        return lista;
    }
    public int buscar(String n){
        lista=selectRec();
        for(Receta re:lista){
            if(re.getNombre().equals(n)){
                return 1;
            }

        }
        return 0;
    }
    public boolean updateReceta(Receta r){
        ContentValues cv = new ContentValues();
        cv.put("pasos", r.getPasos());
        return (sql.update("t_receta",cv,"nombre='"+r.getNombre()+"'",null)>0);
    }
    public ArrayList<Receta> selectReceta(String nombre){
        ArrayList<Receta> listaRecetas =new ArrayList<>();
        Cursor cr = sql.rawQuery("select * from t_receta where nombre='"+nombre+"'",null);
        if(cr != null && cr.moveToFirst()){
            do{
                Receta receta = new Receta();
                receta.setIdReceta(cr.getInt(0));
                receta.setNombre(cr.getString(1));
                receta.setNombre(cr.getString(2));
                listaRecetas.add(receta);
            }while(cr.moveToNext());
            cr.close();
        }
        return listaRecetas;
    }

    public ArrayList<Receta> selectRecetas(){
        ArrayList<Receta> listaRecetas =new ArrayList<>();
        Cursor cr = sql.rawQuery("select * from t_receta",null);
        if(cr != null && cr.moveToFirst()){
            do{
                Receta receta = new Receta();
                receta.setIdReceta(cr.getInt(0));
                receta.setNombre(cr.getString(1));
                receta.setPasos(cr.getString(2));
                listaRecetas.add(receta);
            }while(cr.moveToNext());
            cr.close();
        }
        return listaRecetas;
    }

}
