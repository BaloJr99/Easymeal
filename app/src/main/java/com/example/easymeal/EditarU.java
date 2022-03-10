package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.daoUsuario;

public class EditarU extends AppCompatActivity {
    String username;
    EditText user,clave,nombre,apellidoPaterno,apellidoMaterno,fecha;
    Button btneditar;
    daoUsuario dao;
    Usuario u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_u);
        user = (EditText) findViewById(R.id.edituser);
        //clave = (EditText) findViewById(R.id.editclave);
        nombre = (EditText) findViewById(R.id.editnombre);
        apellidoPaterno = (EditText) findViewById(R.id.editapellidop);
        apellidoMaterno = (EditText) findViewById(R.id.editapellidom);
        fecha = (EditText) findViewById(R.id.editfecha);
        btneditar = (Button) findViewById(R.id.btneditar2);
        Bundle b=getIntent().getExtras();
        username=b.getString("Username");
        user.setText(username);

        dao = new daoUsuario(this);
        u = dao.getUsuarioById(username);
        //clave.setText(u.getClave());
        nombre.setText(u.getNombre());
        apellidoPaterno.setText(u.getApellidoPaterno());
        apellidoMaterno.setText(u.getApellidoMaterno());
        fecha.setText(u.getFechaNacimiento());

        btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario u = new Usuario();
                u.setUsername(user.getText().toString());
                u.setNombre(nombre.getText().toString());
                u.setApellidoPaterno(apellidoPaterno.getText().toString());
                u.setApellidoMaterno(apellidoMaterno.getText().toString());
               // u.setFechaNacimiento(fecha.getText().toString());
                if (!u.isNull()) {
                    Toast.makeText(EditarU.this, "ERROR", Toast.LENGTH_LONG).show();
                } else if (dao.updateUsuario(u)) {
                    Toast.makeText(EditarU.this, "Actualizacion Exitosa", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(EditarU.this, MenuUsuario.class);
                    i2.putExtra("Username",username);
                    startActivity(i2);
                    finish();
                }
            }
        });
    }
}