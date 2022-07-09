package com.example.heroesprojects;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Estadisticas extends AppCompatActivity {
    private RequestQueue mQueue2 = null;
    private String url = "https://superheroapi.com/api/5451027931653038/";
    private TextView n_ombre,nombreC;
    private ImageView imagenHeroe;
    private BarChart diagrama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        Intent intent = getIntent();
        String id = intent.getSerializableExtra("id").toString();
        System.out.println(id);
        n_ombre=(TextView) findViewById(R.id.Nombre);
        nombreC=(TextView) findViewById(R.id.NombreCompleto);
        imagenHeroe=(ImageView) findViewById(R.id.imageView2);
        diagrama=(BarChart) findViewById(R.id.BarChart);
        mQueue2= Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url+id,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {

                            //JSONObject resultadoConsulta= response.getJSONObject("results");
                            //System.out.println(resultadoConsulta);
                            String nombre= response.getString("name");
                            String nombreCompleto=response.getJSONObject("biography").getString("full-name");
                            String urlImagen= response.getJSONObject("image").getString("url");
                            String inteligencia = response.getJSONObject("powerstats").getString("intelligence");
                            String fuerza = response.getJSONObject("powerstats").getString("strength");
                            String velocidad = response.getJSONObject("powerstats").getString("speed");
                            String durabilidad = response.getJSONObject("powerstats").getString("durability");
                            String poder = response.getJSONObject("powerstats").getString("power");
                            String combate = response.getJSONObject("powerstats").getString("combat");

                            n_ombre.setText(nombre);
                            nombreC.setText(nombreCompleto);

                            Picasso.with(getApplicationContext()).load(urlImagen).fit().into(imagenHeroe);

                            ArrayList<BarEntry> dataValores = new ArrayList<BarEntry>();
                            dataValores.add(new BarEntry(0,Integer.parseInt(inteligencia)));
                            dataValores.add(new BarEntry(1,Integer.parseInt(fuerza)));
                            dataValores.add(new BarEntry(2,Integer.parseInt(velocidad)));
                            dataValores.add(new BarEntry(3,Integer.parseInt(durabilidad)));
                            dataValores.add(new BarEntry(4,Integer.parseInt(poder)));
                            dataValores.add(new BarEntry(5,Integer.parseInt(combate)));
                            BarDataSet barset1=new BarDataSet(dataValores,"Data");
                            BarData barData = new BarData();
                            barData.addDataSet(barset1);

                            diagrama.setData(barData);
                            diagrama.invalidate();








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
        mQueue2.add(jsObjRequest);
    }
}