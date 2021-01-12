package com.example.food.Onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.food.Login.LoginActivity;
import com.example.food.R;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class StartupActivity1 extends AppCompatActivity {
    private static final String TAG = "StartupActivity1";
    AppCompatButton btnNext;
    TextView skipButton;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startup1);

        btnNext = (AppCompatButton) findViewById(R.id.next_button1);
        skipButton = (TextView) findViewById(R.id.skipBtn1);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StartupActivity1.this, StartupActivity2.class);
                startActivity(intent1);
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(StartupActivity1.this, LoginActivity.class);
                startActivity(intent2);
            }
        });
    }
}