package com.example.priya.socialmediaapp.ChatApplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Contact;
import com.example.priya.socialmediaapp.R;

import java.util.List;

/**
 * Created by Priya on 8/15/2018.
 */

public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.ViewHolder> {
    private Context context;
    private List<Contact> contacts;


    @Override
    public MyContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(null);
    }

    @Override
    public void onBindViewHolder(@NonNull MyContactAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
