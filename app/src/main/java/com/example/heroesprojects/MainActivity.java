package com.example.heroesprojects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText txtHeroe;
    private Button btnBusqueda;
    private String heroeAbuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtHeroe=(EditText) findViewById(R.id.editTextTextPersonName);
        btnBusqueda=(Button) findViewById(R.id.button);



    }
    public void buscarHeroe(View view){
        heroeAbuscar=txtHeroe.getText().toString();
        Intent intent= new Intent(this, busquedaHeroe.class);
        intent.putExtra("Heroe",heroeAbuscar);
        startActivity(intent);
        System.out.println("Inciie la actividad 2");

    }
}