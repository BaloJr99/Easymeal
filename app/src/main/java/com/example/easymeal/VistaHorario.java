package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.dao.IngredienteDao;

public class VistaHorario extends AppCompatActivity {
    DrawerLayout dl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_horario);
        dl=findViewById(R.id.drawer_vista);
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
    {
        float cantidad;
        String marca = "";
        ingDao = new IngredienteDao();
        ingDao.ingredienteDao(this);

        listaIng = ingDao.listaMandado(tipo);
        TableRow.LayoutParams lfila = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TableRow.LayoutParams lcomun = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4f);
        TableRow.LayoutParams lacciones = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        for(Ingrediente listing: listaIng){
            tring = new TableRow(this);
            tring.setLayoutParams(lfila);

            cbMarcar = new CheckBox(this);
            cbMarcar.setTag(listing.getIdIngrediente());
            cbMarcar.setWidth(35);
            cbMarcar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    TableRow tr = (TableRow) compoundButton.getParent();
                    if(compoundButton.isChecked()){
                        tr.setBackgroundColor(Color.LTGRAY);
                    }else{
                        tr.setBackgroundColor(0);
                    }
                }
            });
            tring.addView(cbMarcar);

            if(tipo.equals("mandado")){
                cantidad = listing.getCantidadAComprar();
            }else{
                cbMarcar.setEnabled(false);
                cantidad = listing.getCantidad();
            }

            marca = listing.getProveedor();

            if(marca.equals("NA")){
                marca = "";
            }
            tvDescripcion = new TextView(this);
            tvDescripcion.setText(cantidad + " " + listing.getUnidadDeMedida() + " " + listing.getDescripcion() + " " + marca);
            tvDescripcion.setLayoutParams(lcomun);
            tvDescripcion.setTextSize(20);
            tvDescripcion.setGravity(Gravity.CENTER_VERTICAL);
            tring.addView(tvDescripcion);

            ivEliminar = new ImageView(this);
            ivEliminar.setLayoutParams(lacciones);

            ivEditar = new ImageView(this);
            ivEditar.setLayoutParams(lacciones);

            ivEditar.setTag(listing.getIdIngrediente());
            ivEditar.setImageResource(R.drawable.ic_edit);
            ivEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow tablerow = (TableRow) view.getParent();
                    ImageView items = (ImageView) tablerow.getChildAt(2);
                    ClickEditar(Integer.parseInt(items.getTag().toString()));
                }
            });

            tring.addView(ivEditar);
            tring.addView(ivEliminar);

            tling.addView(tring);

        }
    }
}