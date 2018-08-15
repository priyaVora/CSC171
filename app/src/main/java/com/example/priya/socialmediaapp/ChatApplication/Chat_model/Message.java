package com.example.priya.socialmediaapp.ChatApplication.Chat_model;

import java.util.List;

/**
 * Created by Priya on 8/15/2018.
 */

public class Message {
    private List<User> listOfUsers;
    private String messageId;
    private String timestamp;
    public String message_body;
    public String userid;

    public Message() {

    }

    public Message(List<User> listOfUsers, String messageId, String timestamp, String message_body, String userid) {
        this.listOfUsers = listOfUsers;
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.message_body = message_body;
        this.userid = userid;
    }

    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(List<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
