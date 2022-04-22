package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.daoUsuario;

import java.util.Calendar;

public class EditarU extends AppCompatActivity {
    int id,dia,mes,anio;
    EditText nombre,apellidoPaterno,apellidoMaterno;
    TextView fecha;
    Button btneditar,regresar;
    ImageView imgFecha;
    daoUsuario dao;
    Usuario u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_editar_u);
        nombre = findViewById(R.id.editnombre);
        apellidoPaterno = findViewById(R.id.editapellidop);
        apellidoMaterno = findViewById(R.id.editapellidom);
        fecha = findViewById(R.id.editfecha);
        btneditar = findViewById(R.id.btneditar2);
        regresar = findViewById(R.id.btnregresar2);
        imgFecha = findViewById(R.id.edtfechaNac);
        Bundle b=getIntent().getExtras();
        id=b.getInt("idUsuario");

        dao = new daoUsuario(this);
        u = dao.getUsuarioById(id);
        nombre.setText(u.getNombre());
        apellidoPaterno.setText(u.getApellidoPaterno());
        apellidoMaterno.setText(u.getApellidoMaterno());
        fecha.setText(u.getFechaNacimiento());

        btneditar.setOnClickListener(view -> {
            Usuario u = new Usuario();
            u.setIdUsuario(id);
            u.setNombre(nombre.getText().toString());
            u.setApellidoPaterno(apellidoPaterno.getText().toString());
            u.setApellidoMaterno(apellidoMaterno.getText().toString());
            u.setFechaNacimiento(fecha.getText().toString());
            if (nombre.getText().toString().equals("")||apellidoPaterno.getText().toString().equals("")||apellidoMaterno.getText().toString().equals("")||fecha.getText().toString().equals("--/--/--")) {
                Toast.makeText(EditarU.this, "ERROR", Toast.LENGTH_LONG).show();
            } else if (dao.updateUsuario(u)) {
                Toast.makeText(EditarU.this, "Actualizacion Exitosa", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(EditarU.this, MenuUsuario.class);
                i2.putExtra("idUsuario",id);
                startActivity(i2);
                finish();
            }
        });
        imgFecha.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio= c.get(Calendar.YEAR);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(EditarU.this, (datePicker, year, monthOfYear, dayOfMonth) -> fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year),anio,mes,dia);
            datePickerDialog.show();
        });
        regresar.setOnClickListener(view -> {
            Intent i2 = new Intent(EditarU.this, MenuUsuario.class);
            i2.putExtra("idUsuario",id);
            startActivity(i2);
            finish();
        });
    }
}