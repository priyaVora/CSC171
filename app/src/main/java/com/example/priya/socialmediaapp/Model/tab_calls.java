package com.example.priya.socialmediaapp.Model;

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
import java.util.List;

/**
 * Created by Priya on 8/11/2018.
 */

public class tab_calls extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Contact> listofContacts;
    List<String> phone_number_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calls, container, false);
        listofContacts = new ArrayList<>();
        phone_number_list = new ArrayList<>();
        context =  rootView.getContext();
        recyclerView = rootView.findViewById(R.id.recycler_call_viewId);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManger = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManger);


        if(listofContacts.isEmpty()) {
            getContactList();
        }

        Alphatical_Order order_list = new Alphatical_Order();
        listofContacts = order_list.sort(listofContacts);


        adapter = new MyContactAdapter(context,listofContacts);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public static Bitmap retrieveContactPhoto(Context context, String number) {
        ContentResolver contentResolver = context.getContentResolver();

        String contactId = null;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID};

        Cursor cursor =
                contentResolver.query(
                        uri,
                        projection,
                        null,
                        null,
                        null);
        //   Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
            }
            cursor.close();
        }

        Bitmap photo = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.white_circle);

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }

            assert inputStream != null;
            if(inputStream != null) {
                inputStream.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            int counter = 0;
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));


                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("CONTACT from call", "Name: " + name);
                        Log.i("CONTACT from call", "Phone Number: " + phoneNo);

                        Contact newContact = new Contact();
                        newContact.setName(name);
                        newContact.setPhone_number(phoneNo);

                        Bitmap d =  retrieveContactPhoto(context, newContact.getPhone_number());

                        newContact.setProfileImage(d);
                        String test_number = getSimplifiedNumber(newContact.getPhone_number());
                        if(!phone_number_list.contains(test_number)) {
                            phone_number_list.add(test_number);
                            listofContacts.add(counter, newContact);
                            counter++;
                            phone_number_list.add(test_number);
                        } else {
                            Log.d("CONTACT from call","ALREADY IN LIST... " + test_number);
                        }



                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

    public String getSimplifiedNumber(String phone_number) {
        phone_number = phone_number.replace("+", "");
        phone_number = phone_number.replace("(", "");
        phone_number = phone_number.replace(")", "");
        phone_number = phone_number.replace("-", "");
        phone_number = phone_number.replace(" ", "");
        Log.d("CONTACT from call", phone_number);
        return phone_number;
    }

    public ContentResolver getContentResolver() {
        return context.getContentResolver();
    }
}
