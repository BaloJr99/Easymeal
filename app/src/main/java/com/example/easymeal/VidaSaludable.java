package com.example.easymeal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.daoUsuario;
import com.example.easymeal.mensajeria.AdapterMensaje;
import com.example.easymeal.mensajeria.Mensaje;
import com.example.easymeal.mensajeria.MensajeEnviar;
import com.example.easymeal.mensajeria.MensajeRecibir;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VidaSaludable extends AppCompatActivity {

    DrawerLayout dl;
    TextView tvTitulo, tvNombre;
    RecyclerView tvChat;
    private String nombre;

    daoUsuario dao;
    Usuario u;
    EditText etMensaje;

    int id;

    private AdapterMensaje adapterMensaje;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vida_saludable);
        dl = findViewById(R.id.drawer_vidaSaludable);
        tvChat = findViewById(R.id.tvChat);
        tvNombre = findViewById(R.id.tvNombre);
        tvTitulo = findViewById(R.id.titulo);
        etMensaje = findViewById(R.id.etMensaje);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");

        adapterMensaje = new AdapterMensaje(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        tvChat.setLayoutManager(l);
        tvChat.setAdapter(adapterMensaje);

        adapterMensaje.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MensajeRecibir m = snapshot.getValue(MensajeRecibir.class);
                adapterMensaje.addMensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id = bundle.getInt("idUsuario");
            nombre = new daoUsuario(this).getUsuarioById(id).getNombre();
        }
        llenarDatosNutriologo();

    }

    private void setScrollBar(){
        tvChat.scrollToPosition(adapterMensaje.getItemCount() - 1);
    }

    private void llenarDatosNutriologo() {
        String URL = "http://192.168.0.9/easymeal/asignarNutriologo.php";
        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            if(!response.trim().isEmpty()){
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tvNombre.setText(jsonObject.getString("nombre") + " " + jsonObject.get("apellidoPaterno") + " " + jsonObject.get("apellidoMaterno"));
                    tvTitulo.setText(jsonObject.getString("titulo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(VidaSaludable.this, "Ocurrio un error al obtener datos del nutriologo", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(VidaSaludable.this, error.toString(), Toast.LENGTH_SHORT).show()){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("accion", "asignando");
                parametros.put("idCliente", String.valueOf(id));
                dao = new daoUsuario(VidaSaludable.this);
                u = dao.getUsuarioById(id);
                parametros.put("nombre", u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    public void ClickUsuarios (View v){
        //Nos dirijimos al menu de los usuarios
        Menu.redirectActivity(this,MenuUsuario.class, "");
    }

    public void ClickAlacena(View view) {
        //Redireccionamos actividad a dashboard
        Menu.redirectActivity(this, ListaMandado.class, "");
    }

    public void ClickNutricionista(View view) {
        //Redireccionamos actividad a dashboard
        recreate();
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

    public void ClickEmoji(View view) {

    }

    public void ClickSend(View view) {
        databaseReference.push().setValue(new MensajeEnviar(etMensaje.getText().toString(), nombre, "1", ServerValue.TIMESTAMP));
        etMensaje.setText("");
    }
}