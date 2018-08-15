package com.example.priya.socialmediaapp.ChatApplication.Dialog_Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.example.priya.socialmediaapp.R;
import android.widget.Button;

/**
 * Created by Priya on 8/15/2018.
 */

public class AddContactDialog extends Dialog implements View.OnClickListener {
    public Context context;
    public Dialog dialog;

    private RecyclerView recyclerView;
    private Button addContactButton;
    private Button cancelButton;

    public AddContactDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.add_contacts_dialog);

       addContactButton = findViewById(R.id.addContactsButton_Dialog);
       cancelButton = findViewById(R.id.cancelContactsButton_Dialog);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
