package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easymeal.database.DbAyuda;

public class MainActivity extends AppCompatActivity {

    Button btnCreate;
    public void siguiente(View v){
        Intent intento=new Intent(this,MainActivity2.class);
        startActivity(intento);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreate = findViewById(R.id.btnCreate);
        String str = "Hola";
        String str2 = "Hola2";
        String str3 = "Hola3";
        String str4 = "Hola4";
        DbAyuda Helper = new DbAyuda(MainActivity.this);
        SQLiteDatabase db = Helper.getWritableDatabase();
        /*Button inicio = findViewById(R.id.inicio);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity2.class);
                startActivityForResult(intent, 0);

    }
});*/


    }
}
