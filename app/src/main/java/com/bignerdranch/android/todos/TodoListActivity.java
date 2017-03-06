package com.bignerdranch.android.todos;


import android.support.v4.app.Fragment;

/**
 * Created by Tan on 2/3/2017.
 */

public class TodoListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new TodoListFragment();
    }
}
