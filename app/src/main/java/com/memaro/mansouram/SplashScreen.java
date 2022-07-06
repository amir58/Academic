package com.memaro.mansouram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.memaro.mansouram.authentication.SignIn;

public class SplashScreen extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView = findViewById(R.id.splash_tv);

        YoYo.with(Techniques.FadeIn).duration(3000).playOn(textView);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent;
                    if (user == null) {
                        intent = new Intent(SplashScreen.this, SignIn.class);
                    } else {
                        intent = new Intent(SplashScreen.this, MainActivity.class);
                    }
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }
}
