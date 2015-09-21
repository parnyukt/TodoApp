package com.tanya.todoapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.tanya.todoapp.adapter.RecyclerItemClickListener;
import com.tanya.todoapp.adapter.TodoAdapter;
import com.tanya.todoapp.data.TodoDao;
import com.tanya.todoapp.data.TodoEntry;
import com.tanya.todoapp.model.TodoItem;
import com.tanya.todoapp.model.TodoState;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private RecyclerView mTodoRecycleView;
    private TodoAdapter mTodoAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mTodoRecycleView = (RecyclerView)findViewById(R.id.todo_recycler_view);

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
                        //// TODO
//                        CheckBox stateCheckBox = (CheckBox) view.findViewById(R.id.state_check);
//                        if (stateCheckBox.isChecked()){
//                            return;
//                        }

                        TodoItem item = mTodoAdapter.getItem(position);

                        Intent intent = new Intent(mContext, TodoNewItemActivity.class);
                        intent.putExtra("todoItem", item);
                        startActivity(intent);
                    }
                })
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<TodoItem> items = TodoDao.getSortedItems();

        mTodoAdapter.setData(items);
        mTodoAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        startActivity(intent);
    }

    private void onSearch(){

    }
}
