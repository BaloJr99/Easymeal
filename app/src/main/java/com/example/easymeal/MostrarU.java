package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.Conexion;
import com.example.easymeal.cl.model.dao.daoUsuario;

import java.util.ArrayList;

public class MostrarU extends AppCompatActivity {
    Conexion c= new Conexion(this,"easymeal.db",null,12);
    int id;
ListView listaUser;
daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_mostrar_u);
        Bundle b=getIntent().getExtras();
        listaUser = (ListView) findViewById(R.id.listUsers);
        id=b.getInt("idUsuario");

        SQLiteDatabase op=c.getWritableDatabase();
        Cursor cr = op.rawQuery("SELECT * FROM t_usuarios WHERE idUsuario="+id,null);
        ArrayList<Usuario> lista =new ArrayList<Usuario>();
        lista.clear();
        if(cr != null && cr.moveToFirst()){
            do{
                Usuario u = new Usuario();
                //u.setId(cr.getInt(0));
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
        ArrayList<String> list = new ArrayList<String>();

        for (Usuario u:lista) {
            list.add("Usuario: "+u.getUsername());
            list.add("Nombre: "+u.getNombre());
            list.add("Apellido Paterno: "+u.getApellidoPaterno());
            list.add("Apellido Materno: "+u.getApellidoMaterno());
            list.add("Fecha de Nacimiento: "+u.getFechaNacimiento());
        }
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
        listaUser.setAdapter(a);
        /*ArrayList<Usuario> l = dao.selectUsuarioById(1);
        ArrayList<String> list = new ArrayList<String>();

        for (Usuario u:l) {
            list.add("Usuario: "+u.getUsername());
            list.add("Nombre: "+u.getNombre());
            list.add("Apellido Paterno: "+u.getApellidoPaterno());
            list.add("Apellido Materno: "+u.getApellidoMaterno());
            list.add("Fecha de Nacimiento: "+u.getFechaNacimiento());
        }
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
        lista.setAdapter(a);*/
    }
}