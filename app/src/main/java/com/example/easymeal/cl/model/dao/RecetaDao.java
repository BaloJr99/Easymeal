package com.example.easymeal.cl.model.dao;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.easymeal.cl.model.bd.Receta;
import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;
import android.content.Context;


public class RecetaDao {
    Context c;
    Receta receta;
    ArrayList<Receta> lista;
    SQLiteDatabase sql;
    Cursor cr;

    public RecetaDao(Context c){
        this.c = c;
        DbAyuda dbAyuda = new DbAyuda(c);
        sql = dbAyuda.getWritableDatabase();
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
        cr = sql.rawQuery("select * from t_receta",null);
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

    public int eliminarReceta(String nombre){
        cr = sql.rawQuery("select * from t_receta as R INNER JOIN t_recetaPreparacion as P on R.idReceta = P.idReceta WHERE nombre = ?",new String[]{nombre});
        if(cr.moveToFirst()){
            return 0;
        }
        sql.execSQL("DELETE FROM t_receta WHERE nombre='"+nombre+"'");
        return 1;
    }
    public ArrayList<Receta> selectReceta(String nombre){
        ArrayList<Receta> listaRecetas =new ArrayList<>();
        cr = sql.rawQuery("select * from t_receta where nombre='"+nombre+"'",null);
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

    public int maxId(){
        int aux = 0;
        cr = sql.rawQuery("SELECT MAX(idReceta) from t_receta", null);
        if (cr != null && cr.moveToFirst()) {
            aux = cr.getInt(0);
            cr.close();
        }
        return aux;
    }

    public int idReceta(String nombre){
        int aux = 0;
        cr = sql.rawQuery("SELECT idReceta from t_receta WHERE nombre='"+nombre+"'", null);
        if (cr != null && cr.moveToFirst()) {
            aux = cr.getInt(0);
            cr.close();
        }
        return aux;
    }
}
