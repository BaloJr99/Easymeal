package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class MenuUsuario extends AppCompatActivity {

    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);
        dl = findViewById(R.id.drawer_usuario);
    }
    public void ClickMenu(View v){
        //Abrimos Drawer
        Menu.openDrawer(dl);
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
        //Redireccionamos actividad a tablero
        Menu.redirectActivity(this, Recetas.class);
    }

    public void ClickAcercaDe(View v){
        //Recreamos actividad
        Menu.redirectActivity(this, AcercaNosotros.class);
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class);
    }

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class);
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        recreate();
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