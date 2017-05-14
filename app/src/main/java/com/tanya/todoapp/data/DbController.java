package com.tanya.todoapp.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.tanya.todoapp.TodoApp;
import com.tanya.todoapp.model.TodoItem;
import com.tanya.todoapp.model.TodoState;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class DbController {

    @Inject
    DatabaseHelper dbHelper;

    public DbController() {
        TodoApp.getAppComponent().inject(this);
    }

    public static void search() {

    }

    public List<TodoItem> getSortedItems() {
        List<TodoItem> items = new ArrayList<>();

        final Dao<TodoItem, Integer> todoDao;
        try {
            todoDao = dbHelper.getTodoDao();

            QueryBuilder<TodoItem, Integer> todoQb = todoDao.queryBuilder();
            PreparedQuery<TodoItem> preparedQuery = todoQb.orderBy(TodoEntry.COLUMN_URGENT, false).prepare();

            items = todoDao.query(preparedQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void createOrUpdateItem(TodoItem item) {
        try {
            final Dao<TodoItem, Integer> todoDao = dbHelper.getTodoDao();
            todoDao.createOrUpdate(item);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteItem(TodoItem item) {
        try {
            final Dao<TodoItem, Integer> todoDao = dbHelper.getTodoDao();
            todoDao.delete(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<TodoItem> markOverdueItems() {
        List<TodoItem> items;
        List<TodoItem> overdueItems = new ArrayList<>();

        final Dao<TodoItem, Integer> todoDao;
        try {
            todoDao = dbHelper.getTodoDao();

            QueryBuilder<TodoItem, Integer> todoQb = todoDao.queryBuilder();
            PreparedQuery<TodoItem> preparedQuery = todoQb.where().eq(TodoEntry.COLUMN_STATE, TodoState.Undone).prepare();
            items = todoDao.query(preparedQuery);

            Date currentDate = new Date();
            for (TodoItem item : items){
                if (item.getDue().before(currentDate)){
                    item.setState(TodoState.Overdue);
                    createOrUpdateItem(item);
                    overdueItems.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overdueItems;
    }
}
