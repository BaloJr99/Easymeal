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

public class Registro extends AppCompatActivity {
EditText us,cla,nom,ap,am;
TextView fn;
Button reg;
daoUsuario dao;
ImageView fecha;
    int dia,mes,anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro);

        us = findViewById(R.id.user);
        cla = findViewById(R.id.contra);
        nom = findViewById(R.id.nombre);
        ap = findViewById(R.id.apellidop);
        am = findViewById(R.id.apellidom);
        fn = findViewById(R.id.fechaN);
        reg = findViewById(R.id.reg);
        fecha = findViewById(R.id.fechaNac);
        dao = new daoUsuario(this);

        reg.setOnClickListener(view -> {
            Usuario u = new Usuario();
            u.setUsername(us.getText().toString());
            u.setClave(cla.getText().toString());
            u.setNombre(nom.getText().toString());
            u.setApellidoPaterno(ap.getText().toString());
            u.setApellidoMaterno(am.getText().toString());
            u.setFechaNacimiento(fn.getText().toString());
            if(us.getText().toString().equals("")||cla.getText().toString().equals("")||nom.getText().toString().equals("")||ap.getText().toString().equals("")||am.getText().toString().equals("")||fn.getText().equals("--/--/--")){
                Toast.makeText(Registro.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
            }else if(dao.insertUsuario(u)){
                Toast.makeText(Registro.this,"Registro Exitoso",Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(Registro.this,MainActivity.class);
                i2.putExtra("user", us.getText().toString());
                startActivity(i2);
            }else{
                Toast.makeText(Registro.this,"Usuario ya registrado",Toast.LENGTH_LONG).show();
            }
        });
        fecha.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio= c.get(Calendar.YEAR);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(Registro.this, (datePicker, year, monthOfYear, dayOfMonth) -> fn.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year),anio,mes,dia);
            datePickerDialog.show();
        });
    }
}