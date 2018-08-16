package com.example.priya.socialmediaapp.ChatApplication.Dialog_Fragment;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Contact;
import com.example.priya.socialmediaapp.R;
import android.widget.Button;

import java.util.ArrayList;
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

    public AddContactDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listOfContacts = new ArrayList<>();

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

        //set self made adapter here...
        recyclerView.setAdapter(adapter);
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
                        listOfContacts.add(counter, newContact);
                        counter++;
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

    @Override
    public void onClick(View v) {

    }

    public ContentResolver getContentResolver() {
        return context.getContentResolver();
    }
}
