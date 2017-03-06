package com.bignerdranch.android.todos;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Tan on 2/3/2017.
 */

public class Todo {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mChecked;

    public Todo(){
        this(UUID.randomUUID());
    }

    //Add to-do constructor
    public Todo(UUID id){
        mId = id;
        mDate = new Date();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }
}
