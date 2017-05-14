package com.tanya.todoapp;

import android.app.Application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.tanya.todoapp.data.DatabaseHelper;
import com.tanya.todoapp.di.AppComponent;
import com.tanya.todoapp.di.AppUIModule;
import com.tanya.todoapp.di.DaggerAppComponent;
import com.tanya.todoapp.di.DataModule;

import javax.inject.Inject;

public class TodoApp extends Application {

    private static TodoApp sInstance;
    private static AppComponent appComponent;
    @Inject
    DatabaseHelper mDbHelper;

    public TodoApp() {
        super();
        sInstance = this;
    }

    public static TodoApp get() {
        return sInstance;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appUIModule(new AppUIModule(sInstance))
                    .dataModule(new DataModule())
                    .build();
        }
        return appComponent;
    }

//    public DatabaseHelper getDbHelper() { return mDbHelper; }


//    private static TodoApp instance = new TodoApp();
//    private static AppComponent appComponent;
//
//    public static TodoApp getInstance() {
//        return sInstance;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = getAppComponent();
//        mDbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
//        AppComponent.inject(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (mDbHelper != null) {
            OpenHelperManager.releaseHelper();
            mDbHelper = null;
        }
    }
}
