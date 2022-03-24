package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easymeal.Excepciones.MisExcepciones;
import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.Producto;
import com.example.easymeal.cl.model.dao.ProductoDao;
import com.example.easymeal.cl.model.dao.IngredienteDao;
import com.example.easymeal.cl.model.dao.ProductoIngredienteDao;

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
    Producto pro;
    Ingrediente ing;
    IngredienteDao ingdao;
    ProductoDao prodao;
    ProductoIngredienteDao proingdao;
    ArrayList<Ingrediente> listaing;
    ArrayList<Producto> listaprod;

    String tipo;

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
        sDescripcion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sDescripcion.getSelectedItemPosition() == 0){
                    etDescripcion.setEnabled(true);
                    if(tipo.equals("mandado")){
                        ivFoto.setVisibility(View.GONE);
                        llfecha.setVisibility(View.GONE);
                    }
                }else{
                    etDescripcion.setEnabled(false);
                    System.out.println("Hola");
                    etDescripcion.setText("");
                    System.out.println("Hola1");
                    ingdao = new IngredienteDao();
                    System.out.println("Hola2");
                    ingdao.ingredienteDao(AgregarLista.this);
                    System.out.println("Hola3");
                    listaing = ingdao.listaIngredientes();
                    System.out.println("Hola4");
                    for(Ingrediente in: listaing){
                        System.out.println("Hola5");
                        if(in.getDescripcion().equals(sDescripcion.getSelectedItem().toString())){
                            System.out.println("Hola6");
                            if(in.getImagen() != null){
                                System.out.println("Hola7");
                                ivFoto.setVisibility(View.VISIBLE);
                                System.out.println("Hola8");
                                Bitmap bmp = BitmapFactory.decodeByteArray(in.getImagen(),0,in.getImagen().length);
                                System.out.println("Hola9");
                                ivFoto.setImageBitmap(bmp);
                                System.out.println("Hola10");
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
        if(parametros != null) {
            tipo = parametros.getString("tipo");
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
        Menu.redirectActivity(this, ListaMandado.class);
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class);
    }

    public void ClickSalir(View v){
        //Cerramos app
        Menu.logout(this);
    }

    public void ClickSalirAgregar(View v){
        //Cerramos app
        Menu.redirectActivity(this, ListaMandado.class);
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class);
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
            prodao = new ProductoDao();
            prodao.productoDao(this);
            proingdao = new ProductoIngredienteDao();
            proingdao.productoIngredienteDao(this);
            pro = new Producto();
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
                pro.setProveedor(marca);
                ing.setImagen(img);

                if(tipo.equals("mandado")){
                    ing.setMandado(1);
                    ing.setCantidadAComprar(Float.valueOf(etCantidad.getText().toString()));
                    ing.setCantidad(0f);
                    ing.setFechaCaducidad(fechaCaducidad);
                }else{
                    ing.setMandado(0);
                    ing.setCantidadAComprar(0f);
                    ing.setCantidad(Float.valueOf(etCantidad.getText().toString()));
                    ing.setFechaCaducidad(fechaCaducidad);
                }
                int insertaprod = prodao.insertarProducto(pro);
                int insertaing = ingdao.insertarLista(ing);
                proingdao.insertarProductoIngredienteDao(insertaprod, insertaing);
                Toast.makeText(this, "INSERTADO", Toast.LENGTH_LONG).show();
                limpiarCampos();
            }
        } catch (Exception e) {
            System.out.println("Error");
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
        prodao = new ProductoDao();
        prodao.productoDao(this);
        listaprod = prodao.listaProducto();

        ArrayList<String> listadesc = new ArrayList<>();
        ArrayList<String> listaMedida = new ArrayList<>();
        ArrayList<String> listaprodu = new ArrayList<>();
        listadesc.add("Seleccione...");
        listaMedida.add("Seleccione...");
        listaprodu.add("Seleccione...");

        for(Ingrediente ingre: listaing){
            if(!listadesc.contains(ingre.getDescripcion())){
                listadesc.add(ingre.getDescripcion());
            }

            if(!listaMedida.contains(ingre.getUnidadDeMedida())){
                listaMedida.add(ingre.getUnidadDeMedida());
            }
        }

        for(Producto produc: listaprod){
            listaprodu.add(produc.getProveedor());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listadesc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDescripcion.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaMedida);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMedida.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaprodu);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMarca.setAdapter(arrayAdapter);
    }

    public void limpiarCampos(){
        etDescripcion.setText("");
        etCantidad.setText("0.00");
        etMarca.setText("");
        etMedida.setText("");
        ivFoto.setImageResource(R.drawable.ic_camara);
        ivFoto.setTag("pred");
        etFecha.setText("");
        sDescripcion.setSelection(0);
        sMedida.setSelection(0);
        sMarca.setSelection(0);
        llenarSpinners();
    }

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

        DatePickerDialog date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                etFecha.setText(dia+"/"+mes+"/"+anio);
            }
        }, ano, mes, dia);
        date.show();
    }
}