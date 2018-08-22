package com.example.priya.socialmediaapp.Model;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priya.socialmediaapp.ChatApplication.Adapters.MyContactAdapter;
import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Alphatical_Order;
import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Contact;
import com.example.priya.socialmediaapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Priya on 8/11/2018.
 */

public class tab_calls extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Contact> listofContacts;

    private ProgressDialog mProggress_dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calls, container, false);

        context =  rootView.getContext();
        recyclerView = rootView.findViewById(R.id.recycler_call_viewId);
        recyclerView.setHasFixedSize(true);
        mProggress_dialog = new ProgressDialog(context);
        mProggress_dialog.setMessage("Reading Contacts....");
        mProggress_dialog.show();

        LinearLayoutManager layoutManger = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManger);

        listofContacts = new ArrayList<>();
        adapter = new MyContactAdapter(context,listofContacts);
        recyclerView.setAdapter(adapter);
        mProggress_dialog.dismiss();

        return rootView;
    }

    public List<Contact> getListofContacts() {
        return listofContacts;
    }

    public void setListofContacts(List<Contact> listofContacts) {
        this.listofContacts = new ArrayList<>();

        this.listofContacts = listofContacts;

//        List<Contact> new_list = new ArrayList<>();
//        List<String> phone_number = new ArrayList<>();
//
//        int phone_counter = 0;
//        for(int i = 0; i < listofContacts.size(); i++) {
//            if (!new_list.contains(listofContacts.get(i)) && !(phone_number.contains(listofContacts.get(i).getPhone_number()))) {
//                    new_list.add(i, listofContacts.get(i));
//                    phone_number.add(phone_counter, listofContacts.get(i).getPhone_number());
//                    phone_counter++;
//
//            }
//        }
//
//        this.listofContacts = new_list;
//        if(listofContacts.size() == 1) {
//            listofContacts = new ArrayList<>();
//        }
        Alphatical_Order order = new Alphatical_Order();
     //   listofContacts = order.sort(new_list);
        listofContacts = order.sort(listofContacts);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManger = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManger);
        adapter = new MyContactAdapter(context,listofContacts);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RecyclerView.Adapter return_adapter() {
        return adapter;
    }

    public ContentResolver getContentResolver() {
        return context.getContentResolver();
    }
}
