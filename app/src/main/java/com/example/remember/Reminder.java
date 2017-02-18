package com.example.remember;

public class Reminder {

    private int id;
    private String title;
    private String description;
    private long category_id;

    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;

    //region Constructors

    public Reminder() {

    }

    public Reminder(int id, String title, String description, long category_id, int minute, int hour, int day, int month, int year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category_id = category_id;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Reminder(String title, String description, long category_id, int minute, int hour, int day, int month, int year) {
        this.title = title;
        this.description = description;
        this.category_id = category_id;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Reminder(String title, long category_id, int minute, int hour, int day, int month, int year) {
        this.title = title;
        this.category_id = category_id;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
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

    public long getCategory() {
        return category_id;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
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

    public void setCategory(long category_id) {
        this.category_id = category_id;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
    //endregion

    //region Functions
    public String stringDate() {
        String strDay = (day >= 10) ? "" + day : "0" + day;
        String strMonth = (month >= 10) ? "" + month : "0" + month;
        String strHour = (hour >= 10) ? "" + hour : "0" + hour;
        String strMinute = (minute >= 10) ? "" + minute : "0" + minute;
        return strDay + "." + strMonth + "." + year + " " + strHour + ":" + strMinute;
    }
    //endregion
}
