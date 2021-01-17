package com.example.food.Login;

import androidx.appcompat.app.AppCompatActivity;
import com.example.food.R;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private EditText mEmail, mPassword, mUsername;
    private ProgressBar mProgressBar;
    private Button btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
    }
}