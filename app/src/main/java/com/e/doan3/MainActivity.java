package com.e.doan3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler chuyen = new Handler();
        chuyen.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(MainActivity.this, TrangChu.class);
                startActivity(it);
                finish();
                chuyen.removeCallbacks(this);
            }
        }, 6000);
    }

}



