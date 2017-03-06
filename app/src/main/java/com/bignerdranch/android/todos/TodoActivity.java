package com.bignerdranch.android.todos;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class TodoActivity extends SingleFragmentActivity{

    private static final String EXTRA_TODO_ID = "com.bignerdranch.android.todo.todo_id";


    //Intent method
    public static Intent newIntent(Context packageContext, UUID todo_id){
        Intent intent = new Intent(packageContext,TodoActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todo_id);
        return intent;
    }

    //Using newInstance(UUID)
    @Override
    protected Fragment createFragment(){
        UUID expenseId = (UUID) getIntent().getSerializableExtra(EXTRA_TODO_ID);
        return TodoFragment.newInstance(expenseId);
    }

}
