package com.example.easymeal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Preparaciones;
import com.example.easymeal.cl.model.bd.Receta;
import com.example.easymeal.cl.model.bd.RecetaPreparacion;
import com.example.easymeal.cl.model.dao.Conexion;
import com.example.easymeal.cl.model.dao.PreparacionesDao;
import com.example.easymeal.cl.model.dao.RecetaDao;
import com.example.easymeal.cl.model.dao.RecetaPreparacionDao;
import com.example.easymeal.database.DbAyuda;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class Horario extends AppCompatActivity implements View.OnClickListener {
    Conexion co= new Conexion(this,"easymeal.db",null,16);
    DbAyuda db;
    //Inicializamo variable
    DrawerLayout dl;
    ImageView fecha;
    TextView txtFecha;
    int dia,mes,anio;
    int idPreparacion;

    Spinner lunes_alm,lunes_com,lun_cen,
            martes_alm,martes_com,martes_cen,
            miercoles_alm,miercoles_com, miercoles_cen,
            jueves_alm,jueves_com,jueves_cen,
            viernes_alm, viernes_com, viernes_cen,
            sabado_alm, sabado_com, sabado_cen,
            domingo_alm, domingo_com, domingo_cen;

    EditText cant_lunes_alm, cant_lunes_com,cant_lunes_cen,
            cant_martes_alm, cant_martes_com,cant_martes_cen,
            cant_miercoles_alm, cant_miercoles_com,cant_miercoles_cen,
            cant_jueves_alm, cant_jueves_com,cant_jueves_cen,
            cant_viernes_alm, cant_viernes_com,cant_viernes_cen,
            cant_sabado_alm, cant_sabado_com,cant_sabado_cen,
            cant_domingo_alm, cant_domingo_com,cant_domingo_cen;

    Button btnModificar;
    LinearLayout layout;

    ArrayList<Receta> listaRecetas;
    RecetaDao reDao;

    PreparacionesDao predao;
    Preparaciones pre;
    RecetaPreparacionDao recpredao;
    RecetaPreparacion recpre;

    String[] datos = new String[]{"AlmuerzoLunes", "ComidaLunes", "CenaLunes",
            "AlmuerzoMartes", "ComidaMartes", "CenaMartes",
            "AlmuerzoMiercoles", "ComidaMiercoles", "CenaMiercoles",
            "AlmuerzoJueves", "ComidaJueves", "CenaJueves",
            "AlmuerzoViernes", "ComidaViernes", "CenaViernes",
            "AlmuerzoSabado", "ComidaSabado", "CenaSabado",
            "AlmuerzoDomingo", "ComidaDomingo", "CenaDomingo"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.horario);

        //Asignamos variable
        dl = findViewById(R.id.drawer_Horario);
        fecha=findViewById(R.id.fecha);
        txtFecha=findViewById(R.id.txtFecha);
        lunes_alm=findViewById(R.id.lunes_alm);
        lunes_com=findViewById(R.id.lunes_com);
        lun_cen=findViewById(R.id.lunes_cen);
        martes_alm=findViewById(R.id.martes_alm);
        martes_com=findViewById(R.id.martes_com);
        martes_cen=findViewById(R.id.martes_cen);
        miercoles_alm=findViewById(R.id.miercoles_alm);
        miercoles_com=findViewById(R.id.miercoles_com);
        miercoles_cen=findViewById(R.id.miercoles_cen);
        jueves_alm=findViewById(R.id.jueves_alm);
        jueves_com=findViewById(R.id.jueves_com);
        jueves_cen=findViewById(R.id.jueves_cen);
        viernes_alm=findViewById(R.id.viernes_alm);
        viernes_com=findViewById(R.id.viernes_com);
        viernes_cen=findViewById(R.id.viernes_cen);
        sabado_alm=findViewById(R.id.sabado_alm);
        sabado_com=findViewById(R.id.sabado_com);
        sabado_cen=findViewById(R.id.sabado_cen);
        domingo_alm=findViewById(R.id.domingo_alm);
        domingo_com=findViewById(R.id.domingo_com);
        domingo_cen=findViewById(R.id.domingo_cen);

        cant_lunes_alm= findViewById(R.id.cant_lunes_alm);
        cant_lunes_com= findViewById(R.id.cant_lunes_com);
        cant_lunes_cen= findViewById(R.id.cant_lunes_cen);
        cant_martes_alm= findViewById(R.id.cant_martes_alm);
        cant_martes_com= findViewById(R.id.cant_martes_com);
        cant_martes_cen= findViewById(R.id.cant_martes_cen);
        cant_miercoles_alm= findViewById(R.id.cant_miercoles_alm);
        cant_miercoles_com= findViewById(R.id.cant_miercoles_com);
        cant_miercoles_cen= findViewById(R.id.cant_miercoles_cen);
        cant_jueves_alm= findViewById(R.id.cant_jueves_alm);
        cant_jueves_com= findViewById(R.id.cant_jueves_com);
        cant_jueves_cen= findViewById(R.id.cant_jueves_cen);
        cant_viernes_alm= findViewById(R.id.cant_viernes_alm);
        cant_viernes_com= findViewById(R.id.cant_viernes_com);
        cant_viernes_cen= findViewById(R.id.cant_viernes_cen);
        cant_sabado_alm= findViewById(R.id.cant_sabado_alm);
        cant_sabado_com= findViewById(R.id.cant_sabado_com);
        cant_sabado_cen= findViewById(R.id.cant_sabado_cen);
        cant_domingo_alm= findViewById(R.id.cant_domingo_alm);
        cant_domingo_com= findViewById(R.id.cant_domingo_com);
        cant_domingo_cen= findViewById(R.id.cant_domingo_cen);

        btnModificar = findViewById(R.id.modificar);
        btnModificar.setEnabled(false);

        layout = findViewById(R.id.linear_Horario);

        fecha.setOnClickListener(this);
        llenarSpinners();

    }

    public void ClickMenu(View v){
        //Abrimos Drawer
        Menu.openDrawer(dl);
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class, "");
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

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "mandado");
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
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

    @Override
    public void onClick(View v) {

            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio= c.get(Calendar.YEAR);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
                c.set(year, monthOfYear, dayOfMonth);
                txtFecha.setText("SEMANA: " + (c.get(Calendar.WEEK_OF_YEAR) - 1));
                predao = new PreparacionesDao(this);
                ArrayList<Object[]> listapre = predao.buscarHorario(txtFecha.getText().toString());
                if(listapre != null){
                    int spin = 0;
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View v0 = layout.getChildAt(i);
                        if (v0 instanceof LinearLayout) {
                            LinearLayout layout1 = (LinearLayout) v0;
                            for (int j = 0; j < layout1.getChildCount(); j++) {
                                View v1 = layout1.getChildAt(j);
                                View v2 = layout1.getChildAt((j + 1));
                                if (v1 instanceof Spinner) {
                                    for(Object[] o: listapre){
                                        if(datos[spin].equals(o[0])){
                                            ((Spinner) v1).setSelection(Integer.parseInt(o[1].toString()));
                                            ((EditText) v2).setText(o[2].toString());
                                        }
                                    }
                                    spin++;
                                }
                            }

                        }
                    }
                    btnModificar.setEnabled(true);
                }
            },anio,mes,dia);
            datePickerDialog.show();
    }
    //modificar una semana
    public void eliminarReceta(View v){
        SQLiteDatabase op=db.getWritableDatabase();
        AlertDialog.Builder b= new AlertDialog.Builder(this);
        b.setMessage("¿Seguro que desea modificar el horario?");
        b.setCancelable(false);
        b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                op.execSQL("DELETE FROM t_receta WHERE fecha='"+txtFecha.getText().toString()+"'");
                Intent i2 = new Intent(Horario.this,Preparaciones.class);
                try {
                    if(!txtFecha.getText().toString().equals("--/--/----") &&
                            ((lunes_alm.getSelectedItemPosition()!=(0))||(lunes_com.getSelectedItemPosition()!=(0))|| (lun_cen.getSelectedItemPosition()!=(0))||
                                    (martes_alm.getSelectedItemPosition()!=(0))||(martes_com.getSelectedItemPosition()!=(0))|| (martes_cen.getSelectedItemPosition()!=(0))||
                                    (miercoles_alm.getSelectedItemPosition()!=(0))||(miercoles_com.getSelectedItemPosition()!=(0))|| (miercoles_cen.getSelectedItemPosition()!=(0))||
                                    (jueves_alm.getSelectedItemPosition()!=(0))||(jueves_com.getSelectedItemPosition()!=(0))|| (jueves_cen.getSelectedItemPosition()!=(0))||
                                    (viernes_alm.getSelectedItemPosition()!=(0))||(viernes_com.getSelectedItemPosition()!=(0))|| (viernes_cen.getSelectedItemPosition()!=(0))||
                                    (sabado_alm.getSelectedItemPosition()!=(0))||(sabado_com.getSelectedItemPosition()!=(0))|| (sabado_cen.getSelectedItemPosition()!=(0))||
                                    (domingo_alm.getSelectedItemPosition()!=(0))||(domingo_com.getSelectedItemPosition()!=(0))|| (domingo_cen.getSelectedItemPosition()!=(0)))){

                        predao = new PreparacionesDao(Horario.this);
                        pre = new Preparaciones();
                        recpredao = new RecetaPreparacionDao(Horario.this);
                        recpre = new RecetaPreparacion();

                        int flag = 0;

                        for (int x = 0; x < layout.getChildCount(); x++) {
                            View v = layout.getChildAt(i);
                            if (v instanceof LinearLayout) {
                                LinearLayout layout1 = (LinearLayout) v;
                                for (int j = 0; j < layout1.getChildCount(); j++) {
                                    View v1 = layout1.getChildAt(j);
                                    View v2 = layout1.getChildAt((j + 1));
                                    if (v1 instanceof Spinner) {
                                        if(((Spinner) v1).getSelectedItemPosition() != 0){
                                            pre.setTipoComida(datos[flag]);
                                            flag++;

                                            pre.setfechaPreparacion(txtFecha.getText().toString());
                                            int resultado = predao.insertarPreparacion(pre);
                                            System.out.println(resultado);
                                            if(resultado != 0 ){
                                                System.out.println("Entro");
                                                recpre.setIdPreparaciones(resultado);
                                                recpre.setIdReceta(((Spinner) v1).getSelectedItemPosition());
                                                if(!((EditText)v2).getText().toString().trim().equals("")){
                                                    recpre.setCantidadAPreparar(Integer.valueOf(((EditText)v2).getText().toString()));
                                                }else{
                                                    recpre.setCantidadAPreparar(1);
                                                }
                                                recpredao.insertarRecetaPreparacion(recpre);
                                                Toast.makeText(Horario.this,"Insertado exitosamente",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        limpiarCampos();
                    }else{
                        Toast.makeText(Horario.this,"Existen campos vacios",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    System.out.println("Error");
                    System.out.println(e.getMessage());
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


    //llenar todos los spinner
    public void llenarSpinners() {
        reDao = new RecetaDao(this);
        listaRecetas = reDao.selectRecetas();

        ArrayList<String> listaR = new ArrayList<>();
        listaR.add("Seleccione...");

        for(Receta rec: listaRecetas){
         listaR.add(rec.getNombre());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaR);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof LinearLayout) {
                LinearLayout layout1 = (LinearLayout) v;
                for (int j = 0; j < layout1.getChildCount(); j++) {
                    View v1 = layout1.getChildAt(j);
                    if (v1 instanceof Spinner) {
                        ((Spinner) v1).setAdapter(arrayAdapter);
                    }
                }

            }
        }
    }

    public void insertarInformacion(View view){
        try {
            if(!txtFecha.getText().toString().equals("--/--/----") &&
                    ((lunes_alm.getSelectedItemPosition()!=(0))||(lunes_com.getSelectedItemPosition()!=(0))|| (lun_cen.getSelectedItemPosition()!=(0))||
                    (martes_alm.getSelectedItemPosition()!=(0))||(martes_com.getSelectedItemPosition()!=(0))|| (martes_cen.getSelectedItemPosition()!=(0))||
                    (miercoles_alm.getSelectedItemPosition()!=(0))||(miercoles_com.getSelectedItemPosition()!=(0))|| (miercoles_cen.getSelectedItemPosition()!=(0))||
                    (jueves_alm.getSelectedItemPosition()!=(0))||(jueves_com.getSelectedItemPosition()!=(0))|| (jueves_cen.getSelectedItemPosition()!=(0))||
                    (viernes_alm.getSelectedItemPosition()!=(0))||(viernes_com.getSelectedItemPosition()!=(0))|| (viernes_cen.getSelectedItemPosition()!=(0))||
                    (sabado_alm.getSelectedItemPosition()!=(0))||(sabado_com.getSelectedItemPosition()!=(0))|| (sabado_cen.getSelectedItemPosition()!=(0))||
                    (domingo_alm.getSelectedItemPosition()!=(0))||(domingo_com.getSelectedItemPosition()!=(0))|| (domingo_cen.getSelectedItemPosition()!=(0)))){

                predao = new PreparacionesDao(this);
                pre = new Preparaciones();
                recpredao = new RecetaPreparacionDao(this);
                recpre = new RecetaPreparacion();

                int flag = 0;

                for (int i = 0; i < layout.getChildCount(); i++) {
                    View v = layout.getChildAt(i);
                    if (v instanceof LinearLayout) {
                        LinearLayout layout1 = (LinearLayout) v;
                        for (int j = 0; j < layout1.getChildCount(); j++) {
                            View v1 = layout1.getChildAt(j);
                            View v2 = layout1.getChildAt((j + 1));
                            if (v1 instanceof Spinner) {
                                if(((Spinner) v1).getSelectedItemPosition() != 0){
                                    pre.setTipoComida(datos[flag]);
                                    flag ++;

                                    pre.setfechaPreparacion(txtFecha.getText().toString());
                                    int resultado = predao.insertarPreparacion(pre);
                                    System.out.println(resultado);
                                    if(resultado != 0 ){
                                        System.out.println("Entro");
                                        recpre.setIdPreparaciones(resultado);
                                        recpre.setIdReceta(((Spinner) v1).getSelectedItemPosition());
                                        if(!((EditText)v2).getText().toString().trim().equals("")){
                                            recpre.setCantidadAPreparar(Integer.valueOf(((EditText)v2).getText().toString()));
                                        }else{
                                            recpre.setCantidadAPreparar(1);
                                        }
                                        recpredao.insertarRecetaPreparacion(recpre);
                                        Toast.makeText(this,"Insertado exitosamente",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                    }
                }
                limpiarCampos();
            }else{
                Toast.makeText(this,"Existen campos vacios",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    }

    public void limpiarCampos(){

        LinearLayout layout = findViewById(R.id.linear_Horario);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof LinearLayout) {
                LinearLayout layout1 = (LinearLayout) v;
                for (int j = 0; j < layout1.getChildCount(); j++) {
                    View v1 = layout1.getChildAt(j);
                    View v2 = layout1.getChildAt((j + 1));
                    if (v1 instanceof Spinner) {
                        ((Spinner) v1).setSelection(0);
                        ((EditText)v2).setText("");
                    }
                }

            }
        }
    }
}
