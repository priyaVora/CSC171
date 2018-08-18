package com.example.priya.socialmediaapp.ChatApplication.Dialog_Fragment;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.priya.socialmediaapp.ChatApplication.Adapters.MyContactAdapter;
import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Contact;
import com.example.priya.socialmediaapp.R;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Priya on 8/15/2018.
 */

public class AddContactDialog extends Dialog implements View.OnClickListener {
    public Context context;
    public Dialog dialog;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Button addContactButton;
    private Button cancelButton;

    List<Contact> listOfContacts;
    List<String> phone_number_list;

    public AddContactDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listOfContacts = new ArrayList<>();
        phone_number_list = new ArrayList<>();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_contacts_dialog);

        recyclerView = findViewById(R.id.recyclerView_Dialog);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManger = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManger);


       addContactButton = findViewById(R.id.addContactsButton_Dialog);
       cancelButton = findViewById(R.id.cancelContactsButton_Dialog);
        getContactList();


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        adapter = new MyContactAdapter(context,listOfContacts);
        recyclerView.setAdapter(adapter);
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
                        Log.i("CONTACT", "Name: " + name);
                        Log.i("CONTACT", "Phone Number: " + phoneNo);

                        Contact newContact = new Contact();
                        newContact.setName(name);
                        newContact.setPhone_number(phoneNo);

                       Bitmap d =  retrieveContactPhoto(context, newContact.getPhone_number());

                        newContact.setProfileImage(d);
                        String test_number = getSimplifiedNumber(newContact.getPhone_number());
                        if(!phone_number_list.contains(test_number)) {
                            phone_number_list.add(test_number);
                            listOfContacts.add(counter, newContact);
                            counter++;
                            phone_number_list.add(test_number);
                        } else {
                            Log.d("CONTACT","ALREADY IN LIST... " + test_number);
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
        Log.d("CONTACT", phone_number);
        return phone_number;
    }

    @Override
    public void onClick(View v) {

    }

    public ContentResolver getContentResolver() {
        return context.getContentResolver();
    }
}
