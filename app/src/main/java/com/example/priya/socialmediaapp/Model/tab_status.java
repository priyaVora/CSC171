package com.example.priya.socialmediaapp.Model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.priya.socialmediaapp.Activities.Status_Edit_Activity;
import com.example.priya.socialmediaapp.R;

/**
 * Created by Priya on 8/11/2018.
 */

public class tab_status extends Fragment {

    private Button menu_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.status, container, false);
        menu_button = rootView.findViewById(R.id.status_menu_button);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(rootView.getContext(), Status_Edit_Activity.class));

            }
        });
        Log.d("Status tab was called","Status called");
        return rootView;
    }
}
