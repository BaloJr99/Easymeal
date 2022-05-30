package com.example.easymeal;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.example.easymeal.cl.model.dao.PreparacionesDao;
import com.example.easymeal.cl.model.dao.daoUsuario;
import com.example.easymeal.getToken.AsyncResponse;
import com.example.easymeal.getToken.GetToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class VistaHorario extends AppCompatActivity implements AsyncResponse {
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
    Usuario u;

    ImageButton paymentButton;
    private TextView paymentTV;

    Dialog dialog;

    private static final int REQUEST_CODE = 1234;
    final String API_GET_CHEKOUT = "http://192.168.0.10/braintree/checkout.php";

    String token, amount;
    HashMap<String, String> paramsHash;

    static int id;

    daoUsuario dao;

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
        final Calendar c = Calendar.getInstance();
        txtFecha.setText("SEMANA: " + (c.get(Calendar.WEEK_OF_YEAR) - 1));
        llenarVista();

        Bundle b = getIntent().getExtras();
        id = b.getInt("idUsuario");

        dao = new daoUsuario(this);
    }

    public void llenarVista(){
        predao = new PreparacionesDao(this);
        ArrayList<Object[]> listapre = predao.buscarHorarioSemanal(txtFecha.getText().toString());
        if(listapre != null){
            System.out.println("Entro");
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
                dao = new daoUsuario(VistaHorario.this);
                u = dao.getUsuarioById(id);
                parametros.put("nombre", u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void sendPayment(){
        RequestQueue queue = Volley.newRequestQueue(VistaHorario.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_GET_CHEKOUT,
                response -> {
                    if(response.contains("Successful")){
                        Toast.makeText(VistaHorario.this, "Transaction successfull!", Toast.LENGTH_SHORT).show();
                        dao = new daoUsuario(VistaHorario.this);
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
                        Toast.makeText(VistaHorario.this, "Transaction failed!", Toast.LENGTH_SHORT).show();
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