package com.example.priya.socialmediaapp.ChatApplication.Chat_model;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

/**
 * Created by Priya on 8/15/2018.
 */

public class Contact {

    private String name;
    private String phone_number;
    private String profileImage;

    public Contact() {

    }

    public Contact(String name, String phonenumber, String profileImage) {
        this.name = name;
        this.phone_number = phonenumber;
        this.profileImage = profileImage;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


}
