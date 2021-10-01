package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tms.directionhelpers.FetchURL;
import com.example.tms.directionhelpers.TaskLoadedCallback;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    String rrr;
    private static final String GET_LOCA = "http://192.168.43.254/TMS/getlocabypoint.php";
    private static final String GET_LOCAB = "http://192.168.43.254/TMS/getlocabybusid.php";
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 400,iss;
    private GoogleMap mMap;
    TextView id;
    TextView reachtime;
    ProgressBar bar;
    FrameLayout llout;
    List<locationModel>lll = new ArrayList<>();

    private LatLng origin;
    private LatLng destina;
    private Polyline mPolyline;
    ArrayList<LatLng> mMarkerPoints;
    LatLng ln1 =new LatLng(24.89045748, 91.86762873);
    LatLng ln2 = new LatLng(24.8990212, 91.862321);
    String time;
    RequestQueue xs,xs1;

    private MarkerOptions place1, place2;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Intent i = getIntent();
        rrr= i.getStringExtra("busid");
        gettt();

        llout = findViewById(R.id.llout);
        bar = findViewById(R.id.progressBarDialog);
        Sprite cb = new Wave();
        bar.setIndeterminateDrawable(cb);
        mMarkerPoints = new ArrayList<>();
        reachtime = findViewById(R.id.reachtime);
        llout.setVisibility(View.GONE);
        bar.setVisibility(View.GONE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(MapActivity.this);
        }

        findViewById(R.id.gettime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
                getTime();
            }
        });

        place2 = new MarkerOptions().position(ln1).title("bus").icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.bus));
        place1 = new MarkerOptions().position(ln2).title("stop");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(place1);
        mMap.addMarker(place2);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ln2, 14.0f));
    }
    public void gettt(){
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LOCA,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                               JSONObject userJson = obj.getJSONObject("user");
//                               place1 = new MarkerOptions().position(new LatLng(Double.parseDouble(userJson.getString("latitude")), Double.parseDouble(userJson.getString("longitude")))).title("02");
//
//                            mMap.addMarker(place1);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                user user = SharedPrefManager.getInstance(MapActivity.this).getUser();
//                Map<String, String> params = new HashMap<>();
//                params.put("route", user.pick);
//                return params;
//            }
//        };
//        if (xs == null) {
//            xs = Volley.newRequestQueue(MapActivity.this);
//            xs.add(stringRequest);
//        } else {
//            xs = null;
//        }
//
//        StringRequest stringRequests = new StringRequest(Request.Method.POST, GET_LOCAB,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                                JSONObject userJson = obj.getJSONObject("user");
//                                place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(userJson.getString("latitude")), Double.parseDouble(userJson.getString("longitude")))).title("01");
//
//                            mMap.addMarker(place2);
//                            time = userJson.getString("time");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("route", rrr);
//                return params;
//            }
//        };
//        if (xs1 == null) {
//            xs1 = Volley.newRequestQueue(MapActivity.this);
//            xs1.add(stringRequests);
//        } else {
//            xs1 = null;
//        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    protected void onResume() {
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //loadlocation();
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public void getTime(){
        Double d = distance(ln1.latitude,ln1.longitude,ln2.latitude,ln2.longitude);
        Double speed = 40.00;
        Double time = d/speed;
        time = time * 3600;
        int S = (int) (time % 60);
        int H = (int) (time / 60);
        int M = H % 60;
        H = H / 60;
        reachtime.setText(H + ":" + M + ":" + S);

        Log.e("SD", H + ":" + M + ":" + S);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}