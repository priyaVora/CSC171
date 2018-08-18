package com.example.priya.socialmediaapp.ChatApplication.Chat_model;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

/**
 * Created by Priya on 8/15/2018.
 */

public class Contact {
    private String contact_id;
    private String name;
    private String phone_number;
    private Bitmap profileImage;

    public Contact() {

    }

    public Contact(String name, String phonenumber, Bitmap profileImage) {
        this.name = name;
        this.phone_number = phonenumber;
        this.profileImage = profileImage;
    }


    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
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
