package com.example.easymeal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.cl.model.dao.daoUsuario;

public class MenuUsuario extends AppCompatActivity {

    DrawerLayout dl;
    String username;
    TextView idusuario;
    Button editar,eliminar;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu_usuario);
        dl = findViewById(R.id.drawer_usuario);
        idusuario = (TextView) findViewById(R.id.idUsuario);
        editar = (Button) findViewById(R.id.btneditar);
        eliminar = (Button) findViewById(R.id.btneliminar);
        Bundle b=getIntent().getExtras();
        username=b.getString("username");
        idusuario.setText(username);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuUsuario.this,EditarU.class);
                i.putExtra("Username",username);
                startActivity(i);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b= new AlertDialog.Builder(MenuUsuario.this);
                b.setMessage("¿Seguro que desea eliminar la cuenta?");
                b.setCancelable(false);
                b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dao.deleteUsuario(username)) {
                            Toast.makeText(MenuUsuario.this,"Cuenta Eliminada",Toast.LENGTH_LONG).show();
                            Intent i2 =   new Intent(MenuUsuario.this, MainActivity.class);
                            startActivity(i2);
                            finish();
                        }else{
                            Toast.makeText(MenuUsuario.this,"ERROR: No se pudo eliminar la cuenta",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                b.show();
            }
        });
    }
    public void ClickMenu(View v){
        //Abrimos Drawer
        Menu.openDrawer(dl);
        /*int sum=0;
        Menu.openDrawer(dl);
        sum++;
        if(sum == 1) {
            Intent i = new Intent(this, Menu.class);
            i.putExtra("Username", username);
            startActivity(i);
        }*/
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