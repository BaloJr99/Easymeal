package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.cl.model.bd.Usuario;
import com.example.easymeal.cl.model.dao.daoUsuario;

import java.util.Calendar;

public class EditarU extends AppCompatActivity {
    String username;
    int id,dia,mes,anio;
    EditText user,clave,nombre,apellidoPaterno,apellidoMaterno;
    TextView fecha;
    Button btneditar,regresar;
    ImageView imgFecha;
    daoUsuario dao;
    Usuario u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_editar_u);
        //clave = (EditText) findViewById(R.id.editclave);
        nombre = (EditText) findViewById(R.id.editnombre);
        apellidoPaterno = (EditText) findViewById(R.id.editapellidop);
        apellidoMaterno = (EditText) findViewById(R.id.editapellidom);
        fecha = (TextView) findViewById(R.id.editfecha);
        btneditar = (Button) findViewById(R.id.btneditar2);
        regresar = (Button) findViewById(R.id.btnregresar2);
        imgFecha = (ImageView) findViewById(R.id.edtfechaNac);
        Bundle b=getIntent().getExtras();
        id=b.getInt("idUsuario");
        //user.setText(String.valueOf(id));

        dao = new daoUsuario(this);
        u = dao.getUsuarioById(id);
        //clave.setText(u.getClave());
        nombre.setText(u.getNombre());
        apellidoPaterno.setText(u.getApellidoPaterno());
        apellidoMaterno.setText(u.getApellidoMaterno());
        fecha.setText(u.getFechaNacimiento());

        btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario u = new Usuario();
                u.setIdUsuario(id);
                u.setNombre(nombre.getText().toString());
                u.setApellidoPaterno(apellidoPaterno.getText().toString());
                u.setApellidoMaterno(apellidoMaterno.getText().toString());
                u.setFechaNacimiento(fecha.getText().toString());
                if (nombre.getText().equals("")||apellidoPaterno.getText().equals("")||apellidoMaterno.getText().equals("")||fecha.getText().equals("--/--/--")) {
                    Toast.makeText(EditarU.this, "ERROR", Toast.LENGTH_LONG).show();
                } else if (dao.updateUsuario(u)) {
                    Toast.makeText(EditarU.this, "Actualizacion Exitosa", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(EditarU.this, MenuUsuario.class);
                    i2.putExtra("idUsuario",id);
                    startActivity(i2);
                    finish();
                }
            }
        });
        imgFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio= c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditarU.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }

                },anio,mes,dia);
                datePickerDialog.show();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(EditarU.this, MenuUsuario.class);
                i2.putExtra("idUsuario",id);
                startActivity(i2);
                finish();
            }
        });
    }
}