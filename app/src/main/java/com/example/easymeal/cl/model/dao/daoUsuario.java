package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class daoUsuario {
    Context c;
    Usuario u;
    ArrayList<Usuario> lista;
    SQLiteDatabase sql;
    Cursor cr;
    ContentValues cv;

    public daoUsuario(Context c) {
        this.c = c;
        DbAyuda dbAyuda = new DbAyuda(c);
        sql = dbAyuda.getWritableDatabase();
        u=new Usuario();
    }

    public boolean insertUsuario(Usuario u){
        if(buscar(u.getUsername())==0){
            cv = new ContentValues();
            cv.put("username", u.getUsername());
            cv.put("clave", u.getClave());
            cv.put("nombre", u.getNombre());
            cv.put("apellidoPaterno", u.getApellidoPaterno());
            cv.put("apellidoMaterno", u.getApellidoMaterno());
            cv.put("fechaNacimiento", u.getFechaNacimiento());
            cv.put("vidaSaludable", u.getVidaSaludable());
            cv.put("fechaVencimiento", u.getFechaVencimiento());
            return (sql.insert("t_usuarios",null,cv)>0);
        }else{
            return false;
        }
    }

    public int buscar(String u){
        int x=0;
        lista=selectUsuario();
        for(Usuario us:lista){
            if(us.getUsername().equals(u)){
                x++;
            }

        }
        return x;
    }
    public ArrayList<Usuario> selectUsuario(){
        ArrayList<Usuario> lista =new ArrayList<>();
        cr = sql.rawQuery("select * from t_usuarios",null);
        if(cr != null && cr.moveToFirst()){
            do{
                Usuario u = new Usuario();
                u.setIdUsuario(cr.getInt(0));
                u.setUsername(cr.getString(1));
                u.setClave(cr.getString(2));
                u.setNombre(cr.getString(3));
                u.setApellidoPaterno(cr.getString(4));
                u.setApellidoMaterno(cr.getString(5));
                u.setFechaNacimiento(cr.getString(6));
                lista.add(u);
            }while(cr.moveToNext());
            cr.close();
        }
        return lista;
    }
    public int login(String u, String p){
        int a = 0;
        cr = sql.rawQuery("select * from t_usuarios",null);
        if(cr!=null && cr.moveToFirst()){
            do{
                if(cr.getString(1).equals(u) && cr.getString(2).equals(p)){
                    a++;
                }
            }while(cr.moveToNext());
            cr.close();
        }
        return a;
    }
    public Usuario getUsuario(String u,String p){
        lista=selectUsuario();
        for(Usuario us:lista){
            if(us.getUsername().equals(u) && us.getClave().equals(p)){
                return us;
            }
        }
        return null;
    }
    public Usuario getUsuarioById(int user){
        lista=selectUsuario();
        for(Usuario us:lista){
            if(us.getIdUsuario() == user){
                return us;
            }
        }
        return null;
    }
    public boolean updateUsuario(Usuario u){
        cv = new ContentValues();
        cv.put("idUsuario",u.getIdUsuario());
        //cv.put("username", u.getUsername());
        cv.put("nombre", u.getNombre());
        cv.put("apellidoPaterno", u.getApellidoPaterno());
        cv.put("apellidoMaterno", u.getApellidoMaterno());
        cv.put("fechaNacimiento", u.getFechaNacimiento());
        return (sql.update("t_usuarios",cv,"idUsuario="+u.getIdUsuario(),null)>0);
    }

    public void eliminarUsuario(Integer id){
        sql.execSQL("DELETE FROM t_usuarios WHERE idUsuario="+id);
    }

    public ArrayList<Usuario> datosUsuario(Integer id){
        cr = sql.rawQuery("SELECT * FROM t_usuarios WHERE idUsuario="+id,null);
        ArrayList<Usuario> lista = null;
        if(cr != null && cr.moveToFirst()){
            lista =new ArrayList<>();
            do{
                Usuario u = new Usuario();
                u.setUsername(cr.getString(1));
                u.setNombre(cr.getString(3));
                u.setApellidoPaterno(cr.getString(4));
                u.setApellidoMaterno(cr.getString(5));
                u.setFechaNacimiento(cr.getString(6));
                lista.add(u);
            }while(cr.moveToNext());
        }
        if (cr != null) {
            cr.close();
        }
        return lista;
    }

    public String plan(Integer id){
        cr = sql.rawQuery("SELECT vidaSaludable from t_usuarios WHERE idUsuario="+id, null);
        if(cr.moveToFirst()){
            return cr.getString(0);
        }
        return "";
    }

    public void compraMensual(int id, String tipo, String fecha) {
        cv = new ContentValues();
        cv.put("idUsuario",id);
        cv.put("vidaSaludable", tipo);
        cv.put("fechaVencimiento", fecha);
        sql.update("t_usuarios",cv,"idUsuario="+id,null);
    }

    public String getFechaVencimiento(int id) {
        cr = sql.rawQuery("SELECT fechaVencimiento, VidaSaludable from t_usuarios WHERE idUsuario="+id, null);
        if(cr.moveToFirst()){

            if(cr.getString(1).equals("Premium")){
                return cr.getString(0);
            }
        }
        return "";
    }

    public void asignarNutriologo(int nutriologo, int usuario) {
        if(!tieneNutriologo(usuario)){
            cv = new ContentValues();
            cv.put("idNutriologo", nutriologo);
            cv.put("idUsuario", usuario);
            sql.insert("t_vidaSaludable",null,cv);
        }
    }

    private boolean tieneNutriologo(int usuario){
        cr = sql.rawQuery("SELECT * from t_vidaSaludable WHERE idUsuario="+usuario, null);
        if(cr.moveToFirst()){
            return true;
        }
        return false;
    }

    public String getNutriologo(int id) {
        cr = sql.rawQuery("SELECT idNutriologo from t_vidaSaludable WHERE idUsuario="+id, null);
        if(cr.moveToFirst()){
            return cr.getString(0);
        }
        return "";
    }
}
