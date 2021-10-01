package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {


    private static final String GET_UPDAT = "http://192.168.43.254/TMS/registerapi.php?apicall=update";
    TextInputEditText fname, section, batch, dept;
    TextInputLayout lfname, lsec, lbatch, ldept;

    Spinner spinner;
    RequestQueue xs;
    ProgressBar bar;
    boolean stud = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        fname = findViewById(R.id.fname);
        section = findViewById(R.id.sec);
        batch = findViewById(R.id.batch);
        dept = findViewById(R.id.dept);
        lfname = findViewById(R.id.lfname);
        lbatch = findViewById(R.id.lbatch);
        ldept = findViewById(R.id.ldept);
        lsec = findViewById(R.id.lsec);
        spinner = findViewById(R.id.spinner);


        bar = findViewById(R.id.spin_kit);
        Sprite cb = new Circle();
        bar.setIndeterminateDrawable(cb);


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            user user = SharedPrefManager.getInstance(this).getUser();
            Log.e("ROLE", user.getRole());
            if(user.getRole().equals("Student")){
                stud = true;
                fname.setText(user.getFname());
                if(!user.getSection().equals("null")){
                    section.setText(user.getSection());
                    batch.setText(user.getBatch());
                }
            }else if(user.getRole().equals("Teacher")){
                stud = false;
                lbatch.setHint("Code Name");
                lsec.setHint("Designation");
                section.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                lfname.setVisibility(View.GONE);
                batch.setInputType(InputType.TYPE_CLASS_TEXT);
                if(!user.getCodename().equals("null")){
                    batch.setText(user.codename);
                    section.setText(user.designation);
                }
            }else{
                finish();
                startActivity(new Intent(UpdateProfileActivity.this,ProfileActivity.class));
            }
        }
        else{
            finish();
            startActivity(new Intent(UpdateProfileActivity.this,LoginActivity.class));
        }


        findViewById(R.id.dept).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinner.performClick();
                return false;
            }
        });

        String[] type = new String[] {"CSE", "EEE", "Civil", "Architecture","BBA","English","LAW"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, type);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dept.setText(type[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {updateUser();
            }
        });

    }

    private void updateUser() {

        final String sfname = fname.getText().toString().trim();
        final String sbatch = batch.getText().toString().trim();
        final String ssect = section.getText().toString().trim();
        final String sdept = dept.getText().toString().trim();


        if (TextUtils.isEmpty(String.valueOf(sbatch))) {
            if(stud){
                lbatch.setError("Please enter Your Batch!");
                lbatch.requestFocus();
            }else{
                lbatch.setError("Please enter Your Code Name!");
                lbatch.requestFocus();
            }
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(ssect))) {
            if(stud){
                lsec.setError("Please enter Your Section!");
                lsec.requestFocus();
            }else{
                lsec.setError("Please enter Your Designation!");
                lsec.requestFocus();
            }
            return;
        }


        if (TextUtils.isEmpty(sdept)) {
            ldept.setError("Select A Department!!");
            ldept.requestFocus();
            return;
        }

        bar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_UPDAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
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

                user user = SharedPrefManager.getInstance(UpdateProfileActivity.this).getUser();
                Map<String, String> params = new HashMap<>();
                params.put("sid", user.sid);
                params.put("uname", user.uname);
                params.put("pass", user.pass);
                params.put("phone", user.phone);
                params.put("dept", sdept);
                params.put("pick", user.pick);
                if(stud){
                    params.put("section", ssect);
                    params.put("batch", sbatch);
                    params.put("fname", sfname);
                    params.put("codename", "null");
                    params.put("designation", "null");
                }else{
                    params.put("fname", user.fname);
                    params.put("section", "null");
                    params.put("batch", "null");
                    params.put("codename", sbatch);
                    params.put("designation", ssect);
                }
                params.put("role", user.role);
                return params;
            }
        };
        if (xs == null) {
            xs = Volley.newRequestQueue(UpdateProfileActivity.this);
            xs.add(stringRequest);
        } else {
            xs = null;
        }
    }
}