package com.example.food.Onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.food.R;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StartupActivity2 extends AppCompatActivity {
    private static final String TAG = "StartupActivity2";

    AppCompatButton btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_startup2);

        btnNext = (AppCompatButton) findViewById(R.id.next_button);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartupActivity2.this, StartupActivity3.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartupActivity2.this).toBundle());
            }
        });
    }
}
