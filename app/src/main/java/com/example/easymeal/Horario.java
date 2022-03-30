package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Preparaciones;
import com.example.easymeal.cl.model.bd.Receta;
import com.example.easymeal.cl.model.bd.RecetaPreparacion;
import com.example.easymeal.cl.model.dao.PreparacionesDao;
import com.example.easymeal.cl.model.dao.RecetaDao;
import com.example.easymeal.cl.model.dao.RecetaPreparacionDao;

import java.util.ArrayList;
import java.util.Calendar;

public class Horario extends AppCompatActivity implements View.OnClickListener {

    //Inicializamo variable
    DrawerLayout dl;
    ImageView fecha;
    TextView txtFecha;
    int dia,mes,anio;
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
    ArrayList<Receta> listaRecetas;
    Recetas re;
    RecetaDao reDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.horario);

        //Asignamos variable
        dl = findViewById(R.id.drawer_Horario);
        fecha=(ImageView)findViewById(R.id.fecha);
        txtFecha=(TextView)findViewById(R.id.txtFecha);
        lunes_alm=(Spinner)findViewById(R.id.lunes_alm);
        lunes_com=(Spinner)findViewById(R.id.lunes_com);
        lun_cen=(Spinner)findViewById(R.id.lunes_cen);
        martes_alm=(Spinner)findViewById(R.id.martes_alm);
        martes_com=(Spinner)findViewById(R.id.martes_com);
        martes_cen=(Spinner)findViewById(R.id.martes_cen);
        miercoles_alm=(Spinner)findViewById(R.id.miercoles_alm);
        miercoles_com=(Spinner)findViewById(R.id.miercoles_com);
        miercoles_cen=(Spinner)findViewById(R.id.miercoles_cen);
        jueves_alm=(Spinner)findViewById(R.id.jueves_alm);
        jueves_com=(Spinner)findViewById(R.id.jueves_com);
        jueves_cen=(Spinner)findViewById(R.id.jueves_cen);
        viernes_alm=(Spinner)findViewById(R.id.viernes_alm);
        viernes_com=(Spinner)findViewById(R.id.viernes_com);
        viernes_cen=(Spinner)findViewById(R.id.viernes_cen);
        sabado_alm=(Spinner)findViewById(R.id.sabado_alm);
        sabado_com=(Spinner)findViewById(R.id.sabado_com);
        sabado_cen=(Spinner)findViewById(R.id.sabado_cen);
        domingo_alm=(Spinner)findViewById(R.id.domingo_alm);
        domingo_com=(Spinner)findViewById(R.id.domingo_com);
        domingo_cen=(Spinner)findViewById(R.id.domingo_cen);

        cant_lunes_alm=(EditText) findViewById(R.id.cant_lunes_alm);
        cant_lunes_com=(EditText) findViewById(R.id.cant_lunes_com);
        cant_lunes_cen=(EditText) findViewById(R.id.cant_lunes_cen);
        cant_martes_alm=(EditText) findViewById(R.id.cant_martes_alm);
        cant_martes_com=(EditText) findViewById(R.id.cant_martes_com);
        cant_martes_cen=(EditText) findViewById(R.id.cant_martes_cen);
        cant_miercoles_alm=(EditText) findViewById(R.id.cant_miercoles_alm);
        cant_miercoles_com=(EditText) findViewById(R.id.cant_miercoles_com);
        cant_miercoles_cen=(EditText) findViewById(R.id.cant_miercoles_cen);
        cant_jueves_alm=(EditText) findViewById(R.id.cant_jueves_alm);
        cant_jueves_com=(EditText) findViewById(R.id.cant_jueves_com);
        cant_jueves_cen=(EditText) findViewById(R.id.cant_jueves_cen);
        cant_viernes_alm=(EditText) findViewById(R.id.cant_viernes_alm);
        cant_viernes_com=(EditText) findViewById(R.id.cant_viernes_com);
        cant_viernes_cen=(EditText) findViewById(R.id.cant_viernes_cen);
        cant_sabado_alm=(EditText) findViewById(R.id.cant_sabado_alm);
        cant_sabado_com=(EditText) findViewById(R.id.cant_sabado_com);
        cant_sabado_cen=(EditText) findViewById(R.id.cant_sabado_cen);
        cant_domingo_alm=(EditText) findViewById(R.id.cant_domingo_alm);
        cant_domingo_com=(EditText) findViewById(R.id.cant_domingo_com);
        cant_domingo_cen=(EditText) findViewById(R.id.cant_domingo_cen);
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                    txtFecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }

            },anio,mes,dia);
            datePickerDialog.show();
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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaR);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lunes_alm.setAdapter(arrayAdapter);
        lunes_com.setAdapter(arrayAdapter);
        lun_cen.setAdapter(arrayAdapter);
        martes_alm.setAdapter(arrayAdapter);
        martes_com.setAdapter(arrayAdapter);
        martes_cen.setAdapter(arrayAdapter);
        miercoles_alm.setAdapter(arrayAdapter);
        miercoles_com.setAdapter(arrayAdapter);
        miercoles_cen.setAdapter(arrayAdapter);
        jueves_alm.setAdapter(arrayAdapter);
        jueves_com.setAdapter(arrayAdapter);
        jueves_cen.setAdapter(arrayAdapter);
        viernes_alm.setAdapter(arrayAdapter);
        viernes_com.setAdapter(arrayAdapter);
        viernes_cen.setAdapter(arrayAdapter);
        sabado_alm.setAdapter(arrayAdapter);
        sabado_com.setAdapter(arrayAdapter);
        sabado_cen.setAdapter(arrayAdapter);
        domingo_alm.setAdapter(arrayAdapter);
        domingo_com.setAdapter(arrayAdapter);
        domingo_cen.setAdapter(arrayAdapter);

    }

    public void insetarInformacion(View view){
        try {

            PreparacionesDao predao = new PreparacionesDao(this);
            Preparaciones pre = new Preparaciones();
            RecetaPreparacionDao recpredao = new RecetaPreparacionDao(this);
            RecetaPreparacion recpre = new RecetaPreparacion();

        if(lunes_alm.getSelectedItemPosition() != 0){
            pre.setTipoComida("AlmuerzoLunes");
            pre.setfechaPreparacion(txtFecha.getText().toString());
            int resultado = predao.insertarPreparacion(pre);
            if(resultado != 0 ){
                recpre.setIdPreparaciones(resultado);
                recpre.setIdReceta(lunes_alm.getSelectedItemPosition());
                    if(!cant_lunes_alm.getText().toString().trim().equals("")){
                        recpre.setCantidadAPreparar(Integer.valueOf(cant_lunes_alm.getText().toString()));
                    }else{
                        recpre.setCantidadAPreparar(1);
                    }
                recpredao.insertarRecetaPreparacion(recpre);
                Toast.makeText(this,"Insertado exitosamente",Toast.LENGTH_SHORT).show();
            }
        }
            if(lunes_com.getSelectedItemPosition() != 0){
                pre.setTipoComida("ComidaLunes");
                pre.setfechaPreparacion(txtFecha.getText().toString());
                int resultado = predao.insertarPreparacion(pre);
                if(resultado != 0 ){
                    recpre.setIdPreparaciones(resultado);
                    recpre.setIdReceta(lunes_com.getSelectedItemPosition());
                    if(!cant_lunes_com.getText().toString().trim().equals("")){
                        recpre.setCantidadAPreparar(Integer.valueOf(cant_lunes_com.getText().toString()));
                    }else{
                        recpre.setCantidadAPreparar(1);
                    }
                    recpredao.insertarRecetaPreparacion(recpre);
                    Toast.makeText(this,"Insertado exitosamente",Toast.LENGTH_SHORT).show();
                }
            }
            if(lun_cen.getSelectedItemPosition() != 0){
                pre.setTipoComida("CenaLunes");
                pre.setfechaPreparacion(txtFecha.getText().toString());
                int resultado = predao.insertarPreparacion(pre);
                if(resultado != 0 ){
                    recpre.setIdPreparaciones(resultado);
                    recpre.setIdReceta(lun_cen.getSelectedItemPosition());
                    if(!cant_lunes_cen.getText().toString().trim().equals("")){
                        recpre.setCantidadAPreparar(Integer.valueOf(cant_lunes_cen.getText().toString()));
                    }else{
                        recpre.setCantidadAPreparar(1);
                    }
                    recpredao.insertarRecetaPreparacion(recpre);
                    Toast.makeText(this,"Insertado exitosamente",Toast.LENGTH_SHORT).show();
                }
            }


        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    }
}
