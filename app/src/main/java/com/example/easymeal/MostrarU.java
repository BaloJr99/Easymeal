package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.Conexion;

import java.util.ArrayList;

public class MostrarU extends AppCompatActivity {
    Conexion c= new Conexion(this,"easymeal.db",null,16);
    int id;

    ListView listaUser;

    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mostrar_u);
        dl = findViewById(R.id.drawer_mostrar_u);
        Bundle b=getIntent().getExtras();
        listaUser = findViewById(R.id.listUsers);
        id=b.getInt("idUsuario");

        SQLiteDatabase op=c.getWritableDatabase();
        Cursor cr = op.rawQuery("SELECT * FROM t_usuarios WHERE idUsuario="+id,null);
        ArrayList<Usuario> lista =new ArrayList<>();
        if(cr != null && cr.moveToFirst()){
            do{
                Usuario u = new Usuario();
                u.setUsername(cr.getString(1));
                System.out.println(cr.getString(1));
                u.setNombre(cr.getString(3));
                System.out.println(cr.getString(3));
                u.setApellidoPaterno(cr.getString(4));
                u.setApellidoMaterno(cr.getString(5));
                u.setFechaNacimiento(cr.getString(6));
                lista.add(u);
            }while(cr.moveToNext());
        }
        if (cr != null) {
            cr.close();
        }
        ArrayList<String> list = new ArrayList<>();

        for (Usuario u:lista) {
            list.add("Usuario: "+u.getUsername());
            list.add("Nombre: "+u.getNombre());
            list.add("Apellido Paterno: "+u.getApellidoPaterno());
            list.add("Apellido Materno: "+u.getApellidoMaterno());
            list.add("Fecha de Nacimiento: "+u.getFechaNacimiento());
        }
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,list);
        listaUser.setAdapter(a);
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
        //Redireccionamos actividad a acerca de
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

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class, "");
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
    }

    public void ClickSalir(View v){
        //Cerramos app
        Menu.logout(this);
    }
}