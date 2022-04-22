package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.easymeal.cl.model.bd.IngredienteReceta;
import com.example.easymeal.cl.model.dao.Conexion;

import java.util.ArrayList;

public class IngRec extends AppCompatActivity {
    int idReceta,idIngredienteReceta,idIngrediente;
    ListView listaIng;
    EditText nombreIng,cantidad;
    Button modificar, eliminar;
    Conexion co= new Conexion(this,"easymeal.db",null,16);
    ArrayAdapter adapter;
    ArrayList<IngredienteReceta> IngRecetaList;
    ArrayList<String> infoList;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ing_rec);

        Bundle b=getIntent().getExtras();
        idReceta=b.getInt("idReceta");
        listaIng = findViewById(R.id.listIngredientes);
        nombreIng = findViewById(R.id.txtNombreIng);
        cantidad = findViewById(R.id.txtCantidad);
        modificar = findViewById(R.id.btnModificaIng);
        eliminar = findViewById(R.id.btnEliminarIng);
        poblar();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,infoList);
        listaIng.setAdapter(adapter);

        listaIng.setOnItemClickListener((adapterView, view, i, l) -> {
            idIngredienteReceta = IngRecetaList.get(i).getIdIngredientesReceta();
            idIngrediente = IngRecetaList.get(i).getIdIngrediente();
            nombreIng.setText(listaIng.getItemAtPosition(i).toString());
            cantidad.setText(IngRecetaList.get(i).getCantidad().toString());
            modificar.setEnabled(true);
            eliminar.setEnabled(true);
        });
        modificar.setOnClickListener(view -> {
            modificarIng();
            Intent i = new Intent(IngRec.this,Recetas.class);
            startActivity(i);
        });
        eliminar.setOnClickListener(view -> {
            eliminarIng();
            Intent i = new Intent(IngRec.this,Recetas.class);
            startActivity(i);
        });
    }
    private void poblar(){  //Metodo para poblar el array objeto
        SQLiteDatabase bd = co.getWritableDatabase();
        IngredienteReceta IngReceta;
        IngRecetaList = new ArrayList<>();
        Cursor cursor = bd.rawQuery("SELECT * FROM "+"t_ingredienteReceta WHERE idReceta="+idReceta,null);

        while(cursor.moveToNext()){
            IngReceta = new IngredienteReceta();
            IngReceta.setIdIngredientesReceta(cursor.getInt(0));
            IngReceta.setCantidad(cursor.getFloat(1));
            IngReceta.setIdReceta(cursor.getInt(2));
            IngReceta.setIdIngrediente(cursor.getInt(3));

            IngRecetaList.add(IngReceta);
        }
        cursor.close();
        crearLista();
    }
    private void crearLista(){ //Metodo para poblar la lista
        infoList = new ArrayList<>();
        SQLiteDatabase bd = co.getWritableDatabase();

        for(int i=0;i<IngRecetaList.size();i++){
            Cursor cursor = bd.rawQuery("SELECT * FROM "+"t_ingrediente WHERE idIngrediente="+IngRecetaList.get(i).getIdIngrediente(),null);
            while(cursor.moveToNext()){
                infoList.add(String.valueOf(cursor.getString(1)));
            }
            cursor.close();
        }
    }
    private void modificarIng() {
        //Metodo que modifica el ingrediente de la receta
        SQLiteDatabase bd = co.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("idIngredientesReceta",idIngredienteReceta);
        cv.put("cantidad", Float.valueOf(cantidad.getText().toString()));
        cv.put("idReceta",idReceta);
        cv.put("idIngrediente",idIngrediente);
        bd.update("t_ingredienteReceta", cv, "idIngredientesReceta=" + idIngredienteReceta, null);
    }

    private void eliminarIng(){
        SQLiteDatabase bd = co.getWritableDatabase();
        bd.delete("t_ingredienteReceta", "idIngredientesReceta=" + idIngredienteReceta, null);
    }
}