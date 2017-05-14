package com.tanya.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tanya.todoapp.data.DbController;
import com.tanya.todoapp.fragment.DatePickerFragment;
import com.tanya.todoapp.fragment.TimePickerFragment;
import com.tanya.todoapp.model.TodoItem;
import com.tanya.todoapp.model.TodoState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoNewItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    @Inject
    DbController dbController;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm");
    @Nullable
    @BindView(R.id.edit_title)
    TextView mTitleTextView;
    @Nullable
    @BindView(R.id.edit_description)
    TextView mDescriptionTextView;
    @Nullable
    @BindView(R.id.urgent_check_box)
    CheckBox mUrgentCheckBox;
    @Nullable
    @BindView(R.id.state_text)
    TextView mStateText;
    @Nullable
    @BindView(R.id.btn_date)
    Button mDateBtn;
    @Nullable
    @BindView(R.id.btn_time)
    Button mTimeBtn;
    TodoItem todoItem;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_todo_new_item);
        ButterKnife.bind(this);
        TodoApp.getAppComponent().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mTitleTextView = (TextView) findViewById(R.id.edit_title);
//        mDescriptionTextView = (TextView) findViewById(R.id.edit_description);
//        mUrgentCheckBox = (CheckBox) findViewById(R.id.urgent_check_box);
//        mStateText = (TextView) findViewById(R.id.state_text);
//        mDateBtn = (Button) findViewById(R.id.btn_date);
//        mTimeBtn = (Button) findViewById(R.id.btn_time);

        todoItem = (TodoItem) getIntent().getSerializableExtra("todoItem");
        if (todoItem.getTitle() != null) {
            showDate(todoItem);
        }
    }

    private void showDate(TodoItem item) {
        mTitleTextView.setText(item.getTitle());
        mDescriptionTextView.setText(item.getDescription());
        mUrgentCheckBox.setChecked(item.getUrgent());
        mStateText.setText(item.getState().getValue());
        mDateBtn.setText(formatterDate.format(item.getDue()));
        mTimeBtn.setText(formatterTime.format(item.getDue()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_item, menu);

        if (todoItem != null){
            menu.findItem(R.id.action_save).setTitle(R.string.action_update);
            menu.findItem(R.id.action_delete).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onSaveItem(todoItem);
                return true;

            case R.id.action_delete:
                onDeleteItem(todoItem);
                return true;

            default:
                super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    private void onSaveItem(TodoItem item){
        //todo: save to db new todoItem or update if exists
        item.setTitle(mTitleTextView.getText().toString());
        item.setDescription(mDescriptionTextView.getText().toString());
        item.setUrgent(mUrgentCheckBox.isChecked());
        item.setState(TodoState.Undone);
        String dateString = mDateBtn.getText() + " " + mTimeBtn.getText();
        Date dueDate;
        try {
            dueDate = formatter.parse(dateString);
            item.setDue(dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dbController.createOrUpdateItem(item);
        Toast.makeText(mContext, "Todo is added", Toast.LENGTH_LONG).show();
        finish();
    }

    private void onDeleteItem(TodoItem item) {
        dbController.deleteItem(item);
        Toast.makeText(mContext, "Todo is deleted", Toast.LENGTH_LONG).show();
        finish();
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        Date date = new Date(cal.getTimeInMillis());
        mDateBtn.setText(formatterDate.format(date));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        GregorianCalendar cal = new GregorianCalendar(year, month, day, hourOfDay, minute);
        Date date = new Date(cal.getTimeInMillis());
        mTimeBtn.setText(formatterTime.format(date));
    }
}
