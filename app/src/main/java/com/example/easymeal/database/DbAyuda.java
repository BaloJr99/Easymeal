package com.example.easymeal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbAyuda extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 16;
    private static final String DATABASE_NAME = "easymeal.db";
    private static final String TABLE_PREPARACIONES = "t_preparaciones";
    private static final String TABLE_USUARIOS = "t_usuarios";
    private static final String TABLE_RECETAPREPARACION = "t_recetaPreparacion";
    private static final String TABLE_RECETA = "t_receta";
    private static final String TABLE_INGREDIENTE = "t_ingrediente";
    private static final String TABLE_INGREDIENTERECETA = "t_ingredienteReceta";
    private static final String TABLE_COMPRAS = "t_compras";

    public DbAyuda(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_PREPARACIONES+"("+
                "idPreparacion INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "tipoComida VARCHAR(45) NOT NULL,"+
                "semanaPreparacion VARCHAR(45) NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_USUARIOS+"("+
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "username VARCHAR(20) NOT NULL,"+
                "clave VARCHAR(20) NOT NULL,"+
                "nombre VARCHAR(20) NOT NULL,"+
                "apellidoPaterno VARCHAR(20) NOT NULL,"+
                "apellidoMaterno VARCHAR(20),"+
                "fechaNacimiento DATE NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_RECETA+"("+
                "idReceta INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre VARCHAR(45) NOT NULL UNIQUE,"+
                "pasos VARCHAR(60))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_INGREDIENTE+"("+
                "idIngrediente INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "descripcion VARCHAR(45) NOT NULL,"+
                "unidadDeMedida VARCHAR(45),"+
                "cantidad NUMERIC NOT NULL,"+
                "fechaDeCaducidad DATE, "+
                "mandado INTEGER NOT NULL, "+
                "cantidadAcomprar FLOAT NOT NULL, "+
                "proveedor VARCHAR(45) NOT NULL,"+
                "imagen BLOB)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_RECETAPREPARACION+"("+
                "idRecetaPreparacion INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "idPreparacion INTEGER NOT NULL,"+
                "idReceta INTEGER NOT NULL,"+
                "cantidadAPreparar INTEGER NOT NULL,"+
                "FOREIGN KEY(idPreparacion) REFERENCES t_preparaciones(idPreparacion),"+
                "FOREIGN KEY(idReceta) REFERENCES t_receta(idReceta))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_INGREDIENTERECETA+"("+
                "idIngredientesReceta INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "cantidad NUMERIC NOT NULL,"+
                "idReceta INTEGER NOT NULL,"+
                "idIngrediente INTEGER NOT NULL,"+
                "FOREIGN KEY(idReceta) REFERENCES t_receta(idReceta),"+
                "FOREIGN KEY(idIngrediente) REFERENCES t_ingrediente(idIngrediente))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_COMPRAS+"("+
                "idCompras INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "fechaCompra DATE NOT NULL,"+
                "importeGastado NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS t_alimento");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PREPARACIONES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_RECETAPREPARACION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS t_horario");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_RECETA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_INGREDIENTE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS t_producto");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS t_productoIngrediente");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_INGREDIENTERECETA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS t_lista");
        onCreate(sqLiteDatabase);
    }

}
