package com.example.easymeal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.easymeal.cl.model.dao.Conexion;

public class MenuUsuario extends AppCompatActivity {
    Conexion c= new Conexion(this,"easymeal.db",null,16);
    DrawerLayout dl;
    int id = 0;
    Button editar,eliminar,ver,salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu_usuario);
        dl = findViewById(R.id.drawer_usuario);
        editar = findViewById(R.id.btneditar);
        eliminar = findViewById(R.id.btneliminar);
        salir = findViewById(R.id.btnsalir);
        ver = findViewById(R.id.ver);
        Bundle b=getIntent().getExtras();
        id=b.getInt("idUsuario");

        editar.setOnClickListener(view -> {
            Intent i = new Intent(MenuUsuario.this,EditarU.class);
            i.putExtra("idUsuario",id);
            startActivity(i);
        });
        ver.setOnClickListener(view -> {
            Intent i = new Intent(MenuUsuario.this,MostrarU.class);
            i.putExtra("idUsuario",id);
            startActivity(i);
        });
        salir.setOnClickListener(view -> {
            Intent i = new Intent(MenuUsuario.this,Menu.class);
            i.putExtra("idUsuario",id);
            startActivity(i);
        });
    }
    public void eliminarRegistro(View v){
        SQLiteDatabase op=c.getWritableDatabase();
        AlertDialog.Builder b= new AlertDialog.Builder(MenuUsuario.this);
        b.setMessage("¿Seguro que desea eliminar la cuenta?");
        b.setCancelable(false);
        b.setPositiveButton("SI", (dialogInterface, i) -> {
                op.execSQL("DELETE FROM t_usuarios WHERE idUsuario="+id);
                Toast.makeText(MenuUsuario.this,"Cuenta Eliminada",Toast.LENGTH_LONG).show();
                Intent i2 =   new Intent(MenuUsuario.this, MainActivity.class);
                startActivity(i2);
                finish();
        });
        b.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.cancel());
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
        Menu.redirectActivity(this, Menu.class, "");
    }

    public void ClickRecetas(View v){
        //Redireccionamos actividad a tablero
        Menu.redirectActivity(this, Recetas.class, "");
    }

    public void ClickAcercaDe(View v){
        //Recreamos actividad
        Menu.redirectActivity(this, AcercaNosotros.class, "");
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class, "");
    }

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "mandado");
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
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