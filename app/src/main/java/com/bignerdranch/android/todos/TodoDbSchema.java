package com.bignerdranch.android.todos;

import android.support.v7.util.SortedList;

import java.util.Date;

/**
 * Created by Tan on 3/3/2017.
 */

public class TodoDbSchema {
    public static final class TodoTable {
        public static final String TITLE = "todo";

        //Define table columns
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String CHECKED = "checked";
        }
    }
}
