package com.example.food.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.food.Interfaces.ICompleteListener;
import com.example.food.R;
import com.example.food.User.User;
import com.example.food.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private String email,password, username, phoneNumber;
    private EditText mEmail, mPassword, mUsername, mPhoneNumber;
    private ProgressBar mProgressBar;
    private AppCompatButton btnRegister;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseMethods = new FirebaseMethods(RegisterActivity.this);
        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "onCreate: started");

        initWidgets();
        init();
    }

    private void init(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                phoneNumber = mPhoneNumber.getText().toString();

                if(checkInputs(email, username, password, phoneNumber)){
                    mProgressBar.setVisibility(View.VISIBLE);
                    firebaseMethods.registerNewEmail(email, password, username, phoneNumber, new ICompleteListener() {
                        @Override
                        public void OnComplete(boolean isSuccessfulCompleted) {

                            if(isSuccessfulCompleted)
                            {
                                firebaseMethods.addNewUser(email,username,phoneNumber,"",new ICompleteListener()
                                {

                                    @Override
                                    public void OnComplete(boolean isSuccessfulCompleted) {
                                        if(isSuccessfulCompleted)
                                        {
                                            mAuth.signOut();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }

                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private boolean checkInputs(String email, String username, String password, String phoneNumber){
        Log.d(TAG, "checkkInputs: checking inputs for null value");
        if(email.equals("") || username.equals("") || password.equals("") || phoneNumber.equals("")){
            Toast.makeText(RegisterActivity.this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initWidgets(){
        Log.d(TAG, "initWidgets: initializing widgets");
        mEmail = (EditText) findViewById(R.id.email_field);
        mPassword = (EditText) findViewById(R.id.password_field);
        mUsername = (EditText) findViewById(R.id.name_field);
        mPhoneNumber = (EditText) findViewById(R.id.phone_field);
        btnRegister = (AppCompatButton) findViewById(R.id.btnRegister);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.GONE);
    }
}

