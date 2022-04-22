package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.dao.IngredienteDao;
import com.example.easymeal.pdf.TemplatePDF;

import java.util.ArrayList;
import java.util.Calendar;

public class ListaMandado extends AppCompatActivity {

    //Inicializamos variable
    DrawerLayout dl;
    Button btnAgregar, btnGenerar;

    IngredienteDao ingDao;
    ArrayList<Ingrediente> listaIng;

    TableLayout tling;
    TableRow tring;

    TextView tvDescripcion, tvTitulo;
    ImageView ivEliminar, ivEditar;
    CheckBox cbMarcar;
    String tipo = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lista_mandado);

        //Asignamos variable
        dl = findViewById(R.id.drawer_listamandado);
        btnAgregar = findViewById(R.id.agregarlista);
        btnGenerar = findViewById(R.id.btnGenerar);
        tvTitulo = findViewById(R.id.tvTitulo);
        tling = findViewById(R.id.tling);

        Bundle datos = this.getIntent().getExtras();
        tipo = datos.getString("tipo");
        if(!tipo.equals("mandado")){
            btnGenerar.setVisibility(View.GONE);
            tvTitulo.setText("Alacena");
        }
        llenarMandado();
        btnAgregar.setOnClickListener(view -> {
            Intent abrirAgregarLista = new Intent(ListaMandado.this, AgregarLista.class);
            abrirAgregarLista.putExtra("tipo", tipo);
            startActivity(abrirAgregarLista);
        });

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

    }

    public void ClickMenu(View v){
        //Abrimos Drawer
        Menu.openDrawer(dl);
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
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

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class, "");
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class, "");
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

    public void ClickEliminar(int id) {
        ingDao = new IngredienteDao();
        ingDao.ingredienteDao(this);
        if(ingDao.eliminarLista(id)){
            Toast.makeText(this, "ELIMINADO", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
        this.recreate();
    }

    public void ClickEditar(int idIngrediente) {
        Intent abrirAgregarLista = new Intent(ListaMandado.this, AgregarLista.class);
        abrirAgregarLista.putExtra("tipo", tipo);
        abrirAgregarLista.putExtra("idIngrediente", idIngrediente);
        startActivity(abrirAgregarLista);
    }

    @SuppressLint("SetTextI18n")
    public void llenarMandado(){
        float cantidad;
        String marca;
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
            cbMarcar.setOnCheckedChangeListener((compoundButton, b) -> {
                TableRow tr = (TableRow) compoundButton.getParent();
                if(compoundButton.isChecked()){
                    tr.setBackgroundColor(Color.LTGRAY);
                }else{
                    tr.setBackgroundColor(0);
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

            if(tipo.equals("mandado")){
                ivEliminar.setTag(listing.getIdIngrediente());
                ivEliminar.setImageResource(R.drawable.ic_delete);
                ivEliminar.setOnClickListener(view -> {
                    TableRow tablerow = (TableRow) view.getParent();
                    ImageView items = (ImageView) tablerow.getChildAt(3);

                    ClickEliminar(Integer.parseInt(items.getTag().toString()));
                });

            }

            ivEditar.setTag(listing.getIdIngrediente());
            ivEditar.setImageResource(R.drawable.ic_edit);
            ivEditar.setOnClickListener(view -> {
                TableRow tablerow = (TableRow) view.getParent();
                ImageView items = (ImageView) tablerow.getChildAt(2);
                ClickEditar(Integer.parseInt(items.getTag().toString()));
            });

            tring.addView(ivEditar);
            tring.addView(ivEliminar);

            tling.addView(tring);

        }
    }

    private final String[] header = {"Nombre", "Cantidad", "Marca"};

    public void ClickGenerar(View view) {
        TemplatePDF templatePDF = new TemplatePDF(this);
        templatePDF.openDocument();
        templatePDF.addMetaData("Lista de Mandado de la semana" + Calendar.getInstance().get(Calendar.WEEK_OF_YEAR), "Lista de Mandado", "Braulio");
        templatePDF.addTitles("Easy Meal", "Lista de Mandado",
                Calendar.getInstance().getTime().toString());
        templatePDF.createTable(header, listaIng);
        templatePDF.closeDocument();
        templatePDF.appViewPDF(this);
    }
}