package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class Recetas extends AppCompatActivity{
    DbAyuda db;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    //Inicializamos variable
    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);

        //Asignamos Variable
        dl = findViewById(R.id.drawer_recetas);
         db = new DbAyuda(this);

         listItem = new ArrayList<>();

         viewData();
        
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

    private void viewData() {
        Cursor cursor = db.selectReceta();
        if(cursor.getCount()==0){
            Toast.makeText(this,"Sin datos",Toast.LENGTH_SHORT).show();
        }else{
          while(cursor.moveToNext()){
              listItem.add(cursor.getString(1));
          }
          adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listItem);
        }
    }
}
