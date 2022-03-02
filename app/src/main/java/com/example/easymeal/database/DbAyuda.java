package com.example.easymeal.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbAyuda extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "easymeal.db";
    private static final String TABLE_PREPARACIONES = "t_preparaciones";
    private static final String TABLE_USUARIOS = "t_usuarios";
    private static final String TABLE_ALIMENTO = "t_alimento";
    private static final String TABLE_HORARIO = "t_horario";
    private static final String TABLE_RECETA = "t_receta";
    private static final String TABLE_INGREDIENTE = "t_ingrediente";
    private static final String TABLE_PRODUCTO = "t_producto";
    private static final String TABLE_LISTA = "t_lista";

    public DbAyuda(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_PREPARACIONES+"("+
                "idPreparaciones INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombreComida VARCHAR(30) NOT NULL,"+
                "tipoComida VARCHAR(45) NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_USUARIOS+"("+
                "username VARCHAR(20) NOT NULL,"+
                "clave VARCHAR(20) NOT NULL,"+
                "nombre VARCHAR(20) NOT NULL,"+
                "apellidoPaterno VARCHAR(20) NOT NULL,"+
                "apellidoMaterno VARCHAR(20),"+
                "fechaNacimiento DATE NOT NULL,"+
                "PRIMARY KEY('username'))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_ALIMENTO+"("+
                "idAlimento INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre VARCHAR(20),"+
                "cantidad NUMERIC NOT NULL,"+
                "idPreparaciones INTEGER NOT NULL,"+
                "FOREIGN KEY(idPreparaciones) REFERENCES t_preparaciones(idPreparaciones))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_HORARIO+"("+
                "idHorario INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "comidas INTEGER NOT NULL,"+
                "fecha DATE NOT NULL,"+
                "username VARCHAR(20) NOT NULL,"+
                "FOREIGN KEY(username) REFERENCES t_horario(username))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_RECETA+"("+
                "idReceta INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "pasos VARCHAR(100),"+
                "idPreparaciones INTEGER NOT NULL,"+
                "FOREIGN KEY(idPreparaciones) REFERENCES t_preparaciones(idPreparaciones))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_INGREDIENTE+"("+
                "idIngrediente INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "unidadDeMedida VARCHAR(20) NOT NULL,"+
                "tipo VARCHAR(20),"+
                "idAlimento INTEGER NOT NULL,"+
                "FOREIGN KEY(idAlimento) REFERENCES t_alimento(idAlimento))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_PRODUCTO+"("+
                "idProducto INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombreProducto VARCHAR(20) NOT NULL,"+
                "cantidadAComprar NUMERIC NOT NULL,"+
                "idIngrediente INTEGER NOT NULL,"+
                "FOREIGN KEY(idIngrediente) REFERENCES t_ingrediente(idIngrediente))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_LISTA+"("+
                "idLista INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "fecha DATE NOT NULL,"+
                "producto VARCHAR(20) NOT NULL,"+
                "cantidad NUMERIC NOT NULL,"+
                "idProducto INTEGER NOT NULL,"+
                "FOREIGN KEY(idProducto) REFERENCES t_producto(idProducto))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_PREPARACIONES);
        onCreate(sqLiteDatabase);
    }

    public Cursor selectReceta(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_RECETA;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }
}
