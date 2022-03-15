package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ListaMandado extends AppCompatActivity {

    //Inicializamo variable
    DrawerLayout dl;
    Button btnAgregar;
    static String username

    IngredienteDao ingDao;
    Ingrediente ing;

    TableLayout tling;
    TableRow tring;

    TextView tvDescripcion, tvCantidad;
    ImageView ivEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_lista_mandado);

        //Asignamos variable
        dl = findViewById(R.id.drawer_listamandado);
        btnAgregar = findViewById(R.id.agregarlista);
        tling = findViewById(R.id.tling);
        llenarMandado();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirAgregarLista = new Intent(ListaMandado.this, AgregarLista.class);
                startActivity(abrirAgregarLista);
            }
        });
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
        Menu.redirectActivity(this, Menu.class);
    }

    public void ClickRecetas(View v){
        //Redireccionamos actividad a tablero
        Menu.redirectActivity(this, Recetas.class);
    }

    public void ClickAcercaDe(View v){
        //Recreamos actividad
        Menu.redirectActivity(this, AcercaNosotros.class);
    }

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        recreate();
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class);
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class);
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

    public void ClickEliminar() {
        Toast.makeText(ListaMandado.this, "Se dio clic a eliminar", Toast.LENGTH_LONG).show();
    }

    public void llenarMandado(){
        ingDao = new IngredienteDao();
        ingDao.ingredienteDao(this);
        ArrayList<Ingrediente> listaIng = ingDao.listaMandado();
        TableRow.LayoutParams lfila = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TableRow.LayoutParams ldescripcion = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 10f);
        TableRow.LayoutParams lcantidad = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 10f);
        TableRow.LayoutParams leliminar = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        for(Ingrediente listing: listaIng){
            tring = new TableRow(this);
            tring.setLayoutParams(lfila);

            tvDescripcion = new TextView(this);
            tvDescripcion.setText(listing.getDescripcion());
            tvDescripcion.setLayoutParams(ldescripcion);
            tvDescripcion.setGravity(Gravity.CENTER);
            tring.addView(tvDescripcion);

            tvCantidad = new TextView(this);
            tvCantidad.setText(String.valueOf(listing.getCantidad()));
            tvCantidad.setLayoutParams(lcantidad);
            tvCantidad.setGravity(Gravity.CENTER);
            tring.addView(tvCantidad);

            ivEliminar = new ImageView(this);
            ivEliminar.setImageResource(R.drawable.ic_delete);
            ivEliminar.setLayoutParams(leliminar);
            ivEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClickEliminar();
                }
            });
            tring.addView(ivEliminar);

            tling.addView(tring);

        }
    }
}