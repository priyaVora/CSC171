package com.example.priya.socialmediaapp.ChatApplication.Adapters;

import android.Manifest;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;


import com.example.priya.socialmediaapp.Activities.Camera.RunTimePermission;
import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Contact;
import com.example.priya.socialmediaapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by Priya on 8/15/2018.
 */

public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.ViewHolder> {
    private Context context;
    private List<Contact> contacts;

    private RunTimePermission runTimePermission;
    public MyContactAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public MyContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyContactAdapter.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.contact_name.setText(contact.getName());
        holder.contact_phone_number.setText(contact.getPhone_number());
        Uri myUri = Uri.parse(contact.getProfileImage());
        holder.contact_profile_button.setImageURI(myUri);

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private ImageButton contact_profile_button;
        private EditText contact_phone_number;
        private EditText contact_name;
        private Button call_button;
        private Button video_call_button;



        public ViewHolder(View itemView, final Context contxt) {
            super(itemView);
            context = contxt;
            contact_name = itemView.findViewById(R.id.contact_name_view);
            contact_phone_number = itemView.findViewById(R.id.contact_phone_number_view);
            contact_profile_button = itemView.findViewById(R.id.contact_profile_image_button);

            contact_name.setInputType(InputType.TYPE_NULL);
            contact_phone_number.setInputType(InputType.TYPE_NULL);

            call_button = itemView.findViewById(R.id.callButton);
            video_call_button = itemView.findViewById(R.id.video_call_Button);


            call_button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Call Button was clicked!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact_phone_number.getText()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                        Toast.makeText(context, "Permissions to call are granted...", Toast.LENGTH_SHORT).show();

                        context.startActivity(intent);

                        return;
                    } else {
                        Toast.makeText(context, "Permissions to call are not granted...", Toast.LENGTH_SHORT).show();
                        context.startActivity(intent);
                    }

                    video_call_button.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "Video Calling...", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            List<Contact> contact = (List<Contact>) contacts.get(position);

            Toast.makeText(context, "Clicked!", Toast.LENGTH_LONG);
        }
    }
}
