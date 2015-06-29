package com.example.akshaypall.mycontacts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Akshay Pall on 25/03/2015.
 */
public class Contact implements Serializable {
    private String mName;
    public ArrayList<String> emails; //public! Security danger
    public ArrayList<String> phoneNumbers; //public! Security danger

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
