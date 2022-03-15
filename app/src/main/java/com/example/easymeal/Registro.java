package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.daoUsuario;

public class Registro extends AppCompatActivity {
EditText us,cla,nom,ap,am,fn;
Button reg;
daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro);

        us = (EditText) findViewById(R.id.user);
        cla = (EditText) findViewById(R.id.contra);
        nom = (EditText) findViewById(R.id.nombre);
        ap = (EditText) findViewById(R.id.apellidop);
        am = (EditText) findViewById(R.id.apellidom);
        fn = (EditText) findViewById(R.id.fechaN);
        reg = (Button) findViewById(R.id.reg);

        dao = new daoUsuario(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario u = new Usuario();
                u.setUsername(us.getText().toString());
                u.setClave(cla.getText().toString());
                u.setNombre(nom.getText().toString());
                u.setApellidoPaterno(ap.getText().toString());
                u.setApellidoMaterno(am.getText().toString());
                u.setFechaNacimiento(fn.getText().toString());
                if(!u.isNull()){
                    Toast.makeText(Registro.this,"ERROR: CAMPOS VACIOS",Toast.LENGTH_LONG).show();
                }else if(dao.insertUsuario(u)){
                    Toast.makeText(Registro.this,"Registro Exitoso",Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Registro.this,MainActivity.class);
                    startActivity(i2);
                }else{
                    Toast.makeText(Registro.this,"Usuario ya registrado",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}