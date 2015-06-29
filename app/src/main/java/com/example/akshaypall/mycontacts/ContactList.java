package com.example.akshaypall.mycontacts;

import java.util.ArrayList;

/**
 * Created by Akshay Pall on 15/05/2015.
 */
public class ContactList extends ArrayList<Contact> {
    private static ContactList sInstance = null;

    private ContactList(){}

    public static ContactList getInstance() {
        if (sInstance == null) sInstance = new ContactList();
        return sInstance;
    }
}
