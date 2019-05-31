package com.alan.moviles.contactossqlite;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.pgb);


        CountDownTimer count = new CountDownTimer(2000, 1000) {
            int progress = 0;
            @Override
            public void onTick(long millisUntilFinished) {
                progress += 10;
                progressBar.setProgress(progress);
                if (progress >=100){
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFinish() {
                Intent i = new Intent(getApplicationContext(), ListaContactos.class);
                startActivity(i);
                finish();
            }
        }.start();
    }
}
