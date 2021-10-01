package com.example.tms;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String GET_REGI = "http://192.168.43.254/TMS/registerapi.php?apicall=signup";
    private static final String GET_POINT = "http://192.168.43.254/TMS/getpoint.php";
    TextInputEditText esid, euname,efname, epass, ephone, epick;
    TextInputLayout lsid,lfname, luname, lpass, lphone, lpick;

    boolean stud = true, tech = false, staf = false;

    ProgressBar bar;
    CardView student, teacher, staff;

    List<String> sss;
    Spinner spinner;
    RequestQueue xs, xs1;
    String role = "Student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        spinner = findViewById(R.id.spinner);
        efname = findViewById(R.id.fname);
        euname = findViewById(R.id.uname);
        epass = findViewById(R.id.pass);
        esid = findViewById(R.id.sid);
        ephone = findViewById(R.id.phone);
        epick = findViewById(R.id.pick);
        lphone = findViewById(R.id.lphone);
        lpick = findViewById(R.id.lpick);
        lsid = findViewById(R.id.lsid);
        lfname = findViewById(R.id.lfname);
        luname = findViewById(R.id.luname);
        lpass = findViewById(R.id.lpass);
        student = findViewById(R.id.student);
        teacher = findViewById(R.id.teacher);
        staff = findViewById(R.id.staff);
        sss = new ArrayList<>();


        bar = findViewById(R.id.spin_kit);
        Sprite cb = new Circle();
        bar.setIndeterminateDrawable(cb);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {registerUser();
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        findViewById(R.id.pick).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinner.performClick();
                return false;
            }
        });


        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!stud){
                    stud = true;
                    tech = false;
                    staf = false;
                    student.setCardBackgroundColor(getResources().getColor(R.color.main));
                    teacher.setCardBackgroundColor(getResources().getColor(R.color.dash));
                    staff.setCardBackgroundColor(getResources().getColor(R.color.dash));
                }
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tech){
                    tech = true;
                    stud = false;
                    staf = false;
                    student.setCardBackgroundColor(getResources().getColor(R.color.dash));
                    teacher.setCardBackgroundColor(getResources().getColor(R.color.main));
                    staff.setCardBackgroundColor(getResources().getColor(R.color.dash));
                }
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!staf){
                    tech = false;
                    stud = false;
                    staf = true;
                    student.setCardBackgroundColor(getResources().getColor(R.color.dash));
                    teacher.setCardBackgroundColor(getResources().getColor(R.color.dash));
                    staff.setCardBackgroundColor(getResources().getColor(R.color.main));
                }
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                sss.add(new String(hit.getString("sname")));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_activated_1, sss);
                            spinner.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        if (xs == null) {
            xs = Volley.newRequestQueue(RegisterActivity.this);
            xs.add(stringRequest);
        } else {
            xs = null;
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                epick.setText(sss.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    private void registerUser() {

        final String sid = esid.getText().toString().trim();
        final String uname = euname.getText().toString().trim();
        final String fname = efname.getText().toString().trim();
        final String password = epass.getText().toString().trim();
        final String phone = ephone.getText().toString().trim();
        final String pick = epick.getText().toString().trim();

        if(stud){
            role = "Student";
        }else if(tech){
            role = "Teacher";
        }else{
            role = "Staff";
        }



       if (TextUtils.isEmpty(fname)) {
            lfname.setError("Please enter Your Full Name!");
            lfname.requestFocus();
            return;
        }if (TextUtils.isEmpty(fname)) {
            luname.setError("Please enter Your User Name!");
            luname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(sid))) {
            lsid.setError("Please enter Your Student ID!");
            lsid.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(phone))) {
            lphone.setError("Please enter Your Contact Number!");
            lphone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(String.valueOf(pick))) {
            lpick.setError("Please select Your Pick Up Stopage!");
            lpick.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            lpass.setError("Enter a password!");
            lpass.requestFocus();
            return;
        }

        bar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_REGI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("tag", response);
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONObject userJson = obj.getJSONObject("user");
                                user user = new user(
                                        userJson.getString("sid"),
                                        userJson.getString("uname"),
                                        userJson.getString("fname"),
                                        userJson.getString("pass"),
                                        userJson.getString("phone"),
                                        userJson.getString("dept"),
                                        userJson.getString("pick"),
                                        userJson.getString("section"),
                                        userJson.getString("batch"),
                                        userJson.getString("role"),
                                        userJson.getString("codename"),
                                        userJson.getString("designation")
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
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
                params.put("sid", sid);
                params.put("uname", uname);
                params.put("fname", fname);
                params.put("pass", password);
                params.put("phone", phone);
                params.put("dept", "null");
                params.put("pick", pick);
                params.put("section", "null");
                params.put("batch", "null");
                params.put("role", role);
                params.put("codename", "null");
                params.put("designation", "null");
                return params;
            }
        };
        if (xs == null) {
            xs = Volley.newRequestQueue(RegisterActivity.this);
            xs.add(stringRequest);
        } else {
            xs = null;
        }
    }
}