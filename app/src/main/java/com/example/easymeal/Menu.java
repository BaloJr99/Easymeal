package com.example.easymeal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
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
import com.example.easymeal.Excepciones.MisExcepciones;
import com.example.easymeal.cl.model.bd.Compras;
import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.ComprasDao;
import com.example.easymeal.cl.model.dao.daoUsuario;
import com.example.easymeal.getToken.AsyncResponse;
import com.example.easymeal.getToken.GetToken;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Menu extends AppCompatActivity  implements AsyncResponse{

    private static final int REQUEST_CODE = 1234;
    //Inicializamos variables
    DrawerLayout dl;
    Dialog dialog;

    final String API_GET_CHEKOUT = "http://192.168.0.9/braintree/checkout.php";

    String token, amount;
    HashMap<String, String> paramsHash;

    static int id;

    daoUsuario dao;
    Usuario u;
    TextView nombreusuario;
    ImageButton paymentButton;

    private LineChart lineChart;
    private EditText etFecha;
    private EditText etImporte;

    private ArrayList<Compras> listaCompras;
    private ComprasDao compraDao;
    private Compras compras;
    private TextView paymentTV;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu);

        //Asignamos variable
        dl = findViewById(R.id.drawer_menu);
        nombreusuario = findViewById(R.id.username);
        lineChart = findViewById(R.id.grafico);
        etFecha = findViewById(R.id.etFechaCompra);
        etImporte = findViewById(R.id.importeCompra);

        Bundle b = getIntent().getExtras();
        id = b.getInt("idUsuario");

        dao = new daoUsuario(this);
        u = dao.getUsuarioById(id);
        nombreusuario.setText("Bienvenido " + u.getNombre());
        String fecha = dao.getFechaVencimiento(id);
        if(!fecha.equals("")){
            LocalDate dateBefore = LocalDate.now();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            LocalDate dateAfter = null;
            try {
                dateAfter = sdf.parse(fecha).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long days = ChronoUnit.DAYS.between(dateBefore, dateAfter);
            if(days < 7 && days > 1){
                Toast.makeText(this, "Le quedan " + days + " dias de suscripción", Toast.LENGTH_SHORT).show();
            }else if(days == 1){
                Toast.makeText(this, "Le queda " + days + " día de suscripción", Toast.LENGTH_SHORT).show();
            }else if(days == 0){
                Toast.makeText(this, "Su suscripción ha vencido favor de renovarla", Toast.LENGTH_SHORT).show();
                dao.compraMensual(id, "Gratis", "");
            }
        }
        u = dao.getUsuarioById(id);

        compraDao = new ComprasDao();
        compraDao.comprasDao(this);

        llenarGrafico();
    }

    public void ClickMenu(View view) {
        //Abrimos drawer
        openDrawer(dl);
    }

    public static void openDrawer(DrawerLayout drawer) {
        //Abrimos el drawer layout
        drawer.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        //Cerramos el drawer
        closeDrawer(dl);
    }

    public static void closeDrawer(DrawerLayout drawer) {
        //Cerramos el layout si esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //Cuando este abierto cerramos el drawer
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickInicio(View view) {
        //Recreamos actividad
        recreate();
    }

    public void ClickRecetas(View view) {
        //Redireccionamos actividad a dashboard
        redirectActivity(this, Recetas.class, "");
    }

    public void ClickLista(View view) {
        //Redireccionamos actividad a dashboard
        redirectActivity(Menu.this, ListaMandado.class, "mandado");
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        redirectActivity(Menu.this, ListaMandado.class, "");
    }

    public void ClickHorario(View view) {
        //Redireccionamos actividad a dashboard
        redirectActivity(this, Horario.class, "");
    }

    public void ClickHorarioSemana(View view) {
        //Redireccionamos actividad a dashboard
        redirectActivity(this, VistaHorario.class, "");
    }

    public void ClickAcercaDe(View view) {
        //Redireccionamos actividad a acerca de nosotros
        redirectActivity(this, AcercaNosotros.class, "");
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
            redirectActivity(this, VidaSaludable.class, "");
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
                dao = new daoUsuario(Menu.this);
                dao.asignarNutriologo(Integer.parseInt(response), id);
                Toast.makeText(Menu.this, "Se le asigno un nutriologo exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Menu.this, "Ocurrio un error al asignar nutriologo \npronto se le asignara uno", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(Menu.this, error.toString(), Toast.LENGTH_SHORT).show()){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("accion", "asignando");
                parametros.put("idCliente", String.valueOf(id));
                dao = new daoUsuario(Menu.this);
                u = dao.getUsuarioById(id);
                parametros.put("nombre", u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendPayment(){
        RequestQueue queue = Volley.newRequestQueue(Menu.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_GET_CHEKOUT,
                response -> {
                    if(response.contains("Successful")){
                        Toast.makeText(Menu.this, "Transaction successfull!", Toast.LENGTH_SHORT).show();
                        dao = new daoUsuario(Menu.this);
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
                        redirectActivity(this, VidaSaludable.class, "");

                    }else{
                        Toast.makeText(Menu.this, "Transaction failed!", Toast.LENGTH_SHORT).show();
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

    public void ClickSalir(View view) {
        //Cerramos app
        logout(this);
    }

    public void ClickUsuarios(View v) {
        //Nos dirijimos al menu de los usuarios
        redirectActivity(this, MenuUsuario.class, "");
    }

    public static void logout(Activity activity) {
        //Inicializamos mensaje de alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Establecemos el titulo
        builder.setTitle("Logout");
        //Establecemos el mensaje
        builder.setMessage("Estas seguro de que desea salir?");
        //Boton de seguro
        builder.setPositiveButton("YES", (dialogInterface, i) -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        });

        //Boton negativo

        builder.setNegativeButton("NO", (dialogInterface, i) -> {
            //Hacemos caso omiso al dialogo
            dialogInterface.dismiss();
        });

        //Mostramos el dialogo
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass, String tipo) {
        //Inicializamos el intento
        Intent intent = new Intent(activity, aClass);
        intent.putExtra("idUsuario", id);
        intent.putExtra("tipo", tipo);
        //Creamos bandera
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Empezamos activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Cerramos drawer
        closeDrawer(dl);
    }

    private void llenarGrafico() {
        listaCompras = compraDao.listaCompras();
        //Diseño de grafico
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setNoDataText("NO HAY DATOS REGISTRADOS");
        lineChart.setNoDataTextColor(Color.WHITE);
        lineChart.setDrawBorders(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(e.getX() != 0){
                    etImporte.setText(String.valueOf(listaCompras.get((int)e.getX() - 1).getImporteGasto()));
                    etImporte.setTag((int)e.getX());
                    etFecha.setText(listaCompras.get((int)e.getX() - 1).getFechaCompra());
                }else{
                    etImporte.setText("");
                    etFecha.setText("");
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //Eliminamos legenda
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        //Añadimos descripcion
        lineChart.setDescription(null);

        //Declaracion de datos y diseño de linea
        LineDataSet lineDataSet = new LineDataSet(getDataVals(), "Data Set 1");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(3);
        lineDataSet.setColor(Color.parseColor("#FA851E"));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setValueTextColor(Color.BLACK);

        //Se agregan los datos
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        //Se crea la linea
        LineData data = new LineData(dataSets);

        //Se configura el Eje X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(90);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0) {
                    return "";
                }

                if (listaCompras.size() != 0) {
                    return listaCompras.get((int) value - 1).getFechaCompra();
                } else {
                    return "";
                }
            }
        });

        lineChart.animateY(1000);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    private ArrayList<Entry> getDataVals() {
        ArrayList<Entry> dataVals = new ArrayList<>();
        int index = 1;

        dataVals.add(new Entry(0, 0));
        for (Compras c : listaCompras) {
            dataVals.add(new Entry(index, (int) c.getImporteGasto()));
            index++;
        }

        return dataVals;
    }

    public void mostrarFecha(View view) {
        int dia, mes, ano;
        Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        @SuppressLint("SetTextI18n") DatePickerDialog date = new DatePickerDialog(this, (datePicker, anio, mes1, dia1) -> etFecha.setText(dia1 + "/" + (mes1 + 1) + "/" + anio), ano, mes, dia);
        date.show();
    }

    public void agregarCompra(View view) {
        if(!camposVacios()){
            compras = new Compras();
            compras.setFechaCompra(etFecha.getText().toString());
            compras.setImporteGasto(Float.parseFloat(etImporte.getText().toString()));
            if(compraDao.insertarCompra(compras)){
                Toast.makeText(this, "Insertado exitosamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                llenarGrafico();
            }else {
                Toast.makeText(this, "Ocurrio un error al insertar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void limpiarCampos(){
        etFecha.setText("");
        etImporte.setText("");
        etImporte.setTag("0");
    }

    private boolean camposVacios(){
        try {
            if(etImporte.getText().toString().trim().isEmpty()){
                throw new MisExcepciones(7);
            }else if(etFecha.getText().toString().trim().isEmpty()){
                throw new MisExcepciones(8);
            }
            return false;
        }catch (MisExcepciones me){
            Toast.makeText(this, me.getMessage(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public void modificarCompra(View view) {
        if(!camposVacios()  && etImporte.getTag() != "0"){
            compras = new Compras();
            compras.setIdCompra(Integer.parseInt(etImporte.getTag().toString()));
            compras.setFechaCompra(etFecha.getText().toString());
            compras.setImporteGasto(Float.parseFloat(etImporte.getText().toString()));
            if(compraDao.modificarCompra(compras)){
                Toast.makeText(this, "Modificado exitosamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                llenarGrafico();
            }else {
                Toast.makeText(this, "Ocurrio un error al modificar", Toast.LENGTH_SHORT).show();
            }
        }
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
