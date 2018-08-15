package com.example.priya.socialmediaapp.ChatApplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;

import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Message;
import com.example.priya.socialmediaapp.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private EditText message;
    private Button sendMessageButton;
    private static final String USER_ID_KEY = "userId";
    private String currentUserId;
    private ListView listView;
    private ArrayList<Message> mMessages;
  //  private ChatAdapter mAdapter;
    private Handler handler = new Handler();

    private static final int MAX_CHAT_MSG_TO_SHOW = 70;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getCurrentUser();
        handler.postDelayed(runnable, 100);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessage();
            handler.postDelayed(this, 100);
        }
    };

    private void getCurrentUser() {
       // currentUserId = ParseUser.getCurrentUser().getObjectId();
        messagePosting();
    }

    private void messagePosting() {
        message = findViewById(R.id.etMessage);
        sendMessageButton = findViewById(R.id.buttonSend);
        listView = findViewById(R.id.listview_chat);
        mMessages = new ArrayList<>();
        //mAdapter = new ChatAdapter(ChatActivity.this, currentUserId, mMessages);
        //listView.setAdapter(mAdapter);

        //sendMessageButton.setOnClickListener(new View);
        //---> if(!message.getText().toString().equals()) {
        //  Set message user id, set message body
            //receiveMessage(); --> method
        //}  else {

        // "Empty message"
        //}
    }

    private void refreshMessage() {
        receiveMessage();
    }

    private void receiveMessage() {
        //Parse query set max queries to show.
        //setLimitto MaxChatMsg toShow
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
