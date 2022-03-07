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
    private static final String TABLE_RECETAPREPARACION = "t_recetaPreparacion";
    private static final String TABLE_HORARIO = "t_horario";
    private static final String TABLE_RECETA = "t_receta";
    private static final String TABLE_INGREDIENTE = "t_ingrediente";
    private static final String TABLE_PRODUCTO = "t_producto";
    private static final String TABLE_PRODUCTOINGREDIENTE = "t_productoIngrediente";
    private static final String TABLE_INGREDIENTERECETA = "t_ingredienteReceta";

    public DbAyuda(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_PREPARACIONES+"("+
                "idPreparacion INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "tipoComida VARCHAR(45) NOT NULL,"+
                "fechaPreparacion DATE NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_USUARIOS+"("+
                "username VARCHAR(20) NOT NULL,"+
                "clave VARCHAR(20) NOT NULL,"+
                "nombre VARCHAR(20) NOT NULL,"+
                "apellidoPaterno VARCHAR(20) NOT NULL,"+
                "apellidoMaterno VARCHAR(20),"+
                "fechaNacimiento DATE NOT NULL,"+
                "PRIMARY KEY('username'))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_HORARIO+"("+
                "idHorario INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "comidas INTEGER NOT NULL,"+
                "fecha DATE NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_RECETA+"("+
                "idReceta INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "pasos VARCHAR(45))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_INGREDIENTE+"("+
                "idIngrediente INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "descripcion VARCHAR(45),"+
                "unidadDeMedida VARCHAR(45) NOT NULL,"+
                "cantidad NUMERIC NOT NULL,"+
                "fechaDeCaducidad DATE NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_PRODUCTO+"("+
                "idProducto INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "proveedor VARCHAR(45) NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_PRODUCTOINGREDIENTE+"("+
                "idProductoIngrediente INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "idIngrediente INTEGER NOT NULL,"+
                "idProducto INTEGER NOT NULL,"+
                "FOREIGN KEY(idIngrediente) REFERENCES t_ingrediente(idIngrediente),"+
                "FOREIGN KEY(idProducto) REFERENCES t_producto(idProducto))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_RECETAPREPARACION+"("+
                "idRecetaPreparacion INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "idPreparacion INTEGER NOT NULL,"+
                "idReceta INTEGER NOT NULL,"+
                "FOREIGN KEY(idPreparacion) REFERENCES t_preparaciones(idPreparacion),"+
                "FOREIGN KEY(idReceta) REFERENCES t_receta(idReceta))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_INGREDIENTERECETA+"("+
                "idIngredientesReceta INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "cantidad NUMERIC NOT NULL,"+
                "idReceta INTEGER NOT NULL,"+
                "idIngrediente INTEGER NOT NULL,"+
                "FOREIGN KEY(idReceta) REFERENCES t_receta(idReceta),"+
                "FOREIGN KEY(idIngrediente) REFERENCES t_ingrediente(idIngrediente))");
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
