package com.example.easymeal.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.easymeal.cl.model.bd.Usuario;

import java.util.ArrayList;

public class daoUsuario {
    Context c;
    Usuario u;
    ArrayList<Usuario> lista;
    SQLiteDatabase sql;
    SQLiteOpenHelper oh;
    String bd="easymeal.db";
    //String tabla="create table if not exists usuario(id integer primary key autoincrement, usuario text, pass text, nombre text, ap text)";
    String tabla = "create table if not exists t_usuarios(idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(20) NOT NULL,clave VARCHAR(20) NOT NULL,nombre VARCHAR(20) NOT NULL,apellidoPaterno VARCHAR(20) NOT NULL,apellidoMaterno VARCHAR(20),fechaNacimiento DATE NOT NULL)";

    public daoUsuario(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(bd,c.MODE_PRIVATE,null);

        sql.execSQL(tabla);
        u=new Usuario();
    }
    public boolean insertUsuario(Usuario u){
        if(buscar(u.getUsername())==0){
            ContentValues cv = new ContentValues();
            cv.put("username", u.getUsername());
            cv.put("clave", u.getClave());
            cv.put("nombre", u.getNombre());
            cv.put("apellidoPaterno", u.getApellidoPaterno());
            cv.put("apellidoMaterno", u.getApellidoMaterno());
            cv.put("fechaNacimiento", u.getFechaNacimiento());
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
        ArrayList<Usuario> lista =new ArrayList<Usuario>();
        lista.clear();
        Cursor cr = sql.rawQuery("select * from t_usuarios",null);
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
        }
        return lista;
    }
    public int login(String u, String p){
        int a = 0;
        Cursor cr = sql.rawQuery("select * from t_usuarios",null);
        if(cr!=null && cr.moveToFirst()){
            do{
                if(cr.getString(1).equals(u) && cr.getString(2).equals(p)){
                    a++;
                }
            }while(cr.moveToNext());
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
        ContentValues cv = new ContentValues();
        cv.put("idUsuario",u.getIdUsuario());
        //cv.put("username", u.getUsername());
        cv.put("nombre", u.getNombre());
        cv.put("apellidoPaterno", u.getApellidoPaterno());
        cv.put("apellidoMaterno", u.getApellidoMaterno());
        cv.put("fechaNacimiento", u.getFechaNacimiento());
        return (sql.update("t_usuarios",cv,"idUsuario="+u.getIdUsuario(),null)>0);
    }

    public  boolean deleteUsuario(){

        return (sql.delete("t_usuarios","idUsuario="+1,null)>0);
    }

   /* public void deleteUser(String courseName){

        // on below line we are creating
        // a variable to write our database.
        sql = daoUsuario.this.oh.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        sql.delete("t_usuarios", "username=?", new String[]{courseName});
        sql.close();
    }*/
}
