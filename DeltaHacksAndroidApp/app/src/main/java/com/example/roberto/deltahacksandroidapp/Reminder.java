package com.example.roberto.deltahacksandroidapp;

/**
 * Created by Roberto on 2016-01-16.
 */
public class Reminder {

    private String mTitle,mDescription;

    public Reminder(String title, String description){
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
