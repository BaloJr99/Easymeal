package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.easymeal.cl.model.dao.PreparacionesDao;

import java.util.ArrayList;
import java.util.Calendar;


public class VistaHorario extends AppCompatActivity {
    DrawerLayout dl;
    TextView txtFecha;
    TextView tvAlmuerzoLunes,tvComidaLunes,tvCenaLunes,
            tvAlmuerzoMartes,tvComidaMartes,tvCenaMartes,
            tvAlmuerzoMiercoles,tvComidaMiercoles,tvCenaMiercoles,
            tvAlmuerzoJueves,tvComidaJueves,tvCenaJueves,
            tvAlmuerzoViernes,tvComidaViernes,tvCenaViernes,
            tvAlmuerzoSabado,tvComidaSabado,tvCenaSabado,
            tvAlmuerzoDomingo,tvComidaDomingo,tvCenaDomingo;
    PreparacionesDao predao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_horario);
        dl=findViewById(R.id.drawer_vista);
        txtFecha=(TextView) findViewById(R.id.txtFecha);
        tvAlmuerzoLunes=(TextView)findViewById(R.id.tvAlmuerzoLunes);
        tvComidaLunes=(TextView) findViewById(R.id.tvComidaLunes);
        tvCenaLunes=(TextView) findViewById(R.id.tvCenaLunes);
        tvAlmuerzoMartes=(TextView)findViewById(R.id.tvAlmuerzoMartes);
        tvComidaMartes=(TextView) findViewById(R.id.tvComidaMartes);
        tvCenaMartes=(TextView) findViewById(R.id.tvCenaMartes);
        tvAlmuerzoMiercoles=(TextView)findViewById(R.id.tvAlmuerzoMiercoles);
        tvComidaMiercoles=(TextView) findViewById(R.id.tvComidaMiercoles);
        tvCenaMiercoles=(TextView) findViewById(R.id.tvCenaMiercoles);
        tvAlmuerzoJueves=(TextView)findViewById(R.id.tvAlmuerzoJueves);
        tvComidaJueves=(TextView) findViewById(R.id.tvComidaJueves);
        tvCenaJueves=(TextView) findViewById(R.id.tvCenaJueves);
        tvAlmuerzoViernes=(TextView)findViewById(R.id.tvAlmuerzoViernes);
        tvComidaViernes=(TextView) findViewById(R.id.tvComidaViernes);
        tvCenaViernes=(TextView) findViewById(R.id.tvCenaViernes);
        tvAlmuerzoSabado=(TextView)findViewById(R.id.tvAlmuerzoSabado);
        tvComidaSabado=(TextView) findViewById(R.id.tvComidaSabado);
        tvCenaSabado=(TextView) findViewById(R.id.tvCenaSabado);
        tvAlmuerzoDomingo=(TextView)findViewById(R.id.tvAlmuerzoDomingo);
        tvComidaDomingo=(TextView) findViewById(R.id.tvComidaDomingo);
        tvCenaDomingo=(TextView) findViewById(R.id.tvCenaDomingo);

        final Calendar c = Calendar.getInstance();
        txtFecha.setText("SEMANA: " + (c.get(Calendar.WEEK_OF_YEAR) - 1));
    }
    public void llenarVista(View v){
        predao = new PreparacionesDao(this);
        ArrayList<Object[]> listapre = predao.buscarHorario(txtFecha.getText().toString());
        for(Object[] o: listapre){
            if("AlmuerzoLunes".equals(o[0])){
                tvAlmuerzoLunes.setText(o[1].toString());
            }
            if("ComidaLunes".equals(o[0])){
                tvComidaLunes.setText(o[1].toString());
            }
            if("CenaLunes".equals(o[0])){
                tvCenaLunes.setText(o[1].toString());
            }
        }

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
        recreate();
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
    @Override
    protected void onPause() {
        super.onPause();
        //Cerramos Drawer
        Menu.closeDrawer(dl);
    }
}