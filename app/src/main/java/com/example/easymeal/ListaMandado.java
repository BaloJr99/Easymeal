package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListaMandado extends AppCompatActivity {


    //Inicializamo variable
    DrawerLayout dl;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mandado);

        //Asignamos variable
        dl = findViewById(R.id.drawer_listamandado);
        btnAgregar = findViewById(R.id.agregarlista);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirAgregarLista = new Intent(ListaMandado.this, AgregarLista.class);
                startActivity(abrirAgregarLista);
            }
        });
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

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        recreate();
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class);
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