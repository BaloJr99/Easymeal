package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import com.example.easymeal.cl.model.bd.Receta;
import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class Recetas extends AppCompatActivity{
    DbAyuda db;
    ArrayAdapter adapter;
    ListView listaRecetas;
    ArrayList<String> infoList;
    ArrayList<Receta> recetasList;



    //Inicializamos variable
    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_recetas);
        dl = findViewById(R.id.drawer_recetas);
        db = new DbAyuda(getApplicationContext());
        listaRecetas = (ListView) findViewById(R.id.listaRecetas);
        poblar();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,infoList);
        listaRecetas.setAdapter(adapter);

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
