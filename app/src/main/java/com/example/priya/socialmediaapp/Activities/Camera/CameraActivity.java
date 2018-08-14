package com.example.priya.socialmediaapp.Activities.Camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.priya.socialmediaapp.Activities.Camera.Fragment.Camera2BasicFragment;
import com.example.priya.socialmediaapp.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.getSupportActionBar().hide();
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }

}

