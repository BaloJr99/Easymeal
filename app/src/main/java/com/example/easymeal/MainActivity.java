package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.daoUsuario;

public class MainActivity extends AppCompatActivity {

    Button tv_registrar;
    EditText usuario,pass;
    Button login;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        tv_registrar= findViewById(R.id.tv_registrar);
        login = findViewById(R.id.login);
        usuario = findViewById(R.id.usuario);
        pass = findViewById(R.id.password);
        dao=new daoUsuario(this);

        Bundle p = this.getIntent().getExtras();
        if(p != null){
            usuario.setText(p.getString("user"));
        }

        login.setOnClickListener(view -> {
            String u = usuario.getText().toString();
            String p1 = pass.getText().toString();
            if (u.equals("") && p1.equals("")) {
                Toast.makeText(MainActivity.this, "ERROR:Campos vacíos", Toast.LENGTH_LONG).show();
            } else if (dao.login(u, p1) == 1) {
                Usuario us = dao.getUsuario(u, p1);
                Intent intent = new Intent(view.getContext(), Menu.class);
                intent.putExtra("idUsuario",us.getIdUsuario());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Usuario y/o Contrseña incorrectos", Toast.LENGTH_LONG).show();
            }
        });

        tv_registrar.setOnClickListener(view -> {
            Intent intentReg = new Intent(MainActivity.this, Registro.class);
            MainActivity.this.startActivity(intentReg);
        });
    }
}
