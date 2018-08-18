package com.example.priya.socialmediaapp.ChatApplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Message;
import com.example.priya.socialmediaapp.ChatApplication.Dialog_Fragment.AddContactDialog;
import com.example.priya.socialmediaapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText message;
    private Button sendMessageButton;
    private ImageButton addContactsButton;
    private static final String USER_ID_KEY = "userId";
    private String currentUserId;
    private ListView listView;
    private ArrayList<Message> mMessages;
    private ChatAdapter mAdapter;
    private Handler handler = new Handler();

    private static final int MAX_CHAT_MSG_TO_SHOW = 70;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.getSupportActionBar().hide();
        getCurrentUser();
        handler.postDelayed(runnable, 100);
        sendMessageButton = findViewById(R.id.buttonSend);
        addContactsButton = findViewById(R.id.addContactsButton);

        addContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContacts();
                AddContactDialog cd = new AddContactDialog(ChatActivity.this);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                cd.setCancelable(false);
                cd.show();
                Toast.makeText(ChatActivity.this, "ADD CONTACT VIEW", Toast.LENGTH_LONG).show();

            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            //lstNames.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessage();
            handler.postDelayed(this, 100);
        }
    };

    private void refreshMessage() {

    }

    private void getCurrentUser() {
        currentUserId = "1";// getuserFromFirebase(currentUserId)// get object id of the current user
        messagePosting();
    }

    private void messagePosting() {
        message = findViewById(R.id.etMessage);
        sendMessageButton = findViewById(R.id.buttonSend);
        listView = findViewById(R.id.listview_chat);
        mMessages = new ArrayList<>();

        Message new_message = new Message();
        new_message.setTimestamp(String.valueOf(System.currentTimeMillis()));
        new_message.setUserid("2");
        new_message.setMessage_body("Hey Priya!");

        mMessages.add(new_message);
        mAdapter = new ChatAdapter(ChatActivity.this, currentUserId, mMessages);
        listView.setAdapter(mAdapter);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.getText().toString().equals("")) {
                    Message msg = new Message();
                    msg.setUserid(currentUserId);
                    msg.setMessage_body("This is the messsage body");
                    msg.setTimestamp(String.valueOf(java.lang.System.currentTimeMillis()));
                    //save message
                } else {
                    Toast.makeText(getApplicationContext(), "Empty Message!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
