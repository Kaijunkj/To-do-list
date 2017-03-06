package com.bignerdranch.android.todos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Tan on 2/3/2017.
 */

public class TodoFragment extends Fragment {

    private static final String ARG_TODO_ID = "todo_id";
    private static final String DIALOG_DATE = "DialogDate";

    //Setting target fragment
    private static final int REQUEST_DATE = 0;

    private Todo mTodo;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mCheckedBox;
    private TextView mShowDate;
    private Button mClearText;
    private Button mRecord;

    //newInstance(UUID) method
    public static TodoFragment newInstance(UUID todoId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO_ID, todoId);

        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Getting todoId from arguments
        UUID todoId = (UUID) getArguments().getSerializable(ARG_TODO_ID);
        mTodo = TodoLab.get(getActivity()).getTodo(todoId);
    }

    //Push update
    @Override
    public void onPause(){
        super.onPause();

        TodoLab.get(getActivity()).updateTodo(mTodo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todo, container, false);

        //Wiring up with EditText Widget
        mTitleField = (EditText) v.findViewById(R.id.todo_title);
        mTitleField.setText(mTodo.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTodo.setTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        mDateButton = (Button)v.findViewById(R.id.todo_date);
        mDateButton.setText(mTodo.getDate().toString());
        //Show DialogFragment
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getActivity().getSupportFragmentManager();
                //Add call to newInstance
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTodo.getDate());
                dialog.setTargetFragment(TodoFragment.this, REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mCheckedBox = (CheckBox)v.findViewById(R.id.todo_checked);
        mCheckedBox.setChecked(mTodo.isChecked());
        mCheckedBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Set the total todo checked
                mTodo.setChecked(isChecked);
            }
        });

        mClearText = (Button)v.findViewById(R.id.clear_text);
        mClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mTitleField.setText("");
            }
        });

        mShowDate = (TextView)v.findViewById(R.id.todo_title_label);
        //mShowDate.setText(mTodo.getDate().toString());

        mRecord = (Button)v.findViewById(R.id.record_button);
        mRecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TodoListActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    //Responding to dialog
    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTodo.setDate(date);
            mDateButton.setText(mTodo.getDate().toString());
        }
    }
}
