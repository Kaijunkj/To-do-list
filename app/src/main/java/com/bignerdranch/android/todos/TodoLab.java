package com.bignerdranch.android.todos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tan on 2/3/2017.
 */

public class TodoLab {

    public static TodoLab sTodoLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TodoLab get(Context context){
        if(sTodoLab == null){
            sTodoLab = new TodoLab(context);
        }
        return sTodoLab;
    }

    private TodoLab(Context context){
        //OpenSQLiteDatabase
        mContext = context.getApplicationContext();
        mDatabase = new TodoBaseHelper(mContext).getWritableDatabase();
    }

    public void addTodo(Todo e){

        //Insert row
        ContentValues values = getContentValues(e);

        mDatabase.insert(TodoDbSchema.TodoTable.TITLE, null, values);
    }


    public List<Todo> getTodo(){
        //return to-do list
        List<Todo> todos = new ArrayList<>();

        TodoCursorWrapper cursor = queryTodo(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                todos.add(cursor.getTodo());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return todos;
    }

    public Todo getTodo(UUID id) {
        TodoCursorWrapper cursor = queryTodo(TodoDbSchema.TodoTable.Cols.UUID + " = ?", new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTodo();
        } finally {
            cursor.close();
        }
    }

    //Update to-do
    public void updateTodo(Todo todo){
        String uuidString = todo.getId().toString();
        ContentValues values = getContentValues(todo);

        mDatabase.update(TodoDbSchema.TodoTable.TITLE, values, TodoDbSchema.TodoTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    //Create ContentValues
    private static ContentValues getContentValues(Todo todo){
        ContentValues values = new ContentValues();
        values.put(TodoDbSchema.TodoTable.Cols.UUID, todo.getId().toString());
        values.put(TodoDbSchema.TodoTable.Cols.DESCRIPTION, todo.getTitle());
        values.put(TodoDbSchema.TodoTable.Cols.DATE, todo.getDate().getTime());
        values.put(TodoDbSchema.TodoTable.Cols.CHECKED, todo.isChecked()?1:0);

        return values;
    }

    //Vending cursor wrapper
    private TodoCursorWrapper queryTodo(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                TodoDbSchema.TodoTable.TITLE,
                null, //Colums = null selects all colums
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new TodoCursorWrapper(cursor);
    }
}
