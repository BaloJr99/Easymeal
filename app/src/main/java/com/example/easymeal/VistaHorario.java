package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_horario);
        dl=findViewById(R.id.drawer_vista);
        txtFecha= findViewById(R.id.txtFecha);
        tvAlmuerzoLunes=findViewById(R.id.tvAlmuerzoLunes);
        tvComidaLunes= findViewById(R.id.tvComidaLunes);
        tvCenaLunes= findViewById(R.id.tvCenaLunes);
        tvAlmuerzoMartes=findViewById(R.id.tvAlmuerzoMartes);
        tvComidaMartes= findViewById(R.id.tvComidaMartes);
        tvCenaMartes= findViewById(R.id.tvCenaMartes);
        tvAlmuerzoMiercoles=findViewById(R.id.tvAlmuerzoMiercoles);
        tvComidaMiercoles= findViewById(R.id.tvComidaMiercoles);
        tvCenaMiercoles= findViewById(R.id.tvCenaMiercoles);
        tvAlmuerzoJueves=findViewById(R.id.tvAlmuerzoJueves);
        tvComidaJueves= findViewById(R.id.tvComidaJueves);
        tvCenaJueves= findViewById(R.id.tvCenaJueves);
        tvAlmuerzoViernes=findViewById(R.id.tvAlmuerzoViernes);
        tvComidaViernes= findViewById(R.id.tvComidaViernes);
        tvCenaViernes= findViewById(R.id.tvCenaViernes);
        tvAlmuerzoSabado=findViewById(R.id.tvAlmuerzoSabado);
        tvComidaSabado= findViewById(R.id.tvComidaSabado);
        tvCenaSabado= findViewById(R.id.tvCenaSabado);
        tvAlmuerzoDomingo=findViewById(R.id.tvAlmuerzoDomingo);
        tvComidaDomingo= findViewById(R.id.tvComidaDomingo);
        tvCenaDomingo= findViewById(R.id.tvCenaDomingo);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final Calendar c = Calendar.getInstance();
        txtFecha.setText("SEMANA: " + (c.get(Calendar.WEEK_OF_YEAR) - 1));
        llenarVista();
    }

    public void llenarVista(){
        predao = new PreparacionesDao(this);
        ArrayList<Object[]> listapre = predao.buscarHorarioSemanal(txtFecha.getText().toString());
        if(listapre != null){
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

                if("AlmuerzoMartes".equals(o[0])){
                    tvAlmuerzoMartes.setText(o[1].toString());
                }
                if("ComidaMartes".equals(o[0])){
                    tvComidaMartes.setText(o[1].toString());
                }
                if("CenaMartes".equals(o[0])){
                    tvCenaMartes.setText(o[1].toString());
                }

                if("AlmuerzoMiercoles".equals(o[0])){
                    tvAlmuerzoMiercoles.setText(o[1].toString());
                }
                if("ComidaMiercoles".equals(o[0])){
                    tvComidaMiercoles.setText(o[1].toString());
                }
                if("CenaMiercoles".equals(o[0])){
                    tvCenaMiercoles.setText(o[1].toString());
                }

                if("AlmuerzoJueves".equals(o[0])){
                    tvAlmuerzoJueves.setText(o[1].toString());
                }
                if("ComidaJueves".equals(o[0])){
                    tvComidaJueves.setText(o[1].toString());
                }
                if("CenaJueves".equals(o[0])){
                    tvCenaJueves.setText(o[1].toString());
                }

                if("AlmuerzoViernes".equals(o[0])){
                    tvAlmuerzoViernes.setText(o[1].toString());
                }
                if("ComidaViernes".equals(o[0])){
                    tvComidaViernes.setText(o[1].toString());
                }
                if("CenaViernes".equals(o[0])){
                    tvCenaViernes.setText(o[1].toString());
                }

                if("AlmuerzoSabado".equals(o[0])){
                    tvAlmuerzoSabado.setText(o[1].toString());
                }
                if("ComidaSabado".equals(o[0])){
                    tvComidaSabado.setText(o[1].toString());
                }
                if("CenaSabado".equals(o[0])){
                    tvCenaSabado.setText(o[1].toString());
                }

                if("AlmuerzoDomingo".equals(o[0])){
                    tvAlmuerzoDomingo.setText(o[1].toString());
                }
                if("ComidaDomingo".equals(o[0])){
                    tvComidaDomingo.setText(o[1].toString());
                }
                if("CenaDomingo".equals(o[0])){
                    tvCenaDomingo.setText(o[1].toString());
                }
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
        Menu.redirectActivity(this, AcercaNosotros.class, "");
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class, "");
    }

    public void ClickHorarioSemana(View view) {
        //Redireccionamos actividad a dashboard
        recreate();
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