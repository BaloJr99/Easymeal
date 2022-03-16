package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.easymeal.cl.model.bd.Receta;

import com.example.easymeal.cl.model.dao.RecetaDao;

import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class Recetas extends AppCompatActivity{
    DbAyuda db;
    ArrayAdapter adapter;
    ListView listaRecetas;
    ArrayList<String> infoList;
    ArrayList<Receta> recetasList;
    EditText nom,pasos;
    Button insertar,editar,buscar;
    RecetaDao dao;
    ArrayList<Receta> busqueda;



    //Inicializamos variable
    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recetas);
        dl = findViewById(R.id.drawer_recetas);
        db = new DbAyuda(getApplicationContext());
        nom = (EditText) findViewById(R.id.fieldNombre);
        pasos = (EditText) findViewById(R.id.mFieldPasos);
        insertar = (Button) findViewById(R.id.btnAgregar);
        editar = (Button) findViewById(R.id.btnEdit);
        buscar = (Button) findViewById(R.id.btnBuscar);
        dao = new RecetaDao(this);
        listaRecetas = (ListView) findViewById(R.id.listaRecetas);
        poblar();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,infoList);
        listaRecetas.setAdapter(adapter);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receta c = new Receta();
                c.setNombre(nom.getText().toString());
                c.setPasos(pasos.getText().toString());
                if(!c.isNull()){
                    Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
                }else if(dao.insertarReceta(c)){
                    Toast.makeText(Recetas.this,"Registro Exitoso",Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Recetas.this,MainActivity.class);
                    startActivity(i2);
                    poblar();
                }else{
                    Toast.makeText(Recetas.this,"Receta ya registrada",Toast.LENGTH_LONG).show();
                }
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receta c = new Receta();
                c.setNombre(nom.getText().toString());
                c.setPasos(pasos.getText().toString());
                if(!c.isNull()){
                    Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
                }else if(dao.updateReceta(c)){
                    Toast.makeText(Recetas.this,"Registro Exitoso",Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Recetas.this,MainActivity.class);
                    startActivity(i2);
                    poblar();
                }else{
                    Toast.makeText(Recetas.this,"Receta ya registrada",Toast.LENGTH_LONG).show();
                }
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receta c = new Receta();
                if(nom.getText().toString()==""){
                    Toast.makeText(Recetas.this,"ERROR: CAMPO DE NOMBRE VACIO",Toast.LENGTH_LONG).show();
                }else{
                    busqueda = dao.selectReceta(nom.getText().toString());
                    pasos.setText(busqueda.get(0).getPasos());
                    Intent i2 = new Intent(Recetas.this,MainActivity.class);
                    startActivity(i2);
                    poblar();
                }
            }
        });
    }
    private void poblar(){  //Metodo para poblar el array objeto
        SQLiteDatabase bd = db.getReadableDatabase();
        Receta receta = null;
        recetasList = new ArrayList<Receta>();
        Cursor cursor = bd.rawQuery("SELECT * FROM "+"t_receta",null);

        while(cursor.moveToNext()){
            receta = new Receta();
            receta.setIdReceta(cursor.getInt(0));
            receta.setPasos(cursor.getString(1));

            recetasList.add(receta);
        }
        crearLista();
    }
    private void crearLista(){ //Metodo para poblar la lista
         infoList = new ArrayList<String>();
         for(int i=0;i<recetasList.size();i++){
             infoList.add(String.valueOf(recetasList.get(i).getIdReceta())+"/n Pasos:"+recetasList.get(i).getPasos());
        }
    }

    public void ClickMenu(View v){
        //Abrimos Drawer
        openDrawer(dl);
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class);
    }

    public static void openDrawer(DrawerLayout drawer) {
        //Abrimos el drawer layout
        drawer.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View v){
        //Cerramos drawer
        Menu.closeDrawer(dl);
    }

    public void ClickInicio(View v){
        //Redireccionamos activity a inicio
        Menu.redirectActivity(this, Menu.class);
    }

    public void ClickRecetas(View v){
        //Recreamos actividad
        recreate();
    }

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class);
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class);
    }

    public void ClickAcercaDe(View v){
        //Redireccionamos actividad a acerca de nosotros
        Menu.redirectActivity(this, AcercaNosotros.class);
    }

    public void ClickSalir(View v){
        //Cerramos app
        Menu.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Cerramos Drawer
        Menu.closeDrawer(dl);
    }


}
