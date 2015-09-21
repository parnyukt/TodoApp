package com.tanya.todoapp;

import android.app.Application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.tanya.todoapp.data.DatabaseHelper;

/**
 * Created by tatyana on 21.09.15.
 */
public class TodoApp extends Application {

    private static TodoApp sInstance;
    private DatabaseHelper mDbHelper = null;

    public static TodoApp get() { return sInstance; }

    public TodoApp() {
        super();
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mDbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

    }

    public DatabaseHelper getDbHelper() { return mDbHelper; }


    @Override
    public void onTerminate() {
        super.onTerminate();

        if (mDbHelper != null) {
            OpenHelperManager.releaseHelper();
            mDbHelper = null;
        }
    }
}
