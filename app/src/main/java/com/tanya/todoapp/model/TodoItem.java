package com.tanya.todoapp.model;

import java.util.Date;

/**
 * Created by tatyana on 16.09.15.
 */
public class TodoItem {

    public TodoItem(long id, String title, TodoState state){
        setId(id);
        setTitle(title);
        setState(state);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public Date getDueDatetime() {
        return dueDatetime;
    }

    public void setDueDatetime(Date dueDatetime) {
        this.dueDatetime = dueDatetime;
    }

    public TodoState getState() {
        return state;
    }

    public void setState(TodoState state) {
        this.state = state;
    }

    private long id;
    private String title; // required
    private String description;
    private Boolean urgent;
    private Date dueDatetime;
    private TodoState state;

}
