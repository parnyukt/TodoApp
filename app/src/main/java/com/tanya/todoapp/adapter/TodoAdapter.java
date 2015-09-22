package com.tanya.todoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tanya.todoapp.R;
import com.tanya.todoapp.TodoNewItemActivity;
import com.tanya.todoapp.model.TodoItem;
import com.tanya.todoapp.model.TodoState;

import java.util.List;

/**
 * Created by tparnyuk on 17.09.15.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoItem> records;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mStateCheckBox;
        public TextView mTitleTextView;


        public ViewHolder(View view) {
            super(view);
            mStateCheckBox = (CheckBox) view.findViewById(R.id.state_check);
            mTitleTextView = (TextView) view.findViewById(R.id.title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TodoAdapter(List<TodoItem> dataset) {
        records = dataset;
    }

    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_todo, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TodoItem item = records.get(position);

        viewHolder.mStateCheckBox.setChecked(item.getState().equals(TodoState.Done));
        viewHolder.mTitleTextView.setText(item.getTitle());

        disableTouchTheft(viewHolder.mStateCheckBox);

    }

    public static void disableTouchTheft(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public TodoItem getItem(int position){
        return records.get(position);
    }

    public void setData(List<TodoItem> data){
        records.clear();
        records.addAll(data);
    }
}
