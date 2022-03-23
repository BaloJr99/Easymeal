package com.example.easymeal.cl.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {
    public Conexion(Context context , String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS t_usuarios(idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(20) NOT NULL,clave VARCHAR(20) NOT NULL,nombre VARCHAR(20) NOT NULL,apellidoPaterno VARCHAR(20) NOT NULL,apellidoMaterno VARCHAR(20),fechaNacimiento DATE NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
