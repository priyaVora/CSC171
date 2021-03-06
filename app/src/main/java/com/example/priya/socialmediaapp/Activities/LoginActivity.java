package com.example.priya.socialmediaapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Alphatical_Order;
import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Contact;
import com.example.priya.socialmediaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
                    if (!TextUtils.isEmpty(emailAddressView.getText().toString())
                            && !TextUtils.isEmpty(passwordView.getText().toString())) {
                        String email = emailAddressView.getText().toString();
                        String pwd = passwordView.getText().toString();
                        login(email, pwd);
                    } else {

                    }
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
