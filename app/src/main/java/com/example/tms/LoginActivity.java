package com.example.tms;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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

public class LoginActivity extends AppCompatActivity {

    private static final String GET_LOGIN = "http://192.168.43.254/TMS/registerapi.php?apicall=login";
    TextInputEditText euname, epass;
    TextInputLayout luname, lpass;
    RequestQueue xs;

    ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        epass = findViewById(R.id.pass);
        euname = findViewById(R.id.uname);
        luname = findViewById(R.id.luname);
        lpass = findViewById(R.id.lpass);

        bar = findViewById(R.id.spin_kit);
        Sprite cb = new Circle();
        bar.setIndeterminateDrawable(cb);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }
    private void userLogin() {
        final String uname = euname.getText().toString();
        final String pass = epass.getText().toString();
        if (TextUtils.isEmpty(uname)) {
            luname.setError("Please enter User Name!");
            luname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            lpass.setError("Please enter your password");
            lpass.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            Log.e("error", response);
                            if (!obj.getBoolean("error")) {
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
                                Toast.makeText(getApplicationContext(), "error"+obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uname", uname);
                params.put("pass", pass);
                return params;
            }
        };
        if (xs == null) {
            xs = Volley.newRequestQueue(LoginActivity.this);
            xs.add(stringRequest);
        } else {
            xs = null;
        }
    }
}