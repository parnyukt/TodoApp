package com.tanya.todoapp.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppUIModule {
    private final Context context;

    public AppUIModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context context() {
        return context;
    }
}
