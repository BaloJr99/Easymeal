package com.example.easymeal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageButton;
import android.widget.ListView;
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
import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.daoUsuario;
import com.example.easymeal.getToken.AsyncResponse;
import com.example.easymeal.getToken.GetToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MostrarU extends AppCompatActivity implements AsyncResponse {

    ListView listaUser;

    DrawerLayout dl;
    ImageButton paymentButton;
    private TextView paymentTV;

    Dialog dialog;

    private static final int REQUEST_CODE = 1234;
    final String API_GET_CHEKOUT = "http://192.168.0.10/braintree/checkout.php";

    String token, amount;
    HashMap<String, String> paramsHash;

    static int id;
    Usuario u;

    daoUsuario dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mostrar_u);
        dl = findViewById(R.id.drawer_mostrar_u);
        Bundle b=getIntent().getExtras();
        listaUser = findViewById(R.id.listUsers);
        id=b.getInt("idUsuario");

        ArrayList<Usuario> lista = new daoUsuario(this).datosUsuario(id);

        ArrayList<String> list = new ArrayList<>();

        for (Usuario u:lista) {
            list.add("Usuario: "+u.getUsername());
            list.add("Nombre: "+u.getNombre());
            list.add("Apellido Paterno: "+u.getApellidoPaterno());
            list.add("Apellido Materno: "+u.getApellidoMaterno());
            list.add("Fecha de Nacimiento: "+u.getFechaNacimiento());
        }
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,list);
        listaUser.setAdapter(a);
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
        //Redireccionamos actividad a acerca de
        Menu.redirectActivity(this, AcercaNosotros.class, "");
    }

    public void ClickHorario(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, Horario.class, "");
    }

    public void ClickHorarioSemana(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, VistaHorario.class, "");
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

    public void ClickNutricionista(View view) {
        //Redireccionamos actividad a acerca de nosotros
        String tipoCuenta = dao.plan(id);
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
    private void obtenerNutriologo(){
        String URL = "http://192.168.0.9/easymeal/asignarNutriologo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            if(!response.trim().isEmpty()){
                dao = new daoUsuario(this);
                dao.asignarNutriologo(Integer.parseInt(response), id);
                Toast.makeText(this, "Se le asigno un nutriologo exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Ocurrio un error al asignar nutriologo \npronto se le asignara uno", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("accion", "asignando");
                parametros.put("idCliente", String.valueOf(id));
                dao = new daoUsuario(MostrarU.this);
                u = dao.getUsuarioById(id);
                parametros.put("nombre", u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void sendPayment(){
        RequestQueue queue = Volley.newRequestQueue(MostrarU.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_GET_CHEKOUT,
                response -> {
                    if(response.contains("Successful")){
                        Toast.makeText(MostrarU.this, "Transaction successfull!", Toast.LENGTH_SHORT).show();
                        dao = new daoUsuario(MostrarU.this);
                        Calendar cal = Calendar.getInstance();
                        int dia = cal.get(Calendar.DAY_OF_MONTH);
                        int mes;
                        int anio;
                        if(amount.equals("10")){
                            mes = cal.get(Calendar.MONTH) + 2;
                        }else{
                            mes = cal.get(Calendar.MONTH) + 1;
                        }
                        if(amount.equals("100")){
                            anio = cal.get(Calendar.YEAR) + 1;
                        }else{
                            anio = cal.get(Calendar.YEAR);
                        }
                        dao.compraMensual(id, "Premium", dia + "/" + mes + "/" + anio);
                        obtenerNutriologo();
                        Menu.redirectActivity(this, VidaSaludable.class, "");

                    }else{
                        Toast.makeText(MostrarU.this, "Transaction failed!", Toast.LENGTH_SHORT).show();
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