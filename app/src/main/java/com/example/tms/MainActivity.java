package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String GET_REQ = "http://192.168.43.254/TMS/requestseat.php";

    CardView profile, send, view;
    TextInputEditText edate, etime;
    TextInputLayout ldate, ltime;
    DatePickerDialog picker;
    ProgressBar bar, bar2;
    Dialog dialog, dialog2;
    RequestQueue xs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        profile = findViewById(R.id.updates);
        send = findViewById(R.id.send);
        view = findViewById(R.id.view);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user user = SharedPrefManager.getInstance(MainActivity.this).getUser();
                if(user.role.equals("Student")){
                    if(user.getSection().equals("null")){
                        finish();
                        startActivity(new Intent(MainActivity.this, UpdateProfileActivity.class));
                    }else{
                        finish();
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    }
                }else if(user.role.equals("Teacher")){
                    if(user.getCodename().equals("null")){
                        finish();
                        startActivity(new Intent(MainActivity.this, UpdateProfileActivity.class));
                    }else{
                        finish();
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    }
                }else{
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.request_seat);

                CardView r1 = dialog.findViewById(R.id.r1);
                CardView r2 = dialog.findViewById(R.id.r2);
                CardView r3= dialog.findViewById(R.id.r3);
                CardView r4 = dialog.findViewById(R.id.r4);

                ldate = dialog.findViewById(R.id.ldate);
                ltime = dialog.findViewById(R.id.ltime);
                edate = dialog.findViewById(R.id.date);
                etime = dialog.findViewById(R.id.time);
                bar = dialog.findViewById(R.id.spin_kit);

                ImageButton cancel = dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog                                                                                               .dismiss();
                    }
                });
                Sprite cb = new ThreeBounce();
                bar.setIndeterminateDrawable(cb);


                edate.setInputType(InputType.TYPE_NULL);
                dialog.findViewById(R.id.date).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent motionEvent) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        picker = new DatePickerDialog(MainActivity.this,
                                (view, year1, monthOfYear, dayOfMonth) -> edate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1), year, month, day);
                        picker.show();
                        return false;
                    }
                });

                dialog.findViewById(R.id.time).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        final Calendar c = Calendar.getInstance();
                        int mHour = c.get(Calendar.HOUR_OF_DAY);
                        int mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(  MainActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        etime.setText(hourOfDay + ":" + minute);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                        return false;
                    }
                });

                r1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestSeat( "1");
                    }
                });
                r2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestSeat( "2");
                    }
                });
                r3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestSeat( "3");
                    }
                });
                r4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestSeat( "4");
                    }
                });
                dialog.show();

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2 = new Dialog(MainActivity.this);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setCancelable(false);
                dialog2.setContentView(R.layout.request_seat);

                CardView r1 = dialog2.findViewById(R.id.r1);
                CardView r2 = dialog2.findViewById(R.id.r2);
                CardView r3= dialog2.findViewById(R.id.r3);
                CardView r4 = dialog2.findViewById(R.id.r4);

                ImageButton cancel = dialog2.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });

                ldate = dialog2.findViewById(R.id.ldate);
                ltime = dialog2.findViewById(R.id.ltime);
                edate = dialog2.findViewById(R.id.date);
                etime = dialog2.findViewById(R.id.time);
                ldate.setVisibility(View.GONE);
                ltime.setVisibility(View.GONE);
                TextView ssr=dialog2.findViewById(R.id.ssr);
                ssr.setVisibility(View.GONE);

                r1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                        allowcatbus( "1");
                    }
                });
                r2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                        allowcatbus( "2");
                    }
                });
                r3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                        allowcatbus( "3");
                    }
                });
                r4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                        allowcatbus( "4");
                    }
                });
                dialog2.show();


            }
        });
    }

    private void requestSeat(String route) {

        bar.setVisibility(View.VISIBLE);
        final String time = etime.getText().toString().trim();
        final String date = edate.getText().toString().trim();
        user user = SharedPrefManager.getInstance(this).getUser();
        final String uname = user.uname;

        if (TextUtils.isEmpty(time)) {
            ltime.setError("Please Select A Time!");
            ltime.requestFocus();
            return;
        }if (TextUtils.isEmpty(date)) {
            ldate.setError("Please Select A Date!");
            ldate.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            if (obj.getBoolean("error")){
                                bar.setVisibility(View.INVISIBLE);
                                dialog.dismiss();
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
                params.put("time", time);
                params.put("date", date);
                params.put("uname", uname);
                params.put("route", route);
                return params;
            }
        };
        if (xs == null) {
            xs = Volley.newRequestQueue(MainActivity.this);
            xs.add(stringRequest);
        } else {
            xs = null;
        }
    }

    private void allowcatbus(String route){
        Intent i = new Intent(MainActivity.this, AllowcatedBusActivity.class);
        i.putExtra("route", route);
        startActivity(i);
    }

}