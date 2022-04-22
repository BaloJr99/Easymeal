package com.example.easymeal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

import com.example.easymeal.cl.model.dao.Conexion;
import com.example.easymeal.cl.model.dao.RecetaDao;

import com.example.easymeal.cl.model.dao.RecetaIngredienteDao;
import com.example.easymeal.database.DbAyuda;

import java.util.ArrayList;

public class Recetas extends AppCompatActivity{
    Conexion co= new Conexion(this,"easymeal.db",null,16);
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
        nom = findViewById(R.id.fieldNombre);
        pasos = findViewById(R.id.mFieldPasos);
        cantidad = findViewById(R.id.txtCantidad);
        insertar = findViewById(R.id.btnAgregar);
        editar = findViewById(R.id.btnEdit);
        buscar = findViewById(R.id.btnBuscar);
        borrar = findViewById(R.id.btnBorrar);
        agregarIng = findViewById(R.id.btnAgregarIng);
        nuevoIng = findViewById(R.id.btnNuevoIng);
        verIng = findViewById(R.id.btnVerIng);
        ing =  findViewById(R.id.spiingrediente);
        dao = new RecetaDao(this);
        listaRecetas = findViewById(R.id.listaRecetas);
        poblar();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,infoList);
        listaRecetas.setAdapter(adapter);

        SQLiteDatabase op=co.getWritableDatabase();
        Cursor cr = op.rawQuery("SELECT * FROM t_ingrediente",null);
        ArrayList<Ingrediente> lista =new ArrayList<>();
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
            cr.close();
        }
        ArrayList<String> list = new ArrayList<>();
        for (Ingrediente i:lista) {
            list.add(i.getDescripcion());
        }
        //String[] datos = new String[] {"C#", "Java", "Python", "R", "Go"};
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,list);
        ing.setAdapter(a);


        listaRecetas.setOnItemClickListener((adapterView, view, i, l) -> {

            nom.setText(recetasList.get(i).getNombre());
            pasos.setText(recetasList.get(i).getPasos());
            idReceta = recetasList.get(i).getIdReceta();
            if(ing.getAdapter().getCount() != 0){
                agregarIng.setEnabled(true);
                verIng.setEnabled(true);
            }else{
                nuevoIng.setEnabled(true);
            }

        });
        insertar.setOnClickListener(view -> {

            Receta c = new Receta();
            c.setNombre(nom.getText().toString());
            c.setPasos(pasos.getText().toString());
            if(!c.isNull()){
                Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
            }else if(dao.insertarReceta(c)) {
                Toast.makeText(Recetas.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
                SQLiteDatabase op1 = co.getWritableDatabase();
                Cursor cr1 = op1.rawQuery("SELECT MAX(idReceta) from t_receta", null);
                if (cr1 != null && cr1.moveToFirst()) {
                    do {
                        System.out.println(cr1.getInt(0));
                        idReceta = cr1.getInt(0);
                    } while (cr1.moveToNext());
                    cr1.close();
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
            });

        editar.setOnClickListener(view -> {
            Receta c = new Receta();
            c.setNombre(nom.getText().toString());
            c.setPasos(pasos.getText().toString());
            if(!c.isNull()){
                Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
            }else if(dao.updateReceta(c)){
                SQLiteDatabase op13 = co.getWritableDatabase();
                Cursor cr13 = op13.rawQuery("SELECT idReceta from t_receta WHERE nombre='"+nom.getText().toString()+"'", null);
                if (cr13 != null && cr13.moveToFirst()) {
                    do {
                        System.out.println(cr13.getInt(0));
                        idReceta = cr13.getInt(0);
                    } while (cr13.moveToNext());
                    cr13.close();
                }
                poblar();
                ArrayAdapter adapter = new ArrayAdapter(Recetas.this, android.R.layout.simple_expandable_list_item_1,infoList);
                listaRecetas.setAdapter(adapter);
                agregarIng.setEnabled(true);
                Toast.makeText(Recetas.this,"Registro Editado Exitoso",Toast.LENGTH_LONG).show();
                nom.setText("");
                pasos.setText("");
            }
        });
        buscar.setOnClickListener(view -> {
            if(nom.getText().toString().equals("")){
                Toast.makeText(Recetas.this,"ERROR: CAMPO DE NOMBRE VACIO",Toast.LENGTH_LONG).show();
            }else{
                busqueda = dao.selectReceta(nom.getText().toString());
                pasos.setText(busqueda.get(0).getPasos());
                idReceta = recetasList.get(0).getIdReceta();
                poblar();
                agregarIng.setEnabled(true);
            }
        });
        agregarIng.setOnClickListener(view -> {
            SQLiteDatabase op12 = co.getWritableDatabase();
            Cursor cr12 = op12.rawQuery("SELECT idIngrediente from t_ingrediente WHERE descripcion='"+ing.getSelectedItem().toString()+"'", null);
            if (cr12 != null && cr12.moveToFirst()) {
                do {
                    System.out.println(cr12.getInt(0));
                    idIngrediente = cr12.getInt(0);
                } while (cr12.moveToNext());
                cr12.close();
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
        });
        nuevoIng.setOnClickListener(view -> {
            String tipo = "mandado";
            Intent i = new Intent(Recetas.this,ListaMandado.class);
            i.putExtra("tipo",tipo);
            startActivity(i);
        });
        verIng.setOnClickListener(view -> {
            Intent i = new Intent(Recetas.this,IngRec.class);
            i.putExtra("idReceta",idReceta);
            startActivity(i);
        });
    }
    private void poblar(){  //Metodo para poblar el array objeto
        SQLiteDatabase bd = db.getReadableDatabase();
        Receta receta;
        recetasList = new ArrayList<>();
        Cursor cursor = bd.rawQuery("SELECT * FROM "+"t_receta",null);

        while(cursor.moveToNext()){
            receta = new Receta();
            receta.setIdReceta(cursor.getInt(0));
            receta.setNombre(cursor.getString(1));
            receta.setPasos(cursor.getString(2));

            recetasList.add(receta);
        }
        cursor.close();
        crearLista();
    }
    private void crearLista(){ //Metodo para poblar la lista
         infoList = new ArrayList<>();
         for(int i=0;i<recetasList.size();i++){
             infoList.add(recetasList.get(i).getNombre() +"\n Pasos:"+recetasList.get(i).getPasos());
        }
    }
    public void eliminarReceta(View v){
        SQLiteDatabase op=db.getWritableDatabase();
        AlertDialog.Builder b= new AlertDialog.Builder(Recetas.this);
        b.setMessage("¿Seguro que desea eliminar la receta?");
        b.setCancelable(false);
        b.setPositiveButton("SI", (dialogInterface, i) -> {
            op.execSQL("DELETE FROM t_receta WHERE nombre='"+nom.getText().toString()+"'");
            Toast.makeText(Recetas.this,"Receta Eliminada",Toast.LENGTH_LONG).show();
            Intent i2 = new Intent(Recetas.this,Recetas.class);
            startActivity(i2);
            agregarIng.setEnabled(false);

        });
        b.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.cancel());
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
