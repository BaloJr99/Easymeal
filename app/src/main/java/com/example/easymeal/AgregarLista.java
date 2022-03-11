package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.Producto;
import com.example.easymeal.cl.model.dao.ProductoDao;
import com.example.easymeal.cl.model.dao.IngredienteDao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AgregarLista extends AppCompatActivity {

    //Inicializamo variable
    DrawerLayout dl;
    EditText etcantidad, etmarca, etdescripcion;
    Spinner scantidad, smarca, sdescripcion;
    ImageView ivfoto;
    Producto pro;
    Ingrediente ing;
    IngredienteDao ingdao;
    ProductoDao prodao;
    ArrayList<Ingrediente> listaing;
    ArrayList<Producto> listaprod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_lista);

        //Asignamos variable
        dl = findViewById(R.id.drawer_agregar_lista);
        etdescripcion = findViewById(R.id.etDescripcion);
        etmarca = findViewById(R.id.etMarca);
        etcantidad = findViewById(R.id.etCantidad);
        sdescripcion = findViewById(R.id.sDescripcion);
        smarca = findViewById(R.id.sMarca);
        scantidad = findViewById(R.id.sCantidad);
        ivfoto = findViewById(R.id.ivFoto);

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

    public void AgregarMarca(View view) {
        String marca = etmarca.getText().toString();
        if(!marca.trim().isEmpty()){
            prodao = new ProductoDao();
            prodao.productoDao(this);
            pro = new Producto();
            pro.setProveedor(marca);
            prodao.insertarProducto(pro);
            Toast.makeText(this, "INSERTADO", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Escribir el proveedor", Toast.LENGTH_LONG).show();
        }

    }

    public void ClickAgregarLista(View view) {
        String descripcion = etdescripcion.getText().toString();
        String cantidad = etcantidad.getText().toString();
        if(!descripcion.trim().isEmpty() && !cantidad.trim().isEmpty()){
            ingdao = new IngredienteDao();
            ingdao.ingredienteDao(this);
            ing = new Ingrediente();
            ing.setDescripcion(descripcion);
            ing.setCantidad(Integer.parseInt(cantidad));
            Bitmap bitmap = ((BitmapDrawable)ivfoto.getDrawable()).getBitmap();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] img = bos.toByteArray();
            ing.setImagen(img);
            ingdao.insertarIngrediente(ing);
            Toast.makeText(this, "INSERTADO", Toast.LENGTH_LONG).show();
            limpiarCampos();
        }else{
            Toast.makeText(this, "Hay campos vacíos", Toast.LENGTH_LONG).show();
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
        ArrayList<String> listacant = new ArrayList<>();
        ArrayList<String> listaprodu = new ArrayList<>();
        listadesc.add("Seleccione...");
        listacant.add("Seleccione...");
        listaprodu.add("Seleccione...");

        for(Ingrediente ingre: listaing){
            listadesc.add(ingre.getDescripcion());
            listacant.add(String.valueOf(ingre.getCantidad()));
        }

        for(Producto produc: listaprod){
            listaprodu.add(produc.getProveedor());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listadesc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sdescripcion.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listacant);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scantidad.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaprodu);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smarca.setAdapter(arrayAdapter);
    }

    public void limpiarCampos(){
        etdescripcion.setText("");
        etcantidad.setText("");
        etmarca.setText("");
        ivfoto.setImageBitmap(null);
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
            ivfoto.setImageBitmap(imageBitmap);
        }
    }
}