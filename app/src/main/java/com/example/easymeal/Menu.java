package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.Excepciones.MisExcepciones;
import com.example.easymeal.cl.model.bd.Compras;
import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.ComprasDao;
import com.example.easymeal.cl.model.dao.daoUsuario;
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

import java.util.ArrayList;
import java.util.Calendar;


public class Menu extends AppCompatActivity {

    //Inicializamos variables
    DrawerLayout dl;

    static String username;
    static int id;

    daoUsuario dao;
    Usuario u;
    TextView nombreusuario;

    static String tipo = "";
    private LineChart lineChart;
    private EditText etFecha;
    private EditText etImporte;

    private ArrayList<Compras> listaCompras;
    private ComprasDao compraDao;
    private Compras compras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
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
        nombreusuario.setText("BIENVENIDO " + u.getNombre() + " " + u.getApellidoPaterno());

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
        redirectActivity(this, Recetas.class);
    }

    public void ClickLista(View view) {
        //Redireccionamos actividad a dashboard
        redirectActivity(Menu.this, ListaMandado.class);

    }

    public void ClickHorario(View view) {
        //Redireccionamos actividad a dashboard
        redirectActivity(this, Horario.class);
    }

    public void ClickAcercaDe(View view) {
        //Redireccionamos actividad a acerca de nosotros
        redirectActivity(this, AcercaNosotros.class);
    }

    public void ClickSalir(View view) {
        //Cerramos app
        logout(this);
    }

    public void ClickUsuarios(View v) {
        //Nos dirijimos al menu de los usuarios
        redirectActivity(this, MenuUsuario.class);
    }

    public static void logout(Activity activity) {
        //Inicializamos mensaje de alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Establecemos el titulo
        builder.setTitle("Logout");
        //Establecemos el mensaje
        builder.setMessage("Estas seguro de que desea salir?");
        //Boton de seguro
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Finalizamos actividad
                activity.finishAffinity();
                //Cerramos app
                System.exit(0);
            }
        });

        //Boton negativo

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Hacemos caso omiso al dialogo
                dialogInterface.dismiss();
            }
        });

        //Mostramos el dialogo
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //Inicializamos el intento

        Intent intent = new Intent(activity, aClass);
        intent.putExtra("idUsuario", id);
        if (aClass == ListaMandado.class) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            //Establecemos el titulo
            builder.setTitle("Alacena");
            //Establecemos el mensaje
            builder.setMessage("Deseas generar lista de mandado?");
            //Boton de seguro
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    tipo = "mandado";
                    intent.putExtra("tipo", tipo);
                    //Creamos bandera
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //Empezamos activity
                    activity.startActivity(intent);
                }
            });

            //Boton negativo

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    tipo = "";
                    intent.putExtra("tipo", tipo);
                    //Creamos bandera
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //Empezamos activity
                    activity.startActivity(intent);
                }
            });

            //Mostramos el dialogo
            builder.show();
        } else {
            //Creamos bandera
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Empezamos activity
            activity.startActivity(intent);
        }
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
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
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

        DatePickerDialog date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                etFecha.setText(dia + "/" + mes + "/" + anio);
            }
        }, ano, mes, dia);
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
}
