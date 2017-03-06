package com.bignerdranch.android.todos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tan on 3/3/2017.
 */

public class TodoBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "todoBase.db";

    public TodoBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    //Create to-do Database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TodoDbSchema.TodoTable.TITLE + "(" +
                " _id integer primary key autoincrement, " + TodoDbSchema.TodoTable.Cols.UUID + ", " + TodoDbSchema.TodoTable.Cols.DESCRIPTION + ", " + TodoDbSchema.TodoTable.Cols.DATE + ", " + TodoDbSchema.TodoTable.Cols.CHECKED + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
