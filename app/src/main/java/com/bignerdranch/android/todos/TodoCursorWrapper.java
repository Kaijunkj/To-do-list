package com.bignerdranch.android.todos;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Tan on 3/3/2017.
 */

public class TodoCursorWrapper extends CursorWrapper {
    public TodoCursorWrapper(Cursor cursor){
        super(cursor);
    }

    //Adding getTodo() method to pull out relevant column data
    public Todo getTodo(){
        String uuidString = getString(getColumnIndex(TodoDbSchema.TodoTable.Cols.UUID));
        String title = getString(getColumnIndex(TodoDbSchema.TodoTable.Cols.DESCRIPTION));
        long date = getLong(getColumnIndex(TodoDbSchema.TodoTable.Cols.DATE));
        int isChecked = getInt(getColumnIndex(TodoDbSchema.TodoTable.Cols.CHECKED));

        Todo todo = new Todo((UUID.fromString(uuidString)));
        todo.setTitle(title);
        todo.setDate(new Date(date));
        todo.setChecked(isChecked != 0);

        return todo;
    }
}
