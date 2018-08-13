package com.example.priya.socialmediaapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.priya.socialmediaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailAddressView;
    private EditText passwordView;
    private Button loginButton;
    private Button createButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddressView = findViewById(R.id.emailAddressLoginScreen);
        loginButton = findViewById(R.id.loginButtonLoginScreen);
        passwordView = findViewById(R.id.passwordLoginScreen);
        createButton = findViewById(R.id.createButtonLoginScreen);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if(mUser != null) {
                    Toast.makeText(LoginActivity.this, "Signed In", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainContentTabbedActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Not Signed In", Toast.LENGTH_LONG).show();
                }
            }
        };


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailAddressView != null && passwordView !=null) {
                    Log.d("AuthMessage", "NOTTTTTTTTTNULL");
                    if (!TextUtils.isEmpty(emailAddressView.getText().toString())
                            && !TextUtils.isEmpty(passwordView.getText().toString())) {
                        String email = emailAddressView.getText().toString();
                        String pwd = passwordView.getText().toString();
                        login(email, pwd);
                    } else {

                    }
                } else {
                    Log.d("AuthMessage", "NULLLLLLLLLLLLLLLlll");
                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
                finish();
            }
        });
    }


    private void login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Signed In", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(LoginActivity.this, MainContentTabbedActivity.class ));
                                    finish();
                                } else {

                                }
                            }
                        }
                );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
