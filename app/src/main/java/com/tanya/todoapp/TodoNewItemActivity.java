package com.tanya.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.tanya.todoapp.fragment.DatePickerFragment;
import com.tanya.todoapp.fragment.TimePickerFragment;
import com.tanya.todoapp.model.TodoItem;
import com.tanya.todoapp.model.TodoState;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TodoNewItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm");

    private Context mContext;
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private CheckBox mUrgentCheckBox;
    private Button mDateBtn;
    private Button mTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_todo_new_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitleTextView = (TextView) findViewById(R.id.edit_title);
        mDescriptionTextView = (TextView) findViewById(R.id.edit_description);
        mUrgentCheckBox = (CheckBox) findViewById(R.id.urgent_check_box);
        mDateBtn = (Button) findViewById(R.id.btn_date);
        mTimeBtn = (Button) findViewById(R.id.btn_time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onSaveItem();
                return true;

            default:
                super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    private void onSaveItem(){
        //todo: save to db new item or update if exists
        TodoItem todoDetails = new TodoItem();
        todoDetails.setTitle(mTitleTextView.getText().toString());
        todoDetails.setDescription(mDescriptionTextView.getText().toString());
        todoDetails.setUrgent(mUrgentCheckBox.isChecked());
        todoDetails.setState(TodoState.Undone);
        String dateString = mDateBtn.getText() + " " + mTimeBtn.getText();
        Date dueDate = null;
        try {
            dueDate = formatter.parse(dateString);
            todoDetails.setDueUtc(dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            // This is how, a reference of DAO object can be done
            final Dao<TodoItem, Integer> todoDao = TodoApp.get().getDbHelper().getTodoDao();
            todoDao.create(todoDetails);

            Toast.makeText(mContext, "Todo is added", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
