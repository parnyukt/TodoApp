package com.tanya.todoapp.di;

import android.content.Context;

import com.tanya.todoapp.MainActivity;
import com.tanya.todoapp.TodoNewItemActivity;
import com.tanya.todoapp.adapter.TodoAdapter;
import com.tanya.todoapp.data.DbController;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, AppUIModule.class})
public interface AppComponent {
    void inject(MainActivity activity);

    //    void inject(TodoApp app);
    void inject(TodoNewItemActivity activity);

    void inject(TodoAdapter adapter);

    void inject(DbController controller);

    Context context();
}

