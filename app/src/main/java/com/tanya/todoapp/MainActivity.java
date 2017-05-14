package com.tanya.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tanya.todoapp.adapter.RecyclerItemClickListener;
import com.tanya.todoapp.adapter.TodoAdapter;
import com.tanya.todoapp.data.DbController;
import com.tanya.todoapp.di.AppComponent;
import com.tanya.todoapp.model.TodoItem;
import com.tanya.todoapp.receiver.TodoAlarmBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String CUSTOM_INTENT = "com.tanya.todoapp.CUSTOM_INTENT";
    @Inject
    DbController dbController;
    @BindView(R.id.todo_recycler_view)
    RecyclerView mTodoRecycleView;
    private Context mContext;
    //    private RecyclerView mTodoRecycleView;
    private TodoAdapter mTodoAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private AppComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);


        TodoApp.getAppComponent().inject(this);
//        MainActivity_MembersInjector.inject(this);
//        .inject(this);

//        mTodoRecycleView = (RecyclerView)findViewById(R.id.todo_recycler_view);

        mTodoRecycleView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mTodoRecycleView.setLayoutManager(mLayoutManager);

        mTodoAdapter = new TodoAdapter(new ArrayList<TodoItem>());
        mTodoRecycleView.setAdapter(mTodoAdapter);

        mTodoRecycleView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        TodoItem item = mTodoAdapter.getItem(position);

                        Intent intent = new Intent(mContext, TodoNewItemActivity.class);
                        intent.putExtra("todoItem", item);
                        startActivity(intent);
                    }
                })
        );

        dbController.markOverdueItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<TodoItem> items = dbController.getSortedItems();

        mTodoAdapter.setData(items);
        mTodoAdapter.notifyDataSetChanged();

        // Cancel the alarm when we are in the app.
        TodoAlarmBroadcastReceiver todoAlarm = new TodoAlarmBroadcastReceiver();
        todoAlarm.cancelAlarm(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        // setup repeating alarm while the app is stopped.
        TodoAlarmBroadcastReceiver todoAlarm = new TodoAlarmBroadcastReceiver();
        todoAlarm.setupAlarm(this);

        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                onAddNewItem();
                return true;

            case R.id.action_search:
                onSearch();
                return true;

            default:
                super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    private void onAddNewItem(){
        Intent intent = new Intent(mContext, TodoNewItemActivity.class);
        intent.putExtra("todoItem", new TodoItem());
        startActivity(intent);
    }

    private void onSearch(){

    }

}
