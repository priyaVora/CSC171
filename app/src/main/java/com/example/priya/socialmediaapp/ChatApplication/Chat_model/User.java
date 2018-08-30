package com.example.priya.socialmediaapp.ChatApplication.Chat_model;

import android.media.Image;

/**
 * Created by Priya on 8/15/2018.
 */

public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String phone_number;
    private Image profileImage;
    private String channel;

    public User() {

    }

    public User(String firstname, String lastname, String email, String phone_number, Image profileImage, String video_call_default_channel) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone_number = phone_number;
        this.profileImage = profileImage;
        this.channel = video_call_default_channel;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
