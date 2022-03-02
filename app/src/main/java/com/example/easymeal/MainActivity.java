package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.daoUsuario;
import com.example.easymeal.database.DbAyuda;

public class MainActivity extends AppCompatActivity {

    TextView tv_registrar;
    EditText usuario,pass;
    Button login;
    daoUsuario dao;

    public void siguiente(View v){
        Intent intento=new Intent(this, Menu.class);
        startActivity(intento);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_registrar= findViewById(R.id.tv_registrar);
        login = (Button) findViewById(R.id.login);
        usuario = (EditText) findViewById(R.id.usuario);
        pass = (EditText) findViewById(R.id.password);
         dao=new daoUsuario(this);
        //btnCreate = findViewById(R.id.btnCreate);
        DbAyuda Helper = new DbAyuda(MainActivity.this);
        SQLiteDatabase db = Helper.getWritableDatabase();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u = usuario.getText().toString();
                String p = pass.getText().toString();
                if (u.equals("") && p.equals("")) {
                    Toast.makeText(MainActivity.this, "ERROR:Campos vacíos", Toast.LENGTH_LONG).show();
                } else if (dao.login(u, p) == 1) {
                    Usuario us = dao.getUsuario(u, p);
                    Toast.makeText(MainActivity.this, "BIENVENIDO " + us.getNombre(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), Menu.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario y/o Contrseña incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });
        /*Button inicio = findViewById(R.id.inicio);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity2.class);
                startActivityForResult(intent, 0);

    }
});*/
        tv_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(MainActivity.this, Registro.class);
                MainActivity.this.startActivity(intentReg);
            }
        });
    }
}
