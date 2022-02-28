package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class Tablero extends AppCompatActivity {

    //Inicializamos variable
    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);

        //Asignamos Variable
        dl = findViewById(R.id.drawer_lay);
    }

    public void ClickMenu(View v){
        //Abrimos Drawer
        Menu.openDrawer(dl);
    }

    public void ClickLogo(View v){
        //Cerramos drawer
        Menu.closeDrawer(dl);
    }

    public void ClickHome(View v){
        //Redireccionamos activity a inicio
        Menu.redirectActivity(this, Menu.class);
    }

    public void ClickDashboard(View v){
        //Recreamos actividad
        recreate();
    }

    public void AcercaDeNosotros(View v){
        //Redireccionamos actividad a acerca de nosotros
        Menu.redirectActivity(this, AcercaNosotros.class);
    }

    public void ClickLogout(View v){
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