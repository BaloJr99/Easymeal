package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    //Inicializamos variables
    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //Asignamos variable
        dl = findViewById(R.id.drawer_menu);
    }

    public void ClickMenu(View view){
        //Abrimos drawer
        openDrawer(dl);
    }

    public static void openDrawer(DrawerLayout drawer) {
        //Abrimos el drawer layout
        drawer.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //Cerramos el drawer
        closeDrawer(dl);
    }

    public static void closeDrawer(DrawerLayout drawer) {
        //Cerramos el layout si esta abierto
        if(drawer.isDrawerOpen(GravityCompat.START)){
            //Cuando este abierto cerramos el drawer
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickInicio(View view){
        //Recreamos actividad
        recreate();
    }

    public void ClickRecetas(View view){
        //Redireccionamos actividad a dashboard
        redirectActivity(this, Recetas.class);
    }

    public void ClickAcercaDe(View view){
        //Redireccionamos actividad a acerca de nosotros
        redirectActivity(this, AcercaNosotros.class);
    }

    public void ClickSalir(View view){
        //Cerramos app
        logout(this);
    }

    public static void logout(Activity activity) {
        //Inicializamos mensaje de alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Establecemos el titulo
        builder.setTitle("Logout");
        //Establecemos el mensaje
        builder.setMessage("Estas seguro de que desea salir?");
        //Boton de seguro
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Finalizamos actividad
                activity.finishAffinity();
                //Cerramos app
                System.exit(0);
            }
        });

        //Boton negativo

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Hacemos caso omiso al dialogo
                dialogInterface.dismiss();
            }
        });

        //Mostramos el dialogo
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //Inicializamos el intento
        Intent intent = new Intent(activity, aClass);
        //Creamos bandera
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Empezamos activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Cerramos drawer
        closeDrawer(dl);
    }
}