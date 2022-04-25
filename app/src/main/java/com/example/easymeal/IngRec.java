package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.easymeal.cl.model.bd.IngredienteReceta;
import com.example.easymeal.cl.model.dao.RecetaIngredienteDao;

import java.util.ArrayList;

public class IngRec extends AppCompatActivity {
    int idReceta,idIngredienteReceta,idIngrediente;
    RecetaIngredienteDao recetaIngredienteDao;
    ListView listaIng;
    EditText nombreIng,cantidad;
    Button modificar, eliminar;
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
        recetaIngredienteDao = new RecetaIngredienteDao(this);
        IngRecetaList = recetaIngredienteDao.listaIngRec(idReceta);
        crearLista();
    }
    private void crearLista(){ //Metodo para poblar la lista
        recetaIngredienteDao = new RecetaIngredienteDao(this);
        infoList = recetaIngredienteDao.listaIng(IngRecetaList);
    }
    private void modificarIng() {
        //Metodo que modifica el ingrediente de la receta
        recetaIngredienteDao = new RecetaIngredienteDao(this);
        recetaIngredienteDao.modificarIng(new IngredienteReceta(idIngredienteReceta, idIngrediente, idReceta, Float.valueOf(cantidad.getText().toString())));
    }

    private void eliminarIng(){
        recetaIngredienteDao = new RecetaIngredienteDao(this);
        recetaIngredienteDao.eliminarIng(idIngredienteReceta);
    }
}