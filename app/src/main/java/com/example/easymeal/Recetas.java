package com.example.easymeal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.IngredienteReceta;
import com.example.easymeal.cl.model.bd.Receta;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.Conexion;
import com.example.easymeal.cl.model.dao.IngredienteDao;
import com.example.easymeal.cl.model.dao.RecetaDao;

import com.example.easymeal.cl.model.dao.RecetaIngredienteDao;
import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class Recetas extends AppCompatActivity{
    Conexion co= new Conexion(this,"easymeal.db",null,15);
    DbAyuda db;
    ArrayAdapter adapter;
    ListView listaRecetas;
    ArrayList<String> infoList;
    ArrayList<Receta> recetasList;
    EditText nom,pasos,cantidad;
    Button agregarIng,nuevoIng,verIng;
    ImageButton insertar,editar,buscar,borrar;
    RecetaDao dao;
    RecetaIngredienteDao daoing;
    ArrayList<Receta> busqueda;
    Spinner ing;
    Integer idReceta, idIngrediente;



    //Inicializamos variable
    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recetas);
        dl = findViewById(R.id.drawer_recetas);
        db = new DbAyuda(getApplicationContext());
        nom = (EditText) findViewById(R.id.fieldNombre);
        pasos = (EditText) findViewById(R.id.mFieldPasos);
        cantidad = (EditText) findViewById(R.id.txtCantidad);
        insertar = (ImageButton) findViewById(R.id.btnAgregar);
        editar = (ImageButton) findViewById(R.id.btnEdit);
        buscar = (ImageButton) findViewById(R.id.btnBuscar);
        borrar = (ImageButton) findViewById(R.id.btnBorrar);
        agregarIng = (Button) findViewById(R.id.btnAgregarIng);
        nuevoIng = (Button) findViewById(R.id.btnNuevoIng);
        verIng = (Button) findViewById(R.id.btnVerIng);
        ing=(Spinner) findViewById(R.id.spiingrediente);
        dao = new RecetaDao(this);
        listaRecetas = (ListView) findViewById(R.id.listaRecetas);
        poblar();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,infoList);
        listaRecetas.setAdapter(adapter);

        SQLiteDatabase op=co.getWritableDatabase();
        Cursor cr = op.rawQuery("SELECT * FROM t_ingrediente",null);
        ArrayList<Ingrediente> lista =new ArrayList<Ingrediente>();
        lista.clear();
        if(cr != null && cr.moveToFirst()) {
            do {
                Ingrediente i = new Ingrediente();
                i.setIdIngrediente(cr.getInt(0));
                idIngrediente = cr.getInt(0);
                i.setDescripcion(cr.getString(1));
                System.out.println(cr.getString(1));
                i.setUnidadDeMedida(cr.getString(2));
                i.setCantidad(cr.getFloat(3));
                i.setFechaCaducidad(cr.getString(4));
                i.setMandado(cr.getInt(5));
                i.setCantidadAComprar(cr.getFloat(6));
                i.setImagen(cr.getBlob(7));
                lista.add(i);
            } while (cr.moveToNext());
        }
        ArrayList<String> list = new ArrayList<String>();
        for (Ingrediente i:lista) {
            list.add(i.getDescripcion());
        }
        //String[] datos = new String[] {"C#", "Java", "Python", "R", "Go"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
        ing.setAdapter(a);


        listaRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                nom.setText(recetasList.get(i).getNombre());
                pasos.setText(recetasList.get(i).getPasos());
                idReceta = recetasList.get(i).getIdReceta();
                if(ing.getAdapter().getCount() != 0){
                    agregarIng.setEnabled(true);
                    verIng.setEnabled(true);
                }else{
                    nuevoIng.setEnabled(true);
                }

            }
        });
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Receta c = new Receta();
                c.setNombre(nom.getText().toString());
                c.setPasos(pasos.getText().toString());
                if(!c.isNull()){
                    Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
                }else if(dao.insertarReceta(c)) {
                    Toast.makeText(Recetas.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
                    SQLiteDatabase op = co.getWritableDatabase();
                    Cursor cr = op.rawQuery("SELECT MAX(idReceta) from t_receta", null);
                    if (cr != null && cr.moveToFirst()) {
                        do {
                            System.out.println(cr.getInt(0));
                            idReceta = cr.getInt(0);
                        } while (cr.moveToNext());
                        poblar();
                        ArrayAdapter adapter = new ArrayAdapter(Recetas.this, android.R.layout.simple_expandable_list_item_1,infoList);
                        listaRecetas.setAdapter(adapter);
                        if(ing.getSelectedItem() != null){
                            agregarIng.setEnabled(true);
                        }
                        nuevoIng.setEnabled(true);
                }else{
                    Toast.makeText(Recetas.this,"Receta ya registrada",Toast.LENGTH_LONG).show();
                }
                    }else{
                        Toast.makeText(Recetas.this,"Receta ya registrada",Toast.LENGTH_LONG).show();
                    }
                }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receta c = new Receta();
                c.setNombre(nom.getText().toString());
                c.setPasos(pasos.getText().toString());
                if(!c.isNull()){
                    Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
                }else if(dao.updateReceta(c)){
                    SQLiteDatabase op = co.getWritableDatabase();
                    Cursor cr = op.rawQuery("SELECT idReceta from t_receta WHERE nombre='"+nom.getText().toString()+"'", null);
                    if (cr != null && cr.moveToFirst()) {
                        do {
                            System.out.println(cr.getInt(0));
                            idReceta = cr.getInt(0);
                        } while (cr.moveToNext());
                    }
                    poblar();
                    ArrayAdapter adapter = new ArrayAdapter(Recetas.this, android.R.layout.simple_expandable_list_item_1,infoList);
                    listaRecetas.setAdapter(adapter);
                    agregarIng.setEnabled(true);
                    Toast.makeText(Recetas.this,"Registro Editado Exitoso",Toast.LENGTH_LONG).show();
                    nom.setText("");
                    pasos.setText("");
                }
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receta c = new Receta();
                if(nom.getText().toString().equals(null)){
                    Toast.makeText(Recetas.this,"ERROR: CAMPO DE NOMBRE VACIO",Toast.LENGTH_LONG).show();
                }else{
                    busqueda = dao.selectReceta(nom.getText().toString());
                    pasos.setText(busqueda.get(0).getPasos());
                    idReceta = recetasList.get(0).getIdReceta();
                    poblar();
                    agregarIng.setEnabled(true);
                }
            }
        });
        agregarIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase op = co.getWritableDatabase();
                Cursor cr = op.rawQuery("SELECT idIngrediente from t_ingrediente WHERE descripcion='"+ing.getSelectedItem().toString()+"'", null);
                if (cr != null && cr.moveToFirst()) {
                    do {
                        System.out.println(cr.getInt(0));
                        idIngrediente = cr.getInt(0);
                    } while (cr.moveToNext());
                }
                IngredienteReceta r = new IngredienteReceta();
                if(!cantidad.getText().toString().trim().isEmpty()){
                    r.setCantidad(Float.parseFloat(cantidad.getText().toString()));
                    r.setIdReceta(idReceta);
                    r.setIdIngrediente(idIngrediente);
                    daoing = new RecetaIngredienteDao(Recetas.this);
                    if(!r.isNull()){
                        Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
                    }else if(daoing.insertarReceta(r)){
                        Toast.makeText(Recetas.this,"Registro de Ingrediente exitoso",Toast.LENGTH_LONG).show();
                        //idReceta = dao.ultimaReceta();
                        Intent i2 = new Intent(Recetas.this,Recetas.class);
                        startActivity(i2);
                        poblar();
                        agregarIng.setEnabled(true);
                    }else{
                        Toast.makeText(Recetas.this,"Ingrediente ya registrado",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Recetas.this,"Favor de ingresar la cantidad",Toast.LENGTH_LONG).show();
                }
            }
        });
        nuevoIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipo = "mandado";
                Intent i = new Intent(Recetas.this,ListaMandado.class);
                i.putExtra("tipo",tipo);
                startActivity(i);
            }
        });
        verIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Recetas.this,IngRec.class);
                i.putExtra("idReceta",idReceta);
                startActivity(i);
            }
        });
    }
    private void poblar(){  //Metodo para poblar el array objeto
        SQLiteDatabase bd = db.getReadableDatabase();
        Receta receta = null;
        recetasList = new ArrayList<Receta>();
        Cursor cursor = bd.rawQuery("SELECT * FROM "+"t_receta",null);

        while(cursor.moveToNext()){
            receta = new Receta();
            receta.setIdReceta(cursor.getInt(0));
            receta.setNombre(cursor.getString(1));
            receta.setPasos(cursor.getString(2));

            recetasList.add(receta);
        }
        crearLista();
    }
    private void crearLista(){ //Metodo para poblar la lista
         infoList = new ArrayList<String>();
         for(int i=0;i<recetasList.size();i++){
             infoList.add(String.valueOf(recetasList.get(i).getNombre())+"\n Pasos:"+recetasList.get(i).getPasos());
        }
    }
    public void eliminarReceta(View v){
        SQLiteDatabase op=db.getWritableDatabase();
        AlertDialog.Builder b= new AlertDialog.Builder(Recetas.this);
        b.setMessage("¿Seguro que desea eliminar la receta?");
        b.setCancelable(false);
        b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                op.execSQL("DELETE FROM t_receta WHERE nombre='"+nom.getText().toString()+"'");
                Toast.makeText(Recetas.this,"Receta Eliminada",Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(Recetas.this,Recetas.class);
                startActivity(i2);
                agregarIng.setEnabled(false);

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
    public void ClickMenu(View v){
        //Abrimos Drawer
        openDrawer(dl);
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class, "");
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
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
        Menu.redirectActivity(this, Menu.class, "");
    }

    public void ClickRecetas(View v){
        //Recreamos actividad
        recreate();
    }

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "mandado");
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class, "");
    }

    public void ClickAcercaDe(View v){
        //Redireccionamos actividad a acerca de nosotros
        Menu.redirectActivity(this, AcercaNosotros.class, "");
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
