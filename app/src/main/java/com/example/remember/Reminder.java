package com.example.remember;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Reminder implements Serializable {

    private int id;
    private String title;
    private String description;
    private int category_id;

    private long time;

    //region Constructors

    public Reminder() {

    }

    public Reminder(int id, String title, String description, int category_id, long time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category_id = category_id;
        this.time = time;
    }

    public Reminder(String title, String description, int category_id, long time) {
        this.title = title;
        this.description = description;
        this.category_id = category_id;
        this.time = time;
    }

    public Reminder(String title, int category_id, long time) {
        this.title = title;
        this.category_id = category_id;
        this.time = time;
    }
    //endregion

    //region Getters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory() {
        return category_id;
    }

    public long getTime() {
        return time;
    }
    //endregion

    //region Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(int category_id) {
        this.category_id = category_id;
    }

    public void setTime(long time) {
        this.time = time;
    }
    //endregion

    //region Functions
    public String stringDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(cal.getTime());
    }
    //endregion
}
