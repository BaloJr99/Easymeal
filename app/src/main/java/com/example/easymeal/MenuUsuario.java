package com.example.easymeal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ImageButton;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MenuUsuario extends AppCompatActivity implements AsyncResponse {
    DrawerLayout dl;
    ImageButton paymentButton;
    private TextView paymentTV;
    Usuario u;

    Dialog dialog;

    private static final int REQUEST_CODE = 1234;
    final String API_GET_CHEKOUT = "http://192.168.0.9/braintree/checkout.php";

    String token, amount;
    HashMap<String, String> paramsHash;

    static int id;

    daoUsuario dao;

    Button editar,eliminar,ver,salir;

    daoUsuario daoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu_usuario);
        dl = findViewById(R.id.drawer_usuario);
        editar = findViewById(R.id.btneditar);
        eliminar = findViewById(R.id.btneliminar);
        salir = findViewById(R.id.btnsalir);
        ver = findViewById(R.id.ver);
        Bundle b=getIntent().getExtras();
        id=b.getInt("idUsuario");
        dao = new daoUsuario(this);

        editar.setOnClickListener(view -> {
            Intent i = new Intent(MenuUsuario.this,EditarU.class);
            i.putExtra("idUsuario",id);
            startActivity(i);
        });
        ver.setOnClickListener(view -> {
            Intent i = new Intent(MenuUsuario.this,MostrarU.class);
            i.putExtra("idUsuario",id);
            startActivity(i);
        });
        salir.setOnClickListener(view -> {
            Intent i = new Intent(MenuUsuario.this,Menu.class);
            i.putExtra("idUsuario",id);
            startActivity(i);
        });
    }
    public void eliminarRegistro(View v){
        AlertDialog.Builder b= new AlertDialog.Builder(MenuUsuario.this);
        b.setMessage("¿Seguro que desea eliminar la cuenta?");
        b.setCancelable(false);
        b.setPositiveButton("SI", (dialogInterface, i) -> {
            daoUsuario = new daoUsuario(this);
            daoUsuario.eliminarUsuario(id);
            Toast.makeText(MenuUsuario.this,"Cuenta Eliminada",Toast.LENGTH_LONG).show();
            Intent i2 =   new Intent(MenuUsuario.this, MainActivity.class);
            startActivity(i2);
            finish();
        });
        b.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.cancel());
        b.show();
        //op.delete("alumno","id="+id,null);
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
        Menu.redirectActivity(this, VistaHorario.class, "");
    }

    public void ClickLista(View view){
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "mandado");
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
    }

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        recreate();
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
                dao = new daoUsuario(MenuUsuario.this);
                u = dao.getUsuarioById(id);
                parametros.put("nombre", u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void sendPayment(){
        RequestQueue queue = Volley.newRequestQueue(MenuUsuario.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_GET_CHEKOUT,
                response -> {
                    if(response.contains("Successful")){
                        Toast.makeText(MenuUsuario.this, "Transaction successfull!", Toast.LENGTH_SHORT).show();
                        dao = new daoUsuario(MenuUsuario.this);
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
                        Toast.makeText(MenuUsuario.this, "Transaction failed!", Toast.LENGTH_SHORT).show();
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