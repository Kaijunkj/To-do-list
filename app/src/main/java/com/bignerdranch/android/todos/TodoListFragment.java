package com.bignerdranch.android.todos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Created by Tan on 2/3/2017.
 */

public class TodoListFragment extends Fragment {

    private RecyclerView mTodoRecyclerView;
    private TodoAdapter mAdapter;
    private boolean mSubtitleVisible;

    //saving subtitle visibility
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    //Receiving menu callbacks
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //Setting up view for TodoListFragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //saving subtitle visibility
        if(savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    //Reloading the list in onResume()
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    //saving subtitle visibility
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    //Inflating a menu resource
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list, menu);

        //action to show or hide subtitle
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    //Respond to menu selection
    public boolean onOptionsItemSelected(MenuItem item){
        //switch between 2 cases (New to-do item or show subtitle)
        switch ( item.getItemId()){
            case R.id.menu_item_new_todo:
                Todo todo = new Todo();
                TodoLab.get(getActivity()).addTodo(todo);
                Intent intent = TodoPagerActivity.newIntent(getActivity(), todo.getId());
                startActivity(intent);
                return true;
            //Respond to show subtitle action item
            case R.id.menu_item_show_subtitle:
                //Update MenuItem
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Action toolbar subtitle
    private void updateSubtitle(){
        TodoLab todoLab = TodoLab.get(getActivity());
        int todoCount = todoLab.getTodo().size();
        String subtitle = getString(R.string.subtitle_format, todoCount);

        //Show or hide subtitle
        if(!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity =(AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    //Setting adapter
    private  void updateUI(){
        TodoLab todoLab = TodoLab.get(getActivity());
        List<Todo> todos = todoLab.getTodo();

        if(mAdapter == null) {
            mAdapter = new TodoAdapter(todos);
            mTodoRecyclerView.setAdapter(mAdapter);
        }else{
            //Call setTodos()
            mAdapter.setTodos(todos);
            mAdapter.notifyDataSetChanged();
        }

        //update to most recent state
        updateSubtitle();
    }

    public class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mCheckedBox;
        private Todo mTodo;

        public void bindTodo(Todo todo){
            mTodo = todo;

            //Bind views in TodoHolder
            mTitleTextView.setText((mTodo.getTitle()));
            mDateTextView.setText(mTodo.getDate().toString());
            mCheckedBox.setChecked(mTodo.isChecked());
        }

        public TodoHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //Find views in TodoHolder
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_todo_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_todo_date_text_view);
            mCheckedBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_todo_checked_box);
        }

        //Passing to-do activity
        @Override
        public void onClick(View v){
            Intent intent = TodoPagerActivity.newIntent(getActivity(), mTodo.getId());
            startActivity(intent);
        }
    }

    //Implement adapter
    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder>{

        private List<Todo> mTodos;

        public TodoAdapter(List<Todo> todos){
            mTodos = todos;
        }

        @Override
        //onCreateViewHolder called by RecyclerView when new View is needed
        public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //Inflating custom layout
            View view = layoutInflater.inflate(R.layout.list_item_todo, parent, false);
            return new TodoHolder(view);
        }

        @Override
        //Bind ViewHolder's View
        public void onBindViewHolder(TodoHolder holder, int position) {
            Todo todo = mTodos.get(position);
            //Connect CrimeAdapter to CrimeHolder
            holder.bindTodo(todo);
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }

        //swap out the todos display
        public void setTodos(List<Todo> todos){
            mTodos = todos;
        }
    }

}
