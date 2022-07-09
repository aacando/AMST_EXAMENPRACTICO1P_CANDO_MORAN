package com.example.heroesprojects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class busquedaHeroe extends AppCompatActivity {
    private RequestQueue mQueue = null;

    private String url = "https://superheroapi.com/api/5451027931653038/search/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_heroe);
        LinearLayout resultado = (LinearLayout) findViewById(R.id.linear);
        Toast toast = Toast.makeText(getApplicationContext(), "Busqueda en proceso por favor espere", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = getIntent();
        String nombre = intent.getSerializableExtra("Heroe").toString();
        System.out.println(nombre);
        mQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url+nombre,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        TextView texto = new TextView(busquedaHeroe.this);
                        try {

                            JSONArray resultadoConsulta= response.getJSONArray("results");
                            int cantResultados= resultadoConsulta.length();
                            texto.setTextSize(24);
                            texto.setText("Resultados:"+ Integer.toString(cantResultados));
                            resultado.addView(texto);


                            for (int i=0; i < resultadoConsulta.length(); i++) {
                                String nombre= resultadoConsulta.getJSONObject(i).optString("name");
                                String id = resultadoConsulta.getJSONObject(i).optString("id");
                                texto = new TextView(busquedaHeroe.this);
                                texto.setTextSize(18);
                                texto.setText(nombre);
                                texto.setOnClickListener( new View.OnClickListener(){
                                    public void onClick(View view){
                                        Intent i =new Intent(busquedaHeroe.this,Estadisticas.class);
                                        i.putExtra("id",id);
                                        startActivity(i);
                                    }
                                });
                                resultado.addView(texto);

                            }


                            System.out.println(resultadoConsulta.length());

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };;
        mQueue.add(jsObjRequest);

    }
}
