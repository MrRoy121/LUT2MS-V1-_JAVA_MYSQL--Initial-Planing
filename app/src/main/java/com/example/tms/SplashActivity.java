package com.example.tms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.Pulse;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        ProgressBar bar = findViewById(R.id.spin_kit);
        Sprite cb = new CubeGrid();
        bar.setIndeterminateDrawable(cb);


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()){
                    finish();
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }
                else{
                    finish();
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
            }
        }, 5000);
    }
}