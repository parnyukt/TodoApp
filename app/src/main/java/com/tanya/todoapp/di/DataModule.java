package com.tanya.todoapp.di;

import android.content.Context;

import com.tanya.todoapp.data.DatabaseHelper;
import com.tanya.todoapp.data.DbController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.j256.ormlite.android.apptools.OpenHelperManager.getHelper;

@Module
public class DataModule {
    @Provides
    @Singleton
    DbController dbController() {
        return new DbController();
    }

    @Provides
    @Singleton
    DatabaseHelper dbHelper(Context context) {
        return getHelper(context, DatabaseHelper.class);
    }

}
