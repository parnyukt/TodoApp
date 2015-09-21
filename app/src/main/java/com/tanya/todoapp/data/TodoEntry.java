package com.tanya.todoapp.data;

import android.provider.BaseColumns;

/**
 * Created by tatyana on 18.09.15.
 */
public class TodoEntry implements BaseColumns {
    public static final String TABLE_NAME = "todoList";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION= "description";
    public static final String COLUMN_URGENT= "urgent";
    public static final String COLUMN_DUE_DATE= "dueData";
    public static final String COLUMN_STATE= "state";
}
