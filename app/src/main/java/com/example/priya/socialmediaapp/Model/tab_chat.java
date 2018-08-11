package com.example.priya.socialmediaapp.Model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priya.socialmediaapp.R;

/**
 * Created by Priya on 8/11/2018.
 */

public class tab_chat extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chats, container, false);
        return rootView;
    }
}