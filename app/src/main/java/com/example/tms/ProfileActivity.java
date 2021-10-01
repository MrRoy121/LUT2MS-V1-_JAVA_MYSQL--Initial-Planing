package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView sid,fname,uname,pass, phone, section, batch, dept, isection, ibatch, idept;
    ImageButton btnLogout, idedit;
    View secid, batchid, deptid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        btnLogout = findViewById(R.id.logout);
        sid = findViewById(R.id.id);
        fname = findViewById(R.id.fname);
        uname = findViewById(R.id.uname);
        pass = findViewById(R.id.pass);
        btnLogout = findViewById(R.id.logout);
        phone = findViewById(R.id.phone);
        section = findViewById(R.id.sec);
        batch = findViewById(R.id.batch);
        dept = findViewById(R.id.dept);
        ibatch = findViewById(R.id.ibatch);
        isection = findViewById(R.id.isec);
        idept = findViewById(R.id.idept);
        idedit = findViewById(R.id.idedit);
        secid = findViewById(R.id.secid);
        batchid = findViewById(R.id.batchid);
        deptid = findViewById(R.id.deptid);


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            user user = SharedPrefManager.getInstance(this).getUser();

            Log.e("Tags",user.getRole());
            if(user.getRole().equals("Student")){
                sid.setText(String.valueOf(user.getSid()));
                fname.setText(user.getFname());
                pass.setText(user.getPass());
                uname.setText(user.getUname());
                phone.setText(user.getPhone());
                section.setText(user.getSection());
                batch.setText(user.getBatch());
                dept.setText(user.getDept());
            }else if(user.getRole().equals("Teacher")){
                sid.setText(String.valueOf(user.getSid()));
                fname.setText(user.getFname());
                pass.setText(user.getPass());
                uname.setText(user.getUname());
                phone.setText(user.getPhone());
                isection.setText("Code Name");
                section.setText(user.getCodename());
                batch.setText(user.getDesignation());
                ibatch.setText("Designation");
                dept.setText(user.getDept());
            }else{
                sid.setText(String.valueOf(user.getSid()));
                fname.setText(user.getFname());
                pass.setText(user.getPass());
                uname.setText(user.getUname());
                phone.setText(user.getPhone());
                isection.setVisibility(View.GONE);
                section.setVisibility(View.GONE);
                batch.setVisibility(View.GONE);
                ibatch.setVisibility(View.GONE);
                dept.setVisibility(View.GONE);
                idedit.setVisibility(View.GONE);
                secid.setVisibility(View.GONE);
                batchid.setVisibility(View.GONE);
                deptid.setVisibility(View.GONE);
                idept.setVisibility(View.GONE);
            }
        }
        else{
            finish();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        }

        idedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}