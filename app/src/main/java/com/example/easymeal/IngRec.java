package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.IngredienteReceta;
import com.example.easymeal.cl.model.bd.Receta;
import com.example.easymeal.cl.model.dao.Conexion;
import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class IngRec extends AppCompatActivity {
    int idReceta,idIngredienteReceta,idIngrediente;
    ListView listaIng;
    EditText nombreIng,cantidad;
    Button modificar, eliminar;
    DbAyuda db;
    Conexion co= new Conexion(this,"easymeal.db",null,15);
    ArrayAdapter adapter;
    ArrayList<IngredienteReceta> IngRecetaList;
    ArrayList<String> infoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ing_rec);

        Bundle b=getIntent().getExtras();
        idReceta=b.getInt("idReceta");
        listaIng = (ListView) findViewById(R.id.listIngredientes);
        nombreIng = (EditText) findViewById(R.id.txtNombreIng);
        cantidad = (EditText) findViewById(R.id.txtCantidad);
        modificar = (Button) findViewById(R.id.btnModificaIng);
        eliminar = (Button) findViewById(R.id.btnEliminarIng);
        poblar();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,infoList);
        listaIng.setAdapter(adapter);

        listaIng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idIngredienteReceta = IngRecetaList.get(i).getIdIngredientesReceta();
                idIngrediente = IngRecetaList.get(i).getIdIngrediente();
                nombreIng.setText(listaIng.getItemAtPosition(i).toString());
                //System.out.println(listaIng.getAdapter());
                cantidad.setText(IngRecetaList.get(i).getCantidad().toString());
                modificar.setEnabled(true);
                eliminar.setEnabled(true);
            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarIng();
                Intent i = new Intent(IngRec.this,Recetas.class);
                startActivity(i);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarIng();
                Intent i = new Intent(IngRec.this,Recetas.class);
                startActivity(i);
            }
        });
    }
    private void poblar(){  //Metodo para poblar el array objeto
        SQLiteDatabase bd = co.getWritableDatabase();
        IngredienteReceta IngReceta = null;
        IngRecetaList = new ArrayList<IngredienteReceta>();
        Cursor cursor = bd.rawQuery("SELECT * FROM "+"t_ingredienteReceta WHERE idReceta="+idReceta,null);

        while(cursor.moveToNext()){
            IngReceta = new IngredienteReceta();
            IngReceta.setIdIngredientesReceta(cursor.getInt(0));
            IngReceta.setCantidad(cursor.getFloat(1));
            IngReceta.setIdReceta(cursor.getInt(2));
            IngReceta.setIdIngrediente(cursor.getInt(3));

            IngRecetaList.add(IngReceta);
        }
        crearLista();
    }
    private void crearLista(){ //Metodo para poblar la lista
        infoList = new ArrayList<String>();
        SQLiteDatabase bd = co.getWritableDatabase();
        Ingrediente Ing = null;

        for(int i=0;i<IngRecetaList.size();i++){
            Cursor cursor = bd.rawQuery("SELECT * FROM "+"t_ingrediente WHERE idIngrediente="+IngRecetaList.get(i).getIdIngrediente(),null);
            while(cursor.moveToNext()){
                infoList.add(String.valueOf(cursor.getString(1)));
            }
        }
    }
    private boolean modificarIng() {
        //Metodo que modifica el ingrediente de la receta
        SQLiteDatabase bd = co.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("idIngredientesReceta",idIngredienteReceta);
        cv.put("cantidad", Float.valueOf(cantidad.getText().toString()));
        cv.put("idReceta",idReceta);
        cv.put("idIngrediente",idIngrediente);
        return (bd.update("t_ingredienteReceta",cv,"idIngredientesReceta="+idIngredienteReceta,null)>0);
    }
    private boolean eliminarIng(){
        SQLiteDatabase bd = co.getWritableDatabase();
        return (bd.delete("t_ingredienteReceta","idIngredientesReceta="+idIngredienteReceta,null)>0);
    }
}