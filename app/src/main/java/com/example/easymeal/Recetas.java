package com.example.easymeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.easymeal.cl.model.bd.Ingrediente;
import com.example.easymeal.cl.model.bd.IngredienteReceta;
import com.example.easymeal.cl.model.bd.Receta;

import com.example.easymeal.cl.model.dao.IngredienteDao;
import com.example.easymeal.cl.model.dao.RecetaDao;

import com.example.easymeal.cl.model.dao.RecetaIngredienteDao;
import com.example.easymeal.cl.model.dao.daoUsuario;
import com.example.easymeal.getToken.AsyncResponse;
import com.example.easymeal.getToken.GetToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Recetas extends AppCompatActivity implements AsyncResponse {
    ArrayAdapter adapter;
    ListView listaRecetas;
    ArrayList<String> infoList;
    ArrayList<Receta> recetasList;
    EditText nom,pasos,cantidad;
    Button agregarIng,nuevoIng,verIng;
    ImageButton insertar,editar,buscar,borrar;
    RecetaDao dao;
    RecetaIngredienteDao daoing;
    ArrayList<Receta> busqueda;
    Spinner ing;
    Integer idReceta, idIngrediente;

    ImageButton paymentButton;
    private TextView paymentTV;

    Dialog dialog;

    private static final int REQUEST_CODE = 1234;
    final String API_GET_CHEKOUT = "http://192.168.0.10/braintree/checkout.php";

    String token, amount;
    HashMap<String, String> paramsHash;

    static int id;

    daoUsuario udao;

    //Inicializamos variable
    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recetas);
        dl = findViewById(R.id.drawer_recetas);
        nom = findViewById(R.id.fieldNombre);
        pasos = findViewById(R.id.mFieldPasos);
        cantidad = findViewById(R.id.txtCantidad);
        insertar = findViewById(R.id.btnAgregar);
        editar = findViewById(R.id.btnEdit);
        buscar = findViewById(R.id.btnBuscar);
        borrar = findViewById(R.id.btnBorrar);
        agregarIng = findViewById(R.id.btnAgregarIng);
        nuevoIng = findViewById(R.id.btnNuevoIng);
        verIng = findViewById(R.id.btnVerIng);
        ing =  findViewById(R.id.spiingrediente);
        dao = new RecetaDao(this);
        listaRecetas = findViewById(R.id.listaRecetas);
        poblar();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,infoList);
        listaRecetas.setAdapter(adapter);

        ArrayList<Ingrediente> lista = new IngredienteDao(this).listaIngredientes();

        ArrayList<String> list = new ArrayList<>();
        for (Ingrediente i:lista) {
            list.add(i.getDescripcion());
        }

        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,list);
        ing.setAdapter(a);


        listaRecetas.setOnItemClickListener((adapterView, view, i, l) -> {

            nom.setText(recetasList.get(i).getNombre());
            pasos.setText(recetasList.get(i).getPasos());
            idReceta = recetasList.get(i).getIdReceta();
            if(ing.getAdapter().getCount() != 0){
                agregarIng.setEnabled(true);
                verIng.setEnabled(true);
            }else{
                nuevoIng.setEnabled(true);
            }

        });
        insertar.setOnClickListener(view -> {

            Receta c = new Receta();
            c.setNombre(nom.getText().toString());
            c.setPasos(pasos.getText().toString());
            if(!c.isNull()){
                Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
            }else if(dao.insertarReceta(c)) {
                Toast.makeText(Recetas.this, "Registro Exitoso", Toast.LENGTH_LONG).show();

                idReceta = new RecetaDao(this).maxId();

                poblar();
                ArrayAdapter adapter = new ArrayAdapter(Recetas.this, android.R.layout.simple_expandable_list_item_1,infoList);
                listaRecetas.setAdapter(adapter);
                if(ing.getSelectedItem() != null){
                    agregarIng.setEnabled(true);
                    nuevoIng.setEnabled(true);
                }else{
                    Toast.makeText(Recetas.this,"Receta ya registrada",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(Recetas.this,"Receta ya registrada",Toast.LENGTH_LONG).show();
            }
        });

        editar.setOnClickListener(view -> {
            Receta c = new Receta();
            c.setNombre(nom.getText().toString());
            c.setPasos(pasos.getText().toString());
            if(!c.isNull()){
                Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
            }else if(dao.updateReceta(c)){
                idReceta = new RecetaDao(this).idReceta(nom.getText().toString());
                poblar();
                ArrayAdapter adapter = new ArrayAdapter(Recetas.this, android.R.layout.simple_expandable_list_item_1,infoList);
                listaRecetas.setAdapter(adapter);
                agregarIng.setEnabled(true);
                Toast.makeText(Recetas.this,"Registro Editado Exitoso",Toast.LENGTH_LONG).show();
                nom.setText("");
                pasos.setText("");
            }
        });
        buscar.setOnClickListener(view -> {
            if(nom.getText().toString().equals("")){
                Toast.makeText(Recetas.this,"ERROR: CAMPO DE NOMBRE VACIO",Toast.LENGTH_LONG).show();
            }else{
                busqueda = dao.selectReceta(nom.getText().toString());
                pasos.setText(busqueda.get(0).getPasos());
                idReceta = recetasList.get(0).getIdReceta();
                poblar();
                agregarIng.setEnabled(true);
            }
        });
        agregarIng.setOnClickListener(view -> {
            idIngrediente = new IngredienteDao(this).buscarIngredienteRecetas(ing.getSelectedItem().toString());
            IngredienteReceta r = new IngredienteReceta();
            if(!cantidad.getText().toString().trim().isEmpty()){
                r.setCantidad(Float.parseFloat(cantidad.getText().toString()));
                r.setIdReceta(idReceta);
                r.setIdIngrediente(idIngrediente);
                daoing = new RecetaIngredienteDao(Recetas.this);
                if(!r.isNull()){
                    Toast.makeText(Recetas.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
                }else if(daoing.insertarReceta(r)){
                    Toast.makeText(Recetas.this,"Registro de Ingrediente exitoso",Toast.LENGTH_LONG).show();
                    //idReceta = dao.ultimaReceta();
                    Intent i2 = new Intent(Recetas.this,Recetas.class);
                    startActivity(i2);
                    poblar();
                    agregarIng.setEnabled(true);
                }else{
                    Toast.makeText(Recetas.this,"Ingrediente ya registrado",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(Recetas.this,"Favor de ingresar la cantidad",Toast.LENGTH_LONG).show();
            }
        });
        nuevoIng.setOnClickListener(view -> {
            String tipo = "mandado";
            Intent i = new Intent(Recetas.this,ListaMandado.class);
            i.putExtra("tipo",tipo);
            startActivity(i);
        });
        verIng.setOnClickListener(view -> {
            Intent i = new Intent(Recetas.this,IngRec.class);
            i.putExtra("idReceta",idReceta);
            startActivity(i);
        });

        Bundle b = getIntent().getExtras();
        id = b.getInt("idUsuario");

        udao = new daoUsuario(this);
    }
    private void poblar(){  //Metodo para poblar el array objeto
        recetasList = new RecetaDao(this).selectRec();
        crearLista();
    }
    private void crearLista(){ //Metodo para poblar la lista
         infoList = new ArrayList<>();
         for(int i=0;i<recetasList.size();i++){
             infoList.add(recetasList.get(i).getNombre() +"\n Pasos:"+recetasList.get(i).getPasos());
        }
    }
    public void eliminarReceta(View v){
        AlertDialog.Builder b= new AlertDialog.Builder(Recetas.this);
        b.setMessage("¿Seguro que desea eliminar la receta?");
        b.setCancelable(false);
        b.setPositiveButton("SI", (dialogInterface, i) -> {
            if(new RecetaDao(this).eliminarReceta(nom.getText().toString()) == 0){
                Toast.makeText(this, "Esta receta no puede ser eliminada", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Recetas.this,"Receta Eliminada",Toast.LENGTH_LONG).show();
            }
            Intent i2 = new Intent(Recetas.this,Recetas.class);
            startActivity(i2);
            agregarIng.setEnabled(false);
        });
        b.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.cancel());
        b.show();
    }
    public void ClickMenu(View v){
        //Abrimos Drawer
        openDrawer(dl);
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class, "");
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
    }

    public static void openDrawer(DrawerLayout drawer) {
        //Abrimos el drawer layout
        drawer.openDrawer(GravityCompat.START);
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
        //Recreamos actividad
        recreate();
    }

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "mandado");
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class, "");
    }

    public void ClickHorarioSemana(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, VistaHorario.class, "");
    }

    public void ClickAcercaDe(View v){
        //Redireccionamos actividad a acerca de nosotros
        Menu.redirectActivity(this, AcercaNosotros.class, "");
    }

    public void ClickSalir(View v){
        //Cerramos app
        Menu.logout(this);
    }

    public void ClickNutricionista(View view) {
        //Redireccionamos actividad a acerca de nosotros
        String tipoCuenta = udao.plan(id);
        if(tipoCuenta.equals("Gratis")){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.pop_up);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }else{

            Menu.redirectActivity(this, VidaSaludable.class, "");
        }
    }

    public void ClickCompraMensual(View view){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.forma_pago);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        amount = "10";

        new GetToken(this, this, this).execute();

        paymentButton = dialog.findViewById(R.id.btnPaypal);
        paymentTV = dialog.findViewById(R.id.idTVStatus);

        paymentButton.setOnClickListener(view1 -> {
            DropInRequest dropInRequest = new DropInRequest().clientToken(token);
            startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
        });
    }

    public void ClickCompraAnual(View view){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.forma_pago);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        amount = "100";

        new GetToken(this, this, this).execute();;

        paymentButton = dialog.findViewById(R.id.btnPaypal);
        paymentTV = dialog.findViewById(R.id.idTVStatus);

        paymentButton.setOnClickListener(view1 -> {
            DropInRequest dropInRequest = new DropInRequest().clientToken(token);
            startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();

                paramsHash = new HashMap<>();
                paramsHash.put("amount", amount);
                paramsHash.put("nonce", strNonce);

                sendPayment();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "User cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("EDMT_ERROR", error.toString());
            }
        }
    }

    private void sendPayment(){
        RequestQueue queue = Volley.newRequestQueue(Recetas.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_GET_CHEKOUT,
                response -> {
                    if(response.contains("Successful")){
                        Toast.makeText(Recetas.this, "Transaction successfull!", Toast.LENGTH_SHORT).show();
                        udao = new daoUsuario(Recetas.this);
                        Calendar cal = Calendar.getInstance();
                        int dia = cal.get(Calendar.DAY_OF_MONTH);
                        int mes;
                        int anio;
                        if(amount.equals("10")){
                            mes = cal.get(Calendar.DAY_OF_MONTH) + 1;
                        }else{
                            mes = cal.get(Calendar.DAY_OF_MONTH);
                        }
                        if(amount.equals("100")){
                            anio = cal.get(Calendar.YEAR) + 1;
                        }else{
                            anio = cal.get(Calendar.YEAR);
                        }
                        udao.compraMensual(id, "Premium", dia + "/" + mes + "/" + anio);

                        Menu.redirectActivity(this, VidaSaludable.class, "");

                    }else{
                        Toast.makeText(Recetas.this, "Transaction failed!", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("EDMT_LOG", response);
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDMT_LOG", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if(paramsHash == null) return null;
                Map<String, String> params = new HashMap<>();
                for(String key: paramsHash.keySet()){
                    params.put(key, paramsHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Cerramos Drawer
        Menu.closeDrawer(dl);
    }

    @Override
    public void processFinish(String output) {
        this.token = output;
        if(token != null || !token.equals("")){
            paymentTV.setText("CONECTANDO CON EL SERVIDOR");
            new CountDownTimer(1000, 1000) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    paymentTV.setText("CONECTADO");
                }
            }.start();
        }else{
            paymentTV.setText("FALLO LA CONEXION CON EL SERVIDOR");
        }
    }


}
