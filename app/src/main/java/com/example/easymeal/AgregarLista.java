package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easymeal.Excepciones.MisExcepciones;
import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.dao.IngredienteDao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AgregarLista extends AppCompatActivity {

    //Inicializamos variable
    DrawerLayout dl;
    EditText etCantidad, etMarca, etDescripcion, etMedida, etFecha;
    Spinner sMedida, sMarca, sDescripcion;
    ImageView ivFoto, ivFecha;
    LinearLayout llfecha;
    Ingrediente ing;
    IngredienteDao ingdao;
    ArrayList<Ingrediente> listaing;
    Button btnModificar;

    ArrayList<String> listadesc = new ArrayList<>();
    ArrayList<String> listaMedida = new ArrayList<>();
    ArrayList<String> listaprodu = new ArrayList<>();

    String tipo, marca, medida, descripcion;
    int idIngrediente;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_agregar_lista);

        //Asignamos variable
        dl = findViewById(R.id.drawer_agregar_lista);
        etDescripcion = findViewById(R.id.etDescripcion);
        etMarca = findViewById(R.id.etMarca);
        etCantidad = findViewById(R.id.etCantidad);
        sDescripcion = findViewById(R.id.sDescripcion);
        ivFoto = findViewById(R.id.ivFoto);
        btnModificar = findViewById(R.id.modificarLista);
        sDescripcion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sDescripcion.getSelectedItemPosition() == 0){
                    etDescripcion.setEnabled(true);
                    if(tipo.equals("mandado")){
                        ivFoto.setImageResource(R.drawable.ic_camara);
                        ivFoto.setTag("pred");
                        ivFoto.setVisibility(View.GONE);
                        llfecha.setVisibility(View.GONE);
                    }
                }else{
                    etDescripcion.setEnabled(false);
                    etDescripcion.setText("");
                    ingdao = new IngredienteDao();
                    ingdao.ingredienteDao(AgregarLista.this);
                    listaing = ingdao.listaIngredientes();
                    for(Ingrediente in: listaing){
                        if(in.getDescripcion().equals(sDescripcion.getSelectedItem().toString())){
                            if(in.getImagen() != null){
                                ivFoto.setVisibility(View.VISIBLE);
                                Bitmap bmp = BitmapFactory.decodeByteArray(in.getImagen(),0,in.getImagen().length);
                                ivFoto.setImageBitmap(bmp);
                                ivFoto.setTag("nopred");
                            }else{
                                ivFoto.setImageResource(R.drawable.ic_camara);
                                ivFoto.setTag("pred");
                                if(tipo.equals("mandado")){
                                    ivFoto.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sMarca = findViewById(R.id.sMarca);

        sMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sMarca.getSelectedItemPosition() == 0){
                    etMarca.setEnabled(true);
                }else{
                    etMarca.setEnabled(false);
                    etMarca.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sMedida = findViewById(R.id.sMedida);

        sMedida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sMedida.getSelectedItemPosition() == 0){
                    etMedida.setEnabled(true);
                }else{
                    etMedida.setEnabled(false);
                    etMedida.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ivFoto = findViewById(R.id.ivFoto);
        etMedida = findViewById(R.id.etMedida);
        etFecha = findViewById(R.id.etFecha);
        ivFecha = findViewById(R.id.ivFecha);
        llfecha = findViewById(R.id.llfecha);

        Bundle parametros = this.getIntent().getExtras();
        tipo = parametros.getString("tipo");
        idIngrediente = parametros.getInt("idIngrediente");

        if(idIngrediente != 0){
            btnModificar.setText("MODIFICAR");
            ingdao = new IngredienteDao();
            ingdao.ingredienteDao(this);
            ing = ingdao.buscarIngrediente(idIngrediente);
            etFecha.setText(ing.getFechaCaducidad());
            if(tipo.equals("mandado")){
                etCantidad.setText(String.valueOf(ing.getCantidadAComprar()));
            }else{
                etCantidad.setText(String.valueOf(ing.getCantidad()));
            }

            marca = ing.getProveedor();
            descripcion = ing.getDescripcion();
            medida = ing.getUnidadDeMedida();
        }

        llenarSpinners();
    }

    public void ClickMenu(View v){
        //Abrimos Drawer
        Menu.openDrawer(dl);
    }

    public void ClickLogo(View v){
        //Cerramos drawer
        Menu.closeDrawer(dl);
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
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

    public void ClickSalir(View v){
        //Cerramos app
        Menu.logout(this);
    }

    public void ClickSalirAgregar(View v){
        //Cerramos app
        Menu.redirectActivity(this, ListaMandado.class, tipo);
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class, "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Cerramos Drawer
        Menu.closeDrawer(dl);
    }

    public void ClickAgregarLista(View view) {
        try {

            String descripcion;
            String medida;
            String marca;

            String fechaCaducidad = null;

            ingdao = new IngredienteDao();
            ingdao.ingredienteDao(this);
            ing = new Ingrediente();
            Bitmap bitmap = null;
            byte[] img = null;

            if(!ivFoto.getTag().equals("pred")){
                bitmap = ((BitmapDrawable)ivFoto.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
                img = bos.toByteArray();
            }

            if(!camposVacios()){
                if(sDescripcion.getSelectedItemPosition() == 0){
                    descripcion = etDescripcion.getText().toString();
                }else{
                    descripcion = sDescripcion.getSelectedItem().toString();
                }

                if(sMedida.getSelectedItemPosition() == 0){
                    medida = etMedida.getText().toString();
                }else{
                    medida = sMedida.getSelectedItem().toString();
                }

                if(sMarca.getSelectedItemPosition() == 0){
                    marca = etMarca.getText().toString();
                }else{
                    marca = sMarca.getSelectedItem().toString();
                }

                if(!tipo.equals("mandado")){
                    fechaCaducidad = etFecha.getText().toString();
                }

                ing.setDescripcion(descripcion);
                ing.setUnidadDeMedida(medida);
                ing.setImagen(img);
                ing.setProveedor(marca);

                if(tipo.equals("mandado")){
                    ing.setMandado(1);
                    ing.setCantidadAComprar(Float.valueOf(etCantidad.getText().toString()));
                    ing.setCantidad(0f);
                }else{
                    ing.setMandado(0);
                    ing.setCantidadAComprar(0f);
                    ing.setCantidad(Float.valueOf(etCantidad.getText().toString()));
                }

                ing.setFechaCaducidad(fechaCaducidad);

                if(ingdao.insertarLista(ing) != -1){
                    if(idIngrediente != 0){
                        Toast.makeText(this, "MODIFICADO", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, "INSERTADO", Toast.LENGTH_LONG).show();
                    }
                }else{
                    throw new Exception();
                }
                limpiarCampos();
            }
        } catch (Exception e) {
            System.out.println("Error al INSERTAR");
            System.out.println(e.getMessage());
        }
    }

    public boolean camposVacios(){
        try {
            if(etDescripcion.getText().toString().trim().isEmpty()&&sDescripcion.getSelectedItemPosition() == 0){
                throw new MisExcepciones(1);
            }else if((etCantidad.getText().toString().trim().isEmpty() || Float.parseFloat(etCantidad.getText().toString()) == 0f)){
                throw new MisExcepciones(2);
            }else if(etMarca.getText().toString().trim().isEmpty()&&sMarca.getSelectedItemPosition() == 0){
                throw new MisExcepciones(3);
            }else if(etMedida.getText().toString().trim().isEmpty()&&sMedida.getSelectedItemPosition() == 0){
                throw new MisExcepciones(4);
            }

            if(!tipo.equals("mandado")){
                if(etFecha.getText().toString().trim().isEmpty()){
                    throw new MisExcepciones(5);
                }else if(ivFoto.getTag().equals("pred")){
                    throw new MisExcepciones(6);
                }
            }

            return false;
        } catch (MisExcepciones me) {
            Toast.makeText(this, me.getMessage(), Toast.LENGTH_LONG).show();
            return true;
        }
    }

    public void llenarSpinners() {
        ingdao = new IngredienteDao();
        ingdao.ingredienteDao(this);
        listaing = ingdao.listaIngredientes();
        listadesc.clear();
        listaMedida.clear();
        listaprodu.clear();
        listadesc.add("Seleccione...");
        listaMedida.add("Seleccione...");
        listaprodu.add("Seleccione...");

        int iDescripcion = 0;
        int iUnidadMedida = 0;
        int iProveedor = 0;

        int iDescripcionS = 0;
        int iUnidadMedidaS = 0;
        int iProveedorS = 0;

        for(Ingrediente ingre: listaing){
            if(!listadesc.contains(ingre.getDescripcion())){
                iDescripcion++;
                if(ingre.getDescripcion().equals(descripcion)){
                    iDescripcionS = iDescripcion;
                }
                listadesc.add(ingre.getDescripcion());
            }

            if(!listaMedida.contains(ingre.getUnidadDeMedida())){
                iUnidadMedida++;
                if(ingre.getUnidadDeMedida().equals(medida)){
                    iUnidadMedidaS = iUnidadMedida;
                }
                listaMedida.add(ingre.getUnidadDeMedida());
            }

            if(!listaprodu.contains(ingre.getProveedor())){
                iProveedor++;
                if(ingre.getProveedor().equals(marca)){
                    iProveedorS = iProveedor;
                }
                listaprodu.add(ingre.getProveedor());
            }
        }

        ArrayAdapter<String> arrayAdapter;

        arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listadesc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDescripcion.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaMedida);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMedida.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaprodu);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMarca.setAdapter(arrayAdapter);

        sDescripcion.setSelection(iDescripcionS);
        sMedida.setSelection(iUnidadMedidaS);
        sMarca.setSelection(iProveedorS);
    }

    @SuppressLint("SetTextI18n")
    public void limpiarCampos(){
        etDescripcion.setText("");
        etCantidad.setText("0.00");
        etMarca.setText("");
        etMedida.setText("");
        ivFoto.setImageResource(R.drawable.ic_camara);
        ivFoto.setTag("pred");
        etFecha.setText("");
        llenarSpinners();
        sDescripcion.setSelection(0);
        sMedida.setSelection(0);
        sMarca.setSelection(0);
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void ClickFoto(View view) {
        Intent foto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(foto.resolveActivity(getPackageManager()) != null){
            startActivityForResult(foto, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivFoto.setImageBitmap(imageBitmap);
            ivFoto.setTag("nopred");
        }
    }

    public void ClickFecha(View view) {
        int dia, mes, ano;
        Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        @SuppressLint("SetTextI18n") DatePickerDialog date = new DatePickerDialog(this, (datePicker, anio, mes1, dia1) -> etFecha.setText(dia1 +"/"+ mes1 +"/"+anio), ano, mes, dia);
        date.show();
    }
}