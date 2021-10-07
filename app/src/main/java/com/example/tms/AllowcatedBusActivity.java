package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AllowcatedBusActivity extends AppCompatActivity {

    private static final String GET_ALLO = "http://192.168.43.254/TMS/allowcatedbus.php";
    ProgressBar bar;
    String route ;
    TextView r1;
    RequestQueue xs;
    ArrayList<dataset> routes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowcated_bus);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        bar = findViewById(R.id.spin_kit);
        r1 = findViewById(R.id.t1);
        routes = new ArrayList<>();

        Sprite cb = new Circle();
        bar.setIndeterminateDrawable(cb);

        bar.setVisibility(View.VISIBLE);

        Intent i = getIntent();
        if(i.getStringExtra("route")!=null){
            route = i.getStringExtra("route");
            r1.setText("On Route 0"+route);
        }
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ALLO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                routes.add(new dataset(hit.getString("time"),hit.getString("busid")));
                            }
                            if(jsonArray.length()==0){
                                Toast.makeText(getApplicationContext(), "No Busses are allocated on the Route yet!",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            bar.setVisibility(View.GONE);
                            AllowcatedBusAdapter adapter = new AllowcatedBusAdapter(routes, AllowcatedBusActivity.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            bar.setVisibility(View.INVISIBLE);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        bar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("route", route);
                params.put("date", date);
                return params;
            }
        };
        if (xs == null) {
            xs = Volley.newRequestQueue(AllowcatedBusActivity.this);
            xs.add(stringRequest);
        } else {
            xs = null;
        }

    }
}