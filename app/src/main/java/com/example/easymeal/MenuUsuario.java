package com.example.easymeal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.cl.model.dao.Conexion;
import com.example.easymeal.cl.model.dao.daoUsuario;
import com.example.easymeal.database.DbAyuda;

public class MenuUsuario extends AppCompatActivity {
    Conexion c= new Conexion(this,"easymeal.db",null,11);
    DrawerLayout dl;
    String username;
    int id = 0;
    TextView idusuario;
    Button editar,eliminar,ver,salir;
    daoUsuario dao;
    //DbAyuda db = new DbAyuda(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_menu_usuario);
        dl = findViewById(R.id.drawer_usuario);
        editar = (Button) findViewById(R.id.btneditar);
        eliminar = (Button) findViewById(R.id.btneliminar);
        salir = (Button) findViewById(R.id.btnsalir);
        ver = (Button) findViewById(R.id.ver);
        Bundle b=getIntent().getExtras();
        id=b.getInt("idUsuario");

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuUsuario.this,EditarU.class);
                i.putExtra("idUsuario",id);
                startActivity(i);
            }
        });
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuUsuario.this,MostrarU.class);
                i.putExtra("idUsuario",id);
                startActivity(i);
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuUsuario.this,Menu.class);
                i.putExtra("idUsuario",id);
                startActivity(i);
            }
        });
    }
    public void eliminarRegistro(View v){
        SQLiteDatabase op=c.getWritableDatabase();
        AlertDialog.Builder b= new AlertDialog.Builder(MenuUsuario.this);
        b.setMessage("¿Seguro que desea eliminar la cuenta?");
        b.setCancelable(false);
        b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    op.execSQL("DELETE FROM t_usuarios WHERE idUsuario="+id);
                    Toast.makeText(MenuUsuario.this,"Cuenta Eliminada",Toast.LENGTH_LONG).show();
                    Intent i2 =   new Intent(MenuUsuario.this, MainActivity.class);
                    startActivity(i2);
                    finish();
            }
        });
        b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        b.show();
        //op.delete("alumno","id="+id,null);
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