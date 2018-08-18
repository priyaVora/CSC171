package com.example.priya.socialmediaapp.ChatApplication.Chat_model;

import android.util.Log;

import java.util.List;

/**
 * Created by Priya on 8/18/2018.
 */

public class Alphatical_Order {

    private Contact temp_contact;
    public List<Contact> sort(List<Contact> listOfContacts) {
        for (int i = 0; i < listOfContacts.size(); i++)
        {
            for (int j = i + 1; j < listOfContacts.size(); j++)
            {
                if (listOfContacts.get(i).getName().compareTo(listOfContacts.get(j).getName())>0)
                {
                    temp_contact = listOfContacts.get(i);
                    listOfContacts.set(i, listOfContacts.get(j));
                    listOfContacts.set(j,temp_contact);
                }
            }
        }

        for (int i = 0; i < listOfContacts.size() ; i++) {
            Log.d("ORDERED",listOfContacts.get(i).getName());
        }
        return listOfContacts;
    }
}
