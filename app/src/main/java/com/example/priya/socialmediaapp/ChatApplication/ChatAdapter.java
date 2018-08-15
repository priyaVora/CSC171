package com.example.priya.socialmediaapp.ChatApplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Message;
import com.example.priya.socialmediaapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by Priya on 8/15/2018.
 */

public class ChatAdapter extends ArrayAdapter {
    private String mUserId;


    public ChatAdapter(@NonNull Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        mUserId = userId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_row, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = convertView.findViewById(R.id.ProfileLeft);
            holder.imageRight = convertView.findViewById(R.id.ProfileRight);
            convertView.setTag(holder);
        }

        final Message message = (Message) getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final boolean isCurrentUserMe = message.getUserid().equals(mUserId);
        if (isCurrentUserMe) {
            holder.imageRight.setVisibility(View.VISIBLE);
            holder.imageLeft.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            holder.imageRight.setVisibility(View.GONE);
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }

        final ImageView profileView = isCurrentUserMe ? holder.imageRight : holder.imageLeft;
        Picasso.with(getContext()).load(getProfileGravatar(message.getUserid())).into(profileView);
        return super.getView(position, convertView, parent);
    }

    private String getProfileGravatar(String userid) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userid.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }


    class ViewHolder {
        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView body;
    }
}
