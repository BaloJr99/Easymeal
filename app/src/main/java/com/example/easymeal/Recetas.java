package com.example.easymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.easymeal.database.DbAyuda;

import java.sql.Array;
import java.util.ArrayList;

public class Recetas extends AppCompatActivity{
    DbAyuda db;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recetas);
         db = new DbAyuda(this);

         listItem = new ArrayList<>();

         viewData();
        
    }

    private void viewData() {
        Cursor cursor = db.selectReceta();
        if(cursor.getCount()==0){
            Toast.makeText(this,"Sin datos",Toast.LENGTH_SHORT).show();
        }else{
          while(cursor.moveToNext()){
              listItem.add(cursor.getString(1));
          }
          adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listItem);
        }
    }
}
